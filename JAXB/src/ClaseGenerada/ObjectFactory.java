
package ClaseGenerada;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ClaseGenerada package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Ingresos_QNAME = new QName("", "ingresos");
    private final static QName _SexoBuscado_QNAME = new QName("", "sexoBuscado");
    private final static QName _FechaNacimiento_QNAME = new QName("", "fechaNacimiento");
    private final static QName _Descripcion_QNAME = new QName("", "Descripcion");
    private final static QName _ID_QNAME = new QName("", "ID");
    private final static QName _Sexo_QNAME = new QName("", "sexo");
    private final static QName _Nombre_QNAME = new QName("", "nombre");
    private final static QName _Valor_QNAME = new QName("", "Valor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ClaseGenerada
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Corazoncitos }
     * 
     */
    public Corazoncitos createCorazoncitos() {
        return new Corazoncitos();
    }

    /**
     * Create an instance of {@link Persona }
     * 
     */
    public Persona createPersona() {
        return new Persona();
    }

    /**
     * Create an instance of {@link Preferencias }
     * 
     */
    public Preferencias createPreferencias() {
        return new Preferencias();
    }

    /**
     * Create an instance of {@link Gusto }
     * 
     */
    public Gusto createGusto() {
        return new Gusto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ingresos")
    public JAXBElement<BigDecimal> createIngresos(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Ingresos_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sexoBuscado")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createSexoBuscado(String value) {
        return new JAXBElement<String>(_SexoBuscado_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaNacimiento")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createFechaNacimiento(String value) {
        return new JAXBElement<String>(_FechaNacimiento_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Descripcion")
    public JAXBElement<String> createDescripcion(String value) {
        return new JAXBElement<String>(_Descripcion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ID")
    public JAXBElement<BigInteger> createID(BigInteger value) {
        return new JAXBElement<BigInteger>(_ID_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sexo")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createSexo(String value) {
        return new JAXBElement<String>(_Sexo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nombre")
    public JAXBElement<String> createNombre(String value) {
        return new JAXBElement<String>(_Nombre_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Valor")
    public JAXBElement<BigInteger> createValor(BigInteger value) {
        return new JAXBElement<BigInteger>(_Valor_QNAME, BigInteger.class, null, value);
    }

}
