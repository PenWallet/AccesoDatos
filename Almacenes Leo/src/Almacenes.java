/*
    Nombre:
        Almacenes

    Descripción:
        Programa que se usará para manejar los envíos de nuestra aplicación y base de datos

    Pseudocódigo Generalizado:
        Inicio
            Repetir
                Mostrar menú *
                Leer opcion
                Segun (opcion)
                    Caso 1: Mostrar envíos asignados
                    Caso 2: Agregar envío
                    Caso 3: Asignar envíos
                Fin Según
            Mientras (opcion != 0)
        Fin

    Pseudocódigo Generalizado (Asignar Envíos)
        Inicio
            MostrarEnviosSinAsignar *

            Repetir
                Leer ID de envio sin asignar
            Mientras(ValidarIDEnvioSinAsignar * sea falso)

            ObtenerIDAlmacenPreferido *

            ComprobarCabeAlmacen (AlmacenPreferido) *
            Si (envio cabe en almacen preferido)
                AsignarEnvioAlmacen (AlmacenPreferido)
            Sino
                Mientras
                    ObtenerListadoAlmacenesPorDistancia *
                    Si (hay almacenes)
                        Mientras (haya almacenes y no haya aceptado un almacén)
                            ComprobarCabeAlmacen(IDAlmacen)
                            Si (cabe en almacén)
                                Preguntar si desea aceptar ese almacén
                                Leer respuesta
                            Fin Si
                            Si (desea aceptar ese almacén)
                                AsignarEnvioAlmacen(Almacén)
                            Fin Si
                        Fin Mientras
                        Si (no ha elegido ningún almacén)
                            Mostrar mensaje de que no se ha asignado
                        Fin Si
                    Si no
                        Mostrar mensaje no hay almacenes
                    Fin Si
                Fin Mientras
            Fin Si
        Fin
 */

/*
    Preguntar a Leo por cómo hacer las conexiones y las ? de obtenerListadoAlmacenesPorDistancia
 */

import java.sql.SQLException;
import java.util.Scanner;

import Gestoras.GestoraEnvios;
import Gestoras.GestoraFormularios;
import Gestoras.GestoraMenus;

public class Almacenes
{
    public static void main(String[] args)
    {
        GestoraEnvios gestoraEnvios = null;
        try
        {
            gestoraEnvios = new GestoraEnvios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int opcion;
        Scanner teclado = new Scanner(System.in);

        do
        {
            do
            {
                GestoraMenus.MostrarMenuPrincipal();
                opcion = teclado.nextInt();
                if(opcion < 0 || opcion > 3)
                    System.out.println("¡Solo entre 1 o 3, o 0 para salir!");
            }while(opcion < 0 || opcion > 3);

            switch (opcion)
            {
                case 1:
                    System.out.println("Envios Asignados: ");
                    System.out.println(" ");
                    gestoraEnvios.obtenerEnviosAsignados();
                    break;

                case 2:
            {
                try {
                    GestoraFormularios.agregarEnvioFormulario();
                } catch (SQLException ex) {
                    ex.getMessage();
                }
            }
                    break;

                case 3:
                    try {
                        GestoraFormularios.asignarEnvioFormulario();
                    } catch (SQLException ex) {
                        ex.getMessage();
                    }
                    break;
            }

        }while(opcion != 0);
    }
}
