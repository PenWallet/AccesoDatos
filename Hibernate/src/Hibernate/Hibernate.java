/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import gestoras.GestoraDeTo;
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
                if(opcion < 0 || opcion > 16)
                    System.out.println("¡Elige una opción entre 1 y 16, o 0 para salir!");
            }while(opcion < 0 || opcion > 16);
            
            switch(opcion)
            {
                case 1:
                    GestoraDeTo.listarTodasLasCriaturitas();
                    break;
                    
                case 2:
                    GestoraDeTo.listarTodosLosRegalos();
                    break;
                    
                case 3:
                    GestoraDeTo.recuperarCriaturitaConRegalos();
                    break;
                    
                case 4:
                    GestoraDeTo.quitarRegaloDeCriaturita();
                    break;
                    
                case 5:
                    GestoraDeTo.asignarRegaloACriaturita();
                    break;
                    
                case 6:
                    GestoraDeTo.crearUnaNuevaCriaturita();
                    break;
                    
                case 7:
                    GestoraDeTo.crearUnNuevoRegalo();
                    break;
                    
                case 8:
                    GestoraDeTo.borrarUnRegalo();
                    break;
                    
                case 9:
                    GestoraDeTo.borrarUnaCriaturitaYSusRegalos();
                    break;
                    
                case 10:
                    GestoraDeTo.listarTodosLosCuentos();
                    break;
                   
                case 11:
                    GestoraDeTo.recuperarCriaturitaConTodosSusCuentos();
                    break;   
                   
                case 12:
                    GestoraDeTo.recuperarCuentoConLectoras();
                    break;   
                   
                case 13:
                    GestoraDeTo.quitarCuentoACriaturita();
                    break;   
                   
                case 14:
                    GestoraDeTo.asignarCuentoACriaturita();
                    break;      
                    
                case 15:
                    GestoraDeTo.crearUnNuevoCuento();
                    break;   
                
                case 16:
                    GestoraDeTo.borrarUnCuento();
                    break;    
            }
        }while (opcion != 0);
        
        System.out.println("Taluegorl");
    }
    
}
