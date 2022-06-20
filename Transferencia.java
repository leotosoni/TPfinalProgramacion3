package com.company.leotosoni;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transferencia implements Serializable {
    private UUID origen;
    private UUID destino;
    private UUID uniqueID;
    private double monto;
    private Estado estado;
    private UUID[] validaciones;
    private int contador = 0;

    public Transferencia(UUID origen, UUID destino, double monto) {
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.uniqueID = UUID.randomUUID();
        this.estado = Estado.PENDIENTE;
        this.validaciones = new UUID[4];
    }

    public void agregarUsuario(UUID uuid){
        if(contador<4){
            this.validaciones[contador] = uuid;
            contador++;
        }
    }

    public UUID getOrigen() {
        return origen;
    }

    public void setOrigen(UUID origen) {
        this.origen = origen;
    }

    public UUID getDestino() {
        return destino;
    }

    public void setDestino(UUID destino) {
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

    public int getContador() {
        return contador;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public UUID[] getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(UUID[] validaciones) {
        this.validaciones = validaciones;
    }

}


