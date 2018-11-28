package Gestoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestoraFormularios {

    public static void mainFormulario() throws SQLException
    {
        GestoraPedidos gPe = new GestoraPedidos();
        GestoraProductos gPr = new GestoraProductos();
        Scanner teclado = new Scanner(System.in);
        int numeroPedidos, pedidoElegido;
        ResultSet productosEnPedido;

        numeroPedidos = gPe.mostrarListaPedidosSinAtender();

        //El programa continuará en caso de que haya pedidos que atender
        if(numeroPedidos > 0)
        {
            do
            {
                System.out.println("Elija el número del pedido que quiera (posición) o 0 para salir:");
                pedidoElegido = teclado.nextInt();
                if(pedidoElegido < 0 || pedidoElegido > numeroPedidos)
                    System.out.println("¡Escribe un número entre 1 y "+numeroPedidos+", o 0 para salir!");
            }while(pedidoElegido < 0 || pedidoElegido > numeroPedidos);

            //Continuaremos con el programa si ha elegido un número distinto de 0
            if(pedidoElegido != 0)
            {
                gPe.atenderPedido(pedidoElegido);
            }
            else
                System.out.println("Taluego Lucas");
        }
        else
            System.out.println("¡No hay pedidos para mostrar!");
    }
}
