/*
	Función que comprueba si hay suficiente stock de un producto. Devuelve un bit, siendo 1 que hay suficiente, y 0 que no
*/
GO
CREATE FUNCTION fnHaySuficienteStock (@IDProducto int, @Cantidad int) RETURNS Bit
AS
	BEGIN
		DECLARE @ret bit = 0
		DECLARE @StockDisponible int = (SELECT Stock FROM Productos WHERE IDProducto = @IDProducto)
		IF(@StockDisponible >= @Cantidad)
			SET @ret = 1

		RETURN @ret
	END
GO