-- VISTAS
/* DATOS EMPLEADOS_GENERAL */
CREATE OR REPLACE VIEW VST_EMPLEADO_GENERAL AS
SELECT CEDULA, ID_EMPLEADO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, PUESTO, SALARIO 
FROM TBL_EMPLEADOS;

/* AGREGAR EMPLEADO BASE*/
CREATE OR REPLACE PROCEDURE SP_AGREGAR_EMPLEADO_BASE (
        CEDULA IN VARCHAR2,
        ID_EMPLEADO IN VARCHAR2,
        PRIMER_NOMBRE IN VARCHAR2,
        SEGUNDO_NOMBRE IN VARCHAR2,
        PRIMER_APELLIDO IN VARCHAR2,
        SEGUNDO_APELLIDO IN VARCHAR2,
        DIRECCION IN VARCHAR2,
        PUESTO IN VARCHAR2,
        SALARIO IN NUMBER,
        MSJ_SALIDA OUT VARCHAR2
)
AS
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    INSERT INTO TBL_EMPLEADOS
    VALUES (CEDULA, ID_EMPLEADO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, PUESTO, SALARIO);
    
    COMMIT;
    
    MSJ_SALIDA := 'Empleado agregado exitosamente.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_EMPLEADO_BASE', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Error al agregar empleado: ' || SQLCODE || '-' || SQLERRM;
END;
/


/* EDITAR EMPLEADO BASE*/
CREATE OR REPLACE PROCEDURE SP_EDITAR_EMPLEADO_BASE (
        CED IN VARCHAR2,
        ID_EMP IN VARCHAR2,
        NOMBRE1 IN VARCHAR2,
        NOMBRE2 IN VARCHAR2,
        APELLIDO1 IN VARCHAR2,
        APELLIDO2 IN VARCHAR2,
        DIR IN VARCHAR2,
        PUE IN VARCHAR2,
        SAL IN NUMBER,
        MSJ_SALIDA OUT VARCHAR2
)
AS
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    UPDATE TBL_EMPLEADOS
    SET ID_EMPLEADO = ID_EMP,
        PRIMER_NOMBRE = NOMBRE1,
        SEGUNDO_NOMBRE = NOMBRE2,
        PRIMER_APELLIDO = APELLIDO1,
        SEGUNDO_APELLIDO = APELLIDO2,
        DIRECCION = DIR,
        PUESTO = PUE,
        SALARIO = SAL
    WHERE CEDULA = CED;
    
    COMMIT;
    
    MSJ_SALIDA := 'Empleado editado exitosamente.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_EDITAR_EMPLEADO_BASE', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Error al editar empleado: ' || SQLCODE || '-' || SQLERRM;
END;
/

/* BORRAR EMPLEADO BASE*/
CREATE OR REPLACE PROCEDURE SP_BORRAR_EMPLEADO_BASE (
        CEDULA_IN IN VARCHAR2,
        MSJ_SALIDA OUT VARCHAR2
)
AS
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    DELETE FROM TBL_EMPLEADOS
    WHERE CEDULA = CEDULA_IN;
    
    COMMIT;
    
    MSJ_SALIDA := 'Empleado eliminado exitosamente.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_BORRAR_EMPLEADO_BASE', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Error al eliminar empleado: ' || SQLCODE || '-' || SQLERRM;
END;
/

-----------------------------------------------------------------------------------------

/* DATOS ROLES_GENERAL */
CREATE OR REPLACE VIEW VST_ROLES AS
SELECT ID_ROL, DESCRIPCION
FROM TBL_ROLES;

/* ANADIR ROL BASE */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_ROL (
    ID_ROL IN VARCHAR2,
    DESCRIPCION IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    INSERT INTO TBL_ROLES (ID_ROL, DESCRIPCION) VALUES (ID_ROL, DESCRIPCION);
    MSJ_SALIDA := 'Rol agregado exitosamente.';
EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_AGREGAR_ROL', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Error al agregar rol: ' || SQLCODE || ' - ' || SQLERRM;
END SP_AGREGAR_ROL;
/

/* ELIMINAR ROL BASE */
create or replace PROCEDURE SP_BORRAR_ROL (
        IDROL IN VARCHAR2,
        MSJ_SALIDA OUT VARCHAR2
)
AS
        ERROR VARCHAR2(1000);
        TIEMPO TIMESTAMP;
BEGIN
    -- ACTUALIZAR ROL
        DELETE FROM "TBL_ROLES"
        WHERE ID_ROL = IDROL;
        --- ELIMINA
        COMMIT;
        --- MENSAJE SALIDA
        MSJ_SALIDA := 'Editado exitosamente.';
        EXCEPTION
        ---mensaje de error en caso de error
        WHEN OTHERS THEN
            ERROR := SQLERRM;
            TIEMPO := SYSTIMESTAMP;
            INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
            VALUES (SEQ_ERROR.NEXTVAL, 'SP_BORRAR_ROL', ERROR, TIEMPO);
            COMMIT;
            MSJ_SALIDA := 'Error '|| SQLCODE || '-' || SQLERRM || '.';
END;
/

/* EDITAR ROL BASE */
CREATE OR REPLACE PROCEDURE SP_EDITAR_ROL (
    ID_ROL_PARAM IN VARCHAR2,
    NUEVO_DESCRIPCION IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
BEGIN
    -- Actualiza la descripci�n del rol
    UPDATE TBL_ROLES
    SET DESCRIPCION = NUEVO_DESCRIPCION
    WHERE ID_ROL = ID_ROL_PARAM;
    
    COMMIT;
    
    MSJ_SALIDA := 'Editado Exitosamente';
    EXCEPTION
    WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_EDITAR_ROL', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Error al intentar actualizar el rol: ' || SQLCODE || '-' || SQLERRM;
END;
/

-----------------------------------------------------------------------------------------

/* VISTA_ROLES_EMPLEADO */
CREATE OR REPLACE VIEW VST_ROLES_EMPLEADO AS
SELECT ID_EMPLEADO, ID_ROL
FROM TBL_ROLES_EMPLEADOS;

/* AGREGAR ROLES EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_AGREGAR_ROL_EMPLEADO (
    CEDULA IN VARCHAR2,
	ROL IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
    --
BEGIN
	-- INSERTAR ROL
	VSQL := 'INSERT INTO TBL_ROLES_EMPLEADOS
	         VALUES (:CEDULA, :ROL)';
    EXECUTE IMMEDIATE VSQL USING IN CEDULA, IN ROL;
	COMMIT;
	-- MENSAJE SALIDA
	MSJ_SALIDA := 'Rol agregado exitosamente.';
EXCEPTION
	WHEN OTHERS THEN
        ERROR := SQLERRM;
        TIEMPO := SYSTIMESTAMP;
        INSERT INTO TBL_ERRORES(ID_ERROR, NOMBRE_SP, MSJ_ERROR, FECHA_ERROR)
        VALUES (SEQ_ERROR.NEXTVAL, 'VST_ROLES_EMPLEADO', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Rol no agregado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
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
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
    --
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

/* ACTUALIZAR ROLES EMPLEADO */
CREATE OR REPLACE PROCEDURE SP_ACTUALIZAR_ROL_EMPLEADO (
    CEDULA IN VARCHAR2,
	VIEJO_ROL IN VARCHAR2,
	NUEVO_ROL IN VARCHAR2,
    MSJ_SALIDA OUT VARCHAR2
)
AS
    VSQL VARCHAR2(500);
    ERROR VARCHAR2(1000);
    TIEMPO TIMESTAMP;
    --
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
        VALUES (SEQ_ERROR.NEXTVAL, 'SP_ACTUALIZAR_ROL_EMPLEADO', ERROR, TIEMPO);
        COMMIT;
        MSJ_SALIDA := 'Rol no actualizado debido a la falla: ' || SQLCODE || '-' || SQLERRM || '.';
END;
/