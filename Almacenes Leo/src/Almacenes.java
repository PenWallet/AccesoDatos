/*
    Nombre:
        Almacenes

    Descripción:
        Programa que se usará para manejar los envíos de nuestra aplicación y base de datos

    Pseudocódigo Generalizado:
        Inicio
            Repetir
                Mostrar menú *
                Leer opción
                Según (opcion)
                    Caso 1: Mostrar envíos asignados
                    Caso 2: Agregar envío
                    Caso 3: Asignar envíos
                Fin Según
            Mientras (opcion != 0)
        Fin
 */

import java.sql.SQLException;
import java.util.Scanner;

import Gestoras.GestoraEnvios;
import Gestoras.GestoraMenus;

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
                    gestoraEnvios.obtenerEnvios();
                    break;

                case 2:
                    System.out.println("En construcción");
                    break;

                case 3:
                    System.out.println("En construcción");
                    break;
            }

        }while(opcion != 0);
    }
}
