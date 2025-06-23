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
>
> Vale, ahora vamos a hacer algo en el `HomeController` para llevar a cabo esto que hemos hecho. Piensa que quiero que devuelva **JSON** porque luego el frontend con **Angular** va a consumir nuestra **API REST**.

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

