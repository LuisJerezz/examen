package com.example.examen.modelos;
import java.util.Objects;

public class Instalacion {
    private int id;
    private String nombre;


    public Instalacion() {
    }

    public Instalacion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Instalacion id(int id) {
        setId(id);
        return this;
    }

    public Instalacion nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Instalacion)) {
            return false;
        }
        Instalacion instalacion = (Instalacion) o;
        return id == instalacion.id && Objects.equals(nombre, instalacion.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
    
}
