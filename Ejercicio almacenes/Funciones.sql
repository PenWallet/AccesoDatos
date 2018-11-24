--ALMACENES LEO

/*
	Una base de datos gestiona una serie de almacenes. En los almacenes se utilizan unos “contenedores” con medidas estandard.

	Cada almacén tiene una capacidad limitada, expresada por el número de contenedores que caben en él.


------------Queremos una aplicación que asigne envíos a los almacenes según las siguientes reglas:-------------------------------------------------------------------------------

	--Un envío se compone de uno o más contenedores.

	--El envío debe estar en un único almacén, no pueden separarse los contenedores de un mismo pedido en almacenes distintos
	--Para ello, se consultará la tabla Envios y se comprobarán todos los envíos que tengan un valor NULL en la columna “Fecha Asignación”.

	Se comprobará si el envío cabe en el almacén que figure en la columna “Almacen Preferido” y caso de no ser así, se intentará derivarlo al almacén más cercano. 
	Si en este tampoco hubiese espacio, probaríamos en el segundo más cercano y así sucesivamente. Si no hubiera espacio suficiente en ninguno, se dejaría sin asignar.
	--Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla “Asignaciones” que estén actualmente asignados a 
	--ese almacén y se restará esta cantidad de la capacidad del almacen. Para esto puede usarse una función

	Antes de asignarlo a un almacén alternativo, la aplicación pedirá confirmación al usuario, que decidirá si acepta la alternativa o deja el pedido sin asignar.

	--Para comprobar si hay espacio, la aplicación intentará hacer una actualización (inserción en Asignaciones y actualizar fecha) asignándolo a un almacén 
	--y si no hay espacio deberá lanzarse una excepción personalizada con RAISERROR. La excepción la lanzará un TRIGGER, que será el encargado de comprobar si hay espacio o no.
*/

/*
	Trigger quen se ejecuta al detectar que no hay espacio en un almacen
*/

GO
CREATE TRIGGER NoHayEspacioEnElAlmacen ON Asignaciones AFTER INSERT
AS
	BEGIN
		DECLARE @LLENO INT = (SELECT (Al.Capacidad - E.NumeroContenedores) 
								FROM inserted AS A
								INNER JOIN Almacenes AS Al ON A.IDAlmacen = Al.ID
								INNER JOIN Envios AS E ON A.IDEnvio = E.ID)
		IF (@LLENO < 0) 
			BEGIN
				RAISERROR ('Error, El almacen está lleno', 10, 1)
				ROLLBACK TRANSACTION
			END
	END
GO

/*
	Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla “Asignaciones” que estén actualmente asignados a 
	ese almacén y se restará esta cantidad de la capacidad del almacen. Para esto puede usarse una función
*/
	
GO
CREATE FUNCTION fnCabePedidoEnAlmacen (@IDAlmacen Bigint, @IDEnvio BigInt) RETURNS Bit
AS
	BEGIN
		DECLARE @Ret Bit
		DECLARE @Capacidad BigInt	=  (SELECT D.disponible 
										FROM 
										(
											SELECT A.ID, A.Capacidad, Sum(E.NumeroContenedores) AS Ocupado, A.Capacidad - Sum(E.NumeroContenedores) AS disponible 
											From Almacenes AS A 
											Inner Join Asignaciones As Ag ON A.ID = Ag.IDAlmacen
											Inner Join Envios AS E ON Ag.IDEnvio = E.ID
											Group By A.ID, A.Capacidad
										) AS D
										WHERE D.ID = @IDAlmacen)						

		DECLARE @Contenedores BigInt =  (SELECT NumeroContenedores
										 FROM Envios
										 WHERE ID = @IDEnvio)

			IF (@Capacidad >= @Contenedores) 
				SET @Ret = 1
			ELSE
				SET @Ret = 0	

		RETURN @Ret
	END
GO

/*
	Funcion que comprueba si la id introducida corresponde a un almacen de la base de datos
*/

GO
CREATE FUNCTION fnValidarIdAlmacen (@ID Bigint) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit = 0
		IF (EXISTS (SELECT ID FROM Almacenes WHERE ID = @ID))
			SET @ret = 1
		RETURN @ret
	END
GO

/*
	Funcion que comprueba si la id introducida corresponde a un envio sin asignar de la base de datos
*/

GO
CREATE FUNCTION fnValidarIdEnvioSinAsignar (@ID Bigint) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit = 0
		IF (EXISTS (SELECT ID FROM Envios WHERE ID = @ID AND FechaAsignacion IS NULL))
			SET @ret = 1
		RETURN @ret
	END
GO

--SELECT dbo.fnValidarIdAlmacen(10) AS ret
--INSERT INTO Envios(NumeroContenedores, FechaCreacion, AlmacenPreferido) VALUES (100, )ç

SELECT * FROM Envios