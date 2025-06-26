package modelo;

public class Arista {
    public String destino;
    public int peso; // el peso es la latencia que vamos a usar simulada


    public Arista(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
}
