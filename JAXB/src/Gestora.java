import ClaseGenerada.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gestora {
    private Corazoncitos listadoCorazones1;
    private Corazoncitos listadoCorazones2;
    private Corazoncitos listadoFinal;

    public Gestora()
    {
        listadoCorazones1 = null;
        listadoCorazones2 = null;
        listadoFinal = null;
    }

    public void abrirListados(File archivoXML1, File archivoXML2)
    {
        JAXBContext contexto;
        try {
            contexto = JAXBContext.newInstance(Corazoncitos.class);
            Unmarshaller u = contexto.createUnmarshaller();
            listadoCorazones1 = (Corazoncitos)u.unmarshal(archivoXML1);
            listadoCorazones2 = (Corazoncitos)u.unmarshal(archivoXML2);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void juntarListados()
    {
        List<Persona> listadoTotal = listadoCorazones1.getPersona();
        listadoTotal.addAll(listadoCorazones2.getPersona());

        ArrayList<Persona> arrayListPersona = new ArrayList<>(listadoTotal);

        for(int i = 0; i < arrayListPersona.size(); i++)
        {
            System.out.println(arrayListPersona.get(i));
        }

        Collections.sort(listadoTotal);

        for(int i = 0; i < arrayListPersona.size(); i++)
        {
            System.out.println(arrayListPersona.get(i));
        }


    }
}
