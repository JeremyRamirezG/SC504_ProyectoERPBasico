/* SCRIPT PARA LA CREACIÓN DEL SCHEMA DEL GRUPO# */
-- USUARIO SYS DE LA BASE DE DATOS
-- SE ELIMINA EL USUARIO A UTILIZAR PARA EVITAR PROBLEMAS DE CONFLICTOS DURANTE LA EJECUCION DEL SCRIP.
--DROP USER ADMINT CASCADE;

-- CREACION DEL USUARIO, DEFINICION DEL TABLE SPACE.
ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;
CREATE USER ADMIN_TIENDA IDENTIFIED BY admin_tienda_proyecto;

ALTER USER ADMIN_TIENDA DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
ALTER USER ADMIN_TIENDA TEMPORARY TABLESPACE TEMP;

-- DAR PERMISOS AL USUARIO ADMINISTRADOR DEL SCHEMA PARA QUE PUEDA REALIZAR ACCIONES.
GRANT CONNECT TO ADMIN_TIENDA;

GRANT CREATE SESSION, CREATE VIEW, CREATE TABLE, ALTER SESSION, CREATE SEQUENCE TO ADMIN_TIENDA;
GRANT CREATE SYNONYM, CREATE DATABASE LINK, RESOURCE, UNLIMITED TABLESPACE TO ADMIN_TIENDA;

-- SE PONE LA SESIÓN EN COSTA RICA Y LENGUAJE ESPAÑOL
ALTER SESSION SET NLS_LANGUAGE = 'SPANISH';
ALTER SESSION SET NLS_TERRITORY = 'COSTA RICA';

-- USUARIO ADMIN_TIENDA
-- SELECCION DEL SCHEMA CREADO
ALTER SESSION SET CURRENT_SCHEMA = ADMIN_TIENDA;

CREATE SEQUENCE SEQ_ERROR
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE SEQ_USUARIOS_AUD
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE SEQ_FACTURAS_AUD
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE SEQ_PRODUCTOS_AUD
    START WITH 1
    INCREMENT BY 1;

-- CREACION DE TABLAS
/* TBL_ERRORES */
CREATE TABLE TBL_ERRORES (
    ID_ERROR NUMBER PRIMARY KEY,
    NOMBRE_SP VARCHAR(50),
    MSJ_ERROR VARCHAR(1000),
    FECHA_ERROR TIMESTAMP
);

CREATE TABLE TBL_AUDITORIA_FACTURAS (
    ID_AUD_FACTURA NUMBER PRIMARY KEY,
    ACCION VARCHAR2(25),
    ID_FACTURA NUMBER NOT NULL,
    FECHA_REGISTRO DATE NOT NULL,
    ID_CLIENTE VARCHAR2(9) NOT NULL,
    ID_EMPLEADO VARCHAR2(9) NOT NULL,
    TOTAL NUMBER,
    TIPO_PAGO VARCHAR2(50) NOT NULL
);

CREATE TABLE TBL_AUDITORIA_PRODUCTOS (
    ID_AUD_PRODUCTO NUMBER PRIMARY KEY,
    ACCION VARCHAR2(25),
    ID_PRODUCTO VARCHAR2(20) NOT NULL,
    DESCRIPCION VARCHAR2(250) NOT NULL,
    PRECIO NUMBER NOT NULL,
    PROVEEDOR VARCHAR2(9) NOT NULL,
    FECHA_INGRESO DATE,
    CANTIDAD NUMBER NOT NULL
    
);

CREATE TABLE TBL_AUDITORIA_USUARIOS (
    ID_AUD_USUARIO NUMBER PRIMARY KEY,
    ACCION VARCHAR2(25) NOT NULL,
    CEDULA VARCHAR2(9) NOT NULL,
    PRIMER_NOMBRE VARCHAR2(50) NOT NULL,
    PRIMER_APELLIDO VARCHAR2(50) NOT NULL,
    DIRECCION VARCHAR2(250) NOT NULL,
    TIPO_USUARIO VARCHAR2(25) NOT NULL
);

/* TBL_EMPLEADOS */
CREATE TABLE TBL_EMPLEADOS (
    CEDULA VARCHAR2(9) NOT NULL,
    ID_EMPLEADO VARCHAR2(6) NOT NULL,
    PRIMER_NOMBRE VARCHAR2(50) NOT NULL,
    SEGUNDO_NOMBRE VARCHAR2(50),
    PRIMER_APELLIDO VARCHAR2(50) NOT NULL,
    SEGUNDO_APELLIDO VARCHAR2(50),
    DIRECCION VARCHAR2(250) NOT NULL,
    PUESTO VARCHAR2(25) NOT NULL,
    SALARIO NUMBER NOT NULL
);

/* TBL_CLIENTES */
CREATE TABLE TBL_CLIENTES (
    CEDULA VARCHAR2(9) NOT NULL,
	PRIMER_NOMBRE VARCHAR2(50) NOT NULL,
	SEGUNDO_NOMBRE VARCHAR2(50),
	PRIMER_APELLIDO VARCHAR2(50) NOT NULL,
	SEGUNDO_APELLIDO VARCHAR2(50),
	DIRECCION VARCHAR2(250)
);

/* TBL_ROLES */
CREATE TABLE TBL_ROLES (
    ID_ROL VARCHAR2(5) NOT NULL,
	DESCRIPCION VARCHAR2(250) NOT NULL
);

/* TBL_PRODUCTOS */
CREATE TABLE TBL_PRODUCTOS (
    ID_PRODUCTO VARCHAR2(20) NOT NULL,
    DESCRIPCION VARCHAR2(250) NOT NULL,
    PRECIO NUMBER NOT NULL,
    PROVEEDOR VARCHAR2(9) NOT NULL,
    FECHA_INGRESO DATE,
    CANTIDAD NUMBER NOT NULL
);

/* TBL_PROOVEDORES */
CREATE TABLE TBL_PROVEEDORES (
    ID_PROOVEDOR VARCHAR2(20) NOT NULL,
    DESCRIPCION VARCHAR2(2500) NOT NULL,
	FECHA_INGRESO DATE
);

/* TBL_ENCABEZADO_FACTURA */
CREATE TABLE TBL_ENCABEZADO_FACTURA (
    ID_FACTURA NUMBER NOT NULL,
    FECHA_REGISTRO DATE NOT NULL,
    ID_CLIENTE VARCHAR2(9) NOT NULL,
    ID_EMPLEADO VARCHAR2(9) NOT NULL,
    TOTAL NUMBER,
    TIPO_PAGO VARCHAR2(50) NOT NULL
);

/* TBL_DETALLE_FACTURA */
CREATE TABLE TBL_DETALLE_FACTURA (
    ID_FACTURA NUMBER NOT NULL,
    LINEA NUMBER NOT NULL,
	ID_PRODUCTO VARCHAR2(5) NOT NULL,
	PRECIO NUMBER NOT NULL,
	CANTIDAD NUMBER NOT NULL
);

/* TBL_TELEFONOS */
CREATE TABLE TBL_TELEFONOS (
    ID_USUARIO VARCHAR2(9) NOT NULL,
    TELEFONO VARCHAR2(15) NOT NULL
);

/* TBL_CORREOS */
CREATE TABLE TBL_CORREOS (
    ID_USUARIO VARCHAR2(9) NOT NULL,
    CORREO VARCHAR2(50) NOT NULL
);

/* TBL_ROLES_EMPLEADOS */
CREATE TABLE TBL_ROLES_EMPLEADOS (
    ID_EMPLEADO VARCHAR2(9) NOT NULL,
    ID_ROL VARCHAR2(5) NOT NULL
);

-- CREACION DE PRIMARY KEYS, UNIQUE COLUMNS E INDEX
/* TBL_EMPLEADOS */
ALTER TABLE TBL_EMPLEADOS
ADD (
    CONSTRAINT PK_EMPLEADOS PRIMARY KEY (CEDULA),
    CONSTRAINT UK_ID_EMPLEADOS UNIQUE (ID_EMPLEADO)
);

/* TBL_CLIENTES */
ALTER TABLE TBL_CLIENTES
ADD (
    CONSTRAINT PK_CLIENTES PRIMARY KEY (CEDULA)
);

/* TBL_ROLES */
ALTER TABLE TBL_ROLES
ADD (
    CONSTRAINT PK_ROLES PRIMARY KEY (ID_ROL)
);

/* TBL_PRODUCTOS */
ALTER TABLE TBL_PRODUCTOS
ADD (
    CONSTRAINT PK_PRODUCTOS PRIMARY KEY (ID_PRODUCTO)
);

/* TBL_PROOVEDORES */
ALTER TABLE TBL_PROOVEDORES
ADD (
    CONSTRAINT PK_PROOVEDORES PRIMARY KEY (ID_PROOVEDOR),
	CONSTRAINT UK_DESC_PROOVEDORES UNIQUE (DESCRIPCION)
);

/* TBL_ENCABEZADO_FACTURA */
ALTER TABLE TBL_ENCABEZADO_FACTURA
ADD (
    CONSTRAINT PK_ENCABEZADO_FACTURA PRIMARY KEY (ID_FACTURA)
);

/* TBL_DETALLE_FACTURA */
ALTER TABLE TBL_DETALLE_FACTURA
ADD (
    CONSTRAINT PK_DETALLE_FACTURA PRIMARY KEY (ID_FACTURA, LINEA)
);

/* TBL_TELEFONOS */
ALTER TABLE TBL_TELEFONOS
ADD (
    CONSTRAINT PK_TELEFONOS PRIMARY KEY (ID_USUARIO, TELEFONO)
);

/* TBL_CORREOS */
ALTER TABLE TBL_CORREOS
ADD (
    CONSTRAINT TBL_CORREOS PRIMARY KEY (ID_USUARIO, CORREO)
);

/* TBL_ROLES_EMPLEADOS */
ALTER TABLE TBL_ROLES_EMPLEADOS
ADD (
    CONSTRAINT PK_ROLES_EMPLEADOS PRIMARY KEY (ID_EMPLEADO, ID_ROL)
);

-- CREACION DE FOREING KEYS
/* TBL_EMPLEADOS */

/* TBL_CLIENTES */

/* TBL_ROLES */

/* TBL_PRODUCTOS */
ALTER TABLE TBL_PRODUCTOS
ADD (
    CONSTRAINT FK_PRODUCTOS_PROOVEDOR FOREIGN KEY (PROOVEDOR) REFERENCES TBL_PROOVEDORES (ID_PROOVEDOR)
);

/* TBL_PROOVEDORES */

/* TBL_ENCABEZADO_FACTURA */
ALTER TABLE TBL_ENCABEZADO_FACTURA
ADD (
    CONSTRAINT FK_FACTURA_CLIENTE FOREIGN KEY (ID_CLIENTE) REFERENCES TBL_CLIENTES (CEDULA),
	CONSTRAINT FK_FACTURA_EMPLEADO FOREIGN KEY (ID_EMPLEADO) REFERENCES TBL_EMPLEADOS (CEDULA)
);

/* TBL_DETALLE_FACTURA */
ALTER TABLE TBL_DETALLE_FACTURA
ADD (
    CONSTRAINT FK_ENCABEZADO_DETALLE FOREIGN KEY (ID_FACTURA) REFERENCES TBL_ENCABEZADO_FACTURA (ID_FACTURA),
	CONSTRAINT FK_FACTURA_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES TBL_PRODUCTOS (ID_PRODUCTO)
);

/* TBL_TELEFONOS */
ALTER TABLE TBL_TELEFONOS
ADD (
    CONSTRAINT FK_TELEFONO_CLIENTE FOREIGN KEY (ID_USUARIO) REFERENCES TBL_CLIENTES (CEDULA)
);

/* TBL_CORREOS */
ALTER TABLE TBL_CORREOS
ADD (
    CONSTRAINT FK_CORREO_CLIENTE FOREIGN KEY (ID_USUARIO) REFERENCES TBL_CLIENTES (CEDULA)
);

/* TBL_ROLES_EMPLEADOS */
ALTER TABLE TBL_ROLES_EMPLEADOS
ADD (
    CONSTRAINT FK_ROL_EMPLEADO FOREIGN KEY (ID_EMPLEADO) REFERENCES TBL_EMPLEADOS (CEDULA),
	CONSTRAINT FK_ROL_ASOCIADO FOREIGN KEY (ID_ROL) REFERENCES TBL_ROLES (ID_ROL)
);

-- LLENAR TABLA DE ROLES
INSERT INTO TBL_ROLES
VALUES ('ADMIN','Administrador de empleados');
INSERT INTO TBL_ROLES
VALUES ('CAJAS','Empleado en cajas');
INSERT INTO TBL_ROLES
VALUES ('REPAR','Repartidor');
INSERT INTO TBL_ROLES
VALUES ('REPON','Reponedor');

-- FUNCIONES
/* Funcion que permite extraer de la base de datos los productos que en posean una cantidad igual o menor a 10, esto con el objetivo de que los administradores sepan
cuando hacer un encargo de nuevos productos*/
create or replace NONEDITIONABLE FUNCTION ProductosMenorA10 RETURN SYS_REFCURSOR IS
    productos_cursor SYS_REFCURSOR;
BEGIN
    OPEN productos_cursor FOR
        SELECT ID_PRODUCTO, DESCRIPCION, PRECIO, PROOVEDOR, CANTIDAD
        FROM TBL_PRODUCTOS
        WHERE CANTIDAD < 10;

    RETURN productos_cursor;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al intentar obtener los productos: ' || SQLERRM);
        RETURN NULL;
END ProductosMenorA10;

/* Funcion que permite validar cual es la factura mas alta del mes con el objetivo que la tienda premie al cliente que mas les compra */
create or replace NONEDITIONABLE FUNCTION ValidarMontoMasAltoCliente RETURN SYS_REFCURSOR IS
    factura_cursor SYS_REFCURSOR;
BEGIN
    OPEN factura_cursor FOR
        SELECT e.id_cliente, e.total
        FROM TBL_ENCABEZADO_FACTURA e
        WHERE e.total = (SELECT MAX(total) FROM TBL_ENCABEZADO_FACTURA);

    RETURN factura_cursor;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se encontraron facturas en la base de datos.');
        RETURN NULL;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al intentar validar el monto más alto: ' || SQLERRM);
        RETURN NULL;
END ValidarMontoMasAltoCliente;

/* GENERAR CODIGO EMPLEADO */
CREATE OR REPLACE FUNCTION FUN_CODIGO_EMPLEADO RETURN VARCHAR2 IS
    NUEVO_CODIGO VARCHAR2(6);
    NUMERO_ACTUAL NUMBER;
BEGIN
    -- OBTENER ULTIMO EMPLEADO
    SELECT MAX(SUBSTR(ID_EMPLEADO, 4)) INTO NUMERO_ACTUAL
    FROM TBL_EMPLEADOS;

    -- INCREMENTAR UN EMPLEADO
	IF NUMERO_ACTUAL IS NULL THEN
		NUMERO_ACTUAL := 1;
	ELSE
		NUMERO_ACTUAL := NUMERO_ACTUAL + 1;
	END IF;
	
    -- GENERAR CODIGO
    NUEVO_CODIGO := 'EMP' || LPAD(NUMERO_ACTUAL, 3, '0');

    -- RETORNAR CODIGO
    RETURN NUEVO_CODIGO;
END;
/

/* RETORNAR LINEA DETALLE FACTURA */
CREATE OR REPLACE FUNCTION FUN_LINEA_FACTURA (ID_FACTURA_VAL NUMBER) RETURN NUMBER IS
    LINEA_ACTUAL NUMBER;
    LINEA_FACTURA NUMBER;
BEGIN
    SELECT MAX(LINEA) INTO LINEA_ACTUAL
    FROM TBL_DETALLE_FACTURA
    WHERE ID_FACTURA = ID_FACTURA_VAL;

    IF LINEA_ACTUAL IS NULL THEN
        LINEA_FACTURA := 1;
    ELSE
        LINEA_FACTURA := LINEA_ACTUAL + 1;
    END IF;

    RETURN LINEA_FACTURA;
END;
/

/* RETORNAR ID FACTURA */
CREATE OR REPLACE FUNCTION FUN_ID_FACTURA RETURN NUMBER IS
    ID_FACTURA NUMBER;
    ID_ACTUAL NUMBER;
BEGIN
    SELECT MAX(ID_FACTURA) INTO ID_ACTUAL
    FROM TBL_ENCABEZADO_FACTURA;

    -- INCREMENTAR UN EMPLEADO
    IF ID_ACTUAL IS NULL THEN
        ID_FACTURA := 1;
    ELSE
        ID_FACTURA := ID_ACTUAL + 1;
    END IF;

    -- RETORNAR CODIGO
    RETURN ID_FACTURA;
END;
/
-- PROCESOS ALMACENADOS
/* Procedimiento para registrar un producto*/
create or replace NONEDITIONABLE PROCEDURE SP_agregar_producto (
    p_id_producto IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_precio IN NUMBER,
    p_proveedor IN VARCHAR2,
    p_fecha_ingreso IN DATE,
    p_cantidad IN NUMBER
)
IS
    v_sql VARCHAR2(1000);
BEGIN
    v_sql := 'INSERT INTO TBL_PRODUCTOS (ID_PRODUCTO, DESCRIPCION, PRECIO, PROVEEDOR, FECHA_INGRESO, CANTIDAD) ' ||
             'VALUES (:1, :2, :3, :4, :5, :6)';

    EXECUTE IMMEDIATE v_sql USING p_id_producto, p_descripcion, p_precio, p_proveedor, p_fecha_ingreso, p_cantidad;


    DBMS_OUTPUT.PUT_LINE('Producto agregado correctamente');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al agregar el producto: ' || SQLERRM);
END SP_agregar_producto;

/* Procedimiento que disminuye la cantidad de productos en compras utilizando id y cantidad como parametros de entrada */
create or replace NONEDITIONABLE PROCEDURE DisminuirCantidadProducto(
    p_id_producto IN VARCHAR2,
    p_cantidad_comprada IN NUMBER
)
IS
    cantidad_actual NUMBER;
BEGIN
    SELECT cantidad
    INTO cantidad_actual
    FROM TBL_PRODUCTOS
    WHERE id_producto = p_id_producto;

    IF cantidad_actual >= p_cantidad_comprada THEN
        UPDATE TBL_PRODUCTOS
        SET cantidad = cantidad_actual - p_cantidad_comprada
        WHERE id_producto = p_id_producto;

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Cantidad de producto actualizada exitosamente.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('No hay suficiente cantidad del producto en stock.');
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se encontró el producto con el ID proporcionado.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error al intentar actualizar la cantidad del producto: ' || SQLERRM);
END DisminuirCantidadProducto;

/* Consultar productos */
create or replace NONEDITIONABLE PROCEDURE SP_buscar_producto(
    palabra_clave IN VARCHAR2,
    productos_cursor OUT SYS_REFCURSOR
)
AS
    v_query VARCHAR2(1000);
BEGIN
    v_query := 'SELECT ID_PRODUCTO, DESCRIPCION, PRECIO, PROVEEDOR, FECHA_INGRESO, CANTIDAD ' ||
               'FROM TBL_PRODUCTOS ' ||
               'WHERE UPPER(DESCRIPCION) LIKE ''%'' || UPPER(:palabra_clave) || ''%''';

    OPEN productos_cursor FOR v_query USING palabra_clave;
END SP_buscar_producto;
	
/* AGREGAR Proveedor */
create or replace NONEDITIONABLE PROCEDURE SP_AgregarProveedor(
    p_ID_PROVEEDOR IN VARCHAR2,
    p_DESCRIPCION IN VARCHAR2,
    p_FECHA_INGRESO IN DATE
)
IS
BEGIN
    INSERT INTO TBL_PROVEEDORES (ID_PROVEEDOR, DESCRIPCION, FECHA_INGRESO)
    VALUES (p_ID_PROVEEDOR, p_DESCRIPCION, p_FECHA_INGRESO);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Proveedor agregado exitosamente.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_AgregarProveedor',SQLERRM, SYSTIMESTAMP);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Error al intentar agregar el proveedor: ' || SQLERRM);
END SP_AgregarProveedor;
/

/* AGREGAR CLIENTE */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_CLIENTE (
    CEDULA IN VARCHAR2,
	CORREO IN VARCHAR2,
	TELEFONO IN VARCHAR2,
	PRIMER_NOMBRE IN VARCHAR2,
	SEGUNDO_NOMBRE IN VARCHAR2,
	PRIMER_APELLIDO IN VARCHAR2,
	SEGUNDO_APELLIDO IN VARCHAR2,
	DIRECCION IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    -- INSERTAR CLIENTE
	INSERT INTO TBL_CLIENTES
	VALUES (CEDULA, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION);
	-- INSERTAR TELEFONO
	INSERT INTO TBL_TELEFONOS
	VALUES (CEDULA, TELEFONO);
	-- INSERTAR CORREO
	INSERT INTO TBL_CORREOS 
	VALUES (CEDULA, CORREO);
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Cliente agregado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_CLIENTE', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Cliente no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* AGREGAR CORREO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_CORREO (
    CEDULA IN VARCHAR2,
	CORREO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- INSERTAR CORREO
	INSERT INTO TBL_CORREOS 
	VALUES (CEDULA, CORREO);
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Correo agregado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_CORREO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Correo no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* ACTUALIZAR CORREO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_ACTUALIZAR_CORREO (
    CEDULA IN VARCHAR2,
	VIEJO_CORREO IN VARCHAR2,
	NUEVO_CORREO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- MODIFICAR CORREO
	UPDATE TBL_CORREOS 
	SET CORREO = NUEVO_CORREO
	WHERE ID_USUARIO = CEDULA AND CORREO = VIEJO_CORREO;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Correo modificado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_ACTUALIZAR_CORREO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Correo no modificado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* BORRAR CORREO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_BORRAR_CORREO (
    CEDULA IN VARCHAR2,
	USUARIO_CORREO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- BORRAR CORREO
	DELETE FROM TBL_CORREOS 
	WHERE ID_USUARIO = CEDULA AND CORREO = USUARIO_CORREO;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Correo eliminado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_BORRAR_CORREO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Correo no eliminado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* AGREGAR TELEFONO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_TELEFONO (
    CEDULA IN VARCHAR2,
	TELEFONO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- INSERTAR TELEFONO
	INSERT INTO TBL_TELEFONOS
	VALUES (CEDULA, TELEFONO);
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Telefonno agregado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_TELEFONO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Telefonno no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* ACTUALIZAR TELEFONO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_ACTUALIZAR_TELEFONO (
    CEDULA IN VARCHAR2,
	VIEJO_TELEFONO IN VARCHAR2,
	NUEVO_TELEFONO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- MODIFICAR TELEFONO
	UPDATE TBL_TELEFONOS
	SET TELEFONO = NUEVO_TELEFONO
	WHERE ID_USUARIO = CEDULA AND TELEFONO = VIEJO_TELEFONO;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Telefonno modificado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_ACTUALIZAR_TELEFONO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Telefonno no modificado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* BORRAR TELEFONO CLIENTE */
CREATE OR REPLACE PROCEDURE SP_BORRAR_TELEFONO (
    CEDULA IN VARCHAR2,
	VIEJO_TELEFONO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- MODIFICAR TELEFONO
	DELETE FROM TBL_TELEFONOS
	WHERE ID_USUARIO = CEDULA AND TELEFONO = VIEJO_TELEFONO;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Telefonno eliminado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_BORRAR_TELEFONO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Telefonno eliminado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* AGREGAR EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_EMPLEADO (
    CEDULA IN VARCHAR2,
	PRIMER_NOMBRE IN VARCHAR2,
	SEGUNDO_NOMBRE IN VARCHAR2,
	PRIMER_APELLIDO IN VARCHAR2,
	SEGUNDO_APELLIDO IN VARCHAR2,
	DIRECCION IN VARCHAR2,
	ROL IN VARCHAR2,
	PUESTO IN VARCHAR2,
	SALARIO NUMBER,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    --
	ID_EMPLEADO VARCHAR2(6);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	ID_EMPLEADO := FUN_CODIGO_EMPLEADO();
    -- INSERTAR CLIENTE
	INSERT INTO TBL_EMPLEADOS
	VALUES (CEDULA, ID_EMPLEADO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, PUESTO, SALARIO);
	-- INSERTAR ROL
	INSERT INTO TBL_ROLES_EMPLEADOS
	VALUES (CEDULA, ROL);
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Empleado agregado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_EMPLEADO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Empleado no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* AGREGAR ROLES EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_ROL_EMPLEADO (
    CEDULA IN VARCHAR2,
	ROL IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- INSERTAR ROL
	VSQL := 'INSERT INTO TBL_ROLES_EMPLEADOS
	         VALUES (:CEDULA, :ROL)';
    EXECUTE IMMEDIATE VSQL USING CEDULA, ROL;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Rol agregado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_ROL_EMPLEADO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Rol no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* ACTUALIZAR ROLES EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_EDITAR_ROL_EMPLEADO (
    CEDULA IN VARCHAR2,
	VIEJO_ROL IN VARCHAR2,
	NUEVO_ROL IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- ACTUALIZAR ROL
	VSQL := 'UPDATE TBL_ROLES_EMPLEADOS
	         SET ID_ROL = :NUEVO_ROL
	         WHERE ID_EMPLEADO = :CEDULA AND ID_ROL = :VIEJO_ROL';
    EXECUTE IMMEDIATE VSQL USING NUEVO_ROL, CEDULA, VIEJO_ROL;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Rol actualizado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_EDITAR_ROL_EMPLEADO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Rol no actualizado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* BORRAR ROLES EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_BORRAR_ROL_EMPLEADO (
    CEDULA IN VARCHAR2,
	VIEJO_ROL IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
	-- BORRAR ROL
	VSQL := 'DELETE FROM TBL_ROLES_EMPLEADOS
	        WHERE ID_EMPLEADO = :CEDULA AND ID_ROL = :VIEJO_ROL';
    EXECUTE IMMEDIATE VSQL USING CEDULA, VIEJO_ROL;
    COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Rol borrado exitosamente.';
EXCEPTION
    -- Capturar cualquier excepción y enviar mensaje de error
	WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_BORRAR_ROL_EMPLEADO', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Rol no borrado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* MODIFICAR CANTIDAD DE PRODUCTOS */
CREATE OR REPLACE PROCEDURE SP_MODIFICAR_CANTIDAD_PRODUCTO (
    ID_PRODUCTO IN VARCHAR2,
    CANTIDAD_MOD IN NUMBER
)
AS
    VSQL VARCHAR2(500);
    --
BEGIN
    VSQL := 'UPDATE TBL_PRODUCTOS
             SET CANTIDAD = CANTIDAD - :CANTIDAD_MOD
             WHERE ID_PRODUCTO = :ID_PRODUCTO';
    EXECUTE IMMEDIATE VSQL USING CANTIDAD_MOD, ID_PRODUCTO;
    COMMIT;
END;
/

/* CALCULAR TOTAL DE FACTURA */
CREATE OR REPLACE PROCEDURE SP_TOTAL_FACTURA (
    ID_FACTURA_TOT IN NUMBER
)
AS
    TOTAL_FACT NUMBER := 0;
    --
BEGIN
    FOR TOTAL_FACT_LINEA IN (
        SELECT CANTIDAD, PRECIO
        FROM TBL_DETALLE_FACTURA
        WHERE ID_FACTURA = ID_FACTURA_TOT
    ) LOOP
        TOTAL_FACT := TOTAL_FACT + (TOTAL_FACT_LINEA.PRECIO*TOTAL_FACT_LINEA.CANTIDAD);
    END LOOP;
    
    UPDATE TBL_ENCABEZADO_FACTURA
    SET TOTAL = TOTAL_FACT
    WHERE ID_FACTURA = ID_FACTURA_TOT;
    COMMIT;
END;
/

/* CREAR FACTURA */
CREATE OR REPLACE PROCEDURE SP_CREAR_FACTURA (
    ID_CLIENTE IN VARCHAR2,
    ID_EMPLEADO IN VARCHAR2,
    TIPO_PAGO IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    ID_FACTURA NUMBER;
    FECHA_FACTURA DATE;
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    ID_FACTURA := FUN_ID_FACTURA();
    FECHA_FACTURA := SYSDATE;
    VSQL := 'INSERT INTO TBL_ENCABEZADO_FACTURA(ID_FACTURA, FECHA_REGISTRO, ID_CLIENTE, ID_EMPLEADO, TIPO_PAGO)
             VALUES (:ID_FACTURA, :FECHA_FACTURA, :ID_CLIENTE, :ID_EMPLEADO, :TIPO_PAGO)';
    EXECUTE IMMEDIATE VSQL USING ID_FACTURA, FECHA_FACTURA, ID_CLIENTE, ID_EMPLEADO, TIPO_PAGO;
    COMMIT;

    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Factura agregada con exito, ID: ' || ID_FACTURA;
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_CREAR_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Factura no agregada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* CREAR LINEA FACTURA */
CREATE OR REPLACE PROCEDURE SP_CREAR_LINEA_FACTURA (
    ID_FACTURA IN NUMBER,
    ID_PRODUCTO_PR IN VARCHAR2,
    CANTIDAD IN NUMBER,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    PRECIO_DETALLE NUMBER;
    LINEA NUMBER;
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    LINEA := FUN_LINEA_FACTURA(ID_FACTURA);
    
    FOR PR IN(
                SELECT PRECIO
                FROM TBL_PRODUCTOS
                WHERE ID_PRODUCTO = ID_PRODUCTO_PR
            )
    LOOP
        PRECIO_DETALLE := PR.PRECIO;
    END LOOP;
    VSQL := 'INSERT INTO TBL_DETALLE_FACTURA
             VALUES (:ID_FACTURA, :LINEA, :ID_PRODUCTO, :PRECIO, :CANTIDAD)';
    
    EXECUTE IMMEDIATE VSQL USING ID_FACTURA, LINEA, ID_PRODUCTO_PR, PRECIO_DETALLE, CANTIDAD;
    COMMIT;

    SP_TOTAL_FACTURA(ID_FACTURA);
    SP_MODIFICAR_CANTIDAD_PRODUCTO(ID_PRODUCTO_PR, CANTIDAD);

    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Linea modificada con exito, ID: ' || ID_FACTURA;
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_CREAR_LINEA_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Linea no agregada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* MODIFICAR FACTURA */
CREATE OR REPLACE PROCEDURE SP_ACTUALIZAR_FACTURA (
    ID_CLIENTE IN VARCHAR2,
    ID_EMPLEADO IN VARCHAR2,
    TIPO_PAGO IN VARCHAR2,
    ID_FACTURA NUMBER,
    FECHA_FACTURA DATE,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    VSQL := 'UPDATE TBL_ENCABEZADO_FACTURA
             SET FECHA_REGISTRO = :FECHA_FACTURA,
                 ID_CLIENTE = :ID_CLIENTE,
                 ID_EMPLEADO = :ID_EMPLEADO,
                 TIPO_PAGO = :TIPO_PAGO
             WHERE ID_FACTURA = :ID_FACTURA';
    EXECUTE IMMEDIATE VSQL USING FECHA_FACTURA, ID_CLIENTE, ID_EMPLEADO, TIPO_PAGO, ID_FACTURA;
    COMMIT;

    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Factura modificada con exito, ID: ' || ID_FACTURA;
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_ACTUALIZAR_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Factura no modificada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* MODIFICAR LINEA DE FACTURA */
CREATE OR REPLACE PROCEDURE SP_ACTUALIZAR_LINEA_FACTURA (
    ID_FACTURA_MOD IN NUMBER,
    ID_PRODUCTO_PR IN VARCHAR2,
    CANTIDAD IN NUMBER,
    LINEA_MOD NUMBER,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    PRECIO_DETALLE NUMBER;
    CANTIDAD_ORIGINAL NUMBER;
    CANTIDAD_MOD NUMBER;
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    FOR PR IN(
                SELECT PRECIO
                FROM TBL_PRODUCTOS
                WHERE ID_PRODUCTO = ID_PRODUCTO_PR
            )
    LOOP
        PRECIO_DETALLE := PR.PRECIO;
    END LOOP;

    FOR CR IN(
                SELECT CANTIDAD 
                FROM TBL_DETALLE_FACTURA
                WHERE ID_FACTURA = ID_FACTURA_MOD AND LINEA = LINEA_MOD
            )
    LOOP
        CANTIDAD_ORIGINAL := CR.CANTIDAD;
    END LOOP;
    
    CANTIDAD_MOD := CANTIDAD - CANTIDAD_ORIGINAL;
    
    VSQL := 'UPDATE TBL_DETALLE_FACTURA
             SET ID_PRODUCTO = :ID_PRODUCTO,
                 CANTIDAD = :CANTIDAD,
                 PRECIO = :PRECIO
             WHERE ID_FACTURA = :ID_FACTURA AND LINEA = :LINEA';
    EXECUTE IMMEDIATE VSQL USING ID_PRODUCTO_PR, CANTIDAD, PRECIO_DETALLE, ID_FACTURA_MOD, LINEA_MOD;
    COMMIT;

    SP_TOTAL_FACTURA(ID_FACTURA_MOD);
    SP_MODIFICAR_CANTIDAD_PRODUCTO(ID_PRODUCTO_PR, CANTIDAD_MOD);
    
    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Linea modificada con exito, ID: ' || ID_FACTURA_MOD;
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_ACTUALIZAR_LINEA_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Linea no modificada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* ELIMINAR FACTURA */
CREATE OR REPLACE PROCEDURE SP_ELIMINAR_FACTURA (
    ID_FACTURA_BOR IN NUMBER,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    DELETE FROM TBL_DETALLE_FACTURA
    WHERE ID_FACTURA = ID_FACTURA_BOR;
    VSQL := 'DELETE FROM TBL_ENCABEZADO_FACTURA
             WHERE ID_FACTURA = :ID_FACTURA';
    EXECUTE IMMEDIATE VSQL USING ID_FACTURA_BOR;
    COMMIT;

    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Factura borrada.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_ELIMINAR_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Factura no borrada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/

/* ELIMINAR LINEA FACTURA */
CREATE OR REPLACE PROCEDURE SP_ELIMINAR_LINEA_FACTURA (
    ID_FACTURA_BOR IN NUMBER,
    LINEA_BOR IN NUMBER,
    MSJ_SALIDA OUT VARCHAR2
)
AS 
    ID_PRODUCTO_BOR VARCHAR2(20);
    CANTIDAD_MOD NUMBER;
    VSQL VARCHAR2(500);
    --
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    FOR PCR IN(
                SELECT ID_PRODUCTO, CANTIDAD
                FROM TBL_DETALLE_FACTURA
                WHERE ID_FACTURA = ID_FACTURA_BOR AND LINEA = LINEA_BOR
            )
    LOOP
        ID_PRODUCTO_BOR := PCR.ID_PRODUCTO;
        CANTIDAD_MOD := PCR.CANTIDAD;
    END LOOP;

    CANTIDAD_MOD := CANTIDAD_MOD*-1;
    VSQL := 'DELETE FROM TBL_DETALLE_FACTURA
             WHERE ID_FACTURA = :ID_FACTURA AND LINEA = :LINEA';
    EXECUTE IMMEDIATE VSQL USING ID_FACTURA_BOR, LINEA_BOR;
    COMMIT;
    
    SP_TOTAL_FACTURA(ID_FACTURA_BOR);
    SP_MODIFICAR_CANTIDAD_PRODUCTO(ID_PRODUCTO_BOR, CANTIDAD_MOD);
    
    -- MENSAJE SALIDA
    MSJ_SALIDA := 'Linea borrada.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_ELIMINAR_LINEA_FACTURA', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Linea no borrada debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/
/* Buscar productos en la base de datos */
create or replace NONEDITIONABLE PROCEDURE SP_buscar_producto(
    palabra_clave IN VARCHAR2,
    productos_cursor OUT SYS_REFCURSOR
)
AS
    v_query VARCHAR2(1000);
BEGIN
    v_query := 'SELECT ID_PRODUCTO, DESCRIPCION, PRECIO, PROVEEDOR, FECHA_INGRESO, CANTIDAD ' ||
               'FROM TBL_PRODUCTOS ' ||
               'WHERE UPPER(DESCRIPCION) LIKE ''%'' || UPPER(:palabra_clave) || ''%''';

    OPEN productos_cursor FOR v_query USING palabra_clave;
END SP_buscar_producto;

/* Buscar proveedores en la base de datos */
create or replace NONEDITIONABLE PROCEDURE SP_buscar_proveedor(
    palabra_clave IN VARCHAR2,
    proveedor_cursor OUT SYS_REFCURSOR
)
AS
    v_query VARCHAR2(1000);
BEGIN
    v_query := 'SELECT ID_PROVEEDOR,DESCRIPCION,FECHA_INGRESO ' ||
               'FROM TBL_PROVEEDORES ' ||
               'WHERE UPPER(ID_PROVEEDOR) LIKE ''%'' || UPPER(:palabra_clave) || ''%''';

    OPEN  proveedor_cursor FOR v_query USING palabra_clave;
END SP_buscar_proveedor;

-- PAQUETES
/* PAQUETE QUE PERMITE AGRUPAR LOS SP UTILIZADOS PARA ACTUALIZACION DE TBL PRODUCTOS Y PROVEEDORES */
create or replace NONEDITIONABLE PACKAGE PKG_ACTUALIZACION_TABLAS AS

    PROCEDURE Actualizar_Producto (
        p_id_producto IN TBL_PRODUCTOS.ID_PRODUCTO%TYPE,
        p_descripcion IN TBL_PRODUCTOS.DESCRIPCION%TYPE,
        p_precio IN TBL_PRODUCTOS.PRECIO%TYPE,
        p_proveedor IN TBL_PRODUCTOS.PROVEEDOR%TYPE,
        p_cantidad IN TBL_PRODUCTOS.CANTIDAD%TYPE
    );

   
    PROCEDURE Actualizar_Proveedor (
        p_id_proveedor IN TBL_PROVEEDORES.ID_PROVEEDOR%TYPE,
        p_descripcion IN TBL_PROVEEDORES.DESCRIPCION%TYPE
    );
END PKG_ACTUALIZACION_TABLAS;

create or replace NONEDITIONABLE PACKAGE BODY PKG_ACTUALIZACION_TABLAS AS

    PROCEDURE Actualizar_Producto (
        p_id_producto IN TBL_PRODUCTOS.ID_PRODUCTO%TYPE,
        p_descripcion IN TBL_PRODUCTOS.DESCRIPCION%TYPE,
        p_precio IN TBL_PRODUCTOS.PRECIO%TYPE,
        p_proveedor IN TBL_PRODUCTOS.PROVEEDOR%TYPE,
        p_cantidad IN TBL_PRODUCTOS.CANTIDAD%TYPE
    )
    AS
    BEGIN
        UPDATE TBL_PRODUCTOS
        SET DESCRIPCION = p_descripcion,
            PRECIO = p_precio,
            PROVEEDOR = p_proveedor,
            CANTIDAD = p_cantidad
        WHERE ID_PRODUCTO = p_id_producto;
        COMMIT;
    END Actualizar_Producto;


    PROCEDURE Actualizar_Proveedor (
        p_id_proveedor IN TBL_PROVEEDORES.ID_PROVEEDOR%TYPE,
        p_descripcion IN TBL_PROVEEDORES.DESCRIPCION%TYPE
    )
    AS
    BEGIN
        UPDATE TBL_PROVEEDORES
        SET DESCRIPCION = p_descripcion
        WHERE ID_PROVEEDOR = p_id_proveedor;
        COMMIT;
    END Actualizar_Proveedor;
END PKG_ACTUALIZACION_TABLAS;

/* PAQUETE QUE PERMITE AGRUPAR LOS SP UTILIZADOS PARA ELIMINAR REGISTROS DUPLICADOS DE TBL PRODUCTOS Y PROVEEDORES */
create or replace NONEDITIONABLE PACKAGE pkg_eliminar_duplicados AS

  PROCEDURE eliminar_duplicados_productos;

  PROCEDURE eliminar_duplicados_proveedores;
END pkg_eliminar_duplicados;

create or replace NONEDITIONABLE PACKAGE BODY pkg_eliminar_duplicados AS

  PROCEDURE eliminar_duplicados_productos IS
  BEGIN
    DELETE FROM tbl_productos
    WHERE ROWID NOT IN (
      SELECT MIN(ROWID)
      FROM tbl_productos
      GROUP BY descripcion
      );
      commit;
  END eliminar_duplicados_productos;

  PROCEDURE eliminar_duplicados_proveedores IS
  BEGIN
    DELETE FROM tbl_proveedores
    WHERE ROWID NOT IN (
      SELECT MIN(ROWID)
      FROM tbl_proveedores
      GROUP BY descripcion
    );
    commit;
  END eliminar_duplicados_proveedores;
END pkg_eliminar_duplicados;

-- VISTAS
/* DATOS CLIENTE */
CREATE OR REPLACE VIEW VST_CLIENTE AS
SELECT C.CEDULA, C.PRIMER_NOMBRE || ' ' || C.PRIMER_APELLIDO AS NOMBRE, C.DIRECCION, LISTAGG(CO.CORREO, ' | ') WITHIN GROUP (ORDER BY CO.CORREO) AS CORREOS, LISTAGG(T.TELEFONO, ' | ') WITHIN GROUP (ORDER BY T.TELEFONO) AS TELEFONOS
FROM TBL_CLIENTES C FULL JOIN
     TBL_CORREOS CO ON C.CEDULA = CO.ID_USUARIO FULL JOIN
     TBL_TELEFONOS T ON C.CEDULA = T.ID_USUARIO
GROUP BY C.CEDULA, C.PRIMER_NOMBRE, C.PRIMER_APELLIDO, C.DIRECCION;

/* DATOS EMPLEADO */
CREATE OR REPLACE VIEW VST_EMPLEADO AS
SELECT E.CEDULA, E.ID_EMPLEADO, E.PRIMER_NOMBRE || ' ' || E.PRIMER_APELLIDO AS NOMBRE, E.DIRECCION, E.PUESTO, E.SALARIO, LISTAGG(R.DESCRIPCION, ' | ') WITHIN GROUP (ORDER BY R.DESCRIPCION) AS ROLES
FROM TBL_EMPLEADOS E FULL JOIN
     TBL_ROLES_EMPLEADOS RE ON E.CEDULA = RE.ID_EMPLEADO JOIN
     TBL_ROLES R ON RE.ID_ROL = R.ID_ROL
GROUP BY E.CEDULA, E.ID_EMPLEADO, E.PRIMER_NOMBRE, E.PRIMER_APELLIDO, E.DIRECCION, E.PUESTO, E.SALARIO;

/* FACTURAS */
CREATE OR REPLACE VIEW VST_ITEMS_FACTURA AS
SELECT P.DESCRIPCION, D.PRECIO, D.CANTIDAD, D.ID_FACTURA
FROM TBL_DETALLE_FACTURA D
LEFT JOIN TBL_PRODUCTOS P
ON P.ID_PRODUCTO = D.ID_PRODUCTO;

CREATE OR REPLACE VIEW VST_ENCABEZADO_FACTURA AS
SELECT F.ID_FACTURA, C.PRIMER_NOMBRE || ' '|| C.PRIMER_APELLIDO AS CLIENTE, E.PRIMER_NOMBRE|| ' ' || E.PRIMER_APELLIDO AS EMPLEADO, F.FECHA_REGISTRO, F.TOTAL, F.TIPO_PAGO
FROM TBL_ENCABEZADO_FACTURA F
LEFT JOIN TBL_EMPLEADOS E ON F.ID_EMPLEADO = E.CEDULA
LEFT JOIN TBL_CLIENTES C ON F.ID_CLIENTE = C.CEDULA;

-- TRIGGERS
CREATE OR REPLACE TRIGGER TRG_EMP
AFTER DELETE OR UPDATE ON TBL_EMPLEADOS
FOR EACH ROW
DECLARE
    --
    ACCION VARCHAR2(100);
BEGIN
    CASE
        WHEN DELETING THEN
            ACCION := 'DELETE';
        WHEN UPDATING THEN
            ACCION := 'UPDATE';
    END CASE;
    INSERT INTO TBL_AUDITORIA_USUARIOS (ID_AUD_USUARIO, ACCION, CEDULA, PRIMER_NOMBRE, PRIMER_APELLIDO, DIRECCION, TIPO_USUARIO)
    VALUES (SEQ_USUARIOS_AUD.NEXTVAL, ACCION, :OLD.CEDULA, :OLD.PRIMER_NOMBRE, :OLD.PRIMER_APELLIDO, :OLD.DIRECCION, 'EMPLEADO');
END;
/

CREATE OR REPLACE TRIGGER TGR_CLI
AFTER DELETE OR UPDATE ON TBL_CLIENTES
FOR EACH ROW
DECLARE
    --
    ACCION VARCHAR2(100);
BEGIN
    CASE
        WHEN DELETING THEN
            ACCION := 'DELETE';
        WHEN UPDATING THEN
            ACCION := 'UPDATE';
    END CASE;
    INSERT INTO TBL_AUDITORIA_USUARIOS (ID_AUD_USUARIO, ACCION, CEDULA, PRIMER_NOMBRE, PRIMER_APELLIDO, DIRECCION, TIPO_USUARIO)
    VALUES (SEQ_USUARIOS_AUD.NEXTVAL, ACCION, :OLD.CEDULA, :OLD.PRIMER_NOMBRE, :OLD.PRIMER_APELLIDO, :OLD.DIRECCION, 'CLIENTE');
END;
/

CREATE OR REPLACE TRIGGER TGR_PROD
AFTER DELETE OR UPDATE ON TBL_PRODUCTOS
FOR EACH ROW
DECLARE
    --
    ACCION VARCHAR2(100);
BEGIN
    CASE
        WHEN DELETING THEN
            ACCION := 'DELETE';
        WHEN UPDATING THEN
            ACCION := 'UPDATE';
    END CASE;
    INSERT INTO TBL_AUDITORIA_PRODUCTOS (ID_AUD_PRODUCTO, ACCION, ID_PRODUCTO, DESCRIPCION, PRECIO, PROVEEDOR, FECHA_INGRESO, CANTIDAD)
    VALUES (SEQ_PRODUCTOS_AUD.NEXTVAL, ACCION, :OLD.ID_PRODUCTO, :OLD.DESCRIPCION, :OLD.PRECIO, :OLD.PROOVEDOR, :OLD.FECHA_INGRESO, :OLD.CANTIDAD);
END;
/

CREATE OR REPLACE TRIGGER TGR_FACT
AFTER DELETE OR UPDATE ON TBL_ENCABEZADO_FACTURA
FOR EACH ROW
DECLARE
    --
    ACCION VARCHAR2(100);
BEGIN
    CASE
        WHEN DELETING THEN
            ACCION := 'DELETE';
        WHEN UPDATING THEN
            ACCION := 'UPDATE';
    END CASE;
    INSERT INTO TBL_AUDITORIA_FACTURAS (ID_AUD_FACTURA, ACCION, ID_FACTURA, FECHA_REGISTRO, ID_CLIENTE, ID_EMPLEADO, TOTAL, TIPO_PAGO)
    VALUES (SEQ_FACTURAS_AUD.NEXTVAL, ACCION, :OLD.ID_FACTURA, :OLD.FECHA_REGISTRO, :OLD.ID_CLIENTE, :OLD.ID_EMPLEADO, :OLD.TOTAL, :OLD.TIPO_PAGO);
END;
/