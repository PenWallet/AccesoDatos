package gestoras;

import entidades.*;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        System.out.println("Recuperar criaturita con regalos");
    }
    
    public static void quitarRegaloDeCriaturita()
    {
        System.out.println("Quitar regalo de criaturita");
    }
    
    public static void asignarRegaloACriaturita()
    {
        System.out.println("Asignar regalo a criaturita");
    }
    
    public static void crearUnaNuevaCriaturita()
    {
        System.out.println("Crear una nueva criaturita");
    }
    
    public static void crearUnNuevoRegalo()
    {
        System.out.println("Crear un nuevo regalo");
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
