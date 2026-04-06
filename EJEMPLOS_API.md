## 📌 Guía de Uso Práctico - Ejemplos de API

Esta guía muestra cómo usar los nuevos endpoints con ejemplos reales.

---

## 1️⃣ Autenticación - Obtener Token JWT

```bash
# Login para obtener token
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "123456"
  }'

# Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "admin@example.com",
  "role": "ADMIN",
  "userId": 1
}

# Guardar el token en una variable para uso posterior:
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## 2️⃣ Trabajar con Categorías

### A. Crear Categoría (ADMIN)

```bash
curl -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electrónica",
    "description": "Todos los productos electrónicos"
  }'

# Response (201 Created):
{
  "id": 1,
  "name": "Electrónica",
  "description": "Todos los productos electrónicos",
  "active": true,
  "createdAt": "2026-04-06T15:00:00",
  "updatedAt": "2026-04-06T15:00:00"
}
```

### B. Crear Más Categorías

```bash
curl -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Ropa", "description": "Prendas de vestir"}'

curl -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Libros", "description": "Libros físicos y digitales"}'

curl -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Deportes", "description": "Equipamiento deportivo"}'

# Guardar los IDs de categorías para uso posterior
CATEGORY_ELECTRONICS=1
CATEGORY_CLOTHING=2
CATEGORY_BOOKS=3
CATEGORY_SPORTS=4
```

### C. Obtener Todas las Categorías (SIN AUTH)

```bash
curl -X GET http://localhost:8081/api/categories

# Response (200 OK):
[
  {
    "id": 1,
    "name": "Electrónica",
    "description": "Todos los productos electrónicos",
    "active": true,
    "createdAt": "2026-04-06T15:00:00",
    "updatedAt": "2026-04-06T15:00:00"
  },
  {
    "id": 2,
    "name": "Ropa",
    ...
  },
  ...
]
```

### D. Obtener Una Categoría Específica (SIN AUTH)

```bash
curl -X GET http://localhost:8081/api/categories/1

# Response (200 OK):
{
  "id": 1,
  "name": "Electrónica",
  "description": "Todos los productos electrónicos",
  "active": true,
  "createdAt": "2026-04-06T15:00:00",
  "updatedAt": "2026-04-06T15:00:00"
}
```

### E. Actualizar Categoría (ADMIN)

```bash
curl -X PUT http://localhost:8081/api/categories/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electrónica y Computadores",
    "description": "Productos electrónicos, computadores y accesorios"
  }'

# Response (200 OK): Categoría actualizada
```

---

## 3️⃣ Trabajar con Productos

### A. Crear Producto (USER AUTENTICADO)

```bash
# Primero, login como usuario normal para obtener token
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan@example.com",
    "password": "123456"
  }'

# Guardar token
USER_TOKEN="eyJhbGc..."

# Crear producto (como el usuario autenticado)
curl -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15 Pro",
    "description": "Último modelo de Apple con pantalla de 6.1 pulgadas, procesador A17, cámara mejorada",
    "price": 999.99,
    "stock": 50,
    "categoryId": 1
  }'

# Response (201 Created):
{
  "id": 1,
  "name": "iPhone 15 Pro",
  "description": "Último modelo de Apple...",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1,
  "categoryName": "Electrónica",
  "creatorId": 2,
  "creatorName": "Juan Pérez",
  "available": true,
  "rating": 0.0,
  "createdAt": "2026-04-06T15:10:00",
  "updatedAt": "2026-04-06T15:10:00"
}

# Guardar ID del producto
PRODUCT_ID=1
```

### B. Crear Más Productos de Ejemplo

```bash
# Producto 2: Laptop
curl -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro 14\"",
    "description": "MacBook Pro 14 pulgadas con M3 Pro, 512GB SSD, 16GB RAM",
    "price": 1999.99,
    "stock": 30,
    "categoryId": 1
  }'

# Producto 3: Libro
curl -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Clean Code",
    "description": "A Handbook of Agile Software Craftsmanship por Robert C. Martin",
    "price": 49.99,
    "stock": 100,
    "categoryId": 3
  }'

# Producto 4: Ropa
curl -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Zapatillas Nike Air Max",
    "description": "Zapatillas deportivas Nike Air Max 90, talla 40-46",
    "price": 129.99,
    "stock": 200,
    "categoryId": 2
  }'
```

### C. Obtener Todos los Productos (SIN AUTH)

```bash
curl -X GET http://localhost:8081/api/products

# Response (200 OK):
[
  {
    "id": 1,
    "name": "iPhone 15 Pro",
    "price": 999.99,
    "stock": 50,
    ...
  },
  {
    "id": 2,
    "name": "MacBook Pro 14\"",
    ...
  },
  ...
]
```

### D. Obtener Un Producto Específico (SIN AUTH)

```bash
curl -X GET http://localhost:8081/api/products/1

# Response (200 OK):
{
  "id": 1,
  "name": "iPhone 15 Pro",
  "description": "Último modelo de Apple...",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1,
  "categoryName": "Electrónica",
  "creatorId": 2,
  "creatorName": "Juan Pérez",
  "available": true,
  "rating": 0.0,
  "createdAt": "2026-04-06T15:10:00",
  "updatedAt": "2026-04-06T15:10:00"
}
```

### E. Buscar Productos por Nombre (SIN AUTH)

```bash
# Buscar todos los productos que contengan "iPhone"
curl -X GET "http://localhost:8081/api/products/search?name=iPhone"

# Response (200 OK):
[
  {
    "id": 1,
    "name": "iPhone 15 Pro",
    ...
  },
  {
    "id": 5,
    "name": "iPhone 15",
    ...
  }
]

# Buscar "Zapatillas"
curl -X GET "http://localhost:8081/api/products/search?name=Zapatillas"
```

### F. Obtener Productos por Categoría (SIN AUTH)

```bash
# Obtener todos los productos en categoría "Electrónica" (ID=1)
curl -X GET http://localhost:8081/api/products/category/1

# Response (200 OK):
[
  {
    "id": 1,
    "name": "iPhone 15 Pro",
    "categoryId": 1,
    "categoryName": "Electrónica",
    ...
  },
  {
    "id": 2,
    "name": "MacBook Pro 14\"",
    "categoryId": 1,
    "categoryName": "Electrónica",
    ...
  }
]

# Obtener productos de "Ropa" (ID=2)
curl -X GET http://localhost:8081/api/products/category/2
```

### G. Obtener Productos de un Creador/Vendedor (SIN AUTH)

```bash
# Obtener todos los productos creados por usuario con ID=2 (Juan Pérez)
curl -X GET http://localhost:8081/api/products/creator/2

# Response (200 OK): Todos los productos de ese usuario
```

### H. Actualizar Producto (CREADOR o ADMIN)

```bash
# Actualizar precio y stock del producto
curl -X PUT http://localhost:8081/api/products/1 \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15 Pro",
    "description": "Último modelo de Apple con pantalla de 6.1 pulgadas, procesador A17, cámara mejorada",
    "price": 949.99,
    "stock": 40,
    "categoryId": 1
  }'

# Response (200 OK): Producto actualizado
```

### I. Eliminar Producto (CREADOR o ADMIN)

```bash
curl -X DELETE http://localhost:8081/api/products/1 \
  -H "Authorization: Bearer $USER_TOKEN"

# Response (204 No Content): Producto eliminado
```

---

## 4️⃣ Validaciones de Errores

### A. Intentar Crear Categoría Sin Estar ADMIN

```bash
# Login como usuario normal
USER_TOKEN="eyJ..."

curl -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Nueva Categoría", "description": "..."}'

# Response (403 Forbidden):
{
  "status": 403,
  "message": "Acceso denegado",
  "timestamp": "2026-04-06T15:20:00"
}
```

### B. Campos Inválidos en Request

```bash
curl -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "description": "muy corta",
    "price": -10,
    "stock": -5,
    "categoryId": 999
  }'

# Response (400 Bad Request):
{
  "status": 400,
  "message": "Validación fallida: El nombre del producto no puede estar vacío",
  "timestamp": "2026-04-06T15:21:00"
}
```

### C. Recurso No Encontrado

```bash
curl -X GET http://localhost:8081/api/products/9999

# Response (404 Not Found):
{
  "status": 404,
  "message": "Producto no encontrado con ID: 9999",
  "timestamp": "2026-04-06T15:22:00"
}
```

### D. Sin Token en Endpoint Protegido

```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Test", ...}'

# Response (401 Unauthorized):
{
  "status": 401,
  "message": "Token no proporcionado o inválido",
  "timestamp": "2026-04-06T15:23:00"
}
```

---

## 5️⃣ Flujo Completo de Ejemplo

```bash
# 1. Login como ADMIN
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@example.com", "password": "123456"}' \
  | grep -o '"token":"[^"]*' | cut -d'"' -f4)

# 2. Crear categoría (ADMIN)
CATEGORY_ID=$(curl -s -X POST http://localhost:8081/api/categories \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Nuevos Productos", "description": "Últimos lanzamientos"}' \
  | grep -o '"id":[0-9]*' | cut -d':' -f2)

# 3. Login como USER
USER_TOKEN=$(curl -s -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "seller@example.com", "password": "123456"}' \
  | grep -o '"token":"[^"]*' | cut -d'"' -f4)

# 4. Crear producto (USER)
PRODUCT_ID=$(curl -s -X POST http://localhost:8081/api/products \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"name\": \"Mi Producto\", \"description\": \"Descripción\", \"price\": 99.99, \"stock\": 50, \"categoryId\": $CATEGORY_ID}" \
  | grep -o '"id":[0-9]*' | cut -d':' -f2)

# 5. Ver producto (PUBLIC)
curl -s -X GET http://localhost:8081/api/products/$PRODUCT_ID | jq .

# 6. Actualizar producto (USER)
curl -s -X PUT http://localhost:8081/api/products/$PRODUCT_ID \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"name\": \"Mi Producto Actualizado\", \"description\": \"Nueva descripción\", \"price\": 89.99, \"stock\": 45, \"categoryId\": $CATEGORY_ID}" | jq .

# 7. Buscar por categoría (PUBLIC)
curl -s -X GET http://localhost:8081/api/products/category/$CATEGORY_ID | jq .
```

---

## 🛠️ Herramientas Recomendadas

- **cURL**: Línea de comandos (ejemplos arriba)
- **Postman**: GUI visual, colecciones
- **Insomnia**: Alternativa a Postman
- **Thunder Client**: Plugin VSCode
- **RestClient VSCode**: Plugin para tests en VSCode

---

## 📝 Notas Importantes

- Todos los tokens JWT expiran en **24 horas**
- Todos los endpoints retornan JSON
- Use `-H "Content-Type: application/json"` para POST/PUT
- El servidor corre en `http://localhost:8081` (puerto 8081)
- Los productos se asignan automáticamente al usuario autenticado
- Las categorías solo pueden ser creadas/actualizadas por ADMIN
