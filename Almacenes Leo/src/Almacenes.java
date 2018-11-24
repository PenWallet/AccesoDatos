/*
    Nombre:
        Almacenes

    Descripci�n:
        Programa que se usar� para manejar los env�os de nuestra aplicaci�n y base de datos

    Pseudoc�digo Generalizado:
        Inicio
            Repetir
                Mostrar men� *
                Leer opcion
                Segun (opcion)
                    Caso 1: Mostrar env�os asignados
                    Caso 2: Agregar env�o
                    Caso 3: Asignar env�os
                Fin Seg�n
            Mientras (opcion != 0)
        Fin

    Pseudocódigo Generalizado (Asignar Envíos)
        Inicio
            MostrarEnviosSinAsignar *

            Repetir
                Leer ID de envio sin asignar
            Mientras(ValidarIDEnvioSinAsignar * sea falso)

            ComprobarCabeAlmacen (AlmacenPreferido) *
            Si (envio cabe en almacen preferido)
                AsignarEnvioAlmacen (AlmacenPreferido)
            Sino
                Mientras 

                Fin Mientras
            Fin Si
        Fin
 */

import java.sql.SQLException;
import java.util.Scanner;

import Gestoras.GestoraEnvios;
import Gestoras.GestoraMenus;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Almacenes
{
    public static void main(String[] args)
    {
        GestoraEnvios gestoraEnvios = null;
        try {
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
                    gestoraEnvios.agregarEnvioFormulario();
                } catch (SQLException ex) {
                    ex.getMessage();
                }
            }
                    break;

                case 3:
                    System.out.println("En construccion");
                    break;
            }

        }while(opcion != 0);
    }
}
