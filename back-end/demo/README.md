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
