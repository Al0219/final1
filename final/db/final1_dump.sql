-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg120+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-11-08 15:46:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 225 (class 1255 OID 24683)
-- Name: on_compra_detalle_insert(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.on_compra_detalle_insert() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF NEW.activo THEN
    UPDATE producto
    SET stock = stock + NEW.cantidad
    WHERE id = NEW.producto_id;
  END IF;
  RETURN NEW;
END$$;


ALTER FUNCTION public.on_compra_detalle_insert() OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 24686)
-- Name: on_venta_detalle_insert(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.on_venta_detalle_insert() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE s INT;
BEGIN
  IF NEW.activo THEN
    SELECT stock INTO s FROM producto WHERE id = NEW.producto_id;
    IF s IS NULL THEN
      RAISE EXCEPTION 'Producto % no existe', NEW.producto_id;
    END IF;
    IF s < NEW.cantidad THEN
      RAISE EXCEPTION 'Stock insuficiente para producto % (stock %, intenta vender %)',
        NEW.producto_id, s, NEW.cantidad;
    END IF;

    UPDATE producto
    SET stock = stock - NEW.cantidad
    WHERE id = NEW.producto_id;
  END IF;
  RETURN NEW;
END$$;


ALTER FUNCTION public.on_venta_detalle_insert() OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 32768)
-- Name: set_current_timestamp_updated_at(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.set_current_timestamp_updated_at() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  NEW.actualizado_en = CURRENT_TIMESTAMP;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.set_current_timestamp_updated_at() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 24597)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion character varying(200),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24589)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id bigint NOT NULL,
    nombres character varying(120) NOT NULL,
    apellidos character varying(120) NOT NULL,
    telefono character varying(30),
    email character varying(150),
    direccion character varying(200),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24622)
-- Name: compra; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compra (
    id bigint NOT NULL,
    proveedor_id bigint NOT NULL,
    fecha timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    observacion character varying(250),
    total numeric(14,2) DEFAULT 0.00 NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.compra OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24635)
-- Name: compra_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compra_detalle (
    id bigint NOT NULL,
    compra_id bigint NOT NULL,
    producto_id bigint NOT NULL,
    cantidad integer NOT NULL,
    costo_unitario numeric(12,2) NOT NULL,
    subtotal numeric(14,2) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    CONSTRAINT ck_cdet_cant CHECK ((cantidad > 0))
);


ALTER TABLE public.compra_detalle OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24603)
-- Name: producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto (
    id bigint NOT NULL,
    nombre character varying(150) NOT NULL,
    descripcion character varying(250),
    precio_compra_def numeric(12,2) DEFAULT 0.00,
    precio_venta numeric(12,2) NOT NULL,
    stock integer DEFAULT 0 NOT NULL,
    categoria_id bigint NOT NULL,
    proveedor_id bigint,
    activo boolean DEFAULT true NOT NULL,
    CONSTRAINT ck_stock_nonneg CHECK ((stock >= 0))
);


ALTER TABLE public.producto OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24581)
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedor (
    id bigint NOT NULL,
    nombre character varying(150) NOT NULL,
    persona_contacto character varying(150),
    telefono character varying(30),
    email character varying(150),
    direccion character varying(200),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.proveedor OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24652)
-- Name: venta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venta (
    id bigint NOT NULL,
    cliente_id bigint,
    fecha timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    observacion character varying(250),
    total numeric(14,2) DEFAULT 0.00 NOT NULL,
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.venta OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24665)
-- Name: venta_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venta_detalle (
    id bigint NOT NULL,
    venta_id bigint NOT NULL,
    producto_id bigint NOT NULL,
    cantidad integer NOT NULL,
    precio_unitario numeric(12,2) NOT NULL,
    descuento numeric(12,2) DEFAULT 0.00 NOT NULL,
    subtotal numeric(14,2) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    CONSTRAINT ck_vdet_cant CHECK ((cantidad > 0))
);


ALTER TABLE public.venta_detalle OWNER TO postgres;

--
-- TOC entry 3432 (class 0 OID 24597)
-- Dependencies: 219
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoria (id, nombre, descripcion, activo) FROM stdin;
1	Almacenamiento	USB y memorias	t
2	Periféricos	Teclados y mouse	t
3	Cables/Adaptadores	Video y energía	t
\.


--
-- TOC entry 3431 (class 0 OID 24589)
-- Dependencies: 218
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id, nombres, apellidos, telefono, email, direccion, activo) FROM stdin;
1	Juan	Pérez	5560-1001	juan.perez@mail.com	Huehuetenango	t
2	María	López	5560-1002	maria.lopez@mail.com	Zona 5	t
3	Carlos	Gómez	5560-1003	carlos.gomez@mail.com	Zona 8	t
\.


--
-- TOC entry 3434 (class 0 OID 24622)
-- Dependencies: 221
-- Data for Name: compra; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.compra (id, proveedor_id, fecha, observacion, total, activo) FROM stdin;
1	1	2025-11-08 16:22:23.157079+00	Compra inicial	2320.00	t
2	2	2025-11-08 16:22:23.157079+00	Lote periféricos/cables	5700.00	t
3	3	2025-11-08 16:22:23.157079+00	Adaptadores + HDMI	2400.00	t
4	1	2025-11-08 16:22:23.157079+00	Reposición USB/Mouse	1280.00	t
5	2	2025-11-08 16:22:23.157079+00	Periféricos extra	2130.00	t
\.


--
-- TOC entry 3435 (class 0 OID 24635)
-- Dependencies: 222
-- Data for Name: compra_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.compra_detalle (id, compra_id, producto_id, cantidad, costo_unitario, subtotal, activo) FROM stdin;
1	1	1	50	24.00	1200.00	t
2	1	2	40	28.00	1120.00	t
3	2	3	30	170.00	5100.00	t
4	2	4	60	10.00	600.00	t
5	3	5	20	110.00	2200.00	t
6	3	4	20	10.00	200.00	t
7	4	1	30	24.00	720.00	t
8	4	2	20	28.00	560.00	t
9	5	3	10	175.00	1750.00	t
10	5	4	40	9.50	380.00	t
\.


--
-- TOC entry 3433 (class 0 OID 24603)
-- Dependencies: 220
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producto (id, nombre, descripcion, precio_compra_def, precio_venta, stock, categoria_id, proveedor_id, activo) FROM stdin;
4	Cable HDMI 2m	HDMI 2.0	12.00	25.00	107	3	2	t
1	Memoria USB 16GB	Memoria USB 3.0 16GB	25.00	35.00	55	1	1	t
2	Mouse Óptico	Mouse USB 1200dpi	30.00	45.00	52	2	1	t
3	Teclado Mecánico	Switch blue	180.00	250.00	37	2	2	t
5	Adaptador 90W	Cargador universal 90W	120.00	180.00	15	3	3	t
\.


--
-- TOC entry 3430 (class 0 OID 24581)
-- Dependencies: 217
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proveedor (id, nombre, persona_contacto, telefono, email, direccion, activo) FROM stdin;
1	Global Imports	Luis Ortega	5551-0001	global@imports.com	Zona 1	t
2	Andes Trading	María Ruiz	5551-0002	ventas@andes.com	Zona 10	t
3	Pacífico Ltda	Carlos Mena	5551-0003	contacto@pacifico.com	Zona 4	t
\.


--
-- TOC entry 3436 (class 0 OID 24652)
-- Dependencies: 223
-- Data for Name: venta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.venta (id, cliente_id, fecha, observacion, total, activo) FROM stdin;
1	1	2025-11-08 16:22:23.157079+00	Mostrador	475.00	t
2	2	2025-11-08 16:22:23.157079+00	Pedido web	705.00	t
3	\N	2025-11-08 16:22:23.157079+00	Contado	770.00	t
4	3	2025-11-08 16:22:23.157079+00	Factura crédito	660.00	t
5	2	2025-11-08 16:22:23.157079+00	Con descuento	600.00	t
\.


--
-- TOC entry 3437 (class 0 OID 24665)
-- Dependencies: 224
-- Data for Name: venta_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.venta_detalle (id, venta_id, producto_id, cantidad, precio_unitario, descuento, subtotal, activo) FROM stdin;
1	1	1	10	35.00	0.00	350.00	t
2	1	4	5	25.00	0.00	125.00	t
3	2	2	5	45.00	0.00	225.00	t
4	2	3	2	250.00	20.00	480.00	t
5	3	4	8	25.00	0.00	200.00	t
6	3	5	3	190.00	0.00	570.00	t
7	4	1	15	35.00	0.00	525.00	t
8	4	2	3	45.00	0.00	135.00	t
9	5	3	1	250.00	0.00	250.00	t
10	5	5	2	180.00	10.00	350.00	t
\.


--
-- TOC entry 3263 (class 2606 OID 24602)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 24596)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 3269 (class 2606 OID 24641)
-- Name: compra_detalle compra_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compra_detalle
    ADD CONSTRAINT compra_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 24629)
-- Name: compra compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compra
    ADD CONSTRAINT compra_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 24611)
-- Name: producto producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (id);


--
-- TOC entry 3259 (class 2606 OID 24588)
-- Name: proveedor proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 24672)
-- Name: venta_detalle venta_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venta_detalle
    ADD CONSTRAINT venta_detalle_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 24659)
-- Name: venta venta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venta
    ADD CONSTRAINT venta_pkey PRIMARY KEY (id);


--
-- TOC entry 3283 (class 2620 OID 24685)
-- Name: compra_detalle trg_cdet_ai; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_cdet_ai AFTER INSERT ON public.compra_detalle FOR EACH ROW EXECUTE FUNCTION public.on_compra_detalle_insert();


--
-- TOC entry 3282 (class 2620 OID 32769)
-- Name: producto trg_producto_set_updated_at; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_producto_set_updated_at BEFORE UPDATE ON public.producto FOR EACH ROW EXECUTE FUNCTION public.set_current_timestamp_updated_at();


--
-- TOC entry 3284 (class 2620 OID 24687)
-- Name: venta_detalle trg_vdet_ai; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_vdet_ai AFTER INSERT ON public.venta_detalle FOR EACH ROW EXECUTE FUNCTION public.on_venta_detalle_insert();


--
-- TOC entry 3277 (class 2606 OID 24642)
-- Name: compra_detalle fk_cdet_compra; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compra_detalle
    ADD CONSTRAINT fk_cdet_compra FOREIGN KEY (compra_id) REFERENCES public.compra(id);


--
-- TOC entry 3278 (class 2606 OID 24647)
-- Name: compra_detalle fk_cdet_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compra_detalle
    ADD CONSTRAINT fk_cdet_producto FOREIGN KEY (producto_id) REFERENCES public.producto(id);


--
-- TOC entry 3276 (class 2606 OID 24630)
-- Name: compra fk_compra_prov; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compra
    ADD CONSTRAINT fk_compra_prov FOREIGN KEY (proveedor_id) REFERENCES public.proveedor(id);


--
-- TOC entry 3274 (class 2606 OID 24612)
-- Name: producto fk_prod_cat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT fk_prod_cat FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 3275 (class 2606 OID 24617)
-- Name: producto fk_prod_prov; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT fk_prod_prov FOREIGN KEY (proveedor_id) REFERENCES public.proveedor(id);


--
-- TOC entry 3280 (class 2606 OID 24678)
-- Name: venta_detalle fk_vdet_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venta_detalle
    ADD CONSTRAINT fk_vdet_producto FOREIGN KEY (producto_id) REFERENCES public.producto(id);


--
-- TOC entry 3281 (class 2606 OID 24673)
-- Name: venta_detalle fk_vdet_venta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venta_detalle
    ADD CONSTRAINT fk_vdet_venta FOREIGN KEY (venta_id) REFERENCES public.venta(id);


--
-- TOC entry 3279 (class 2606 OID 24660)
-- Name: venta fk_venta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venta
    ADD CONSTRAINT fk_venta_cliente FOREIGN KEY (cliente_id) REFERENCES public.cliente(id);


-- Completed on 2025-11-08 15:46:53

--
-- PostgreSQL database dump complete
--
