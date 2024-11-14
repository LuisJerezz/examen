package com.example.examen.modelos;
import java.util.Objects;

public class Reserva {
    private int id;
    private int usuario;
    private int horario;
    private String fecha;


    public Reserva() {
    }

    public Reserva(int id, int usuario, int horario, String fecha) {
        this.id = id;
        this.usuario = usuario;
        this.horario = horario;
        this.fecha = fecha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getHorario() {
        return this.horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Reserva id(int id) {
        setId(id);
        return this;
    }

    public Reserva usuario(int usuario) {
        setUsuario(usuario);
        return this;
    }

    public Reserva horario(int horario) {
        setHorario(horario);
        return this;
    }

    public Reserva fecha(String fecha) {
        setFecha(fecha);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Reserva)) {
            return false;
        }
        Reserva reserva = (Reserva) o;
        return id == reserva.id && usuario == reserva.usuario && horario == reserva.horario && Objects.equals(fecha, reserva.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, horario, fecha);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", horario='" + getHorario() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
    
}
