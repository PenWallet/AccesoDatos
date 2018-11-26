package Gestoras;

import Conexion.GeneradorConexiones;

import java.sql.*;

public class GestoraAlmacenes {

    /*
     ***********************************************************************************
     ********************************* C O N E X I O N *********************************
     ***********************************************************************************
    */
    private Connection conexion;
    public GestoraAlmacenes() throws SQLException {
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

    public boolean validarIdAlmacen(int idAlmacen) throws SQLException{
        boolean existe = false;
        String sql = "SELECT dbo.fnValidarIdAlmacen(?) AS ret";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idAlmacen);
        ResultSet rs = sentencia.executeQuery();

        if(rs!=null)
        {
            rs.next();
            existe = rs.getBoolean("ret");
        }

        return existe;
    }

    /*
     ***********************************************************************************
     ********************************** F U N C I O N E S ******************************
     ***********************************************************************************
    */
    public void mostrarAlmacenes(){
        String sql = "SELECT * FROM Almacenes";
        ResultSet rs = ejecutarQuery(sql);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + " | " + rs.getString("Denominacion") + " | " + rs.getString("Direccion") + " | " + rs.getInt("Capacidad"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }
    }

    public ResultSet obtenerListadoAlmacenesPorDistancia(int idAlmacen) throws SQLException {
        String sql = "SELECT * FROM (SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON A.ID = D.IDAlmacen1 AND IDAlmacen2 = "+idAlmacen+" WHERE A.ID < "+idAlmacen+" UNION SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON D.IDAlmacen1 = "+idAlmacen+" AND A.ID = D.IDAlmacen2 WHERE A.ID > "+idAlmacen+") AS D ORDER BY D.Distancia";

        /*PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idAlmacen);
        sentencia.setInt(2, idAlmacen);
        sentencia.setInt(3, idAlmacen);
        sentencia.setInt(4, idAlmacen);*/

        ResultSet rs = ejecutarQuery(sql);

        return rs;
    }

    public boolean cabePedidoEnAlmacen(int idAlmacen, int idEnvio) throws SQLException{
        boolean cabe = false;
        String sql = "SELECT dbo.fnCabePedidoEnAlmacen(?, ?) AS ret";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setInt(1, idAlmacen);
        sentencia.setInt(2, idEnvio);

        ResultSet rs = sentencia.executeQuery();

        if(rs != null)
        {
            rs.next();
            cabe = rs.getBoolean("ret");
        }

        return cabe;
    }

    public int obtenerIdAlmacenPreferido(int idEnvio) throws SQLException{
        int idAlmacen = 0;

        String sql = "SELECT AlmacenPreferido FROM Envios WHERE ID = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idEnvio);
        ResultSet rs = sentencia.executeQuery();
        if(rs != null)
        {
            rs.next();
            idAlmacen = rs.getInt("AlmacenPreferido");
        }

        return idAlmacen;
    }
}
