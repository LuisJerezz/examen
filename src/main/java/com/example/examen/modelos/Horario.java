package com.example.examen.modelos;
import java.util.Objects;

public class Horario {
    private int id;
    private int instalacion;
    private String inicio;
    private String fin;


    public Horario() {
    }

    public Horario(int id, int instalacion, String inicio, String fin) {
        this.id = id;
        this.instalacion = instalacion;
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstalacion() {
        return this.instalacion;
    }

    public void setInstalacion(int instalacion) {
        this.instalacion = instalacion;
    }

    public String getInicio() {
        return this.inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return this.fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public Horario id(int id) {
        setId(id);
        return this;
    }

    public Horario instalacion(int instalacion) {
        setInstalacion(instalacion);
        return this;
    }

    public Horario inicio(String inicio) {
        setInicio(inicio);
        return this;
    }

    public Horario fin(String fin) {
        setFin(fin);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Horario)) {
            return false;
        }
        Horario horario = (Horario) o;
        return id == horario.id && instalacion == horario.instalacion && Objects.equals(inicio, horario.inicio) && Objects.equals(fin, horario.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instalacion, inicio, fin);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", instalacion='" + getInstalacion() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
    
}
