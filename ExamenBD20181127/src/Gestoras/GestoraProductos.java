package Gestoras;

import Conexion.GeneradorConexiones;

import java.sql.*;

public class GestoraProductos
{
    private Connection conexion;
    public GestoraProductos() throws SQLException {
        conexion = GeneradorConexiones.getConexion();
    }

    /**
     * Función que devuelve true si hay disponible la suficiente cantidad del producto introducido, y false en caso contrario
     * @param idProducto ID del producto
     * @param cantidad Cantidad del producto
     * @return True si hay suficiente, false si no
     * @throws SQLException
     */
    public boolean haySuficienteStock(int idProducto, int cantidad) throws SQLException
    {
        boolean valido = false;
        String sql = "SELECT dbo.fnHaySuficienteStock(?, ?) AS ret";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idProducto);
        sentencia.setInt(2, cantidad);
        ResultSet rs = sentencia.executeQuery();

        if(rs!=null)
        {
            rs.next();
            valido = rs.getBoolean("ret");
        }

        return valido;
    }

    /**
     * Función que devuelve un ResultSet con los productos que hay pedidos en un pedido específico
     * @param IDPedido ID del Pedido
     * @return ResultSet
     */
    public ResultSet obtenerProductosEnPedido(int IDPedido)
    {
        String sql = "SELECT LP.IDPedido, LP.Cantidad, LP.IDProducto, LP.precio, P.Nombre, P.Stock FROM LineasPedidos AS LP INNER JOIN Productos AS P ON LP.IDProducto = P.IDProducto WHERE IDPedido = ?";
        PreparedStatement sentencia;
        ResultSet rs = null;
        try
        {
            sentencia = conexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sentencia.setInt(1, IDPedido);
            rs = sentencia.executeQuery();
        } catch (SQLException e) { e.printStackTrace(); }

        return rs;
    }

    public ResultSet obtenerProductosDePedido(int IDPedido)
    {
        String sql = "SELECT * FROM Productos AS P INNER JOIN LineasPedidos AS LP ON P.IDProducto = LP.IDProducto WHERE LP.IDPedido = ?";
        PreparedStatement sentencia;
        ResultSet rs = null;
        try
        {
            sentencia = conexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sentencia.setInt(1, IDPedido);
            rs = sentencia.executeQuery();
        } catch (SQLException e) { e.printStackTrace(); }

        return rs;
    }

    /**
     * Función que devuelve la ID del producto alternativo de un producto
     * @param IDProducto ID del Producto
     * @return ID del Producto alternativo
     */
    public int obtenerIDProductoAlternativo(int IDProducto)
    {
        String sql = "SELECT Sustitutivo FROM Productos WHERE IDProducto = ?";
        PreparedStatement sentencia;
        ResultSet rs = null;
        int idAlternativa = -1;
        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, IDProducto);
            rs = sentencia.executeQuery(sql);

            if(rs != null)
            {
                if(rs.next())
                    idAlternativa = rs.getInt("Sustitutivo");
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return idAlternativa;
    }

    /**
     * Función que devuelve un ResultSet con el nombre del producto
     * @param IDProducto
     * @return
     */
    public ResultSet obtenerFilaProducto(int IDProducto)
    {
        String sql = "SELECT Nombre, Numero, Color, PVP, Stock, Sustitutivo FROM Productos WHERE IDProducto = ?";
        PreparedStatement sentencia;
        ResultSet rs = null;
        try
        {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, IDProducto);
            rs = sentencia.executeQuery();
        } catch (SQLException e) { e.printStackTrace(); }

        return rs;
    }
}
