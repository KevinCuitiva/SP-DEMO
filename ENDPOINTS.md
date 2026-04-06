## Endpoints Disponibles de la API

### 🔐 Autenticación (Public)

```
POST /api/auth/login
  - Body: { email, password }
  - Response: { token, email, role, userId }
  - Descripción: Login
```

---

### 👥 Usuarios (Requiere Auth / Admin)

```
GET /api/users
  - Auth requerida: Sí
  - Response: List[UserResponse]
  - Descripción: Listar todos los usuarios

GET /api/users/{id}
  - Auth requerida: Sí
  - Response: UserResponse
  - Descripción: Obtener usuario por ID

POST /api/users
  - Auth requerida: Admin
  - Body: { name, email, password, phoneNumber, role }
  - Response: UserResponse
  - Descripción: Crear usuario

PUT /api/users/{id}/profile
  - Auth requerida: Sí
  - Body: { name, phoneNumber }
  - Response: UserResponse
  - Descripción: Actualizar perfil del usuario

DELETE /api/users/{id}
  - Auth requerida: Admin
  - Response: 204 No Content
  - Descripción: Eliminar usuario
```

---

### 📦 Categorías (GET Public / Modificar Admin)

```
GET /api/categories
  - Auth requerida: No
  - Response: List[CategoryResponse]
  - Descripción: Listar todas las categorías activas

GET /api/categories/{id}
  - Auth requerida: No
  - Response: CategoryResponse
  - Descripción: Obtener categoría por ID

POST /api/categories
  - Auth requerida: Admin
  - Body: { name, description }
  - Response: CategoryResponse (201 Created)
  - Descripción: Crear categoría

PUT /api/categories/{id}
  - Auth requerida: Admin
  - Body: { name, description }
  - Response: CategoryResponse
  - Descripción: Actualizar categoría

DELETE /api/categories/{id}
  - Auth requerida: Admin
  - Response: 204 No Content
  - Descripción: Eliminar categoría
```

---

### 🛍️ Productos (GET Public / Modificar Auth/Admin)

```
GET /api/products
  - Auth requerida: No
  - Response: List[ProductResponse]
  - Descripción: Listar todos los productos disponibles

GET /api/products/{id}
  - Auth requerida: No
  - Response: ProductResponse
  - Descripción: Obtener producto por ID

GET /api/products/search?name=<texto>
  - Auth requerida: No
  - Query Params: name (búsqueda parcial)
  - Response: List[ProductResponse]
  - Descripción: Buscar productos por nombre

GET /api/products/category/{categoryId}
  - Auth requerida: No
  - Response: List[ProductResponse]
  - Descripción: Obtener productos de una categoría

GET /api/products/creator/{creatorId}
  - Auth requerida: No
  - Response: List[ProductResponse]
  - Descripción: Obtener productos de un vendedor (usuario)

POST /api/products
  - Auth requerida: Sí (Usuario autenticado)
  - Body: { name, description, price, stock, categoryId }
  - Response: ProductResponse (201 Created)
  - Descripción: Crear producto (el usuario actual es el creador)

PUT /api/products/{id}
  - Auth requerida: Sí
  - Body: { name, description, price, stock, categoryId }
  - Response: ProductResponse
  - Descripción: Actualizar producto (solo creador o admin)

DELETE /api/products/{id}
  - Auth requerida: Sí
  - Response: 204 No Content
  - Descripción: Eliminar producto (solo creador o admin)
```

---

### 📋 Ejemplos de Requests/Responses

#### Crear Categoría
```bash
POST /api/categories
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Electrónica",
  "description": "Productos electrónicos en general"
}

Response (201):
{
  "id": 1,
  "name": "Electrónica",
  "description": "Productos electrónicos en general",
  "active": true,
  "createdAt": "2026-04-06T14:30:00",
  "updatedAt": "2026-04-06T14:30:00"
}
```

#### Crear Producto
```bash
POST /api/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Laptop Dell XPS 13",
  "description": "Laptop ultraportátil con pantalla OLED, procesador Intel i7, 16GB RAM",
  "price": 1299.99,
  "stock": 50,
  "categoryId": 1
}

Response (201):
{
  "id": 1,
  "name": "Laptop Dell XPS 13",
  "description": "Laptop ultraportátil con pantalla OLED...",
  "price": 1299.99,
  "stock": 50,
  "categoryId": 1,
  "categoryName": "Electrónica",
  "creatorId": 5,
  "creatorName": "Juan Pérez",
  "available": true,
  "rating": 0.0,
  "createdAt": "2026-04-06T14:35:00",
  "updatedAt": "2026-04-06T14:35:00"
}
```

#### Buscar Productos
```bash
GET /api/products/search?name=laptop

Response (200):
[
  {
    "id": 1,
    "name": "Laptop Dell XPS 13",
    ...
  },
  {
    "id": 2,
    "name": "Laptop HP Pavilion",
    ...
  }
]
```

#### Obtener Productos por Categoría
```bash
GET /api/products/category/1

Response (200):
[
  {
    "id": 1,
    "name": "Laptop Dell XPS 13",
    ...
  },
  {
    "id": 3,
    "name": "Monitor Samsung 27\"",
    ...
  }
]
```

---

### 🔑 Códigos de Estado HTTP

| Código | Significado | Cuándo Ocurre |
|--------|------------|----------------|
| 200 | OK | GET/PUT exitoso |
| 201 | Created | POST exitoso |
| 204 | No Content | DELETE exitoso |
| 400 | Bad Request | Validación fallida (campos inválidos) |
| 401 | Unauthorized | Falta token o token inválido |
| 403 | Forbidden | Token válido pero sin permisos |
| 404 | Not Found | Recurso no existe |
| 500 | Server Error | Error interno del servidor |

---

### 🔐 Seguridad por Endpoint

| Endpoint | Público | Auth | Admin |
|----------|---------|------|-------|
| POST /api/auth/login | ✓ | ✗ | ✗ |
| GET /api/categories | ✓ | ✗ | ✗ |
| POST /api/categories | ✗ | ✗ | ✓ |
| GET /api/products | ✓ | ✗ | ✗ |
| POST /api/products | ✗ | ✓ | ✓ |
| GET /api/users | ✗ | ✓ | ✓ |
| POST /api/users | ✗ | ✗ | ✓ |

---

### 📍 Headers Requeridos

Para endpoints protegidos, incluir:
```
Authorization: Bearer <token>
Content-Type: application/json
```

El token JWT se obtiene en `/api/auth/login` y expira en 24 horas.

---

### ⚠️ Errores Comunes

```json
{
  "status": 400,
  "message": "Validación fallida: El precio debe ser mayor a 0",
  "timestamp": "2026-04-06T14:40:00"
}
```

```json
{
  "status": 401,
  "message": "Token no proporcionado o inválido",
  "timestamp": "2026-04-06T14:41:00"
}
```

```json
{
  "status": 404,
  "message": "Producto no encontrado con ID: 999",
  "timestamp": "2026-04-06T14:42:00"
}
```
