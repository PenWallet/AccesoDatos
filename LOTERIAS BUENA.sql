SET DATEFORMAT dmy
CREATE DATABASE LOTERIAS
GO
USE LOTERIAS 
GO

/*
	*********************************************************************************************
	********************************* C R E A C I Ó N  B B D D **********************************
	*********************************************************************************************
*/
CREATE TABLE BOLETO(
	ID INT IDENTITY(1,1) NOT NULL
		CONSTRAINT PK_BOLETO Primary Key,
	ID_SORTEO INT NOT NULL,
	REINTEGRO SMALLINT NOT NULL,
	FECHA_HORA SMALLDATETIME NOT NULL,
	IMPORTE_TOTAL MONEY NOT NULL,
	GANADO_TOTAL MONEY NULL
)

CREATE TABLE SORTEO(
	ID int IDENTITY(1,1) NOT NULL
		CONSTRAINT PK_SORTEO Primary Key,
	FECHA SMALLDATETIME NOT NULL,
	N1 SMALLINT  NULL,
	N2 SMALLINT  NULL,
	N3 SMALLINT  NULL,
	N4 SMALLINT  NULL,
	N5 SMALLINT  NULL,
	N6 SMALLINT  NULL,
	COMPLEMENTARIO SMALLINT  NULL,
	REINTEGRO SMALLINT  NULL,
	RECAUDACION MONEY  NULL,
	TOTAL_REINTEGRO MONEY NULL,
	CATEGORIA_ESPECIAL MONEY NULL,
	CATEGORIA_1 MONEY NULL,
	CATEGORIA_2 MONEY NULL,
	CATEGORIA_3 MONEY NULL,
	CATEGORIA_4 MONEY NULL,
	CATEGORIA_5 MONEY NULL
)

CREATE TABLE APUESTA_SIMPLE (
	ID INT IDENTITY(1,1) NOT NULL
		CONSTRAINT PK_APUESTA_SIMPLE Primary Key,
	ID_BOLETO INT NOT NULL,
	N1 SMALLINT NOT NULL,
	N2 SMALLINT NOT NULL,
	N3 SMALLINT NOT NULL,
	N4 SMALLINT NOT NULL,
	N5 SMALLINT NOT NULL,
	N6 SMALLINT NOT NULL,
	COMPLEMENTARIO SMALLINT NOT NULL,
	GANADO MONEY NULL
)

CREATE TABLE APUESTA_MULTIPLE (
	ID INT IDENTITY(1,1) NOT NULL
		CONSTRAINT PK_MULTIPLE Primary Key,
	ID_BOLETO INT NOT NULL,
	N1 SMALLINT NOT NULL,
	N2 SMALLINT NOT NULL,
	N3 SMALLINT NOT NULL,
	N4 SMALLINT NOT NULL,
	N5 SMALLINT NOT NULL,
	N6 SMALLINT  NULL,
	N7 SMALLINT  NULL,
	N8 SMALLINT  NULL,
	N9 SMALLINT  NULL,
	N10 SMALLINT  NULL,
	N11 SMALLINT  NULL,
	COMPLEMENTARIO SMALLINT NOT NULL,
	GANADO MONEY NULL,
	IMPORTE MONEY NOT NULL
)

ALTER TABLE BOLETO ADD
	CONSTRAINT FK_BOLETO_SORTEO FOREIGN KEY (ID_SORTEO) REFERENCES SORTEO(ID)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION

ALTER TABLE APUESTA_SIMPLE ADD
	CONSTRAINT FK_APUESTA_SIMPLE_BOLETO FOREIGN KEY (ID_BOLETO) REFERENCES BOLETO(ID)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION

ALTER TABLE APUESTA_MULTIPLE ADD
	CONSTRAINT FK_APUESTA_MULTIPLE_BOLETO FOREIGN KEY (ID_BOLETO) REFERENCES BOLETO(ID)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION


/*
	*********************************************************************************************
	******************************** P R O C E D I M I E N T O S ********************************
	*********************************************************************************************
*/
/*	
	Descripcion: Procedimiento usado para añadir un boleto con una sola apuesta simple.
	Entradas:  El sorteo y los seis números
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE GrabaSencilla (@Id_Sorteo int, @n1 smallint,@n2 smallint,@n3 smallint,@n4 smallint,@n5 smallint,@n6 smallint)
AS
	BEGIN

	--DECLARAMOS LAS VARIABLES CORRESPONDIENTES
	DECLARE @RANDOM_REINTEGRO INT
	DECLARE @RANDOM_COMPLEMENTARIO INT

	--VALIDAMOS EL COMPLEMENTARIO PARA QUE SEA DISTINTO DE TODOS LOS NUMEROS DEL BOLETO
	SELECT @RANDOM_COMPLEMENTARIO = ROUND(((1 + 48) * RAND() + 1), 0)
	
	WHILE(@RANDOM_COMPLEMENTARIO IN (@n1,@n2,@n3,@n4,@n5,@n6))
	BEGIN
		SELECT @RANDOM_COMPLEMENTARIO = ROUND(((1 + 48) * RAND() + 1), 0)
	END
----------------------------------------------------------------------


	--BUSCAMOS EL RANDOM PARA EL REINTEGRO
	SELECT @RANDOM_REINTEGRO = ROUND(((1 + 8) * RAND() + 1), 0)

-----------------------------------------------------------------------

			--INSERTAMOS EL BOLETO CON UNA APUESTA SIEMPLE
			INSERT INTO BOLETO(FECHA_HORA,GANADO_TOTAL,ID_SORTEO,IMPORTE_TOTAL,REINTEGRO) 
				VALUES (GETDATE(),NULL,@Id_Sorteo,1,@RANDOM_REINTEGRO)

			INSERT INTO APUESTA_SIMPLE(COMPLEMENTARIO,ID_BOLETO,N1,N2,N3,N4,N5,N6)
			VALUES (@RANDOM_COMPLEMENTARIO,@@IDENTITY,@n1,@n2,@n3,@n4,@n5,@n6)

	END
GO

/*
	Procedimiento usado para generar un boleto con n apuestas sencillas, con números generados aleatoriamente
	Entradas:
		Int n, la cantidad de apuestas sencillas del boleto
		Int IDSorteo
	Salida: Ninguna
	Requisitos: Solo puede haber 8 apuestas sencillas por boleto
*/
GO
CREATE PROCEDURE GrabaSencillaAleatoria (@NumeroApuestas int, @IDSorteo int)
AS
	BEGIN
		DECLARE @Cont int = 0
		DECLARE @Reintegro int = ROUND((8 * RAND() + 1), 0)
		INSERT INTO BOLETO (ID_SORTEO, REINTEGRO, FECHA_HORA, IMPORTE_TOTAL) 
			VALUES (@IDSorteo, @Reintegro, GETDATE(), @NumeroApuestas)
		DECLARE @IDBoleto int = @@IDENTITY

		/* Generación de todas las apuestas del boleto */
		WHILE (@Cont != @NumeroApuestas)
		BEGIN
			DECLARE @N1 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @N2 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @N3 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @N4 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @N5 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @N6 int = ROUND((48 * RAND() + 1), 0)
			DECLARE @Complementario int = ROUND((48 * RAND() + 1), 0)
			
			WHILE @N2 = @N1
				SET @N2 = ROUND((48 * RAND() + 1), 0)

			WHILE @N3 IN (@N2, @N1)
				SET @N3 = ROUND((48 * RAND() + 1), 0)

			WHILE @N4 IN (@N3, @N2, @N1)
				SET @N4 = ROUND((48 * RAND() + 1), 0)

			WHILE @N5 IN (@N4, @N3, @N2, @N1)
				SET @N5 = ROUND((48 * RAND() + 1), 0)

			WHILE @N6 IN (@N5, @N4, @N3, @N2, @N1)
				SET @N6 = ROUND((48 * RAND() + 1), 0)

			WHILE @Complementario IN (@N6, @N5, @N4, @N3, @N2, @N1)
				SET @Complementario = ROUND((48 * RAND() + 1), 0)

			INSERT INTO APUESTA_SIMPLE (ID_BOLETO, N1, N2, N3, N4, N5, N6, COMPLEMENTARIO)
				VALUES (@IDBoleto, @N1, @N2, @N3, @N4, @N5, @N6, @Complementario)

			SET @Cont = @Cont + 1
		END
	END
GO

/*
	
	Descripcion: Procedimiento que genere n boletos con sus apuestas sencillas
	Entradas:  El sorteo y los boletos que queremos insertar
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE GrabaMuchasSencillas (@Id_Sorteo int, @nveces int)
AS
	BEGIN
	--Declaramos e inicializamos las variables oportunas
	DECLARE @RANDOM_REINTEGRO INT
	DECLARE @CONT INT
	SET @CONT = 0
	DECLARE @N1 int 
	DECLARE @N2 int 
	DECLARE @N3 int 
	DECLARE @N4 int 
	DECLARE @N5 int 
	DECLARE @N6 int
	DECLARE @COMPLEMENTARIO INT 
-----------------------------------------------------------------------

		--Mientras el contador quiera...TRACATRÁÁÁ!!
		WHILE (@Cont != @nveces)
		BEGIN
			
			--Hacemos una random para conseguir los numeros
			SET @N1 = ROUND((48 * RAND() + 1), 0)
			SET @N2  = ROUND((48 * RAND() + 1), 0)
			SET @N3  = ROUND((48 * RAND() + 1), 0)
			SET @N4  = ROUND((48 * RAND() + 1), 0)
			SET @N5  = ROUND((48 * RAND() + 1), 0)
			SET @N6  = ROUND((48 * RAND() + 1), 0)
			SET @Complementario = ROUND((48 * RAND() + 1), 0)

-----------------------------------------------------------------------
			--Validamos que los numeros del boleto no sean iguales, ni el complementario
			WHILE @N2 = @N1
				SET @N2 = ROUND((48 * RAND() + 1), 0)

			WHILE @N3 IN (@N2, @N1)
				SET @N3 = ROUND((48 * RAND() + 1), 0)

			WHILE @N4 IN (@N3, @N2, @N1)
				SET @N4 = ROUND((48 * RAND() + 1), 0)

			WHILE @N5 IN (@N4, @N3, @N2, @N1)
				SET @N5 = ROUND((48 * RAND() + 1), 0)

			WHILE @N6 IN (@N5, @N4, @N3, @N2, @N1)
				SET @N6 = ROUND((48 * RAND() + 1), 0)

			WHILE @Complementario IN (@N6, @N5, @N4, @N3, @N2, @N1)
				SET @Complementario = ROUND((48 * RAND() + 1), 0)
-----------------------------------------------------------------------
			--Sacamos un reintregro para el boleto a insertar
			SELECT @RANDOM_REINTEGRO = ROUND(((8) * RAND() + 1), 0)
-----------------------------------------------------------------------
				--Insertamos los valores obtenidos enlas validaciones anteriores
				INSERT INTO BOLETO(FECHA_HORA,GANADO_TOTAL,ID_SORTEO,IMPORTE_TOTAL,REINTEGRO) 
				VALUES (GETDATE(),NULL,@Id_Sorteo,1,@RANDOM_REINTEGRO)

				INSERT INTO APUESTA_SIMPLE(COMPLEMENTARIO,ID_BOLETO,N1,N2,N3,N4,N5,N6)
				VALUES (@COMPLEMENTARIO,@@IDENTITY,@N1,@N2,@N3,@N4,@N5,@N6)
-----------------------------------------------------------------------

		--Sumamos al contador para salir del bucle cuando se acaben el numero de veces
		SET @CONT = @CONT + 1
	END
END
GO

/*
	Procedimiento que actualiza la columna 'GANADO' de APUESTA_SIMPLE dependiendo de si ha ganado
	el premio de categoría 5, es decir, en caso de haber acertado 3, se le suma 8 a su GANADO
	Entrada: ID del sorteo
	Salida: Ninguna
*/
CREATE PROCEDURE AsignarPremiosCat5 (@IDSorteo int)
AS
	BEGIN
		-- COMPROBAMOS SI YA EXISTE UNA TABLA TEMPORAL LLAMADA #TEMP
		IF(OBJECT_ID('tempdb..#temp') IS NOT NULL)
		BEGIN
			DROP TABLE #Temp
		END

		-- CREAMOS LA TABLA TEMPORAL #TEMP PARA USARLA DE APOYO
		CREATE TABLE #Temp 
		   (ID INT,
			ID_BOLETO INT,
			N1 SMALLINT,
			N2 SMALLINT,
			N3 SMALLINT,
			N4 SMALLINT,
			N5 SMALLINT,
			N6 SMALLINT )

		-- INSERTAMOS LOS DATOS QUE NECESITAREMOS COMPROBAR EN #TEMP
		INSERT INTO #Temp
			SELECT APS.ID, APS.ID_BOLETO, APS.N1, APS.N2, APS.N3, APS.N4, APS.N5, APS.N6
				FROM APUESTA_SIMPLE AS APS
					INNER JOIN BOLETO AS B
						ON APS.ID_BOLETO = B.ID
					INNER JOIN SORTEO AS S
						ON B.ID_SORTEO = S.ID
				WHERE ID_SORTEO = @IDSorteo

		-- VARIABLES QUE VAMOS A NECESITAR PARA LA COMPROBACIÓN DE LOS NÚMEROS
		DECLARE @ContTablas int = (SELECT COUNT(*) FROM #Temp)
		DECLARE @IDTemp int
		DECLARE @N1Temp int
		DECLARE @N2Temp int
		DECLARE @N3Temp int
		DECLARE @N4Temp int
		DECLARE @N5Temp int
		DECLARE @N6Temp int
		DECLARE @N1Ganador int = (SELECT N1 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @N2Ganador int = (SELECT N2 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @N3Ganador int = (SELECT N3 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @N4Ganador int = (SELECT N4 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @N5Ganador int = (SELECT N5 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @N6Ganador int = (SELECT N6 FROM SORTEO WHERE ID = @IDSorteo)
		DECLARE @ContAciertos int
		
		-- RECORREMOS LA TABLA 1 A 1 PARA COMPROBAR SI ES GANADOR
		WHILE @ContTablas > 0
			BEGIN
				SET @ContAciertos = 0
				SET @IDTemp = (SELECT TOP 1 ID FROM #Temp ORDER BY ID)
				SET @N1Temp = (SELECT TOP 1 N1 FROM #Temp ORDER BY ID)
				SET @N2Temp = (SELECT TOP 1 N2 FROM #Temp ORDER BY ID)
				SET @N3Temp = (SELECT TOP 1 N3 FROM #Temp ORDER BY ID)
				SET @N4Temp = (SELECT TOP 1 N4 FROM #Temp ORDER BY ID)
				SET @N5Temp = (SELECT TOP 1 N5 FROM #Temp ORDER BY ID)
				SET @N6Temp = (SELECT TOP 1 N6 FROM #Temp ORDER BY ID)

				IF @N1Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				IF @N2Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				IF @N3Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				IF @N4Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				IF @N5Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				IF @N6Temp IN (@N1Ganador, @N2Ganador, @N3Ganador, @N4Ganador, @N5Ganador, @N6Ganador)
					SET @ContAciertos = @ContAciertos + 1
				
				-- SOLO SI HA ACERTADO 3 SE LE DAN LOS 8 EUROS DE LA APUESTA
				IF @ContAciertos = 3
					UPDATE APUESTA_SIMPLE 
						SET GANADO = ISNULL(GANADO, 0) + 8 
						WHERE ID = @IDTemp

				DELETE FROM #Temp WHERE ID = @IDTemp
				SET @ContTablas = (SELECT COUNT(*) FROM #Temp) 
			END

		-- BORRAMOS LA TABLA TEMPORAL
		DROP TABLE #Temp
	END
GO


/*
	*********************************************************************************************
	*************************************** P R U E B A S ***************************************
	*********************************************************************************************
*/
--INSERT INTO SORTEO (FECHA, N1, N2, N3, N4, N5, N6) VALUES(GETDATE(), 4,18,26,30,45,49)
--SELECT * FROM SORTEO
--EXEC dbo.GrabaMuchasSencillas 6, 5000
--EXEC dbo.AsignarPremiosCat5 6

--SELECT * FROM APUESTA_SIMPLE ORDER BY GANADO DESC