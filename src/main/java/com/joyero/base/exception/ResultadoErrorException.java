package com.joyero.base.exception;

/**
 * Created by alejandro on 13/09/2016.
 */
public class ResultadoErrorException extends ResultadoException {

    private ResultadoException resultadoException;

    public ResultadoErrorException(ResultadoException resultadoException) {
        this.resultadoException = resultadoException;
        errores = resultadoException.getErrores();
        avisos = resultadoException.getAvisos();
    }

    public ResultadoException getResultadoException() {
        return resultadoException;
    }

    public void setResultadoException(ResultadoException resultadoException) {
        this.resultadoException = resultadoException;
    }

}
