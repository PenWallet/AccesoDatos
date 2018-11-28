package Clases;

//Más que nada se usa para guardar qué productos alternativos se van a introducir al final
public class Producto {
    private int posicionAbsolutaProdOrig;
    private int IDAlternativa;
    private int cantidadOrig;
    private int cantidadAlt;

    public Producto(int posicionAbsolutaProdOrig, int IDAlternativa, int cantidadOrig, int cantidadAlt)
    {
        this.posicionAbsolutaProdOrig = posicionAbsolutaProdOrig;
        this.IDAlternativa = IDAlternativa;
        this.cantidadAlt = cantidadAlt;
        this.cantidadOrig = cantidadOrig;
    }

    public int getIDAlternativa() {
        return IDAlternativa;
    }

    public int getCantidadAlt() {
        return cantidadAlt;
    }

    public int getPosicionAbsolutaProdOrig() {
        return posicionAbsolutaProdOrig;
    }

    public int getCantidadOrig() {
        return cantidadOrig;
    }

    public void setIDAlternativa(int IDAlternativa) {
        this.IDAlternativa = IDAlternativa;
    }

    public void setCantidadAlt(int cantidadAlt) {
        this.cantidadAlt = cantidadAlt;
    }

    public void setCantidadOrig(int cantidadOrig) {
        this.cantidadOrig = cantidadOrig;
    }

    public void setPosicionAbsolutaProdOrig(int posicionAbsolutaProdOrig) {
        this.posicionAbsolutaProdOrig = posicionAbsolutaProdOrig;
    }
}
