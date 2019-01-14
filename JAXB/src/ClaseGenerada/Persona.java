
package ClaseGenerada;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ID"/>
 *         &lt;element ref="{}nombre"/>
 *         &lt;element ref="{}sexo"/>
 *         &lt;element ref="{}sexoBuscado"/>
 *         &lt;element ref="{}fechaNacimiento"/>
 *         &lt;element ref="{}ingresos"/>
 *         &lt;element ref="{}Preferencias"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "nombre",
    "sexo",
    "sexoBuscado",
    "fechaNacimiento",
    "ingresos",
    "preferencias"
})
@XmlRootElement(name = "Persona")
public class Persona {

    @XmlElement(name = "ID", required = true)
    protected BigInteger id;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String sexo;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String sexoBuscado;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String fechaNacimiento;
    @XmlElement(required = true)
    protected BigDecimal ingresos;
    @XmlElement(name = "Preferencias", required = true)
    protected Preferencias preferencias;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getID() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setID(BigInteger value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad sexo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Define el valor de la propiedad sexo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Obtiene el valor de la propiedad sexoBuscado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexoBuscado() {
        return sexoBuscado;
    }

    /**
     * Define el valor de la propiedad sexoBuscado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexoBuscado(String value) {
        this.sexoBuscado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimiento(String value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad ingresos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIngresos() {
        return ingresos;
    }

    /**
     * Define el valor de la propiedad ingresos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIngresos(BigDecimal value) {
        this.ingresos = value;
    }

    /**
     * Obtiene el valor de la propiedad preferencias.
     * 
     * @return
     *     possible object is
     *     {@link Preferencias }
     *     
     */
    public Preferencias getPreferencias() {
        return preferencias;
    }

    /**
     * Define el valor de la propiedad preferencias.
     * 
     * @param value
     *     allowed object is
     *     {@link Preferencias }
     *     
     */
    public void setPreferencias(Preferencias value) {
        this.preferencias = value;
    }

}
