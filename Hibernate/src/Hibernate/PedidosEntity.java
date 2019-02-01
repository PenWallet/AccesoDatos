package Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Pedidos", schema = "dbo", catalog = "PennyFlama")
public class PedidosEntity {
    private int id;
    private Date fechaPedido;
    private Date fechaEntrega;
    private Object totalPedido;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Fecha_Pedido", nullable = false)
    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    @Basic
    @Column(name = "Fecha_Entrega", nullable = true)
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Basic
    @Column(name = "Total_Pedido", nullable = false)
    public Object getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Object totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidosEntity that = (PedidosEntity) o;
        return id == that.id &&
                Objects.equals(fechaPedido, that.fechaPedido) &&
                Objects.equals(fechaEntrega, that.fechaEntrega) &&
                Objects.equals(totalPedido, that.totalPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaPedido, fechaEntrega, totalPedido);
    }
}
