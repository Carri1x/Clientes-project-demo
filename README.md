# Base de Datos PostgreSQL

### Configuración

- **Nombre del contenedor:** `demo-clientes-postrges`
- **Contraseña:** `root`
- **Nombre de la base de datos:** `clientesdb`
- **Puerto:** `5432:5432`
- **Versión de Postgres:** `16`

---

## Especificación de Dos o Más Bases de Datos

### 🧠 Pregunta

> Vale, ya tengo el `ClienteRepository` (interfaz) y el `ClienteService` en el proyecto. Ahora una pregunta:
>
> ¿Cómo sabe la aplicación qué base de datos debe utilizar?
>
> Porque si tuviera otra, ¿cómo sabría cuál usar?
>
> Supongo que con esta nomenclatura: `@Repository(Postgres)` y `@Repository(MongoDB)`, y que irá al archivo `application.properties` a averiguar cuál de las dos utilizar, ¿no?

---

### 🤖 Respuesta de Copilot

¡Muy buena pregunta!

Spring Boot sabe qué base de datos usar gracias a la configuración en el archivo `application.properties` (o `application.yml`).

Por ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clientesdb
spring.datasource.username=postgres
spring.datasource.password=admin123
spring.datasource.driver-class-name=org.postgresql.Driver
```
---

# Uso de múltiples bases de datos simultáneamente en Spring Boot
Cuando quieres usar más de una base de datos en tu app (por ejemplo, PostgreSQL y MongoDB), necesitas configurar manualmente ambas conexiones y especificar claramente cuál usar en cada caso. Aquí te explico los conceptos y cómo hacerlo:

1. ¿Qué es un DataSource?
En Spring Boot, un DataSource representa la configuración para conectar a una base de datos SQL (como PostgreSQL).

Incluye detalles como URL, usuario, contraseña y driver.

Normalmente tienes un único DataSource, pero para múltiples bases de datos, tendrás que definir varios beans DataSource.

2. ¿Qué es @Qualifier?
Cuando hay más de un bean del mismo tipo (por ejemplo, varios DataSource), Spring no sabe cuál inyectar.

Usas @Qualifier("nombreDelBean") para indicarle explícitamente a Spring cuál bean debe inyectar.

Ejemplo:

java
Copiar
Editar
@Autowired
@Qualifier("postgresDataSource")
private DataSource postgresDs;

@Autowired
@Qualifier("mongoDataSource")
private DataSource mongoDs;
Con esto, especificas que quieres inyectar el DataSource con nombre postgresDataSource o mongoDataSource.

3. Configuración de dos DataSource (PostgreSQL y MongoDB)
PostgreSQL (DataSource SQL)
java
Copiar
Editar
@Configuration
public class PostgresConfig {

    @Bean(name = "postgresDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Aquí puedes añadir EntityManagerFactory y TransactionManager relacionados
}
Y en application.properties:

properties
Copiar
Editar
spring.datasource.postgres.url=jdbc:postgresql://localhost:5432/clientesdb
spring.datasource.postgres.username=postgres
spring.datasource.postgres.password=admin123
spring.datasource.postgres.driver-class-name=org.postgresql.Driver
MongoDB (cliente distinto, no DataSource)
java
Copiar
Editar
@Configuration
@EnableMongoRepositories(basePackages = "com.tuempresa.tuapp.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "clientesdb";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/clientesdb");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }
}
Tu repositorio Mongo sería algo así:

java
Copiar
Editar
@Repository
public interface ClienteMongoRepository extends MongoRepository<Cliente, String> {
    // consultas Mongo
}
4. Inyección con @Qualifier para usar ambos repositorios
java
Copiar
Editar
@Service
public class ClienteService {

    private final ClienteRepository postgresRepository;
    private final ClienteMongoRepository mongoRepository;

    public ClienteService(
        @Qualifier("clientePostgresRepository") ClienteRepository postgresRepository,
        @Qualifier("clienteMongoRepository") ClienteMongoRepository mongoRepository) {
        
        this.postgresRepository = postgresRepository;
        this.mongoRepository = mongoRepository;
    }

    // métodos que elijan usar uno u otro repositorio según la lógica de negocio
}
Resumen rápido
Concepto	Función
DataSource	Configura conexión SQL (PostgreSQL)
MongoClient	Configura conexión MongoDB
@Primary	Marca el DataSource “por defecto” cuando hay varios
@Qualifier("name")	Selecciona explícitamente qué bean inyectar cuando hay múltiples del mismo tipo

---

# Tipos de HTTPSTATUS que existen y para que sirven
🔵 1xx – Informativos
Estas respuestas indican que la solicitud fue recibida y el proceso continúa.

Código	Significado	Uso común
- 100 Continue	El servidor recibió los encabezados, el cliente puede continuar enviando el cuerpo.	Muy usado en solicitudes con Expect: - - - 100-continue.
- 101 Switching Protocols	El cliente pidió un cambio de protocolo y el servidor lo acepta.	Por ejemplo, de HTTP a WebSocket.
- 102 Processing (WebDAV)	El servidor ha recibido la solicitud y la está procesando, pero no hay respuesta aún.	Evita timeouts en solicitudes largas.

🟢 2xx – Éxito
La solicitud fue recibida, comprendida y aceptada correctamente.

Código	Significado	Uso común
- 200 OK	Todo está bien, el contenido se devuelve normalmente.	Respuesta más común.
- 201 Created	Recurso creado exitosamente.	POST que crea recursos (ej. nueva cuenta).
- 202 Accepted	Solicitud aceptada, pero aún no procesada.	Procesamiento en segundo plano.
- 204 No Content	Éxito, pero sin contenido para devolver.	Respuestas a DELETE o PUT sin necesidad de contenido.

🟡 3xx – Redirección
La solicitud requiere más acciones por parte del cliente, normalmente redirección.

Código	Significado	Uso común
- 301 Moved Permanently	Redirección permanente.	SEO y URLs cambiadas.
- 302 Found	Redirección temporal.	Navegadores son redirigidos.
- 303 See Other	El recurso se encuentra en otra URL, usar GET.	Después de un POST, por ejemplo.
- 304 Not Modified	El recurso no ha cambiado desde la última solicitud.	Usado con If-Modified-Since.
- 307 Temporary Redirect	Redirección temporal, misma solicitud debe usarse.	Como 302, pero más estricto.
- 308 Permanent Redirect	Redirección permanente, mantiene método.	Similar a 301 pero con método fijo.

🔴 4xx – Error del Cliente
El cliente hizo una solicitud incorrecta.

Código	Significado	Uso común
- 400 Bad Request	Solicitud mal formada.	JSON inválido, parámetros erróneos.
- 401 Unauthorized	Falta autenticación.	Requiere login/token.
- 403 Forbidden	Autenticado, pero sin permisos.	Prohibido acceder aunque esté logueado.
- 404 Not Found	Recurso no encontrado.	URL errónea o inexistente.
- 405 Method Not Allowed	Método HTTP no permitido.	Hacer POST en un endpoint solo GET.
- 409 Conflict	Conflicto con estado actual del recurso.	Por ejemplo, creación duplicada.
- 422 Unprocessable Entity	Datos válidos pero lógicamente incorrectos.	Errores de validación. Muy usado con APIs REST.
- 429 Too Many Requests	Límite de solicitudes excedido.	Protección contra spam o DDoS.

🔴🔵 5xx – Error del Servidor
El servidor falló al procesar la solicitud, aunque fue válida.

Código	Significado	Uso común
- 500 Internal Server Error	Error genérico del servidor.	Fallos no controlados.
- 501 Not Implemented	Función no implementada.	Método HTTP no soportado, por ejemplo.
- 502 Bad Gateway	El servidor actuó como proxy y recibió una respuesta inválida.	Problemas entre servidores.
- 503 Service Unavailable	Servidor sobrecargado o en mantenimiento.	Downtime o carga excesiva.
- 504 Gateway Timeout	El servidor no recibió respuesta a tiempo de otro servidor.	Problemas en la cadena de servidores.

## Caso de que el usuario ya esté registrado y haya intentado registrarse
    1. En vez de devolver un objeto `User` con un mensaje en el campo nombre, lo ideal es devolver un objeto de error estructurado, por ejemplo:
    ```
    // Puedes crear una clase simple para respuestas de error
        public class ApiError {
            private String message;
            public ApiError(String message) { this.message = message; }
            public String getMessage() { return message; }
        }
    ```
    2. *Y en el controlador*:
    ```
    if(userService.emailExists(user.getEmail())){
        ApiError error = new ApiError("El email ya está registrado.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    ```
    3. *En el frontend Angular*
    Cuando recibas un error con código 409, puedes mostrar el mensaje al usuario:
    ```
    this.authService.register(user).subscribe({
        next: (res) => { /* registro exitoso */ },
        error: (err) => {
            if (err.status === 409) {
                alert(err.error.message); // Muestra el mensaje del backend
            }
        }
    });
    ```
---
# Distribución del proyecto Angular de forma mantenible
src/
└── app/
    ├── core/                  # Servicios singleton, guards, interceptors, config global
    │   ├── services/
    │   │   └── auth.service.ts
    │   ├── guards/
    │   │   └── auth.guard.ts
    │   ├── interceptors/
    │   │   └── token.interceptor.ts
    │   └── core.module.ts
    ├── shared/                # Componentes, pipes, directivas y modelos reutilizables
    │   ├── components/
    │   │   └── loading-spinner/
    │   │       ├── loading-spinner.component.ts
    │   │       └── loading-spinner.component.html
    │   ├── models/
    │   │   ├── user.model.ts
    │   │   └── cliente.model.ts
    │   ├── pipes/
    │   │   └── capitalize.pipe.ts
    │   └── shared.module.ts
    ├── features/              # Cada dominio funcional tiene su propia carpeta
    │   ├── clientes/
    │   │   ├── clientes-list/
    │   │   │   ├── clientes-list.component.ts
    │   │   │   ├── clientes-list.component.html
    │   │   │   └── clientes-list.component.css
    │   │   ├── cliente-detail/
    │   │   │   ├── cliente-detail.component.ts
    │   │   │   ├── cliente-detail.component.html
    │   │   │   └── cliente-detail.component.css
    │   │   └── clientes-routing.module.ts
    │   ├── login/
    │   │   ├── login.component.ts
    │   │   ├── login.component.html
    │   │   └── login.component.css
    │   └── ...otros features...
    ├── app-routing.module.ts  # Rutas principales
    ├── app.component.ts
    ├── app.component.html
    └── app.module.ts