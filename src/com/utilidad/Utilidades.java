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
import java.util.regex.Pattern;

/**
 *
 * @author mitzyvo
 * @date 11-may-2016
 */
public class Utilidades {

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
     * Expresion regular para numeros hexadecimales negativos y positivos
     */
    public static final String patternHex = "-*0[x|X][0-9a-f]+[0-9]*";
    /**
     * Expresion regular para numeros decimales
     */
    public static final String patternNumberDec = "-*[0-9]+[,0-9]*";
    /**
     * Expresion regular para numeros enteros
     */
    public static final String patternNumberInt = "-*[0-9]+";
    /**
     * Array de Operadores aceptados
     */
    public static final String patternOpers = "[+|-|/|*]";
    /**
     * No es una palabra
     */
    public static final String patternWord = "[a-zA-Z]+";
    /**
     * Es algun simbolo no reconocido
     */
    public static final String patterSomeSymbol = "[\\p{Alnum}\\p{Punct}]*[\\p{Punct}]+[\\p{Alnum}\\p{Punct}]*";

    /**
     * Es un spacio en blanco
     */
    //public static final String patternWhiteSpace = "[ \\t\\n\\x0B\\f\\r]";    
    /**
     * metodo para la evaluacion del lexico
     *
     * @param value
     * @throws com.utilidad.MalFormatExp
     */
    public static void evaluarLexico(String value) throws MalFormatExp {

        if (Pattern.matches(patternNumberDec, value)) {
            System.out.println("-- number dec");
        } else if (Pattern.matches(patternNumberInt, value)) {
            System.out.println("-- number int");
        } else if (Pattern.matches(patternHex, value)) {
            System.out.println("-- hex");
        } else if (Pattern.matches(patternOpers,value)) {
            System.out.println("-- oper");
        } else if (Pattern.matches(patternUpper, value)) { // desde aqui algo va mal
            throw new MalFormatExp("ERROR LEXICO: Tiene caracteres en mayuscula.");
        } else if (Pattern.matches(patternWord, value)) {
            throw new MalFormatExp("ERROR LEXICO: Es una palabra.");
        } else if (Pattern.matches(patternNotHex, value)) {
            throw new MalFormatExp("ERROR LEXICO: Tiene caracteres que no son hexadecimales.");
        } else if (Pattern.matches(patterSomeSymbol, value)) {
            throw new MalFormatExp("ERROR LEXICO: Contiene simbolo(s) no reconocido(s).");
        }
    }

    /**
     * Metodo para conocer jerarquia de operadores
     *
     * @param op
     * @return
     */
    private static int operJerarq(String op) {
        int prf = 0;
        if (op.equals("*") || op.equals("/")) {
            prf = 2;
        }
        if (op.equals("+") || op.equals("-")) {
            prf = 1;
        }
        return prf;
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
            throw new MalFormatExp("Error no reconocido : \n", e);
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
            throw new MalFormatExp("Error no reconocido : \n", e);
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
    public static boolean createFile(String tmpValue, byte[] content) {
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
     * metodo para ayudar a la organizacion de las pilas de operandos y operadores
     * @param value
     * @return
     */
    public static int getTypeToken(String value) {
        int get = 0;
        if (Pattern.matches(patternNumberDec, value) || Pattern.matches(patternNumberInt, value) || Pattern.matches(patternHex, value)) {
            get = 1; 
        } else {
            get = 2; 
        }
        return get;
    }

    /**
     * metodo para organizar los elementos de postfijo a infijo
     * @param post 
     * @return  cadena organizada de los elementos en notacion infija
     */
    public static String organizarInfija(String post) {

        String infix = "";
        String str = "";
        int size = 0;
        Stack< String> p = new Stack< String>();
        Stack< String> n = new Stack< String>();
        StringTokenizer token = new StringTokenizer(post);

        while (token.hasMoreTokens()) {
            str = token.nextToken();
            switch (getTypeToken(str)) {
                case 1:
                    n.push(str);// cola para los numeros
                    break;
                case 2:
                    p.push(str);// cola para los operadores
                    if(n.size()>1 && infix.isEmpty()){// esta inicializando la organizacion
                        size = n.size();
                        infix = n.get(size-1) + p.lastElement() + n.get(size-2);
                        n.remove(size-1);// remover elemento extraido 1
                        n.remove(size-2);// remover elemento extraido 2
                        p.remove(p.size()-1);// remover operador utilizado
                        System.out.println("asi vamos 1:  "+infix);
                    }else if(n.size() >= 1){
                        size = n.size();// remover elemento extraido 1
                        infix = "("+infix+")"+p.lastElement()+n.lastElement();
                        n.remove(size-1);
                        p.remove(p.size()-1);// remover operador utilizado
                        System.out.println("asi vamos 2:  "+infix);
                    }
                    break;
            }
        }
        
        return infix;
    }
    
    // TODO evaluar si aun existen operandos con conteo mayor a los ya finalizados para formar la expresion o al contrario
    
}
