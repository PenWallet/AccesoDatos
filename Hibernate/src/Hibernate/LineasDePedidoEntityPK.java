package Hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LineasDePedidoEntityPK implements Serializable {
    private int idPedido;
    private int idProducto;

    @Column(name = "Id_Pedido", nullable = false)
    @Id
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @Column(name = "Id_Producto", nullable = false)
    @Id
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineasDePedidoEntityPK that = (LineasDePedidoEntityPK) o;
        return idPedido == that.idPedido &&
                idProducto == that.idProducto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProducto);
    }
}
