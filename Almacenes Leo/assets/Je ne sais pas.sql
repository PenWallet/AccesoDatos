SELECT * 
FROM Almacenes AS A
INNER JOIN Envios E ON A.ID = E.AlmacenPreferido


SELECT *
	FROM Almacenes AS A
		INNER JOIN Distancias AS D
			ON A.ID = D.IDAlmacen1 AND D.IDAlmacen2 = 20
	WHERE A.ID < 20
UNION
SELECT *
	FROM Almacenes AS A
		INNER JOIN Distancias AS D
			ON D.IDAlmacen1 = 20 AND A.ID = D.IDAlmacen2
	WHERE A.ID > 20

	SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON A.ID = D.IDAlmacen1 AND IDAlmacen2 = 20 WHERE A.ID < 20 UNION SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON D.IDAlmacen1 = 20 AND A.ID = D.IDAlmacen2 WHERE A.ID > 20 ORDER BY ID


SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON A.ID = D.IDAlmacen1 AND IDAlmacen2 = 20 WHERE A.ID < 20 UNION SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON D.IDAlmacen1 = 20 AND A.ID = D.IDAlmacen2 WHERE A.ID > 20


SELECT AlmacenPreferido FROM Envios WHERE ID = 2

SELECT * FROM Almacenes

SELECT AlmacenPreferido FROM Envios WHERE ID = ?