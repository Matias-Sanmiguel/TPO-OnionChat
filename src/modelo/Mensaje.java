package modelo;

import interfaces.IMensaje;
import java.util.List;

public class Mensaje implements IMensaje {
    private String contenido;
    private byte[] contenidoCifrado;
    private byte[] origenOfuscado;
    private List<byte[]> capasCifradas;

    public Mensaje(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String getContenido() {
        return contenido;
    }

    @Override
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public byte[] getContenidoCifrado() {
        return contenidoCifrado;
    }

    @Override
    public void setContenidoCifrado(byte[] contenidoCifrado) {
        this.contenidoCifrado = contenidoCifrado;
    }

    @Override
    public byte[] getOrigenOfuscado() {
        return origenOfuscado;
    }

    @Override
    public void setOrigenOfuscado(byte[] origenOfuscado) {
        this.origenOfuscado = origenOfuscado;
    }

    @Override
    public List<byte[]> getCapasCifradas() {
        return capasCifradas;
    }

    @Override
    public void setCapasCifradas(List<byte[]> capasCifradas) {
        this.capasCifradas = capasCifradas;
    }
}
