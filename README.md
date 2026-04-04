# segundo-parcial

Base de API Spring Boot con:

- CRUD simple de usuarios
- Autenticacion JWT
- Persistencia con Spring Data JPA
- Seguridad con Spring Security

## Ejecutar

```bash
mvn clean test
mvn spring-boot:run
```

## Datos iniciales

La aplicacion crea dos usuarios al arrancar:

- `admin@segundoparcial.com` / `admin123`
- `user@segundoparcial.com` / `user123`

## Endpoints base

- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}/profile`
- `DELETE /api/users/{id}`
- `GET /api/users/{userId}/payments`
- `GET /api/users/{userId}/payments/{paymentId}`
- `POST /api/users/{userId}/payments`

## Body para Postman

En Postman usa:

- `Body -> raw -> JSON`
- Header `Content-Type: application/json`

### Login (`POST /api/auth/login`)

```json
{
	"email": "admin@segundoparcial.com",
	"password": "admin123"
}
```

### Registro (`POST /api/auth/register`)

```json
{
	"name": "Estudiante Demo",
	"email": "estudiante@ejemplo.com",
	"password": "clave123",
	"phoneNumber": "+573001112233",
	"role": "USER"
}
```

### Crear usuario (`POST /api/users`)

```json
{
	"name": "Nuevo Admin",
	"email": "nuevoadmin@ejemplo.com",
	"password": "admin123",
	"phoneNumber": "+573224445566",
	"role": "ADMIN"
}
```

### Actualizar perfil (`PUT /api/users/{id}/profile`)

```json
{
	"name": "Nombre Actualizado",
	"phoneNumber": "+573000009999"
}
```

### Crear pago de usuario (`POST /api/users/{userId}/payments`)

```json
{
	"amount": 125000.00,
	"concept": "Matricula 2026-1"
}
```

## JWT

El login retorna un token `Bearer` que debes enviar en el header:

```http
Authorization: Bearer <token>
```

La clave y la expiracion estan en `src/main/resources/application.properties`.

## Como modificarla

- Si quieres mas campos de usuario, empieza por `UserEntity`, `UserRequest` y `UserResponse`.
- Si quieres mas reglas de negocio, cambia `UserService` o `AuthService`.
- Si quieres mas endpoints, agrega otro controlador siguiendo el mismo patron de `controller -> service -> repository`.
- Si quieres cambiar seguridad, revisa `SecurityConfig` y `JwtAuthenticationFilter`.
- Si quieres otra base de datos, cambia `application.properties` y la dependencia del driver JDBC.