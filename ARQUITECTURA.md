## Estructura de Nuevas Clases - Guía de Extensibilidad

### 📊 Diagrama de Relaciones

```
BaseEntity (Clase abstracta)
    ↓
categorías y usuarios

CategoryEntity 
    ↓ (hasMany)
ProductEntity
    ↓ (belongsTo)
UserEntity

ProductResponse
    ↓ (contiene)
CategoryResponse + User info

ProductService
    ↓ (depende de)
ProductRepository + CategoryService + UserService
```

---

### 🏗️ Estructura de Capas

```
ENTIDADES (Entity Layer)
├── BaseEntity.java (abstracta)
├── CategoryEntity.java
└── ProductEntity.java

REPOSITORIOS (Data Access Layer)
├── BaseRepository.java (interfaz genérica)
├── CategoryRepository.java
└── ProductRepository.java

SERVICIOS (Business Logic Layer)
├── CategoryService.java
└── ProductService.java

DTOs (Data Transfer Objects)
├── CategoryRequest.java + CategoryResponse.java
└── ProductRequest.java + ProductResponse.java

CONTROLLERS (API Layer)
├── CategoryController.java
├── ProductController.java
└── UserController.java

SEGURIDAD (Security Config)
└── SecurityConfig.java (actualizado)
```

---

### 🔧 Cómo Extender el Proyecto

#### Opción 1: Crear una Nueva Entidad Simple (ej: Review)

1. **Crear la entidad** `ReviewEntity.java`:
   ```java
   @Entity
   @Table(name = "reviews")
   public class ReviewEntity extends BaseEntity {
       private String content;
       private Integer rating;
       
       @ManyToOne
       private ProductEntity product;
       
       @ManyToOne
       private UserEntity author;
   }
   ```

2. **Crear el repositorio** `ReviewRepository.java`:
   ```java
   @Repository
   public interface ReviewRepository extends BaseRepository<ReviewEntity, Long> {
       List<ReviewEntity> findByProduct_Id(Long productId);
   }
   ```

3. **Crear el servicio** `ReviewService.java`:
   - Métodos: `createReview()`, `findByProduct()`, `deleteReview()`
   - Validaciones: usuario no puede reviewear su propio producto

4. **Crear DTOs**: `ReviewRequest.java` y `ReviewResponse.java`

5. **Crear el controller** `ReviewController.java`:
   - Endpoints: GET, POST (usuarios autenticados), DELETE

6. **Actualizar SecurityConfig**: Agregar rutas para reviews

---

#### Opción 2: Agregar Campos a Entidades Existentes

##### Ejemplo: Agregar imagen a ProductEntity

**Pasos**:
1. Agregar campo en `ProductEntity.java`:
   ```java
   @Column(name = "image_url")
   private String imageUrl;
   ```

2. Agregar validación en `ProductRequest.java`:
   ```java
   @Pattern(regexp = "^https?://.*\\.(jpg|png|gif)$")
   private String imageUrl;
   ```

3. Actualizar `ProductResponse.java`:
   ```java
   private String imageUrl;
   ```

4. Los servicios automáticamente soportarán el nuevo campo

---

#### Opción 3: Agregar Métodos de Búsqueda Complejos

**Ejemplo**: Buscar productos por rango de precio

1. Agregar método en `ProductRepository.java`:
   ```java
   List<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice);
   ```

2. Agregar método en `ProductService.java`:
   ```java
   public List<ProductResponse> findProductsByPriceRange(Double min, Double max) {
       return productRepository.findByPriceBetween(min, max)
           .stream()
           .map(this::toResponse)
           .toList();
   }
   ```

3. Agregar endpoint en `ProductController.java`:
   ```java
   @GetMapping("/price-range")
   public List<ProductResponse> getByPriceRange(
       @RequestParam Double min,
       @RequestParam Double max) {
       return productService.findProductsByPriceRange(min, max);
   }
   ```

---

### ✅ Checklist para Nueva Característica

- [ ] **Entidad creada** (extiende BaseEntity)
- [ ] **Repositorio creado** (extiende BaseRepository)
- [ ] **Servicio creado** (@Service, @Transactional)
- [ ] **DTOs creados** (Request y Response)
- [ ] **Controller creado** (@RestController)
- [ ] **SecurityConfig actualizado** (permisos de acceso)
- [ ] **Validaciones agregadas** (@Valid, @NotNull, @Min, etc.)
- [ ] **Compilación exitosa** (`./mvnw clean compile`)
- [ ] **Tests creados** (si aplica)

---

### 🎯 Reglas de Oro

1. **Siempre extender BaseEntity** para nuevas entidades - Proporciona auditoría automática
2. **DTOs siempre en pares** (Request/Response) - Seguridad y flexibilidad
3. **Validación en DTOs** - Los requests deben validarse antes de llegar al servicio
4. **Comentarios en puntos de extensión** - Cada clase marca dónde se puede cambiar
5. **Transacciones correctas** - readOnly=true para queries, @Transactional para cambios
6. **Manejo de excepciones** - Usar ResourceNotFoundException para no encontrado
7. **Conversión Entity→DTO** - Nunca exponer entidades directamente en respuestas

---

### 📝 Archivos Principales y su Propósito

| Archivo | Propósito | Modificar Para... |
|---------|----------|-------------------|
| BaseEntity | Auditoría automática | Agregar campos de auditoría (ej: createdBy) |
| BaseRepository | Métodos CRUD genéricos | Agregar métodos comunes a todos los repos |
| CategoryEntity | Clasificación de productos | Agregar subcategorías o imágenes |
| ProductEntity | Productos | Agregar atributos (color, tamaño, etc) |
| CategoryService | Lógica de categorías | Validaciones especiales de categoría |
| ProductService | Lógica de productos | Cálculos (ej: aplicar descuentos) |
| SecurityConfig | Permisos por ruta | Definir quién puede hacer qué |

---

### 🚀 Próximos Pasos Sugeridos

1. **Crear entidad Cart/Order** - Para compras
2. **Agregar Reviews** - Para calificaciones de productos
3. **Implement Paginación** - En endpoints que listan muchos datos
4. **Agregar Búsqueda Avanzada** - Filtros por múltiples criterios
5. **Crear Entidad Inventory** - Separar gestión de stock
6. **Agregar Notificaciones** - Usar WebSocket o eventos
7. **Implementar Roles Más Finos** - VENDOR, MODERATOR, etc
