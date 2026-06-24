## Estado del despliegue

El backend se encuentra desplegado en Azure App Service y puede probarse públicamente desde Swagger UI.  
La base de datos utilizada es YugabyteDB en la nube.  
Los archivos se almacenan en Cloudinary y los correos automáticos se envían mediante Gmail SMTP.
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
@@ -30,6 +32,53 @@

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
@@ -75,7 +124,9 @@
| Cloudinary               | Almacenamiento de archivos, PDFs, videos e imágenes |
| Gmail SMTP               | Envío automático de correos                         |
| Springdoc OpenAPI        | Documentación automática con Swagger                |
| Spring Security          | Configuración base de seguridad y BCrypt            |
| Spring Security          | Seguridad del backend, filtros, roles y JWT          |
| JWT                      | Autenticación mediante tokens                        |
| CORS                     | Permite conexión del frontend Angular                |
| BCrypt                   | Cifrado de contraseñas                              |
| Docker                   | Contenerización del backend                         |
| Azure Container Registry | Almacenamiento de imagen Docker                     |
@@ -182,17 +233,35 @@

## Seguridad

Para esta entrega, los endpoints se mantienen públicos para facilitar la validación desde Swagger.
La versión nueva del backend implementa autenticación mediante JWT y control de acceso por roles.

Consideraciones implementadas:

```txt
- Las contraseñas de usuario se almacenan cifradas con BCrypt.
- El login se realiza mediante POST /api/auth/login.
- El backend devuelve un token JWT con datos del usuario autenticado.
- El token debe enviarse en el header Authorization con formato Bearer TOKEN.
- El endpoint GET /api/auth/me permite validar la sesión activa.
- La entidad Usuario contempla roles ALUMNO y ADMIN.
- No se implementó autenticación JWT en esta entrega.
- Spring Security se mantiene configurado para permitir las pruebas públicas del API.
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
@@ -214,6 +283,9 @@
| `CLOUDINARY_NAME`       | Cloud name de Cloudinary          |
| `CLOUDINARY_API_KEY`    | API Key de Cloudinary             |
| `CLOUDINARY_API_SECRET` | API Secret de Cloudinary          |
| `JWT_SECRET`            | Clave secreta para firmar tokens JWT |
| `JWT_EXPIRATION`        | Tiempo de expiración del token JWT en milisegundos |
| `CORS_ALLOWED_ORIGINS`  | Orígenes permitidos para Angular y otros clientes |

En Azure App Service también pueden existir variables propias de la plataforma, por ejemplo:

@@ -312,20 +384,31 @@
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
@@ -334,9 +417,10 @@
   ├── opcion
   ├── intento
   └── recurso

```
Cada módulo dentro de `features` contiene sus propios controladores, servicios, repositorios y DTOs.

Cada módulo dentro de `features` contiene sus propios controladores, servicios, repositorios y DTOs.  
El módulo `auth` concentra el login, la respuesta con token y la consulta del usuario autenticado.

## Flujo del administrador

@@ -423,13 +507,22 @@

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
| GET    | `/api/usuarios`               | Lista usuarios registrados                       | Admin / pruebas      |
| GET    | `/api/usuarios`               | Lista usuarios registrados                       | Admin                |
| GET    | `/api/usuarios/{id}`          | Obtiene un usuario por ID                        | Consulta específica  |
| POST   | `/api/usuarios`               | Registra un usuario y envía correo de bienvenida | Registro             |
| POST   | `/api/usuarios`               | Registra un usuario y envía correo de bienvenida | Público / Registro   |
| PUT    | `/api/usuarios/{id}`          | Actualiza datos del usuario                      | Administración       |
| DELETE | `/api/usuarios/{id}`          | Elimina un usuario                               | Administración       |
| GET    | `/api/usuarios/{id}/intentos` | Consulta intentos realizados por un usuario      | Historial del alumno |
@@ -633,6 +726,94 @@

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
@@ -642,12 +823,34 @@
* Para enviar respuestas, usar `POST /api/intentos/resolver`.
* Para ver historial del alumno, usar `GET /api/usuarios/{id}/intentos`.
* Para abrir o descargar recursos, usar el campo `url` devuelto por el backend.
* Para forzar descarga desde Cloudinary, el frontend puede transformar la URL agregando `fl_attachment` después de `/upload/`.

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

## Módulo de Administración (Frontend)

Se han implementado todas las interfaces gráficas en Angular para consumir los endpoints administrativos protegidos. Este módulo permite al personal con rol `ADMIN` gestionar todo el contenido de la plataforma de forma interactiva y amigable.

### Características del Panel de Control:
- **Gestión de Materias (`/admin/materias`)**: Creación, edición y eliminación de las materias principales.
- **Gestión de Temas (`/admin/temas`)**: Permite seleccionar una materia y agregarle temas específicos con su título y descripción.
- **Gestión de Recursos (`/admin/recursos`)**: Sistema en cascada (Materia -> Tema) para la carga de recursos de aprendizaje. Soporta subida de archivos físicos (PDFs/Documentos) mediante `FormData` al endpoint de `/upload`, y registro de URLs externas (videos).
- **Generador de Cuestionarios (`/admin/cuestionarios`)**: Interfaz dinámica ("Builder") de una sola pantalla que permite:
  - Definir el título del cuestionario.
  - Agregar `N` cantidad de preguntas de manera dinámica.
  - Agregar 4 opciones a cada pregunta y marcar cuál es la opción correcta usando _radio buttons_.
  - Guardado en cascada automático (invoca endpoints de creación de Cuestionario, luego N Preguntas, y 4N Opciones secuencialmente).
- **Gestión de Usuarios (`/admin/usuarios`)**: Visualización de la tabla maestra de usuarios y herramientas para modificar roles (promover a Administrador) o corregir datos personales.

> **Nota para el reporte final:** El frontend cumple con todos los requisitos funcionales requeridos, integrando 100% de las rutas públicas y administrativas expuestas por Spring Boot a través de servicios inyectables (`ApiService`) y validando permisos de acceso mediante Guards (`authGuard` y `adminGuard`).
