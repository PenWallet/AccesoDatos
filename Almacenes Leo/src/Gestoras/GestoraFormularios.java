package Gestoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestoraFormularios
{
    public static void asignarEnvioFormulario() throws SQLException
    {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int idAlmacen, idEnvio, idAlmacenCercano;
        char respuesta;
        ResultSet almacenesDistancia;
        boolean idEsValida, cabeAlmacen, envioAsignado;
        GestoraEnvios gestoraEnvios = new GestoraEnvios();

        System.out.println("Envios Sin Asignar: \n");
        gestoraEnvios.mostrarEnviosSinAsignar();

        do{
            System.out.println("Introduce la ID de un envio sin asignar");
            idEnvio = sc.nextInt();
            idEsValida = gestoraEnvios.validarIdEnvioSinAsignar(idEnvio);
            if(!idEsValida)
                System.out.println("Id Errónea, vuelve a intentarlo");
        }while(!idEsValida);

        idAlmacen = gestoraEnvios.obtenerIdAlmacenPreferido(idEnvio);

        if(gestoraEnvios.cabePedidoEnAlmacen(idAlmacen, idEnvio)) {
            if (gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacen))
                System.out.println("Se ha asignado el envio al almacén preferido correctamente");
            else
                System.out.println("Error, no se ha podido asignar el envio al almacen preferido");
        }else{
            System.out.println("El envio no cabe en el almacen preferido, comprobando almacenes más cercanos...");
            almacenesDistancia = gestoraEnvios.obtenerListadoAlmacenesPorDistancia(idAlmacen);
            if (almacenesDistancia != null) {
                envioAsignado = false;
                while(almacenesDistancia.next() && !envioAsignado){
                    idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen1");
                    if (idAlmacenCercano == idAlmacen){
                        idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen2");
                    }

                    if (gestoraEnvios.cabePedidoEnAlmacen(idAlmacenCercano, idEnvio)){
                        System.out.println("El pedido cabe en el almacen: " + almacenesDistancia.getString("Denominacion"));
                        do{
                            System.out.println("¿Desea asignar el pedido a ese almacen? (y/n)");
                            respuesta = Character.toLowerCase(sc2.next().charAt(0));
                        }while(respuesta != 'y' && respuesta != 'n' );

                        if (respuesta == 'y'){
                            envioAsignado = true;
                            if(gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacenCercano))
                                System.out.println("¡¡Envío asignado correctamente!!\n");
                            else
                                System.out.println("Envío no asignado :( \n");
                        }
                    }
                }

                if (!envioAsignado){
                    System.out.println("Tu envio no se ha podido asignar en ningun almacén");
                }
            }
        }
    }

    public static void agregarEnvioFormulario() throws SQLException{
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int numCont = 0, idAlmacenPreferido;
        char respuesta;
        boolean idEsValida;
        GestoraEnvios gestoraEnvios = new GestoraEnvios();

        do{
            System.out.println("Introduce el numero de contenedores:");
            numCont = sc.nextInt();
        }while(numCont <= 0);

        System.out.println("Almacenes Disponibles: \n");
        gestoraEnvios.mostrarAlmacenes();

        do{
            System.out.println("Introduce el ID de tu almacén preferido: ");
            idAlmacenPreferido = sc.nextInt();
            idEsValida = gestoraEnvios.validarIdAlmacen(idAlmacenPreferido);
            if(!idEsValida)
                System.out.println("Id Errónea, vuelve a intentarlo");
        }while(!idEsValida);

        do{
            System.out.println("¿Desea agregar el envio? (y/n)");
            respuesta = Character.toLowerCase(sc2.next().charAt(0));
        }while(respuesta != 'y' && respuesta != 'n' );

        if (respuesta == 'y'){
            if(gestoraEnvios.agregarEnvio(idAlmacenPreferido, numCont))
                System.out.println("¡¡Envío agregado correctamente!!\n");
            else
                System.out.println("Envío no agregado :(\n");
        }
        else
            System.out.println("Okay, pos no se agrega na\n");
    }

}
