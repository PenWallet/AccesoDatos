/*

 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author leo
 */
@Entity
@Table(name="Regalos"
)
public class Regalo implements Serializable {
    @Id 

    @Column(name="Id", nullable=false)	    
    private int id;
    
    @Column(name="Denominacion")
    private String denominacion;
    
    @Column(name="Ancho")
    private int ancho;
    
    @Column(name="Largo")
    private int largo;
    
    @Column(name="Alto")
    private int alto;
    
    @Column(name="Tipo")
    private char tipo;
    
    @Column(name="EdadMinima")
    private int edadMinima;
    
    @Column(name="Precio")
    private BigDecimal precio;

    public Regalo() {
    }
    
    public Regalo(int id, String denominacion, int ancho, int largo, int alto, char tipo, int edadMinima, BigDecimal precio)
    {
        this.id = id;
        this.denominacion = denominacion;
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.tipo = tipo;
        this.edadMinima = edadMinima;
        this.precio = precio;
    }
    
    public int getId() {
        return id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public int getAncho() {
        return ancho;
    }

    public int getLargo() {
        return largo;
    }

    public int getAlto() {
        return alto;
    }

    public char getTipo() {
        return tipo;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
    
// Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString(){
        return "Regalo: Denominacion: "+denominacion+"\nAncho: "+ancho+"\nLargo: "+alto+"\nTipo: "+tipo+"\nEdad mínima: "+edadMinima+"\nPrecio: "+precio;
    }
}
