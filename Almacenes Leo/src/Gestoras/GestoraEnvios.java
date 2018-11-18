package Gestoras;

import Conexion.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestoraEnvios {
    private Connection conexion;

    public GestoraEnvios() throws SQLException {
        conexion = GeneradorConexiones.getConexion();
    }

    public ResultSet ejecutarQuery(String sentenciaSQL) {
        ResultSet resultado;
        Statement sentencia;
        try{
            sentencia=conexion.createStatement();
            resultado=sentencia.executeQuery(sentenciaSQL);
        } catch(SQLException sql){
            System.out.println(sql.getMessage());
            return null;
        }
        return resultado;
    }

    public void obtenerEnvios(){
        String sentencia = "SELECT A.Denominacion, E.FechaAsignacion, E.NumeroContenedores FROM Envios AS E INNER JOIN Asignaciones AS ASI ON E.ID = ASI.IDEnvio INNER JOIN Almacenes AS A  ON ASI.IDAlmacen = A.ID WHERE FechaAsignacion IS NOT NULL";
        ResultSet rs = ejecutarQuery(sentencia);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println("Fecha de Asignacion: " + rs.getDate("FechaAsignacion").toString() + " | Nº Contenedores: "+rs.getInt("NumeroContenedores") + " | Nombre de Almacén: " + rs.getString("Denominacion"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }

    }

}
