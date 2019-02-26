package gestoras;

import entidades.*;
import hibernateutil.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
           
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
    }
    
    public static void listarTodosLosRegalos()
    {
        String hqlQuery = "FROM entidades.Regalo";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Regalo> listado = new ArrayList<>(query.list());
        for(Regalo regalo : listado)
            System.out.println(regalo.getId()+". "+regalo.getDenominacion()+". Medidas: "+regalo.getAncho()+"x"+regalo.getLargo()+"x"+regalo.getAlto()+". Tipo: "+regalo.getTipo()+". Edad mínima: "+regalo.getEdadMinima()+". Precio: "+regalo.getPrecio()+"€");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
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
        
        ID = (int)session.createQuery("SELECT MAX(id) FROM entidades.Regalo").uniqueResult();
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
        
        do
        {
            System.out.println("Introduce el tipo (carácter)");
            tipo = Character.toUpperCase(scannerS.next().charAt(0));
        }while(tipo < 'A' || tipo > 'Z');
        
        
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
        
        Regalo regalo = new Regalo(ID, denominacion, ancho, largo, alto, tipo, edadMinima, precio);
        
        session.beginTransaction();
        session.save(regalo);
        session.getTransaction().commit();
        System.out.println("Regalo agregado");
    }
    
    public static void borrarUnRegalo()
    {
        int indexRegalo;
        Scanner scanner = new Scanner(System.in);
        
        String hqlQuery = "FROM entidades.RegaloParaCriaturitaConRegalos";
        Query query = session.createQuery(hqlQuery);
        ArrayList<RegaloParaCriaturitaConRegalos> listado = new ArrayList<>(query.list());
        for(int i = 0; i < listado.size(); i++)
            System.out.println((i+1)+". "+listado.get(i).getDenominacion()+". Medidas: "+listado.get(i).getAncho()+"x"+listado.get(i).getLargo()+"x"+listado.get(i).getAlto()+". Tipo: "+listado.get(i).getTipo()+". Edad mínima: "+listado.get(i).getEdadMinima()+". Precio: "+listado.get(i).getPrecio()+"€. ¿Propietario?: "+(listado.get(i).getPropietario() == null ? "No" : listado.get(i).getPropietario().getNombre()));
        
        do
        {
            System.out.println("Elige el regalo a borrar (o 0 para salir):");
            indexRegalo = scanner.nextInt();
        }while(indexRegalo < 0 || indexRegalo > listado.size());
        
        if(indexRegalo != 0)
        {
            session.beginTransaction();
            RegaloParaCriaturitaConRegalos regalo = listado.get(indexRegalo - 1);
            
            if(regalo.getPropietario() != null)
            {
                if(regalo.getPropietario().getRegalitos().size() == 1)
                    regalo.getPropietario().setRegalitos(null);
                else
                    regalo.getPropietario().getRegalitos().remove(regalo);
                
                regalo.setPropietario(null);
            }
                
            
            session.delete(regalo);
            session.getTransaction().commit();
            System.out.println("Regalo borrado :)");
        }
        
        
    }
    
    public static void borrarUnaCriaturitaYSusRegalos()
    {
        Scanner scanner = new Scanner(System.in);
        int indexCriaturita;
        
        String hqlQuery = "FROM entidades.CriaturitaConRegalos";
        Query query = session.createQuery(hqlQuery);
        ArrayList<CriaturitaConRegalos> listado = new ArrayList<>(query.list());
        
        if(!listado.isEmpty())
        {
            for(int i = 0; i < listado.size(); i++)
                System.out.println((i+1)+". "+listado.get(i).getNombre()+". ¿Tiene regalos?: "+(listado.get(i).getRegalitos().isEmpty() ? "No" : "Sí"));
            
            do
            {
                System.out.println("Elige la criaturita a borrar:");
                indexCriaturita = scanner.nextInt();
            }while(indexCriaturita < 0 || indexCriaturita > listado.size());
            
            if(indexCriaturita != 0)
            {
                session.beginTransaction();
                
                CriaturitaConRegalos criaturita = listado.get(indexCriaturita - 1);
                
                ArrayList<RegaloParaCriaturitaConRegalos> listadoRegalos = new ArrayList<>(criaturita.getRegalitos());
                for(RegaloParaCriaturitaConRegalos regalo : listadoRegalos)
                    regalo.setPropietario(null);
                
                criaturita.setRegalitos(null);
                
                session.delete(criaturita);
                
                session.getTransaction().commit();
                
                System.out.println("Criaturita borrada :)");
            }
        }
        else
            System.out.println("No hay criaturitas en la base de datos :(");
        
    }
    
    public static void listarTodosLosCuentos()
    {
        String hqlQuery = "FROM entidades.Cuento";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Cuento> listado = new ArrayList<>(query.list());
        for(Cuento cuento : listado)
            System.out.println(cuento.getId()+". "+cuento.getTitulo()+", de "+cuento.getAutor()+". Tema: "+cuento.getTema());
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
    }
    
    public static void recuperarCriaturitaConTodosSusCuentos()
    {
        int idCriaturita;
        Scanner scanner = new Scanner(System.in);
        String hqlQuery = "FROM entidades.CriaturitaConRegalos";
        Query query = session.createQuery(hqlQuery);
        ArrayList<CriaturitaConRegalos> listado = new ArrayList<>(query.list());
        
        for(int i = 0; i < listado.size(); i++)
            System.out.println((i+1)+". "+listado.get(i).getNombre());
        
        if(!listado.isEmpty())
        {
            do
            {
                System.out.println("Escribe el número de la persona (o 0 para salir):");
                idCriaturita = scanner.nextInt();
            }while(idCriaturita < 0 || idCriaturita > listado.size());
            
            if(idCriaturita != 0)
            {
                ArrayList<Cuento> listadoCuentos = new ArrayList<>(listado.get(idCriaturita - 1).getListaCuentos());
                if(!listadoCuentos.isEmpty())
                {
                    for(Cuento cuento : listadoCuentos)
                        System.out.println(cuento.getId()+". "+cuento.getTitulo()+", de "+cuento.getAutor()+". Tema: "+cuento.getTema());
                }
                else
                    System.out.println("Esta criaturita no lee nada, es un incultivao de esos");
            }
        }
        else
            System.out.println("No hay personas en la base de datos");
        
        
        
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
    }
    
    public static void recuperarCuentoConLectoras()
    {
        int idCuento;
        Scanner scanner = new Scanner(System.in);
        String hqlQuery = "FROM entidades.Cuento";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Cuento> listado = new ArrayList<>(query.list());
        for(int i = 0; i < listado.size(); i++)
            System.out.println((i+1)+". "+listado.get(i).getTitulo()+", de "+listado.get(i).getAutor()+". Tema: "+listado.get(i).getTema());
        
        do
        {
            System.out.println("Elige el cuento que quieres ver (o 0 para salir):");
            idCuento = scanner.nextInt();
        }while(idCuento < 0 || idCuento > listado.size());
        
        if(idCuento != 0)
        {
            Cuento cuento = listado.get(idCuento - 1);
            ArrayList<CriaturitaConRegalos> listadoCriaturitas = new ArrayList(cuento.getListaLectores());
            
            if(!listadoCriaturitas.isEmpty())
            {
                System.out.println("Estos son los lectores de este libro:");
                for(CriaturitaConRegalos criaturita : listadoCriaturitas)
                    System.out.println(criaturita.getNombre());
            }
            else
                System.out.println("Este cuento no tiene lectores");
        }
    }
    
    public static void quitarCuentoACriaturita()
    {
        int idCriaturita, idCuento;
        Scanner scanner = new Scanner(System.in);
        String hqlQuery = "FROM entidades.CriaturitaConRegalos";
        Query query = session.createQuery(hqlQuery);
        ArrayList<CriaturitaConRegalos> listado = new ArrayList<>(query.list());
        
        for(int i = 0; i < listado.size(); i++)
            System.out.println((i+1)+". "+listado.get(i).getNombre());
        
        if(!listado.isEmpty())
        {
            do
            {
                System.out.println("Escribe el número de la persona (o 0 para salir):");
                idCriaturita = scanner.nextInt();
            }while(idCriaturita < 0 || idCriaturita > listado.size());
            
            if(idCriaturita != 0)
            {
                ArrayList<Cuento> listadoCuentos = new ArrayList<>(listado.get(idCriaturita - 1).getListaCuentos());
                if(!listadoCuentos.isEmpty())
                {
                    for(int i = 0; i < listadoCuentos.size(); i++)
                        System.out.println((i+1)+". "+listadoCuentos.get(i).getTitulo()+", de "+listadoCuentos.get(i).getAutor()+". Tema: "+listadoCuentos.get(i).getTema());
                    
                    do
                    {
                        System.out.println("Elige el cuento que quieres desasociar (o 0 para salir):");
                        idCuento = scanner.nextInt();
                    }while(idCuento < 0 || idCuento > listadoCuentos.size());
                    
                    if(idCuento != 0)
                    {
                        session.beginTransaction();
                        
                        listado.get(idCriaturita - 1).getListaCuentos().remove(listadoCuentos.get(idCuento - 1));
                        listadoCuentos.get(idCuento - 1).getListaLectores().remove(listado.get(idCriaturita - 1));
                        
                        session.getTransaction().commit();
                        
                        System.out.println("Cuento borrado de su cuenta");
                    }
                }
                else
                    System.out.println("Esta criaturita no lee nada, es un incultivao de esos");
            }
        }
        else
            System.out.println("No hay personas en la base de datos");
        
        
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
    }
    
    public static void asignarCuentoACriaturita()
    {
        //TODO
        System.out.println("Asignar cuento");
    }
    
    public static void crearUnNuevoCuento()
    {
        Scanner scanner = new Scanner(System.in);
        String nombre, autor, tema;
        
        int ID = (int)session.createQuery("SELECT MAX(id) FROM entidades.Cuento").uniqueResult();
        ID++;
        
        System.out.println("Escribe el nombre del cuento: ");
        nombre = scanner.nextLine();
        
        System.out.println("Escribe el nombre del autor: ");
        autor = scanner.nextLine();
        
        System.out.println("Escribe el tema: ");
        tema = scanner.nextLine();
        
        Cuento cuento = new Cuento(ID, nombre, autor, tema);
        
        session.beginTransaction();
        session.save(cuento);
        session.getTransaction().commit();
        
        System.out.println("Cuento guardado con éxito 😄");
    }
    
    public static void borrarUnCuento()
    {
        int idCuento;
        Scanner scanner = new Scanner(System.in);
                
        String hqlQuery = "FROM entidades.Cuento";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Cuento> listado = new ArrayList<>(query.list());
        for(int i = 0; i < listado.size(); i++)
            System.out.println((i+1)+". "+listado.get(i).getTitulo()+", de "+listado.get(i).getAutor()+". Tema: "+listado.get(i).getTema());
        
        do
        {
            System.out.println("Elige el cuento a borrar (o 0 para salir):");
            idCuento = scanner.nextInt();
        }while(idCuento < 0 || idCuento > listado.size());
        
        if(idCuento != 0)
        {
            session.beginTransaction();
            Cuento cuento = listado.get(idCuento - 1);
            cuento.setListaLectores(null);
            session.delete(cuento);
            session.getTransaction().commit();
            
            System.out.println("Cuento borrado con éxito");
        }
        
    }
    
}
