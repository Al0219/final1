-- Esquema cafeteria adaptado a PostgreSQL
-- Ejecutar en BD: final1

-- Tipos adaptados:
--  - AUTO_INCREMENT -> BIGSERIAL
--  - TINYINT(1) -> BOOLEAN
--  - DATETIME -> TIMESTAMP WITHOUT TIME ZONE
--  - ON UPDATE CURRENT_TIMESTAMP -> trigger BEFORE UPDATE

-- Tabla: proveedor
CREATE TABLE IF NOT EXISTS proveedor (
  id            BIGSERIAL PRIMARY KEY,
  nombre        VARCHAR(150) NOT NULL,
  pcontacto     VARCHAR(150),
  telefono      VARCHAR(30),
  email         VARCHAR(150),
  direccion     VARCHAR(200),
  activo        BOOLEAN NOT NULL DEFAULT TRUE,
  creado_en     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: cliente
CREATE TABLE IF NOT EXISTS cliente (
  id            BIGSERIAL PRIMARY KEY,
  nombres       VARCHAR(120) NOT NULL,
  apellidos     VARCHAR(120) NOT NULL,
  nit           VARCHAR(25),
  telefono      VARCHAR(30),
  email         VARCHAR(150),
  direccion     VARCHAR(200),
  activo        BOOLEAN NOT NULL DEFAULT TRUE,
  creado_en     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_cliente_nit UNIQUE (nit)
);

-- Tabla: categoria
CREATE TABLE IF NOT EXISTS categoria (
  id            BIGSERIAL PRIMARY KEY,
  nombre        VARCHAR(100) NOT NULL,
  descripcion   VARCHAR(200),
  activo        BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
);

-- Tabla: producto
CREATE TABLE IF NOT EXISTS producto (
  id                 BIGSERIAL PRIMARY KEY,
  sku                VARCHAR(50)  NOT NULL,
  nombre             VARCHAR(150) NOT NULL,
  descripcion        VARCHAR(250),
  precio_compra_def  NUMERIC(12,2) DEFAULT 0.00,
  precio_venta       NUMERIC(12,2) NOT NULL,
  stock              INT NOT NULL DEFAULT 0,
  categoria_id       BIGINT NOT NULL,
  proveedor_id       BIGINT,
  activo             BOOLEAN NOT NULL DEFAULT TRUE,
  creado_en          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_producto_sku UNIQUE (sku),
  CONSTRAINT fk_prod_cat  FOREIGN KEY (categoria_id)  REFERENCES categoria(id),
  CONSTRAINT fk_prod_prov FOREIGN KEY (proveedor_id)  REFERENCES proveedor(id),
  CONSTRAINT ck_stock_nonneg CHECK (stock >= 0)
);

-- Trigger para actualizado_en en producto (simula ON UPDATE CURRENT_TIMESTAMP)
CREATE OR REPLACE FUNCTION set_current_timestamp_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.actualizado_en = CURRENT_TIMESTAMP;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_trigger WHERE tgname = 'trg_producto_set_updated_at'
  ) THEN
    CREATE TRIGGER trg_producto_set_updated_at
    BEFORE UPDATE ON producto
    FOR EACH ROW EXECUTE FUNCTION set_current_timestamp_updated_at();
  END IF;
END $$;

-- Tabla: compra
CREATE TABLE IF NOT EXISTS compra (
  id            BIGSERIAL PRIMARY KEY,
  proveedor_id  BIGINT NOT NULL,
  fecha         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observacion   VARCHAR(250),
  total         NUMERIC(14,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT fk_compra_prov FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
);

-- Tabla: compra_detalle
CREATE TABLE IF NOT EXISTS compra_detalle (
  id             BIGSERIAL PRIMARY KEY,
  compra_id      BIGINT NOT NULL,
  producto_id    BIGINT NOT NULL,
  cantidad       INT    NOT NULL,
  costo_unitario NUMERIC(12,2) NOT NULL,
  subtotal       NUMERIC(14,2) NOT NULL,
  CONSTRAINT fk_cdet_compra   FOREIGN KEY (compra_id)   REFERENCES compra(id),
  CONSTRAINT fk_cdet_producto FOREIGN KEY (producto_id) REFERENCES producto(id),
  CONSTRAINT ck_cdet_cant CHECK (cantidad > 0)
);

-- Tabla: venta
CREATE TABLE IF NOT EXISTS venta (
  id            BIGSERIAL PRIMARY KEY,
  cliente_id    BIGINT,
  fecha         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observacion   VARCHAR(250),
  total         NUMERIC(14,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT fk_venta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabla: venta_detalle
CREATE TABLE IF NOT EXISTS venta_detalle (
  id               BIGSERIAL PRIMARY KEY,
  venta_id         BIGINT NOT NULL,
  producto_id      BIGINT NOT NULL,
  cantidad         INT    NOT NULL,
  precio_unitario  NUMERIC(12,2) NOT NULL,
  descuento        NUMERIC(12,2) NOT NULL DEFAULT 0.00,
  subtotal         NUMERIC(14,2) NOT NULL,
  CONSTRAINT fk_vdet_venta    FOREIGN KEY (venta_id)   REFERENCES venta(id),
  CONSTRAINT fk_vdet_producto FOREIGN KEY (producto_id) REFERENCES producto(id),
  CONSTRAINT ck_vdet_cant CHECK (cantidad > 0)
);

