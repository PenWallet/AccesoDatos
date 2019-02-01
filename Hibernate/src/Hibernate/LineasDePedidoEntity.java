package Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "LineasDePedido", schema = "dbo", catalog = "PennyFlama")
@IdClass(LineasDePedidoEntityPK.class)
public class LineasDePedidoEntity {
    private int idPedido;
    private int idProducto;
    private Object precioUnitario;
    private int cantidad;
    private BigDecimal impuestos;
    private Object subtotal;

    @Id
    @Column(name = "Id_Pedido", nullable = false)
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @Id
    @Column(name = "Id_Producto", nullable = false)
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Basic
    @Column(name = "Precio_Unitario", nullable = false)
    public Object getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Object precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Basic
    @Column(name = "Cantidad", nullable = false)
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "Impuestos", nullable = false, precision = 2)
    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    @Basic
    @Column(name = "Subtotal", nullable = false)
    public Object getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Object subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineasDePedidoEntity that = (LineasDePedidoEntity) o;
        return idPedido == that.idPedido &&
                idProducto == that.idProducto &&
                cantidad == that.cantidad &&
                Objects.equals(precioUnitario, that.precioUnitario) &&
                Objects.equals(impuestos, that.impuestos) &&
                Objects.equals(subtotal, that.subtotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProducto, precioUnitario, cantidad, impuestos, subtotal);
    }
}
