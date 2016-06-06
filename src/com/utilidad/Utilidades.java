/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilidad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author mitzyvo
 * @date 11-may-2016
 */
public class Utilidades {

    private Stack< String> p = new Stack< String>();
    private Stack< String> n = new Stack< String>();
    private String str = null;

    public static final String isNumber = "N";
    public static final String isOper = "O";
    public static final String dirOut = "../out/";
    public static final String EXT_JAVA = ".java";
    /**
     * Expresion regular para caracteres con letras en mayusculas
     */
    public static final String patternUpper = "[\\p{Alnum}\\p{Punct}]*[A-Z]+[\\p{Alnum}\\p{Punct}]*";
    /**
     * Expresion regular para caracteres con letras no hexadecimales
     */
    public static final String patternNotHex = "[\\p{Alnum}\\p{Punct}]*[G-Zg-z]+[\\p{Alnum}\\p{Punct}]*";
    /**
     * Expresion regular para caracteres con falta de identificadores para hexadecimales
     */
    public static final String patternBadHex = "-*0*[x|X]*[0-9a-f]+[0-9a-f|a-f0-9]*";
    /**
     * Expresion regular para numeros hexadecimales negativos y positivos
     */
    public static final String patternHex = "-*0[x|X][0-9a-f]+[0-9a-f|a-f0-9]*";
    /**
     * Expresion regular para numeros decimales
     * "-*[0-9]+[,0-9]*";
     */
    public static final String patternNumberDec = "^-?[0-9]+([,\\.][0-9]*)?$";
    /**
     * Expresion regular para numeros octales
     */
    public static final String patternNumberOct = "-*[0][1-7]+";
    /**
     * Expresion regular para numeros enteros
     */
    public static final String patternNumberInt = "-*[0-9]+";
    /**
     * Array de Operadores aceptados
     */
    public static final String patternOpers = "[+|/|*|-]";
    /**
     * No es una palabra
     */
    public static final String patternWord = "[a-zA-Z]+";
    /**
     * Es algun simbolo no reconocido
     */
    public static final String patterSomeSymbol = "[\\p{Alnum}\\p{Punct}]*[\\p{Punct}]+[\\p{Alnum}\\p{Punct}]*";

    public static Utilidades getInstance() {
        return new Utilidades();
    }

    /**
     * metodo para la evaluacion del lexico
     *
     * @param value
     * @throws com.utilidad.MalFormatExp
     */
    public void evaluarLexico(String value) throws MalFormatExp {

        StringTokenizer token = new StringTokenizer(value);
        int pos = 0;
        while (token.hasMoreTokens()) {
            str = token.nextToken();
            pos += 1;
            if (Pattern.matches(patternNumberDec, str)) {
                LoggerUtil.getInstance().showLogMessage("-- número encontrado", Utilidades.class.getName());
            } else if (Pattern.matches(patternNumberInt, str)) {
                LoggerUtil.getInstance().showLogMessage("-- número encontrado", Utilidades.class.getName());
            } else if (Pattern.matches(patternNumberOct, str)){
                LoggerUtil.getInstance().showLogMessage("-- número encontrado", Utilidades.class.getName());
            } else if (Pattern.matches(patternHex, str)) {
                LoggerUtil.getInstance().showLogMessage("-- hexadecimal encontrado", Utilidades.class.getName());
            } else if (Pattern.matches(patternOpers, str)) {
                LoggerUtil.getInstance().showLogMessage("-- operador encontrado", Utilidades.class.getName());
            } else if (Pattern.matches(patternUpper, str)) { // desde aqui algo va mal
                throw new MalFormatExp("ERROR LEXICO: El objeto de posición "+pos+" tiene caracteres en mayuscula.");
            } else if (Pattern.matches(patternWord, str)) {
                throw new MalFormatExp("ERROR LEXICO: El objeto de posición "+pos+" es una palabra.");
            } else if (Pattern.matches(patternNotHex, str)) {
                throw new MalFormatExp("ERROR LEXICO: El objeto de posición "+pos+" tiene caracteres que no son hexadecimales.");
            } else if (Pattern.matches(patternBadHex, str)) {
                throw new MalFormatExp("ERROR LEXICO: El objeto de posición "+pos+" no tiene suficientes caracteres para ser hexadecimal.");
            } else if (Pattern.matches(patterSomeSymbol, str)) {
                throw new MalFormatExp("ERROR LEXICO: Contiene simbolo(s) no reconocido(s).");
            }
        }

    }

    /**
     * Obtener numero decimal
     *
     * @param r
     * @return
     */
    private static double getDouble(String r) throws MalFormatExp {
        double d = 0;
        try {
            d = Double.valueOf(r);
        } catch (Exception e) {
            throw new MalFormatExp("Error no reconocido en conversión a decimal : \n", e);
        }
        return d;
    }

    /**
     * Evalua si el numero es decimal o entero en caso de ser decimal devuelve
     * TRUE
     *
     * @param d
     * @return
     */
    private static boolean typeNmber(double d) {
        return (d - (int) d) != 0;
    }

    /**
     *
     * @param value
     * @return
     */
    private double getTypeNumber(String value) {
        double d = 0;
        if (Pattern.matches(patternNumberInt, value)) {
            try {
                d = getInteger(value);
            } catch (MalFormatExp ex) {
                Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                d = getDouble(value);
            } catch (MalFormatExp ex) {
                Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return d;
    }

    /**
     * Metodo para obtener entero
     *
     * @param r
     * @return
     */
    private static int getInteger(String r) throws MalFormatExp {
        int d = 0;
        try {
            d = Integer.decode(r);
        } catch (Exception e) {
            throw new MalFormatExp("Error no reconocido en conversión a entero : \n", e);
        }
        return d;
    }

    /**
     * creacion de archivos
     *
     * @param tmpValue
     * @param content
     * @return
     */
    public boolean createFile(String tmpValue, byte[] content) {
        boolean r = false;
        File file = new File(tmpValue);
        try {
            r = writeByteArraysToFile(file.getAbsolutePath(), content);
        } catch (IOException e) {
            LoggerUtil.getInstance().showErrorMessage(e.getMessage(), Utilidades.class.getName());
        }
        return r;
    }

    /**
     * metodo para escribir el archivo
     *
     * @param fileName
     * @param content
     * @return
     * @throws IOException
     */
    public static boolean writeByteArraysToFile(String fileName, byte[] content)
            throws IOException {

        File file = new File(fileName);
        File theDir = new File(file.getParent());
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        BufferedOutputStream writer = new BufferedOutputStream(
                new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

        return file.exists();

    }

    /**
     * metodo para ayudar a la organizacion de las pilas de operandos y
     * operadores
     *
     * @param value
     * @return
     */
    public static int getTypeToken(String value) {
        int get = 0;
        if (Pattern.matches(patternNumberDec, value) || Pattern.matches(patternNumberInt, value) 
                || Pattern.matches(patternHex, value) || Pattern.matches(patternNumberOct, value)) {
            get = 1;
        } else {
            get = 2;
        }
        return get;
    }

    /**
     * metodo para organizar los elementos de postfijo a infijo
     *
     * @param post notacion infija
     * @param p pila que contiene los registros operandos
     * @param n pila que contiene los registros operadores
     * @return cadena organizada de los elementos en notacion infija
     */
    public String organizarInfija(String post) throws MalFormatExp {

        String infix = "", infix_tmp = "";
        double dtmp1 = 0, dtmp2 = 0;
        str = "";
        int size = 0;
        String tmp1 = "", tmp2 = "";
        Stack< String> pt =  new Stack< String>(); 
        Stack< String> nt =  new Stack< String>();

        StringTokenizer token = new StringTokenizer(post);

        while (token.hasMoreTokens()) {
            str = token.nextToken();
            switch (getTypeToken(str)) {                
                case 1:
                    nt.push(str);// cola para los numeros
                    break;
                case 2:
                    pt.push(str);// cola para los operadores                   
                    if (nt.size()>= 2 && infix.isEmpty()) {// esta inicializando la organizacion
                        size = nt.size();
                        dtmp1 = getTypeNumber(nt.get(size - 1));
                        dtmp2 = getTypeNumber(nt.get(size - 2));
                        if (dtmp1 < 0) {
                            tmp1 = "(" + dtmp1 + ")";
                        } else {
                            tmp1 = String.valueOf(dtmp1);
                        }

                        if (dtmp2 < 0) {
                            tmp2 = "(" + dtmp2 + ")";
                        } else {
                            tmp2 = String.valueOf(dtmp2);
                        }

                        infix = tmp2 + pt.lastElement() + tmp1;
                        nt.remove(size - 1);// remover elemento extraido 1
                        nt.remove(size - 2);// remover elemento extraido 2
                        pt.remove(pt.size() - 1);// remover operador utilizado
                    } else if (nt.size()== 1 && !infix.isEmpty()) { 
                        size = nt.size();// remover elemento extraido 1
                        dtmp1 = getTypeNumber(nt.lastElement());
                        if (dtmp1 < 0) { // numeros negativos
                            tmp1 = "(" + dtmp1 + ")";
                        } else {
                            tmp1 = String.valueOf(dtmp1);
                        }

                        infix = "(" + infix + ")" + pt.lastElement() + tmp1; // (expresion) operador numero
                        nt.remove(size - 1);
                        pt.remove(pt.size() - 1);// remover operador utilizado
                    }else if (nt.size()>= 2 && !infix.isEmpty()) {
                        size = nt.size();
                        dtmp1 = getTypeNumber(nt.get(size - 1));
                        dtmp2 = getTypeNumber(nt.get(size - 2));
                        if (dtmp1 < 0) {
                            tmp1 = "(" + dtmp1 + ")";
                        } else {
                            tmp1 = String.valueOf(dtmp1);
                        }
                        
                        if (dtmp2 < 0) {
                            tmp2 = "(" + dtmp2 + ")";
                        } else {
                            tmp2 = String.valueOf(dtmp2);
                        }
                        
                        infix_tmp = "("+ tmp2 + pt.lastElement() + tmp1 + ")";
                        pt.remove(pt.size()-1);
                        nt.remove(size - 1);
                        nt.remove(size - 2);
                    }else if(nt.size()==0 && !infix_tmp.isEmpty()){
                        infix = "("+infix+")"+pt.lastElement()+infix_tmp;
                        pt.remove(pt.size()-1);
                        infix_tmp = "";
                    }     
                    /**
                     * Notación RPN: 1 2 + 3 - 4 - * 5 6 -
                     * Resultado Notacion Infija: (((1 + 2) - 3) - 4) * (5 - 6)
                     * Concatena la notación infija con el Operador encontrado
                     */
                    else if(nt.size()==0 && !infix.isEmpty()){ // caso 1 2 + 3 - 4 - * 5 6 -
                        infix = "("+infix+")"+pt.lastElement()+infix_tmp;
                        pt.remove(pt.size()-1);
                        infix_tmp = "";
                    }
                    break;
            }
            /**
             * caso 1 2 + 3 - 4 - * 5 6 -
             * Resultado: (((1.0+2.0)-3.0)-4.0)*(5.0-6.0)
             * Concatena la notación infija restante al terminar el token
             */
            if(!infix_tmp.isEmpty()){ // caso 1 2 + 3 - 4 - * 5 6 -
                infix = infix + infix_tmp;
                infix_tmp = "";
            }
            tmp1 = "";
            tmp2 = "";
            dtmp1 = 0;
            dtmp2 = 0;

        }

        return infix;
    }

    // TODO evaluar si aun existen operandos con conteo mayor a los ya finalizados para formar la expresion o al contrario
    /**
     * metodo para evaluacion de la logica de la expresion ingresa segun el
     * numero de identificadores y operadores
     *
     * @param cadena
     * @throws MalFormatExp
     */
    public void evaluarSintactico(String cadena) throws MalFormatExp {

        StringTokenizer token = new StringTokenizer(cadena);
        str = "";
        n.clear();
        p.clear();
        while (token.hasMoreTokens()) {
            str = token.nextToken();
            switch (getTypeToken(str)) {
                case 1:
                    n.push(str);// cola para los numeros
                    break;
                case 2:
                    p.push(str);// cola para los operadores
                    if(n.size() < 2 && Pattern.matches(patternOpers, p.lastElement())){
                        throw new MalFormatExp("ERROR SINTACTICO : Faltan elementos de tipo OPERANDOS para procesar. "
                            + "Por favor verificar.");
                    }
                    break;
            }
        }

        // evaluar numero de operadores y operandos
        if (n.size() - 1 != p.size()) {
            if (n.size() > p.size()) {
                throw new MalFormatExp("ERROR SINTACTICO : Hay demasiados elementos de tipo OPERANDOS para procesar. "
                        + "Por favor verificar.");
            } else {
                throw new MalFormatExp("ERROR SINTACTICO : Hay demasiados elementos de tipo OPERANDORES para procesar. "
                        + "Por favor verificar.");
            }
        }
        
    }

    public Stack<String> getP() {
        return p;
    }

    public Stack<String> getN() {
        return n;
    }

}
