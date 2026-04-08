# DOSW-ParcialT2

API REST en Spring Boot con JWT, JPA y PostgreSQL. El proyecto está organizado por capas para que cada cambio tenga un lugar claro: entidad, repositorio, servicio, controlador, DTO y seguridad.

## Resumen

- Autenticación JWT con roles `ADMIN` y `USER`.
- Gestión de usuarios con actualización de perfil.
- Catálogo de categorías y productos.
- Clases base reutilizables para reducir repetición.
- Swagger disponible en `/swagger-ui/`.
- Configuración separada por perfiles para desarrollo y producción.

## Requisitos

- Java 21.
- Maven.
- PostgreSQL 16 o compatible.
- Opcional: Docker para levantar la base de datos rápidamente.

## Ejecutarlo en Otro PC

1. Instala Git, Java 21, Docker Desktop y Maven (si no usarás el wrapper).
2. Clona el repositorio.
3. Abre Docker Desktop y espera a que quede iniciado.
4. Desde la raíz del proyecto, levanta la base de datos con Docker Compose.
5. Arranca la API de forma normal.

Comandos:

```bash
git clone <URL_DEL_REPOSITORIO>
cd parcial-T2
docker compose up -d
./mvnw spring-boot:run

```

Si estás en PowerShell, usa:

```powershell
.\mvnw spring-boot:run
```

## Configuración de Base de Datos

Este ejercicio usa configuración fija local:

- Host: `localhost`
- Puerto: `5432`
- Base de datos: `segundo_parcial`
- Usuario: `segundo_parcial`
- Contraseña: `segundo_parcial`

Si necesitas cambiar la conexión, edita [src/main/resources/application.properties](src/main/resources/application.properties).

Por defecto la app levanta con `ddl-auto=update` en `application.properties` para crear/actualizar tablas automáticamente en local. En producción, el perfil `application-prod.properties` usa `ddl-auto=validate`.

Importante: si usas Docker, abre Docker Desktop antes de levantar la base.

## Ejecutar la Base de Datos (Docker)

Desde la raíz del proyecto:

```bash
docker compose up -d
docker compose ps
```

Si quieres detenerla:

```bash
docker compose down
```

## Ejecutar la API

Con la base ya arriba:

```bash
mvn clean test
./mvnw spring-boot:run
```

La aplicación queda en `http://localhost:8081`.

Swagger:

```text
http://localhost:8081/swagger-ui/
```

## Ver la Base de Datos

Opción 1 (rápida por terminal, dentro del contenedor):

```bash
docker exec -it parcial-t2-postgres psql -U segundo_parcial -d segundo_parcial
```

Comandos útiles dentro de `psql`:

```sql
\dt
SELECT * FROM users;
SELECT * FROM categories;
SELECT * FROM products;
```

Salir de `psql`:

```bash
\q
```

Opción 2 (interfaz visual): usar DBeaver o pgAdmin con estos datos:

- Host: `localhost`
- Puerto: `5432`
- Base de datos: `segundo_parcial`
- Usuario: `segundo_parcial`
- Contraseña: `segundo_parcial`

## Usuarios Iniciales

Al iniciar por primera vez se crean dos usuarios:

| Email | Contraseña | Rol |
|-------|------------|-----|
| `admin@segundoparcial.com` | `admin123` | `ADMIN` |
| `user@segundoparcial.com` | `user123` | `USER` |

## Estructura General

El código se divide así:

- `entity/`: entidades JPA y clases base.
- `repository/`: acceso a datos.
- `service/`: lógica de negocio.
- `controller/`: endpoints REST.
- `dto/`: request/response de la API.
- `config/`: seguridad y arranque de datos.
- `security/`: filtro JWT y soporte de autenticación.
- `exception/`: manejo centralizado de errores.

La clase principal está en [src/main/java/edu/eci/dosw/segundo_parcial/SegundoParcialApplication.java](src/main/java/edu/eci/dosw/segundo_parcial/SegundoParcialApplication.java).

## Extensibilidad

Si vas a agregar una característica nueva:

1. Crea o ajusta la entidad en `entity/`.
2. Expón datos con DTOs en `dto/`.
3. Agrega o modifica el repositorio en `repository/`.
4. Implementa la lógica en `service/`.
5. Publica el endpoint en `controller/`.
6. Revisa permisos en `config/SecurityConfig.java`.

Reglas prácticas:

- Usa `BaseEntity` para heredar `id`, `createdAt` y `updatedAt`.
- Usa `BaseRepository` para reaprovechar CRUD.
- No expongas entidades directamente en respuestas; usa DTOs.
- Valida datos de entrada en los DTOs con anotaciones Jakarta Validation.

## Endpoints Principales

### Autenticación

- `POST /api/auth/login` - Obtener token JWT.
- `POST /api/auth/register` - Registrar usuario.

### Usuarios

- `GET /api/users` - Listar usuarios.
- `GET /api/users/{id}` - Obtener usuario por ID.
- `POST /api/users` - Crear usuario.
- `PUT /api/users/{id}/profile` - Actualizar perfil.
- `DELETE /api/users/{id}` - Eliminar usuario.

### Categorías

- `GET /api/categories` - Listar categorías activas.
- `GET /api/categories/{id}` - Obtener categoría por ID.
- `POST /api/categories` - Crear categoría.
- `PUT /api/categories/{id}` - Actualizar categoría.
- `DELETE /api/categories/{id}` - Eliminar categoría.

### Productos

- `GET /api/products` - Listar productos disponibles.
- `GET /api/products/{id}` - Obtener producto por ID.
- `GET /api/products/search?name=...` - Buscar por nombre.
- `GET /api/products/category/{categoryId}` - Obtener por categoría.
- `GET /api/products/creator/{creatorId}` - Obtener por creador.
- `POST /api/products` - Crear producto.
- `PUT /api/products/{id}` - Actualizar producto.
- `DELETE /api/products/{id}` - Eliminar producto.

## Seguridad

Endpoints públicos:

- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /api/categories/**`
- `GET /api/products/**`

Endpoints protegidos:

- `GET /api/users/**` requiere autenticación.
- `POST /api/users/**`, `PUT /api/users/**` y `DELETE /api/users/**` requieren rol `ADMIN`.
- `POST /api/categories/**`, `PUT /api/categories/**` y `DELETE /api/categories/**` requieren rol `ADMIN`.
- `POST /api/products/**`, `PUT /api/products/**` y `DELETE /api/products/**` requieren autenticación.

## Ejemplos de Uso

### Login

```bash
curl -X POST http://localhost:8081/api/auth/login \
	-H "Content-Type: application/json" \
	-d '{"email":"admin@segundoparcial.com","password":"admin123"}'
```

### Crear categoría

```bash
curl -X POST http://localhost:8081/api/categories \
	-H "Authorization: Bearer <token>" \
	-H "Content-Type: application/json" \
	-d '{"name":"Electrónica","description":"Productos electrónicos"}'
```

### Crear producto

```bash
curl -X POST http://localhost:8081/api/products \
	-H "Authorization: Bearer <token>" \
	-H "Content-Type: application/json" \
	-d '{"name":"Laptop","description":"Laptop de prueba","price":1299.99,"stock":10,"categoryId":1}'
```

## Checklist de Cambio

- [ ] Crear o ajustar la entidad.
- [ ] Crear o ajustar el DTO.
- [ ] Crear o ajustar el repositorio.
- [ ] Crear o ajustar el servicio.
- [ ] Exponer o actualizar el controlador.
- [ ] Revisar seguridad en `SecurityConfig`.
- [ ] Validar con `mvn test`.

## Notas de Mantenimiento

- Si cambias la estructura de datos, empieza por `entity/` y luego sincroniza `dto/` y `service/`.
- Si cambias reglas de acceso, ajusta `config/SecurityConfig.java`.
- Si cambias la conexión o credenciales, edita `application.properties`.
- Si cambias el comportamiento por entorno, usa los perfiles `application-dev.properties` y `application-prod.properties`.