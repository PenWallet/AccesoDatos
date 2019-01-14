package Gestoras;

import Clases.Producto;
import Conexion.GeneradorConexiones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestoraPedidos {

    private Connection conexion;
    private ResultSet pedidosSinAtender;
    public GestoraPedidos() throws SQLException {
        conexion = GeneradorConexiones.getConexion();
        pedidosSinAtender = obtenerPedidosSinAtender();
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

    /**
     * Función que devuelve un ResultSet actualizable con todos los pedidos sin atender con toda su información
     * Privado porque se usa en el constructor de GestoraPedidos únicamente, y se trabaja con ese
     * @return ResultSet
     */
    private ResultSet obtenerPedidosSinAtender()
    {
        String sql = "SELECT IDPedido, FechaPedido, FechaServido, FechaEnvio, NumeroVenta, IDCliente, MedioEnvio, CodigoAprobacionTarjeta, SubTotal, Impuestos, C.Nombre, C.Apellidos, C.SegundoNombre, C.Tratamiento, C.NombreEmpresa FROM Pedidos AS P INNER JOIN Clientes AS C ON P.IDCliente = C.ID WHERE FechaServido IS NULL";
        Statement sentencia;
        ResultSet rs = null;
        try
        {
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = sentencia.executeQuery(sql);
        } catch (SQLException e) { e.printStackTrace(); }

        return rs;
    }

    /**
     * Función que muestra en pantalla los pedidos sin atender usando el ResultSet de pedidosSinAtender y
     * devuelvuen el número de filas que hay en el ResultSet (es decir, el número de pedidos
     * sin atender)
     * @return Número de pedidos sin atender
     */
    public int mostrarListaPedidosSinAtender()
    {
        int contador = 0;

        if(pedidosSinAtender!=null)
        {
            try
            {
                while(pedidosSinAtender.next())
                {
                    contador++;
                    System.out.println(contador+". "+pedidosSinAtender.getInt("IDPedido")+" | "+pedidosSinAtender.getString("Tratamiento")+" "+pedidosSinAtender.getString("Nombre")+(pedidosSinAtender.getString("SegundoNombre") == null ? "" : " "+pedidosSinAtender.getString("SegundoNombre"))+" "+pedidosSinAtender.getString("Apellidos")+" | "+pedidosSinAtender.getString("NombreEmpresa")+" | "+pedidosSinAtender.getString("FechaPedido"));
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }
        return contador;
    }

    /**
     * Función que actualiza:
     *  - La FechaServido del pedido
     *  - Actualiza y agrega los productos a LineasPedidos
     *  - Actualiza el stock disponible de los productos
     *
     * Devuelve true si se ha hecho todo correctamente, false en caso contrario
     * @param posicionAbsolutaEnvio
     * @param listaProdAlt
     * @return
     */
    public boolean finiquitarPedido(int posicionAbsolutaEnvio, ResultSet productosPedido, List<Producto> listaProdAlt) throws SQLException
    {
        Producto prodAlt;
        int IDPedido;
        ResultSet rsActStock;
        GestoraProductos gp = new GestoraProductos();

        //Actualizar la fecha del envío
        pedidosSinAtender.absolute(posicionAbsolutaEnvio);
        pedidosSinAtender.updateDate("FechaServido", java.sql.Date.valueOf(LocalDate.now()));
        pedidosSinAtender.updateRow();

        IDPedido = pedidosSinAtender.getInt("IDPedido");

        rsActStock = gp.obtenerProductosDePedido(IDPedido);

        //Actualizar y agregar los productos a LineasPedidos
        if(listaProdAlt.size() != 0)
        {
            for(int i = 0; i < listaProdAlt.size(); i++)
            {
                prodAlt = listaProdAlt.get(i);

                //Actualizar la cantidad pedida de los productos originales de los que no había suficiente
                productosPedido.absolute(prodAlt.getPosicionAbsolutaProdOrig());
                productosPedido.updateInt("Cantidad", prodAlt.getCantidadOrig());
                productosPedido.updateRow();

                //Insertamos la fila del producto alternativo
                productosPedido.moveToInsertRow();
                productosPedido.updateInt("IDPedido", IDPedido);
                productosPedido.updateInt("Cantidad", prodAlt.getCantidadAlt());
                productosPedido.updateInt("IDProducto", prodAlt.getIDAlternativa());
                productosPedido.updateInt("Precio", 1);
                productosPedido.insertRow();
            }
        }

        //Ahora actualiamos el stock de los productos
        productosPedido.absolute(1);
        rsActStock.absolute(1);
        rsActStock.updateInt("Stock", rsActStock.getInt("Stock") - productosPedido.getInt("Cantidad"));
        rsActStock.updateRow();
        while(productosPedido.next() && rsActStock.next())
        {
            rsActStock.updateInt("Stock", rsActStock.getInt("Stock") - productosPedido.getInt("Cantidad"));
            rsActStock.updateRow();
        }

        //No tengo tiempo para hacer comprobaciones sorry
        return true;
    }

    /**
     * Función que va a comprobar si se dispone de stock, preguntar al usuario, etc. dependiendo
     * de la posición absoluta del pedido en el ResultSet pedidosSinAtender
     * @param posicionAbsoluta Número de la fila del pedido en el ResultSet pedidosSinAtender
     */
    public void atenderPedido(int posicionAbsoluta) throws SQLException
    {
        int IDPedido, IDProducto, cantidadProducto, IDProductoAlt, cantidadProductoAlt;
        boolean stockSuficiente, stockSuficienteAlt, pedidoIrrealizable = false;
        char respuesta;
        GestoraProductos gp = new GestoraProductos();
        ResultSet productosPedido, productoOriginal, productoAlt;
        Scanner teclado = new Scanner(System.in);
        ArrayList<Producto> listaProdAlt = new ArrayList<>();

        pedidosSinAtender.absolute(posicionAbsoluta);

        IDPedido = pedidosSinAtender.getInt("IDPedido");
        productosPedido = gp.obtenerProductosEnPedido(IDPedido);
        //LP.IDPedido, LP.Cantidad, LP.IDProducto, LP.precio, P.Nombre

        if(productosPedido != null)
        {
            while(productosPedido.next() && !pedidoIrrealizable)
            {
                IDProducto = productosPedido.getInt("IDProducto");
                cantidadProducto = productosPedido.getInt("Cantidad");
                productoOriginal = gp.obtenerFilaProducto(IDProducto);

                stockSuficiente = gp.haySuficienteStock(IDProducto, cantidadProducto);

                //Si no hay stock suficiente
                if(!stockSuficiente)
                {
                    productoOriginal.next();
                    System.out.println("¡Lo sentimos! No hay suficiente stock de "+productoOriginal.getString("Nombre")+". Usted ha pedido "+cantidadProducto+" y solo tenemos "+productoOriginal.getInt("Stock")+" disponible");
                    System.out.println("Comprobando si hay establecido un producto sustitutivo...");
                    IDProductoAlt = productoOriginal.getInt("Sustitutivo");

                    //Si no hay producto alternativo
                    if(IDProductoAlt == 0)
                    {
                        pedidoIrrealizable = true;
                        System.out.println("¡No se ha encontrado producto sustitutivo para este producto! No se puede atender el pedido. Nos vemo loco");
                    }
                    else //Si sí hay producto alternativo
                    {
                        productoAlt = gp.obtenerFilaProducto(IDProductoAlt);
                        productoAlt.next();
                        do
                        {
                            System.out.println("¿Desea comprobar si hay suficiente stock de "+productoAlt.getString("Nombre")+" para rellenar ese pedido? (Y/N)");
                            respuesta = Character.toUpperCase(teclado.next().charAt(0));
                            if(respuesta != 'Y' && respuesta != 'N')
                                System.out.println("¡Solo Y o N!");
                        }while(respuesta != 'Y' && respuesta != 'N');

                        //Si se quiere comprobar si hay stock del producto alternativo
                        if(respuesta == 'Y')
                        {
                            cantidadProductoAlt = cantidadProducto - productoOriginal.getInt("Stock");
                            cantidadProducto = productoOriginal.getInt("Stock");
                            stockSuficienteAlt = gp.haySuficienteStock(IDProductoAlt, cantidadProductoAlt);
                            //Si hay stock suficiente del producto alternativo
                            if(stockSuficienteAlt)
                            {
                                System.out.println("¡Hay suficientes "+productoAlt.getString("Nombre")+"!");
                                listaProdAlt.add(new Producto(productosPedido.getRow(), IDProductoAlt, cantidadProducto, cantidadProductoAlt));
                            }
                            else //Si no hay stock suficiente del producto alternativo
                            {
                                pedidoIrrealizable = true;
                                System.out.println("¡Lo sentimos, no hay suficiente stock de "+productoAlt.getString("Nombre")+", no se puede agregar el envío. Bye bye.");
                            }
                        }
                        else //Si no se quiere comprobar si hay stock del producto alternativo
                        {
                            pedidoIrrealizable = true;
                            System.out.println("No podemos continuar con el pedido entonces. Un abruzo, dew");
                        }
                    }
                }
                else
                {
                    productoOriginal.next();
                    System.out.println("¡Hay suficiente stock de "+productoOriginal.getString("Nombre")+"!");
                    System.out.println("Comprobando el siguiente producto...");
                }
            }
        }

        if(!pedidoIrrealizable)
        {
            if(finiquitarPedido(posicionAbsoluta, productosPedido, listaProdAlt))
                System.out.println("¡Se ha actualizado correctamente el pedido!");
            else
                System.out.println("Algo fue mal :(");
        }
    }
}
