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
        String sentencia = "SELECT E.ID, A.Denominacion, E.FechaAsignacion, E.NumeroContenedores FROM Envios AS E INNER JOIN Asignaciones AS ASI ON E.ID = ASI.IDEnvio INNER JOIN Almacenes AS A  ON ASI.IDAlmacen = A.ID WHERE FechaAsignacion IS NOT NULL ORDER BY FechaAsignacion, E.ID";
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

    //EXAMEN: Ejemplo de Insert con ResultSet
    public boolean agregarEnvio(int idAlmacen, int nContenedores) throws SQLException
    {
        boolean creado = false;
        LocalDate date = LocalDate.now();
        String sql = "SELECT ID, NumeroContenedores, FechaCreacion, AlmacenPreferido FROM Envios";
        Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = sentencia.executeQuery(sql);

        rs.moveToInsertRow();
        rs.updateInt("NumeroContenedores", nContenedores);
        rs.updateDate("FechaCreacion", java.sql.Date.valueOf(date));
        rs.updateInt("AlmacenPreferido", idAlmacen);
        rs.insertRow();
        rs.moveToCurrentRow();

        creado = true;

        return creado; //Tiene razón

        /*
        boolean creado;
        int filasAfectadas;
        //Somos putos dioses del olimpo
        //¿Has visto un semidios?
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
        */
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

    //EXAMEN: Ejemplo de Update con ResultSet y de Insert normal
    public boolean asignarEnvioAlmacen(int idEnvio, int idAlmacen) throws SQLException
    {
        boolean asignado = false, insertValido, updateValido;
        LocalDate date = LocalDate.now();
        String sql = "INSERT INTO Asignaciones(IDEnvio, IDAlmacen) VALUES (?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        boolean filaEncontrada = false;

        sentencia.setInt(1, idEnvio);
        sentencia.setInt(2, idAlmacen);

        //Esto comprueba si el resultado es 1, y si sí, manda true
        insertValido =  sentencia.executeUpdate() == 1;

        sql = "SELECT ID, FechaAsignacion FROM Envios";
        Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = sentencia2.executeQuery(sql);
        while(rs.next() && !filaEncontrada){
            if (rs.getInt("ID") == idEnvio) {
                filaEncontrada = true;
                rs.updateDate("FechaAsignacion", java.sql.Date.valueOf(date));
                rs.updateRow();
            }
        }

       /*sql = "UPDATE Envios SET FechaAsignacion = ? WHERE ID = ?";
        sentencia = conexion.prepareStatement(sql);

        sentencia.setDate(1, java.sql.Date.valueOf(date));
        sentencia.setInt(2, idEnvio);

        //Esto comprueba si el resultado es 1, y si sí, manda true
        updateValido = sentencia.executeUpdate() == 1;
        if (insertValido && filaEncontrada){
            asignado = true;
        }else
            asignado = false;
        return asignado;
           */

        if (insertValido && filaEncontrada){
            asignado = true;
        }else
            asignado = false;

        return asignado;
    }




    
}
