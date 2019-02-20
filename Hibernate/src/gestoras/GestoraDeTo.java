package gestoras;

import entidades.*;
import hibernateutil.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ofunes
 */
public class GestoraDeTo {
    
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final Session session = sessionFactory.openSession();
    
    public static void listarTodasLasCriaturitas()
    {
        String hqlQuery = "FROM entidades.Criaturita";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Criaturita> listado = new ArrayList<>(query.list());
        for(Criaturita criaturita : listado)
            System.out.println(criaturita.getId()+". "+criaturita.getNombre());
            
    }
    
    public static void listarTodosLosRegalos()
    {
        String hqlQuery = "FROM entidades.Regalo";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Regalo> listado = new ArrayList<>(query.list());
        for(Regalo regalo : listado)
            System.out.println(regalo.getId()+". "+regalo.getDenominacion()+". Medidas: "+regalo.getAncho()+"x"+regalo.getLargo()+"x"+regalo.getAlto()+". Tipo: "+regalo.getTipo()+". Edad mínima: "+regalo.getEdadMinima()+". Precio: "+regalo.getPrecio()+"€");
    }
    
    public static void recuperarCriaturitaConRegalos()
    {
        Scanner scanner = new Scanner(System.in);
        int idSeleccionado;
        String hqlQueryCriaturita = "FROM entidades.CriaturitaConRegalos";
        
        Query query = session.createQuery(hqlQueryCriaturita);
        ArrayList<CriaturitaConRegalos> listado = new ArrayList<>(query.list());
        if(listado.size() != 0)
        {
            
            for(int i = 0; i < listado.size(); i++)
                System.out.println((i+1)+". "+listado.get(i).getNombre());

            do
            {
                System.out.print("\nSelecciona la criaturita (o 0 para salir): ");
                idSeleccionado = scanner.nextInt();
                if(idSeleccionado < 0 || idSeleccionado > listado.size())
                    System.out.println("Selecciona la criaturita o 0 para salir");
            }while(idSeleccionado < 0 || idSeleccionado > listado.size());
            
            if(idSeleccionado != 0)
            {
                ArrayList<RegaloParaCriaturitaConRegalos> listadoRegalos = new ArrayList<>(listado.get(idSeleccionado-1).getRegalitos());
                if(!listadoRegalos.isEmpty())
                {
                    for(RegaloParaCriaturitaConRegalos regalo : listadoRegalos)
                        System.out.println(regalo.getId()+". "+regalo.getDenominacion()+". Medidas: "+regalo.getAncho()+"x"+regalo.getLargo()+"x"+regalo.getAlto()+". Tipo: "+regalo.getTipo()+". Edad mínima: "+regalo.getEdadMinima()+". Precio: "+regalo.getPrecio()+"€");
                }
                else
                    System.out.println("Esta criaturita no tiene regalos :(");
            }
        }
        else
            System.out.println("¡No hay criaturitas en la base de datos!");
    }
    
    public static void quitarRegaloDeCriaturita()
    {
        Scanner scanner = new Scanner(System.in);
        int idCriaturitaSel, idRegaloSel;
        String hqlQueryCriaturita = "FROM entidades.CriaturitaConRegalos";
        
        Query query = session.createQuery(hqlQueryCriaturita);
        ArrayList<CriaturitaConRegalos> listado = new ArrayList<>(query.list());
        if(listado.size() != 0)
        {
            for(int i = 0; i < listado.size(); i++)
                System.out.println((i+1)+". "+listado.get(i).getNombre());

            do
            {
                System.out.print("\nSelecciona la criaturita (o 0 para salir): ");
                idCriaturitaSel = scanner.nextInt();
            }while(idCriaturitaSel < 0 || idCriaturitaSel > listado.size());
            
            if(idCriaturitaSel != 0)
            {
                CriaturitaConRegalos criaturita = (CriaturitaConRegalos)session.get(CriaturitaConRegalos.class, listado.get(idCriaturitaSel-1).getId());
                if(!criaturita.getRegalitos().isEmpty())
                {
                    for(int i = 0; i < criaturita.getRegalitos().size(); i++)
                    System.out.println((i+1)+". "+criaturita.getRegalitos().get(i).getDenominacion()+". Medidas: "+criaturita.getRegalitos().get(i).getAncho()+"x"+criaturita.getRegalitos().get(i).getLargo()+"x"+criaturita.getRegalitos().get(i).getAlto()+". Tipo: "+criaturita.getRegalitos().get(i).getTipo()+". Edad mínima: "+criaturita.getRegalitos().get(i).getEdadMinima()+". Precio: "+criaturita.getRegalitos().get(i).getPrecio()+"€");
                    do
                    {
                        System.out.println("\nSelecciona el regalo a eliminar (o 0 para salir): ");
                        idRegaloSel = scanner.nextInt();
                    }while(idRegaloSel < 0 || idRegaloSel > criaturita.getRegalitos().size());

                    if(idRegaloSel != 0)
                    {
                        Transaction transaction = session.beginTransaction();
                        criaturita.getRegalitos().get(idRegaloSel - 1).setPropietario(null);
                        criaturita.getRegalitos().remove(idRegaloSel - 1);
                        transaction.commit();
                        System.out.println("Taluego regalorl");
                    }
                    else
                        System.out.println("Nos vemo");
                }
                else
                    System.out.println("Esta criaturita no tiene regalos :(");
            }
        }
        else
            System.out.println("¡No hay criaturitas en la base de datos!");
    }
    
    public static void asignarRegaloACriaturita()
    {
        Scanner scanner = new Scanner(System.in);
        int idCriaturitaSel, idRegaloSel;
        String hqlQueryCriaturita = "FROM entidades.CriaturitaConRegalos";
        String hqlQueryRegalos = "FROM entidades.RegaloParaCriaturitaConRegalos WHERE propietario is null";
        
        Query queryRegalos = session.createQuery(hqlQueryRegalos);
        ArrayList<RegaloParaCriaturitaConRegalos> listadoRegalos = new ArrayList<>(queryRegalos.list());
        
        if(listadoRegalos.size() != 0)
        {
            Query queryCriaturitas = session.createQuery(hqlQueryCriaturita);
            ArrayList<CriaturitaConRegalos> listadoCriaturitas = new ArrayList<>(queryCriaturitas.list());
            
            for(int i = 0; i < listadoCriaturitas.size(); i++)
                System.out.println((i+1)+". "+listadoCriaturitas.get(i).getNombre());

            do
            {
                System.out.print("\nSelecciona la criaturita (o 0 para salir): ");
                idCriaturitaSel = scanner.nextInt();
                if(idCriaturitaSel < 0 || idCriaturitaSel > listadoCriaturitas.size())
                    System.out.println("Selecciona la criaturita o 0 para salir");
            }while(idCriaturitaSel < 0 || idCriaturitaSel > listadoCriaturitas.size());
            
            if(idCriaturitaSel != 0)
            {
                for(int i = 0; i < listadoRegalos.size(); i++)
                    System.out.println((i+1)+". "+listadoRegalos.get(i).getDenominacion()+". Medidas: "+listadoRegalos.get(i).getAncho()+"x"+listadoRegalos.get(i).getLargo()+"x"+listadoRegalos.get(i).getAlto()+". Tipo: "+listadoRegalos.get(i).getTipo()+". Edad mínima: "+listadoRegalos.get(i).getEdadMinima()+". Precio: "+listadoRegalos.get(i).getPrecio()+"€");
                do
                {
                    System.out.println("\nSelecciona el regalo a eliminar (o 0 para salir): ");
                    idRegaloSel = scanner.nextInt();
                }while(idRegaloSel < 0 || idRegaloSel > listadoRegalos.size());
                
                if(idRegaloSel != 0)
                {
                    Transaction transaction = session.getTransaction();
                    transaction.begin();
                    listadoRegalos.get(idRegaloSel - 1).setPropietario(listadoCriaturitas.get(idCriaturitaSel - 1));
                    listadoCriaturitas.get(idCriaturitaSel - 1).getRegalitos().add(listadoRegalos.get(idRegaloSel - 1));
                    transaction.commit();
                    
                    System.out.println("Regalo agregado a ");
                }
            }
        }
        else
            System.out.println("¡No hay regalos disponibles!");
    }
    
    public static void crearUnaNuevaCriaturita()
    {
        Scanner scannerS = new Scanner(System.in);
        String nombre;
        
        System.out.println("\nIntroduce el nombre de la nueva criaturita:");
        nombre = scannerS.nextLine();
        
        byte ID = (byte)session.createQuery("SELECT MAX(id) FROM entidades.Criaturita").uniqueResult();
        ID++;
        
        Criaturita criaturita = new Criaturita(ID, nombre);
        
        session.beginTransaction();
        session.save(criaturita);
        session.getTransaction().commit();
    }
    
    public static void crearUnNuevoRegalo()
    {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerS = new Scanner(System.in);
        String denominacion;
        int ancho, largo, alto, edadMinima, ID;
        char tipo;
        BigDecimal precio;
        
        ID = (byte)session.createQuery("SELECT MAX(id) FROM entidades.Regalo").uniqueResult();
        ID++;
        
        System.out.println("Introduce el nombre del regalo:");
        denominacion = scannerS.nextLine();
        
        do
        {
            System.out.println("Introduce el ancho");
            ancho = scanner.nextInt();
        }while(ancho <= 0);
        
        do
        {
            System.out.println("Introduce el largo");
            largo = scanner.nextInt();
        }while(largo <= 0);
        
        do
        {
            System.out.println("Introduce el alto");
            alto = scanner.nextInt();
        }while(alto <= 0);
        
        System.out.println("Introduce el tipo (carácter)");
        tipo = scannerS.next().charAt(0);
        
        do
        {
            System.out.println("Introduce la edad mínima");
            edadMinima = scanner.nextInt();
        }while(alto < 0);
        
        do
        {
            System.out.println("Introduce el precio");
            precio = BigDecimal.valueOf(scanner.nextDouble());
        }while(precio.doubleValue() <= 0);
        
        
    }
    
    public static void borrarUnRegalo()
    {
        System.out.println("Crear un regalo");
    }
    
    public static void borrarUnaCriaturitaYSusRegalos()
    {
        System.out.println("Borrar una criaturita y sus regalos");
    }
    
}
