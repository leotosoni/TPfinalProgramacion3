package com.company.leotosoni;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Usuario implements Serializable {

    private static final long serialValue = 88392039029384L;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private UUID uuidCodigo;
    private double utnCoins = 100.0;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String password) {
       this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.uuidCodigo = UUID.randomUUID();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        this.email = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUuidCodigo() {
        return uuidCodigo;
    }

    public void setUuidCodigo(UUID uuidCodigo) {
        this.uuidCodigo = uuidCodigo;
    }

    public double getUtnCoins() {
        return utnCoins;
    }

    public void setUtnCoins(double utnCoins) {
        this.utnCoins = utnCoins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Double.compare(usuario.utnCoins, utnCoins) == 0 && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(email, usuario.email) && Objects.equals(password, usuario.password) && Objects.equals(uuidCodigo, usuario.uuidCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, email, password, uuidCodigo, utnCoins);
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", uuidCodigo=" + uuidCodigo +
                ", utnCoins=" + utnCoins +
                '}';
    }
}
