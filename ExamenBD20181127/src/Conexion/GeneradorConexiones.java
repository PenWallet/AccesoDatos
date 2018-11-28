package Conexion;

import java.sql.*;

public class GeneradorConexiones {

    public static Connection getConexion() throws SQLException {
        String url="jdbc:sqlserver://localhost;databaseName=ACDAT;user=amazon;password=allavoy;";
        return DriverManager.getConnection(url);
    }
    public static void cerrar ( ResultSet rs ) {
        try{
            rs.close();
        }catch (Exception ignored){}
    }
    public static void cerrar ( Statement st ) {
        try{
            st.close();
        }catch (Exception ignored){}
    }
    public static void cerrar (Connection con) {
        try{
            con.close();
        }catch (Exception ignored){}
    }

}
