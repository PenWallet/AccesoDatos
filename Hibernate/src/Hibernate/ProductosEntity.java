package Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Productos", schema = "dbo", catalog = "PennyFlama")
public class ProductosEntity {
    private int id;
    private String nombre;
    private Object precioVenta;
    private String descripcion;
    private int stock;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Nombre", nullable = false, length = 60)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "Precio_venta", nullable = false)
    public Object getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Object precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Basic
    @Column(name = "Descripcion", nullable = true, length = 200)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "Stock", nullable = false)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductosEntity that = (ProductosEntity) o;
        return id == that.id &&
                stock == that.stock &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(precioVenta, that.precioVenta) &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precioVenta, descripcion, stock);
    }
}
