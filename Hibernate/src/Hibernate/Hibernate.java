/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import gestoras.GestoraMenus;
import java.util.Scanner;

/**
 *
 * @author ofunes
 */
public class Hibernate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcion;
        
        do
        {
            do
            {
                GestoraMenus.mostrarMenu();
                opcion = teclado.nextInt();
                if(opcion < 0 || opcion > 9)
                    System.out.println("¡Elige una opción entre 1 y 9, o 0 para salir!");
            }while(opcion < 0 || opcion > 9);
            
            switch(opcion)
            {
                case 1:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 2:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 3:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 4:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 5:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 6:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 7:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 8:
                    System.out.println("En construction, "+opcion);
                    break;
                    
                case 9:
                    System.out.println("En construction, "+opcion);
                    break;
            }
        }while (opcion != 0);
        
        System.out.println("Taluegorl");
    }
    
}
