# API de Cafeteria

Aplicacion Spring Boot conectada a PostgreSQL (contenedor `mi-postgres`, base `final1`). Endpoints REST para administrar catalogo y movimientos (compras/ventas).

## Endpoints principales

### Categorias
- `GET /api/categorias`
- `GET /api/categorias/{id}`
- `POST /api/categorias`
  ```json
  { "nombre": "Perifericos", "descripcion": "Teclados y mouse", "activo": true }
  ```
- `PUT /api/categorias/{id}`
- `DELETE /api/categorias/{id}`

### Clientes
- `GET /api/clientes`
- `GET /api/clientes/{id}`
- `POST /api/clientes`
  ```json
  { "nombres": "Ana", "apellidos": "Soto", "telefono": "5555-0000", "email": "ana@mail.com", "direccion": "Zona 10", "activo": true }
  ```
- `PUT /api/clientes/{id}`
- `DELETE /api/clientes/{id}`

### Proveedores
- `GET /api/proveedores`
- `GET /api/proveedores/{id}`
- `POST /api/proveedores`
  ```json
  { "nombre": "Global Imports", "personaContacto": "Luis Ortega", "telefono": "5551-0001", "email": "ventas@imports.com", "direccion": "Zona 1", "activo": true }
  ```
- `PUT /api/proveedores/{id}`
- `DELETE /api/proveedores/{id}`

### Productos
- `GET /api/productos`
- `GET /api/productos/{id}`
- `POST /api/productos`
  ```json
  {
    "nombre": "Mouse optico",
    "descripcion": "USB 1200dpi",
    "precioCompraDef": 30.00,
    "precioVenta": 45.00,
    "stock": 25,
    "categoriaId": 2,
    "proveedorId": 1,
    "activo": true
  }
  ```
- `PUT /api/productos/{id}`
- `DELETE /api/productos/{id}`

### Compras
- `GET /api/compras`
- `GET /api/compras/{id}`
- `POST /api/compras`
  ```json
  { "proveedorId": 1, "fecha": "2025-11-08T12:00:00-06:00", "observacion": "Reposicion", "total": 1500.00, "activo": true }
  ```
- `PUT /api/compras/{id}`
- `DELETE /api/compras/{id}`

#### Compra Detalle
- `GET /api/compras/detalles`
- `GET /api/compras/detalles/{id}`
- `POST /api/compras/detalles`
  ```json
  { "compraId": 1, "productoId": 2, "cantidad": 10, "costoUnitario": 28.00, "subtotal": 280.00, "activo": true }
  ```
- `PUT /api/compras/detalles/{id}`
- `DELETE /api/compras/detalles/{id}`

### Ventas
- `GET /api/ventas`
- `GET /api/ventas/{id}`
- `POST /api/ventas`
  ```json
  { "clienteId": 1, "fecha": "2025-11-08T15:00:00-06:00", "observacion": "Mostrador", "total": 475.00, "activo": true }
  ```
- `PUT /api/ventas/{id}`
- `DELETE /api/ventas/{id}`

#### Venta Detalle
- `GET /api/ventas/detalles`
- `GET /api/ventas/detalles/{id}`
- `POST /api/ventas/detalles`
  ```json
  { "ventaId": 1, "productoId": 4, "cantidad": 5, "precioUnitario": 25.00, "descuento": 0.00, "subtotal": 125.00, "activo": true }
  ```
- `PUT /api/ventas/detalles/{id}`
- `DELETE /api/ventas/detalles/{id}`

### Historial de movimientos
- `GET /api/movimientos`
  - Filtros opcionales: `?from=2025-11-07T00:00:00-06:00&to=2025-11-09T00:00:00-06:00`
  - Combina compras y ventas ordenadas por fecha descendente.

## Cargar la base de datos (dump completo)
El dump `db/final1_dump.sql` contiene el esquema + datos demo.

1. Levanta PostgreSQL (Docker):
   ```bash
   docker run --name mi-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres:16
   ```
2. Crea la base `final1` y ejecuta el dump:
   ```bash
   docker exec -it mi-postgres psql -U postgres -c "CREATE DATABASE final1;"
   docker exec -i mi-postgres psql -U postgres -d final1 < db/final1_dump.sql
   ```
   > Si usas Postgres local: `psql -U postgres -d final1 -f db/final1_dump.sql`

## Ejecutar la app
```bash
cd final
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```
Variables clave (`src/main/resources/application.properties`):
- `spring.datasource.url=jdbc:postgresql://localhost:5432/final1`
- `spring.datasource.username=postgres`
- `spring.datasource.password=mysecretpassword`
- `spring.jpa.hibernate.ddl-auto=validate`
