# Plataforma de Regularización Académica - Backend

Es una plataforma educativa que permite administrar materias, temas, recursos didácticos y cuestionarios. Los alumnos pueden consultar materiales, resolver cuestionarios y recibir resultados por correo electrónico.

## Estado del despliegue

El backend se encuentra desplegado en Azure App Service y puede probarse públicamente desde Swagger UI.La base de datos utilizada es YugabyteDB en la nube.Los archivos se almacenan en Cloudinary y los correos automáticos se envían mediante Gmail SMTP.

## URL pública del API

### Backend con autenticación JWT

**URL base:**

```txt
https://proyecto-web-auth-api-d7hxcqbdgucnfad0.eastus2-01.azurewebsites.net
```

**Swagger UI:**

```txt
https://proyecto-web-auth-api-d7hxcqbdgucnfad0.eastus2-01.azurewebsites.net/documentacion/swagger-ui/index.html
```



## Repositorio

```txt
https://github.com/gio1313130/Proyecto_web
```

---

## Versión con autenticación JWT y soporte para Angular

Se agregó una nueva versión del backend con autenticación basada en JWT para facilitar la integración con el frontend en Angular.

Características principales:

```txt
- Login mediante POST /api/auth/login.
- Validación de usuario y contraseña con BCrypt.
- Generación de token JWT.
- Endpoint GET /api/auth/me para recuperar el usuario autenticado.
- Roles ALUMNO y ADMIN.
- Protección de endpoints por rol.
- Swagger con botón Authorize para probar endpoints protegidos.
- CORS configurado para permitir Angular local en http://localhost:4200.
- CORS configurable mediante variable de entorno para la URL final del frontend.
```

Flujo general de autenticación:

```txt
Angular
 ↓
POST /api/auth/login
 ↓
Backend valida correo y contraseña
 ↓
Backend devuelve token JWT y datos del usuario
 ↓
Angular guarda el token
 ↓
Angular envía Authorization: Bearer TOKEN en cada petición protegida
 ↓
Backend valida el token y autoriza según el rol
```

El cierre de sesión se maneja desde Angular eliminando el token almacenado localmente. No se requiere un endpoint especial de logout para esta versión.

Roles principales:

| Rol | Permisos generales |
|---|---|
| ALUMNO | Consultar materias, temas, recursos, cuestionarios, resolver cuestionarios y consultar historial. |
| ADMIN | Puede realizar todo lo del alumno y además crear, actualizar y eliminar contenido académico. |

---

## Arquitectura general

El backend está construido con Spring Boot y expone una API REST documentada mediante Swagger/OpenAPI. La información principal se almacena en una base de datos relacional en YugabyteDB. Los archivos educativos se suben a Cloudinary y únicamente se guarda en la base de datos la URL y metadata del recurso. El envío de correos automáticos se realiza mediante SMTP de Gmail.

Flujo general:

```txt
Cliente / Swagger / Futuro Frontend
        ↓
API REST Spring Boot
        ↓
Servicios internos:
- Gestión de usuarios
- Gestión de materias
- Gestión de temas
- Gestión de cuestionarios
- Gestión de preguntas y opciones
- Resolución de cuestionarios
- Gestión de intentos
- Gestión de recursos educativos
- Envío de correos automáticos
        ↓
Servicios externos:
- YugabyteDB
- Cloudinary
- Gmail SMTP
- Azure App Service
```

---

## Tecnologías utilizadas

| Tecnología               | Uso                                                 |
| ------------------------ | --------------------------------------------------- |
| Java 17                  | Lenguaje principal del backend                      |
| Spring Boot              | Framework principal                                 |
| Spring Web MVC           | Creación de API REST                                |
| Spring Data JPA          | Persistencia con repositorios                       |
| Hibernate                | ORM para mapeo entidad-relación                     |
| PostgreSQL Driver        | Conexión con YugabyteDB                             |
| YugabyteDB               | Base de datos relacional en la nube                 |
| Cloudinary               | Almacenamiento de archivos, PDFs, videos e imágenes |
| Gmail SMTP               | Envío automático de correos                         |
| Springdoc OpenAPI        | Documentación automática con Swagger                |
| Spring Security          | Seguridad del backend, filtros, roles y JWT          |
| JWT                      | Autenticación mediante tokens                        |
| CORS                     | Permite conexión del frontend Angular                |
| BCrypt                   | Cifrado de contraseñas                              |
| Docker                   | Contenerización del backend                         |
| Azure Container Registry | Almacenamiento de imagen Docker                     |
| Azure App Service        | Despliegue público del backend                      |

---

## Servicios externos integrados

### YugabyteDB

La base de datos relacional se encuentra desplegada en YugabyteDB. El backend se conecta mediante el driver de PostgreSQL y utiliza JPA/Hibernate para mapear las entidades.

Tablas principales:

```txt
usuario
materia
tema
cuestionario
pregunta
opcion
intento
recurso
```

Relaciones principales:

```txt
Materia 1 ─── N Tema
Tema 1 ─── N Cuestionario
Tema 1 ─── N Recurso
Cuestionario 1 ─── N Pregunta
Pregunta 1 ─── N Opcion
Usuario 1 ─── N Intento
Cuestionario 1 ─── N Intento
```

---

### Cloudinary

Cloudinary se utiliza para almacenar recursos educativos como PDFs, documentos, imágenes y videos.

Endpoint principal:

```http
POST /api/recursos/upload
```

Formato de envío:

```txt
multipart/form-data
```

Tipos manejados:

| Tipo de recurso  | resource_type en Cloudinary |
| ---------------- | --------------------------- |
| PDF / documentos | raw                         |
| Videos           | video                       |
| Imágenes         | image                       |

El backend guarda en la tabla `recurso`:

```txt
url
public_id
resource_type
tipo_recurso
titulo_recurso
autor
descripcion_recurso
id_tema
fecha_publicacion
```

El frontend solo necesita usar la `url` devuelta por el backend para abrir, mostrar o descargar el recurso.

---

### Gmail SMTP

El backend envía correos automáticos mediante SMTP de Gmail.

Casos implementados:

| Evento                     | Acción                              |
| -------------------------- | ----------------------------------- |
| Registro de usuario        | Envía correo de bienvenida          |
| Resolución de cuestionario | Envía correo con resultado obtenido |

Endpoints relacionados:

```http
POST /api/usuarios
POST /api/intentos/resolver
```

El envío de correos fue validado en el despliegue de Azure App Service.

---

## Seguridad

La versión nueva del backend implementa autenticación mediante JWT y control de acceso por roles.

Consideraciones implementadas:

```txt
- Las contraseñas de usuario se almacenan cifradas con BCrypt.
- El login se realiza mediante POST /api/auth/login.
- El backend devuelve un token JWT con datos del usuario autenticado.
- El token debe enviarse en el header Authorization con formato Bearer TOKEN.
- El endpoint GET /api/auth/me permite validar la sesión activa.
- La entidad Usuario contempla roles ALUMNO y ADMIN.
- Los endpoints administrativos quedan restringidos al rol ADMIN.
- Los endpoints de consulta y resolución quedan disponibles para ALUMNO y ADMIN.
- Swagger permite probar endpoints protegidos mediante el botón Authorize.
```

Distribución general de permisos:

| Tipo de endpoint | Acceso |
|---|---|
| Login | Público |
| Registro de usuario | Público |
| Swagger/OpenAPI | Público |
| Consulta de materias, temas, recursos y cuestionarios | ALUMNO / ADMIN |
| Resolver cuestionarios | ALUMNO / ADMIN |
| Historial de intentos | ALUMNO / ADMIN |
| CRUD de contenido académico | ADMIN |
| Preguntas y opciones administrativas | ADMIN |

---
## Credenciales
Las credenciales de YugabyteDB, Cloudinary y Gmail SMTP no se incluyen en el repositorio por seguridad.  
Deben configurarse mediante variables de entorno en local o en Azure App Service.

## Variables de entorno

El proyecto utiliza variables de entorno para evitar subir credenciales sensibles al repositorio.

Variables necesarias:

| Variable                | Descripción                       |
| ----------------------- | --------------------------------- |
| `DATABASE_URL`          | URL JDBC de YugabyteDB            |
| `DATABASE_USERNAME`     | Usuario de la base de datos       |
| `DATABASE_PASSWORD`     | Contraseña de la base de datos    |
| `MAIL_USER`             | Correo Gmail usado para SMTP      |
| `MAIL_PASSWORD`         | Contraseña de aplicación de Gmail |
| `CLOUDINARY_NAME`       | Cloud name de Cloudinary          |
| `CLOUDINARY_API_KEY`    | API Key de Cloudinary             |
| `CLOUDINARY_API_SECRET` | API Secret de Cloudinary          |
| `JWT_SECRET`            | Clave secreta para firmar tokens JWT |
| `JWT_EXPIRATION`        | Tiempo de expiración del token JWT en milisegundos |
| `CORS_ALLOWED_ORIGINS`  | Orígenes permitidos para Angular y otros clientes |

En Azure App Service también pueden existir variables propias de la plataforma, por ejemplo:

```txt
WEBSITES_ENABLE_APP_SERVICE_STORAGE
```

---

## Ejecución local

Para ejecutar el proyecto localmente:

1. Clonar el repositorio.
2. Configurar las variables de entorno necesarias.
3. Ejecutar:

```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

La API local queda disponible en:

```txt
http://localhost:8085
```

Swagger local:

```txt
http://localhost:8085/documentacion/swagger-ui.html
```

---

## Docker

El proyecto incluye un `Dockerfile` multi-stage.

El contenedor:

1. Compila el proyecto usando Maven y Java 17.
2. Genera el archivo `.jar`.
3. Ejecuta el backend en una imagen ligera con Java 17 JRE.

Estructura general del Dockerfile:

```dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar proyecto_web.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "proyecto_web.jar"]
```

---

## Despliegue

El backend fue desplegado en Azure utilizando:

```txt
Azure App Service
Azure Container Registry
Docker
```

Flujo de despliegue:

```txt
Código fuente en GitHub
        ↓
Imagen Docker construida localmente
        ↓
Imagen subida a Azure Container Registry
        ↓
Azure App Service ejecuta la imagen
        ↓
API pública disponible mediante URL de Azure
```

URL pública de Swagger:

```txt
Backend con autenticación JWT:
https://proyecto-web-auth-api-d7hxcqbdgucnfad0.eastus2-01.azurewebsites.net/documentacion/swagger-ui/index.html
```

Para el frontend en Angular se recomienda consumir la versión con autenticación JWT.

---

## Estructura del proyecto

```txt
src/main/java/org/example/proyecto_web
├── config
│   ├── CloudinaryConfig
│   ├── OpenApiConfig
│   ├── SecurityConfig
│   └── security
│       ├── CorsConfig
│       ├── JwtAuthenticationFilter
│       └── JwtService
├── core
│   ├── entidades
│   └── email
└── features
    ├── auth
    ├── usuario
    ├── materia
    ├── tema
    ├── cuestionario
    ├── pregunta
    ├── opcion
    ├── intento
    └── recurso
```

Cada módulo dentro de `features` contiene sus propios controladores, servicios, repositorios y DTOs.  
El módulo `auth` concentra el login, la respuesta con token y la consulta del usuario autenticado.

## Flujo del administrador

El administrador es responsable de crear y mantener el contenido académico.

Flujo general:

```txt
Admin
 ↓
Crea materias
 ↓
Crea temas asociados a materias
 ↓
Sube recursos educativos a los temas
 ↓
Crea cuestionarios asociados a temas
 ↓
Crea preguntas asociadas a cuestionarios
 ↓
Crea opciones asociadas a preguntas
```

Endpoints principales del flujo administrativo:

```txt
POST /api/materias
POST /api/temas
POST /api/recursos/upload
POST /api/cuestionarios
POST /api/preguntas
POST /api/opciones
```

También puede actualizar o eliminar contenido mediante endpoints `PUT` y `DELETE`.

---

## Flujo del alumno

El alumno consume el contenido académico y resuelve cuestionarios.

Flujo general:

```txt
Alumno
 ↓
Consulta materias
 ↓
Selecciona una materia
 ↓
Consulta temas de esa materia
 ↓
Selecciona un tema
 ↓
Consulta recursos y cuestionarios del tema
 ↓
Abre un cuestionario para resolverlo
 ↓
Envía respuestas
 ↓
El backend califica automáticamente
 ↓
Se guarda el intento
 ↓
Se envía correo con el resultado
 ↓
El alumno puede consultar su historial de intentos
```

Endpoints principales del flujo del alumno:

```txt
GET /api/materias
GET /api/materias/{id}/temas
GET /api/temas/{id}/recursos
GET /api/temas/{id}/cuestionarios
GET /api/cuestionarios/{id}/resolver
POST /api/intentos/resolver
GET /api/usuarios/{id}/intentos
```

---

## Endpoints principales

### Autenticación

| Método | Endpoint | Descripción | Acceso |
| ------ | -------- | ----------- | ------ |
| POST | `/api/auth/login` | Valida correo y contraseña; devuelve token JWT y datos del usuario. | Público |
| GET | `/api/auth/me` | Devuelve los datos del usuario autenticado usando el token JWT. | ALUMNO / ADMIN |

---

### Usuarios

| Método | Endpoint                      | Descripción                                      | Uso principal        |
| ------ | ----------------------------- | ------------------------------------------------ | -------------------- |
| GET    | `/api/usuarios`               | Lista usuarios registrados                       | Admin                |
| GET    | `/api/usuarios/{id}`          | Obtiene un usuario por ID                        | Consulta específica  |
| POST   | `/api/usuarios`               | Registra un usuario y envía correo de bienvenida | Público / Registro   |
| PUT    | `/api/usuarios/{id}`          | Actualiza datos del usuario                      | Administración       |
| DELETE | `/api/usuarios/{id}`          | Elimina un usuario                               | Administración       |
| GET    | `/api/usuarios/{id}/intentos` | Consulta intentos realizados por un usuario      | Historial del alumno |

---

### Materias

| Método | Endpoint                   | Descripción                    | Uso principal          |
| ------ | -------------------------- | ------------------------------ | ---------------------- |
| GET    | `/api/materias`            | Lista materias                 | Alumno / Admin         |
| GET    | `/api/materias/{id}`       | Obtiene una materia por ID     | Consulta específica    |
| POST   | `/api/materias`            | Crea una materia               | Admin                  |
| PUT    | `/api/materias/{id}`       | Actualiza una materia          | Admin                  |
| DELETE | `/api/materias/{id}`       | Elimina una materia            | Admin                  |
| GET    | `/api/materias/{id}/temas` | Lista los temas de una materia | Navegación por materia |

---

### Temas

| Método | Endpoint                        | Descripción                         | Uso principal       |
| ------ | ------------------------------- | ----------------------------------- | ------------------- |
| GET    | `/api/temas`                    | Lista temas                         | Alumno / Admin      |
| GET    | `/api/temas/{id}`               | Obtiene un tema por ID              | Consulta específica |
| POST   | `/api/temas`                    | Crea un tema asociado a una materia | Admin               |
| PUT    | `/api/temas/{id}`               | Actualiza un tema                   | Admin               |
| DELETE | `/api/temas/{id}`               | Elimina un tema                     | Admin               |
| GET    | `/api/temas/{id}/recursos`      | Lista recursos de un tema           | Alumno / Admin      |
| GET    | `/api/temas/{id}/cuestionarios` | Lista cuestionarios de un tema      | Alumno / Admin      |

---

### Cuestionarios

| Método | Endpoint                            | Descripción                                                                    | Uso principal       |
| ------ | ----------------------------------- | ------------------------------------------------------------------------------ | ------------------- |
| GET    | `/api/cuestionarios`                | Lista cuestionarios                                                            | Alumno / Admin      |
| GET    | `/api/cuestionarios/{id}`           | Obtiene un cuestionario por ID                                                 | Consulta específica |
| POST   | `/api/cuestionarios`                | Crea un cuestionario asociado a un tema                                        | Admin               |
| PUT    | `/api/cuestionarios/{id}`           | Actualiza un cuestionario                                                      | Admin               |
| DELETE | `/api/cuestionarios/{id}`           | Elimina un cuestionario                                                        | Admin               |
| GET    | `/api/cuestionarios/{id}/preguntas` | Lista preguntas de un cuestionario                                             | Admin / edición     |
| GET    | `/api/cuestionarios/{id}/resolver`  | Obtiene cuestionario con preguntas y opciones sin mostrar respuestas correctas | Alumno              |

---

### Preguntas

| Método | Endpoint                       | Descripción                                  | Uso principal       |
| ------ | ------------------------------ | -------------------------------------------- | ------------------- |
| GET    | `/api/preguntas`               | Lista preguntas                              | Admin / pruebas     |
| GET    | `/api/preguntas/{id}`          | Obtiene una pregunta por ID                  | Consulta específica |
| POST   | `/api/preguntas`               | Crea una pregunta asociada a un cuestionario | Admin               |
| PUT    | `/api/preguntas/{id}`          | Actualiza una pregunta                       | Admin               |
| DELETE | `/api/preguntas/{id}`          | Elimina una pregunta                         | Admin               |
| GET    | `/api/preguntas/{id}/opciones` | Lista opciones de una pregunta               | Admin / edición     |

---

### Opciones

| Método | Endpoint             | Descripción                             | Uso principal       |
| ------ | -------------------- | --------------------------------------- | ------------------- |
| GET    | `/api/opciones`      | Lista opciones                          | Admin / pruebas     |
| GET    | `/api/opciones/{id}` | Obtiene una opción por ID               | Consulta específica |
| POST   | `/api/opciones`      | Crea una opción asociada a una pregunta | Admin               |
| PUT    | `/api/opciones/{id}` | Actualiza una opción                    | Admin               |
| DELETE | `/api/opciones/{id}` | Elimina una opción                      | Admin               |

---

### Intentos

| Método | Endpoint                 | Descripción                                                                           | Uso principal       |
| ------ | ------------------------ | ------------------------------------------------------------------------------------- | ------------------- |
| GET    | `/api/intentos`          | Lista intentos registrados                                                            | Admin / pruebas     |
| GET    | `/api/intentos/{id}`     | Obtiene un intento por ID                                                             | Consulta específica |
| POST   | `/api/intentos/resolver` | Recibe respuestas, califica cuestionario, guarda intento y envía correo con resultado | Alumno              |

---

### Recursos

| Método | Endpoint               | Descripción                                               | Uso principal       |
| ------ | ---------------------- | --------------------------------------------------------- | ------------------- |
| GET    | `/api/recursos`        | Lista recursos registrados                                | Alumno / Admin      |
| GET    | `/api/recursos/{id}`   | Obtiene un recurso por ID                                 | Consulta específica |
| POST   | `/api/recursos/upload` | Sube archivo a Cloudinary y guarda metadata en YugabyteDB | Admin               |
| PUT    | `/api/recursos/{id}`   | Actualiza metadata del recurso sin cambiar el archivo     | Admin               |
| DELETE | `/api/recursos/{id}`   | Elimina recurso de base de datos y Cloudinary             | Admin               |

---

## Ejemplos de uso

### Registrar usuario

```http
POST /api/usuarios
```

Ejemplo de cuerpo:

```json
{
  "nombreUsuario": "Alumno Prueba",
  "correo": "alumno@test.com",
  "passwordUsuario": "123456",
  "rol": "ALUMNO"
}
```

Resultado esperado:

```txt
Usuario registrado correctamente.
Correo de bienvenida enviado.
```

---

### Subir recurso

```http
POST /api/recursos/upload
```

Formato:

```txt
multipart/form-data
```

Campos:

```txt
file: archivo.pdf
tituloRecurso: Guía de matrices
tipoRecurso: PDF
autor: Admin
descripcionRecurso: Material de apoyo
idTema: 3
```

Resultado esperado:

```json
{
  "idRecurso": 1,
  "tituloRecurso": "Guía de matrices",
  "tipoRecurso": "PDF",
  "url": "https://res.cloudinary.com/...",
  "publicId": "recursos/archivo.pdf",
  "resourceType": "raw",
  "idTema": 3,
  "nombreTema": "Matrices y Determinantes"
}
```

---

### Resolver cuestionario

```http
POST /api/intentos/resolver
```

Ejemplo de cuerpo:

```json
{
  "idUsuario": 204,
  "idCuestionario": 3,
  "respuestas": [
    {
      "idPregunta": 5,
      "idOpcion": 13
    },
    {
      "idPregunta": 6,
      "idOpcion": 16
    }
  ]
}
```

Resultado esperado:

```json
{
  "idIntento": 1,
  "puntaje": 100,
  "totalPreguntas": 2,
  "correctas": 2,
  "mensaje": "Cuestionario calificado correctamente"
}
```

---

## Notas para frontend

Para la integración con Angular se recomienda consumir el backend con autenticación JWT:

```txt
https://proyecto-web-auth-api-d7hxcqbdgucnfad0.eastus2-01.azurewebsites.net
```

### Login

El frontend debe iniciar sesión con:

```http
POST /api/auth/login
```

Body esperado:

```json
{
  "correo": "usuario@test.com",
  "password": "123456"
}
```

Respuesta esperada:

```json
{
  "token": "JWT_GENERADO",
  "idUsuario": 1501,
  "nombreUsuario": "Prueba Auth",
  "correo": "usuario@test.com",
  "rol": "ADMIN"
}
```

### Uso del token

Angular debe guardar el token y enviarlo en cada petición protegida:

```http
Authorization: Bearer TOKEN
```

Lo recomendable es agregar este header mediante un `HttpInterceptor`.

### Validar sesión activa

Para validar si el token sigue siendo válido, usar:

```http
GET /api/auth/me
```

Este endpoint devuelve los datos del usuario autenticado.

### Logout

El cierre de sesión se maneja desde Angular eliminando el token guardado:

```ts
localStorage.removeItem("token");
localStorage.removeItem("usuario");
```

Después se redirige al usuario a la pantalla de login.

### CORS

El backend permite Angular local desde:

```txt
http://localhost:4200
```

Cuando el frontend tenga una URL final desplegada, esa URL debe agregarse en Azure dentro de la variable:

```txt
CORS_ALLOWED_ORIGINS
```

Ejemplo:

```txt
CORS_ALLOWED_ORIGINS=http://localhost:4200,https://url-del-front.com
```

### Flujo principal del alumno

* Para mostrar materias, usar `GET /api/materias`.
* Para mostrar temas de una materia, usar `GET /api/materias/{id}/temas`.
* Para mostrar recursos de un tema, usar `GET /api/temas/{id}/recursos`.
* Para mostrar cuestionarios de un tema, usar `GET /api/temas/{id}/cuestionarios`.
* Para abrir un cuestionario como alumno, usar `GET /api/cuestionarios/{id}/resolver`.
* El endpoint `/resolver` no devuelve el campo `esCorrecta`.
* Para enviar respuestas, usar `POST /api/intentos/resolver`.
* Para ver historial del alumno, usar `GET /api/usuarios/{id}/intentos`.
* Para abrir o descargar recursos, usar el campo `url` devuelto por el backend.

### Endpoints restringidos

Los endpoints administrativos requieren token con rol `ADMIN`.  
Los endpoints de preguntas y opciones quedan protegidos para evitar que el alumno consulte respuestas correctas.

Ejemplos:

```txt
GET /api/cuestionarios/{id}/preguntas -> ADMIN
GET /api/preguntas -> ADMIN
GET /api/opciones -> ADMIN
```

Para el alumno se debe usar:

```txt
GET /api/cuestionarios/{id}/resolver
```

### Descarga de recursos desde Cloudinary

Para forzar descarga desde Cloudinary, el frontend puede transformar la URL agregando `fl_attachment` después de `/upload/`.

Ejemplo:

```js
const downloadUrl = recurso.url.replace("/raw/upload/", "/raw/upload/fl_attachment/");
```

---
