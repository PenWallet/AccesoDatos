CREATE DATABASE ViajesDB
GO
USE ViajesDB
GO

SET DATEFORMAT dmy

CREATE TABLE Eventos (
	ID int IDENTITY(1,1) NOT NULL,
	Imagen varchar(100),
	FechaInicio date NOT NULL,
	FechaFin date NOT NULL,
	LatitudInicial decimal(5,2) NOT NULL,
	LongitudInicial decimal(5,2) NOT NULL,
	LatitudFinal decimal(5,2),
	LongitudFinal decimal(5,2),
	Tipo varchar(14) NOT NULL,
	Prioridad int DEFAULT 1 NOT NULL,
	FechaRevisado date NULL,
	CONSTRAINT PK_Eventos PRIMARY KEY (ID),
	CONSTRAINT CHK_Eventos_FechaFinLargerOrEqualToFechaInicio CHECK (FechaFin >= FechaInicio),
	CONSTRAINT CHK_Eventos_TipoIsAValidType CHECK (Tipo IN ('Navegando', 'Escala', 'Acontecimiento'))
)

CREATE TABLE Descripciones (
	IDEvento int NOT NULL,
	Idioma varchar(20) NOT NULL,
	Texto text,
	CONSTRAINT FK_Descripciones_Evento FOREIGN KEY (IDEvento) REFERENCES Eventos(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT PK_Descripciones PRIMARY KEY (IDEvento, Idioma),
	CONSTRAINT CHK_Descripciones_IdiomaIsAValidLanguage CHECK (Idioma IN ('Español', 'Inglés', 'Italiano', 'Francés', 'Alemán', 'Ruso', 'Turco', 'Ucraniano', 'Polaco', 'Holandés'))
)

GO

/*
	Comprobar que la prioridad que se ha establecido en el nuevo evento insertado
	no es igual que la prioridad de un evento que ya exista en el mismo rango de fechas
*/
CREATE TRIGGER ComprobarPrioridadBeforeIU ON Eventos INSTEAD OF INSERT, UPDATE
AS
	BEGIN
		DECLARE @FechaInicioInserted date = (SELECT FechaInicio FROM inserted)
		DECLARE @FechaFinInserted date = (SELECT FechaFin FROM inserted)
		DECLARE @PrioridadInserted int = (SELECT Prioridad FROM inserted)
		
		IF(@PrioridadInserted IN (SELECT Prioridad FROM Eventos WHERE (@FechaInicioInserted >= FechaInicio AND @FechaFinInserted <= FechaFin) OR (@FechaInicioInserted BETWEEN FechaInicio AND FechaFin) OR (@FechaFinInserted BETWEEN FechaInicio AND FechaFin)))
			RAISERROR('La prioridad de este evento es la misma que la de un evento existente en el rango de fechas', 18, 0)
		ELSE
			INSERT INTO Eventos SELECT * FROM inserted
	END
GO