package com.company.leotosoni;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private String nombre;
    private String apellido;
    private String userName;
    private String password;
    private UUID uuidCodigo;
    private double utnCoins = 100.0;

    public Usuario(String nombre, String apellido, String userName, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


    public Transaccion nuevaTransferencia(Usuario destino, double monto) {
        this.setUtnCoins(this.getUtnCoins()-monto);
        return new Transaccion(this, destino, monto);

    }

    public void validarTransaccion(Transaccion transaccion){
        if(transaccion.getContador()<3)
        {
            transaccion.agregarUsuario(this);
            if(transaccion.getContador()>=3)
            {
                transaccion.setEstado(Estado.APROBADA);
                transaccion.setFechaFin(LocalDateTime.now());
            }
        }
    }

    public String mostrarTransaccionesPendientes(List<Transaccion> listaPend){
        StringBuilder sb = new StringBuilder();
        for(Transaccion transaccion : listaPend){
            sb.append(transaccion.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", uuidCodigo=" + uuidCodigo +
                ", utnCoins=" + utnCoins +
                '}';
    }
}
