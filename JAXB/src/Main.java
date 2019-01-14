import java.io.File;


public class Main {
    public static void main(String[] args) {
        File corazones = new File("assets\\corazonesSolitarios.xml");
        File masCorazones = new File("assets\\masCorazones.xml");
        File destino = new File("assets\\todosLosCorazones.xml");

        Gestora gestora = new Gestora();
        gestora.abrirListados(corazones, masCorazones);
        gestora.juntarListados();
    }
}
