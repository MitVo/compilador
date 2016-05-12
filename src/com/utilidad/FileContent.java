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
public class FileContent {
    
    public static final String LINE_PACKAGE = "package out;";
    public static final String LINE_CLASS = "public class CalcularExpresion ";
    public static final String RESERV_WORD_DOUBLE = "double ";
    public static final String SYSOUT = "System.out.println";
    
    public static final String DELIMITER_CM1 = "{";
    public static final String DELIMITER_CM2 = "}";
    public static final String DELIMITER_CM3 = ";";
    public static final String DELIMITER_CM4 = "(";
    public static final String DELIMITER_CM5 = ")";
    public static final char OUTS = '"';
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    
    public static final String FILE_NAME = "CalcularExpresion"+Utilidades.EXT_JAVA;
    public static final String head_main = "public static void main (String[] args) ";
    
    /**
     * Metodo que organiza el contenido para la generacion del archivo java
     * @param lineExpr
     * @return 
     */
    public static String generarArchivoJava(String lineExpr){
        
        String content = "";
        content = LINE_PACKAGE+"\n"; // declaracion del paquete
        content += LINE_CLASS+DELIMITER_CM1+"\n"; // nombre de la clase
        content += "\n\n";
        content += head_main+DELIMITER_CM1;// main
        content += "\n\n";
        content += "    "+RESERV_WORD_DOUBLE+" result = 0"+DELIMITER_CM3;
        content += "\n\n";
        content += "    "+SYSOUT+DELIMITER_CM4+OUTS+"--REALIZANDO CALCULO DE LA EXPRESION "+OUTS+"+"+OUTS+lineExpr+OUTS
                + DELIMITER_CM5+DELIMITER_CM3;
        content += "\n\n";
        content += "    "+SYSOUT+DELIMITER_CM4+OUTS+"--EL RESULTADO ES :"+OUTS+ DELIMITER_CM5+DELIMITER_CM3;
        content += "\n\n";
        content += "    "+"result = "+lineExpr+DELIMITER_CM3;
        content += "\n\n";
        content += "    "+SYSOUT+DELIMITER_CM4+"result"+ DELIMITER_CM5+DELIMITER_CM3;
        content += "\n\n";
        content += "    "+DELIMITER_CM2; // cierre de main
        content += "\n\n";
        content += DELIMITER_CM2; // cierre de clase
        content += "\n\n";
        
        return content;
    }

}
