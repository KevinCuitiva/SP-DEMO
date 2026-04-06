# segundo-parcial

API REST Spring Boot 4.0.3 (Java 21) con arquitectura escalable y extensible.

## ✨ Características Principales

### Autenticación & Seguridad
- ✅ Autenticación JWT con tokens de 24 horas
- ✅ Control de acceso por rol (ADMIN, USER)
- ✅ Spring Security configurado

### Gestión de Usuarios
- ✅ CRUD de usuarios
- ✅ Perfil de usuario con número de teléfono
- ✅ Encriptación de contraseñas con BCrypt

### Catálogo de Productos (NEW ✨)
- ✅ CRUD de productos con relaciones
- ✅ Categorías de productos
- ✅ Búsqueda avanzada (por nombre, categoría, creador)
- ✅ Gestión de inventario (stock)
- ✅ Información del creador/vendedor

### Arquitectura
- ✅ Patrón de capas: Entity → Repository → Service → Controller → DTO
- ✅ Clases base reutilizables (BaseEntity, BaseRepository)
- ✅ Auditoría automática de fechas (createdAt, updatedAt)
- ✅ Persistencia con Spring Data JPA & H2 en memoria
- ✅ Documentación OpenAPI (Swagger disponible en `/swagger-ui/`)

## Ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8081`

## Liberar puerto 8081 (PowerShell)

Si `mvn spring-boot:run` falla porque el puerto ya está en uso, usa estos comandos:

```powershell
# 1) Ver qué proceso está escuchando en 8081
netstat -ano | Select-String ":8081"

# 2) Matar el proceso por PID (reemplaza 12345 por el PID real)
Stop-Process -Id 12345 -Force

# 3) Arrancar de nuevo el proyecto
./mvnw spring-boot:run
```

También puedes hacerlo en una sola línea:

```powershell
Get-NetTCPConnection -LocalPort 8081 -ErrorAction SilentlyContinue | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }; ./mvnw spring-boot:run
```

---

## 📚 Documentación

Consulta estos archivos para entender la API:

| Archivo | Propósito |
|---------|-----------|
| [ENDPOINTS.md](ENDPOINTS.md) | Lista completa de todos los endpoints |
| [ARQUITECTURA.md](ARQUITECTURA.md) | Guía de arquitectura y cómo extender |
| [EJEMPLOS_API.md](EJEMPLOS_API.md) | Ejemplos prácticos con cURL |
| [RESUMEN_CAMBIOS.md](RESUMEN_CAMBIOS.md) | Resumen de nuevas funcionalidades |

---

## 🔑 Datos Iniciales

Al arrancar la aplicación, se crean dos usuarios automáticamente:

| Email | Contraseña | Rol |
|-------|-----------|-----|
| `admin@segundoparcial.com` | `admin123` | ADMIN |
| `user@segundoparcial.com` | `user123` | USER |

---

## 🛣️ Endpoints Principales

### 🔐 Autenticación
- `POST /api/auth/login` - Obtener token JWT (público)
- `POST /api/auth/register` - Registrar nuevo usuario (público)

### 👥 Usuarios
- `GET /api/users` - Listar usuarios (requiere auth)
- `GET /api/users/{id}` - Obtener usuario (requiere auth)
- `POST /api/users` - Crear usuario (solo ADMIN)
- `PUT /api/users/{id}/profile` - Actualizar perfil (requiere auth)
- `DELETE /api/users/{id}` - Eliminar usuario (solo ADMIN)

### 📦 Categorías (NEW ✨)
- `GET /api/categories` - Obtener categorías (público)
- `GET /api/categories/{id}` - Obtener categoría (público)
- `POST /api/categories` - Crear categoría (solo ADMIN)
- `PUT /api/categories/{id}` - Actualizar categoría (solo ADMIN)
- `DELETE /api/categories/{id}` - Eliminar categoría (solo ADMIN)

### 🛍️ Productos (NEW ✨)
- `GET /api/products` - Obtener productos (público)
- `GET /api/products/{id}` - Obtener producto (público)
- `GET /api/products/search?name=...` - Buscar por nombre (público)
- `GET /api/products/category/{categoryId}` - Por categoría (público)
- `GET /api/products/creator/{creatorId}` - Por vendedor (público)
- `POST /api/products` - Crear producto (requiere auth)
- `PUT /api/products/{id}` - Actualizar producto (requiere auth)
- `DELETE /api/products/{id}` - Eliminar producto (requiere auth)

---

## 💡 Ejemplos de Uso
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