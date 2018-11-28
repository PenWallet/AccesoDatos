package Pruebas;

import Gestoras.GestoraProductos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pruebas {
    public static void main(String[] args) {
        try {
            GestoraProductos gp = new GestoraProductos();
            ResultSet rs = gp.obtenerFilaProducto(707);
            rs.next();
            int x = rs.getInt("Sustitutivo");
            System.out.println(x);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
