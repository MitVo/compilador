/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.utilidad.FileContent;
import com.utilidad.LoggerUtil;
import com.utilidad.Utilidades;
import com.utilidad.MalFormatExp;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("*Escribe una notación polaca inversa (RPN): \n");
        System.out.println(":::.Recuerde separar cada caracter con un espacio.::");
        Scanner leer = new Scanner(System.in);
        String expr = leer.nextLine();
        Utilidades ut = new Utilidades();
        String infix = "";
        try {
            // evaluando expresion ingresada 
            LoggerUtil.getInstance().showLogMessage("-- realizando verificación de cadena ingresada ... ", Main.class.getName());
            ut.evaluarLexico(expr);
            
            //Pasar valores Hexadecimales a Decimales
            expr = ut.valoresHexadecimales(expr);
            
            // evaluacion sintactica
            LoggerUtil.getInstance().showLogMessage("-- realizando verificación sintactica ... ", Main.class.getName());
            ut.evaluarSintactico(expr);
            // organizacion de expresion a infija
            LoggerUtil.getInstance().showLogMessage("-- organizando expresión de postfija a infija ... ", Main.class.getName());
            infix = Utilidades.getInstance().organizarInfija(expr);    
            LoggerUtil.getInstance().showLogMessage("  "+infix, Main.class.getName());
            // todo salio bien .. a generar el archivo            
            if(ut.createFile(Utilidades.dirOut+FileContent.FILE_NAME, FileContent.generarArchivoJava(infix).getBytes())){
               System.out.println("*La generación del archivo ha sido exitoso, puede verificar en carpeta out."); 
            }else{
               System.out.println("*Algo malo ha ocurrido... Comunicarse con el admin...");
            }           
            
        } catch (MalFormatExp ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
