package com.company.leotosoni;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaccion {
    private Usuario origen;
    private Usuario destino;
    private UUID uniqueID;
    private double monto;
    private Estado estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Usuario[] validaciones;

    private int contador = 0;

    public Transaccion(Usuario origen, Usuario destino, double monto) {
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.uniqueID = UUID.randomUUID();
        this.estado = Estado.PENDIENTE;
        this.fechaInicio = LocalDateTime.now();
        this.validaciones = new Usuario[5];

    }

    public void agregarUsuario(Usuario usuario){
        if(contador<5){
            this.validaciones[contador] = usuario;
            contador++;
        }
    }

    public Usuario getOrigen() {
        return origen;
    }

    public void setOrigen(Usuario origen) {
        this.origen = origen;
    }

    public Usuario getDestino() {
        return destino;
    }

    public void setDestino(Usuario destino) {
        this.destino = destino;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UUID uniqueID) {
        this.uniqueID = uniqueID;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getContador() {
        return contador;
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ORIGEN: ");
        sb.append(this.origen.getNombre()).append(", DESTINO: ")
                .append(this.destino.getNombre()).append(", ESTADO: ")
                .append(this.estado).append(", MONTO: ")
                .append(this.monto).append(", NRO. VALIDACIONES: ")
                .append(this.getContador()).append("\n");

        return sb.toString();
    }
}


