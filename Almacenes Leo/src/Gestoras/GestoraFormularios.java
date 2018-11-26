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
        boolean idEsValida, envioAsignado;
        GestoraEnvios gestoraEnvios = new GestoraEnvios();
        GestoraAlmacenes gestoraAlmacenes = new GestoraAlmacenes();

        //Mostramos envíos sin asignar
        System.out.println("Envios Sin Asignar: \n");
        gestoraEnvios.mostrarEnviosSinAsignar();

        //Validamos la ID que se ha introducido
        do{
            System.out.println("Introduce la ID de un envio sin asignar");
            idEnvio = sc.nextInt();
            idEsValida = gestoraEnvios.validarIdEnvioSinAsignar(idEnvio);
            if(!idEsValida)
                System.out.println("Id Errónea, vuelve a intentarlo");
        }while(!idEsValida);

        //Obtenemos la ID del Almacén Preferido
        idAlmacen = gestoraAlmacenes.obtenerIdAlmacenPreferido(idEnvio);


        if(gestoraAlmacenes.cabePedidoEnAlmacen(idAlmacen, idEnvio)) //Si el envío cabe en el almacén preferido
        {
            if (gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacen))
                //Asignamos el envío al almacén preferido y mostramos mensaje de éxito
                System.out.println("Se ha asignado el envio al almacén preferido correctamente");
            else
                //Si ha ocurrido algún error al asignar el envío, mostramos un mensaje de error
                System.out.println("Error, no se ha podido asignar el envio al almacen preferido");
        }
        else //Si el envío no cabe en el almacén preferido
        {
            //Obtenemos un listado de almacenes ordenado por distancia
            System.out.println("El envio no cabe en el almacen preferido, comprobando almacenes más cercanos...");
            almacenesDistancia = gestoraAlmacenes.obtenerListadoAlmacenesPorDistancia(idAlmacen);
            if (almacenesDistancia != null) //Si hay almacenes en los que mirar
            {
                envioAsignado = false;
                //Mientras haya almacenes que mirar y no se haya asignado ya el envío
                while(almacenesDistancia.next() && !envioAsignado)
                {
                    //Comprobamos que la ID del almacén más cercano no sea la misma que la del almacén preferido
                    //Si es así, cogemos la ID del almacén 2
                    idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen1");
                    if (idAlmacenCercano == idAlmacen){
                        idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen2");
                    }

                    //Comprobamos si cabe en el almacén
                    if (gestoraAlmacenes.cabePedidoEnAlmacen(idAlmacenCercano, idEnvio))
                    {
                        //Si cabe en el almacén, se pregunta si desea aceptar ese almacén
                        System.out.println("El pedido cabe en el almacen: " + almacenesDistancia.getString("Denominacion"));
                        do{
                            System.out.println("¿Desea asignar el pedido a ese almacen? (y/n)");
                            respuesta = Character.toLowerCase(sc2.next().charAt(0));
                        }while(respuesta != 'y' && respuesta != 'n' );

                        //Si la respuesta es que sí
                        if (respuesta == 'y')
                        {
                            envioAsignado = true;
                            //Se asigna el envío al almacén elegido
                            if(gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacenCercano))
                                System.out.println("¡¡Envío asignado correctamente!!\n");
                            else
                                System.out.println("Envío no asignado :( \n");
                        }
                    }
                }

                //Si no se ha podido asignar o no ha querido asignarlo a ningún almacén
                if (!envioAsignado)
                {
                    //Motrar mensaje no hay almacenes
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
        GestoraAlmacenes gestoraAlmacenes = new GestoraAlmacenes();

        do{
            System.out.println("Introduce el numero de contenedores:");
            numCont = sc.nextInt();
        }while(numCont <= 0);

        System.out.println("Almacenes Disponibles: \n");
        gestoraAlmacenes.mostrarAlmacenes();

        do{
            System.out.println("Introduce el ID de tu almacén preferido: ");
            idAlmacenPreferido = sc.nextInt();
            idEsValida = gestoraAlmacenes.validarIdAlmacen(idAlmacenPreferido);
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
