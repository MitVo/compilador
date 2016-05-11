/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilidad;

import java.util.logging.Logger;

/**
 *
 * @author mitzyvo
 * @date 11-may-2016
 */
public class LoggerUtil {

    private static Logger logger;

    public static LoggerUtil getInstance() {
        return new LoggerUtil();
    }

    /**
     * log informativos
     * @param message
     * @param clase 
     */
    public void showLogMessage(String message, String clase) {
        logger = Logger.getLogger(clase);
        logger.info(message);
    }
    
    /**
     * log de errores
     * @param message
     * @param clase 
     */
    public void showErrorMessage(String message, String clase) {
        logger = Logger.getLogger(clase);
        logger.severe(message);
    }
}
