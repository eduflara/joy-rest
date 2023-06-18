package com.joyero.app.contrato;

public enum TipoContrato {
    TASACION("tasacion", "Tasacion");

    private String codigo;
    private String descripcion;

    TipoContrato(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}