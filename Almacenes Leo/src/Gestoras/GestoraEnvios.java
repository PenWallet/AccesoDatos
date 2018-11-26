package Gestoras;

import Conexion.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Scanner;

public class GestoraEnvios {
    /*
        ***********************************************************************************
        ********************************* C O N E X I O N *********************************
        ***********************************************************************************
    */
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


    /*
       ***********************************************************************************
       ********************************** F U N C I O N E S ******************************
       ***********************************************************************************
    */
    public void obtenerEnviosAsignados(){
        String sentencia = "SELECT A.Denominacion, E.FechaAsignacion, E.NumeroContenedores FROM Envios AS E INNER JOIN Asignaciones AS ASI ON E.ID = ASI.IDEnvio INNER JOIN Almacenes AS A  ON ASI.IDAlmacen = A.ID WHERE FechaAsignacion IS NOT NULL ORDER BY FechaAsignacion";
        ResultSet rs = ejecutarQuery(sentencia);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println("Fecha de Asignacion: " + rs.getDate("FechaAsignacion").toString() + " | Nº Contenedores: "+rs.getInt("NumeroContenedores") + " | Nombre de Almacen: " + rs.getString("Denominacion"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean validarIdEnvioSinAsignar(int idEnvio) throws SQLException {
        boolean valido = false;
        String sql = "SELECT dbo.fnValidarIdEnvioSinAsignar(?) AS ret";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idEnvio);
        ResultSet rs = sentencia.executeQuery();

        if(rs!=null)
        {
            rs.next();
            valido = rs.getBoolean("ret");
        }

        return valido;
    }

    public boolean agregarEnvio(int idAlmacen, int nContenedores) throws SQLException
    {
        boolean creado;
        int filasAfectadas;
        LocalDate date = LocalDate.now();
        String sql = "INSERT INTO Envios(NumeroContenedores, FechaCreacion, AlmacenPreferido) VALUES (?, ?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setInt(1, nContenedores);
        sentencia.setDate(2, java.sql.Date.valueOf(date));
        sentencia.setInt(3, idAlmacen);

        filasAfectadas = sentencia.executeUpdate();

        if (filasAfectadas == 1){
            creado = true;
        }else
            creado = false;

        return creado;
    }

    public void mostrarEnviosSinAsignar(){
        String sql = "SELECT E.ID, E.NumeroContenedores, E.FechaCreacion, A.Denominacion FROM Envios AS E INNER JOIN Almacenes AS A ON E.AlmacenPreferido = A.ID WHERE FechaAsignacion IS NULL ORDER BY ID";
        ResultSet rs = ejecutarQuery(sql);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + " | " + rs.getInt("NumeroContenedores") + " | " + rs.getDate("FechaCreacion") + " | " + rs.getString("Denominacion"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean asignarEnvioAlmacen(int idEnvio, int idAlmacen) throws SQLException
    {
        boolean asignado = false, insertValido, updateValido;
        LocalDate date = LocalDate.now();
        String sql = "INSERT INTO Asignaciones(IDEnvio, IDAlmacen) VALUES (?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setInt(1, idEnvio);
        sentencia.setInt(2, idAlmacen);

        //Esto comprueba si el resultado es 1, y si sí, manda true
        insertValido =  sentencia.executeUpdate() == 1;

        sql = "UPDATE Envios SET FechaAsignacion = ? WHERE ID = ?";
        sentencia = conexion.prepareStatement(sql);

        sentencia.setDate(1, java.sql.Date.valueOf(date));
        sentencia.setInt(2, idEnvio);

        //Esto comprueba si el resultado es 1, y si sí, manda true
        updateValido = sentencia.executeUpdate() == 1;

        if (insertValido && updateValido){
            asignado = true;
        }else
            asignado = false;

        return asignado;
    }




    
}
