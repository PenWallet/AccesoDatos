package Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Categorias", schema = "dbo", catalog = "PennyFlama")
public class CategoriasEntity {
    private String nombre;

    @Id
    @Column(name = "Nombre", nullable = false, length = 30)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriasEntity that = (CategoriasEntity) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
