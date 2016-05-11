/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilidad;

/**
 *
 * @author mitzyvo
 * @date 11-may-2016
 */
public class MalFormatExp extends Exception {

    //private static final long serialVersionUID = 1L;
    private String message = "";

    public MalFormatExp() {
        super();
    }

    public MalFormatExp(String message) {
        super(message);
        this.message = message;
    }

    public MalFormatExp(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public MalFormatExp(Throwable cause) {
        super(cause);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
