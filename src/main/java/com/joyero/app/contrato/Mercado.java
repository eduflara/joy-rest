package com.joyero.app.contrato;

public enum Mercado {
    TASACION("mercadosecundario", "Mercado Secundario");

    private String codigo;
    private String descripcion;

    Mercado(String codigo, String descripcion) {
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