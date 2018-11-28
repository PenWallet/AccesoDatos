import Gestoras.GestoraFormularios;
import Gestoras.GestoraPedidos;

import java.sql.SQLException;
import java.util.Scanner;

public class Empresa {
    public static void main(String[] args)
    {
        try {
            GestoraFormularios.mainFormulario();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
