/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.utilidad.Utilidades;
import com.utilidad.MalFormatExp;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PC
 */
public class Main {

    /**
     * @param args the command line arguments
     * -*0[xX][0-9]+.[0-9]*[f|p]*-*[0-1023]+ dobles [a-f]*[0-9a-f]+[0-9]*
     * enteros patron ingresado antes de saber si es numero
     * -*0[xX][0-9a-f]+[0-9]*
     */
    public static void main(String[] args) {

        //System.out.println("*Escribe una expresión algebraica: ");
        //Scanner leer = new Scanner(System.in);
        //String expr = leer.nextLine();

        //Utilidades.organizarInfija(expr);
//        try {
//            evaluarExpresion(expr);
////        double b = Double.parseDouble("0x25af");
//            //int b = Integer.decode("0x25af");
//            //boolean r = Integer.toHexString(b).matches("[a-f]*[0-9a-f]+[0-9]*");
//            //boolean r = Double.toHexString(b).matches("-*0[x][0-9].[0-9]*[f|p]*-*[0-1023]+");
//            // 0[xX][0-9a-fA-F]+
//            //System.out.println(r + " "+ b + " "+Integer.toHexString(b));
//            //evaluarExpresion(leer);
////        String[] arrayInfix = expr.split(" ");
////       String r = "0x25AF";
        boolean g = Pattern.matches("[+|-|/|*]","+");
////       System.out.println(g);
//            // [\\p{Alnum}\\p{Punct}]*[\\p{Punct}]+[\\p{Alnum}\\p{Punct}]*
//            //boolean g = Pattern.matches(Utilidades.patterSomeSymbol, "2.4");
//        } catch (MalFormatExp ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        // adicionar expresion cuando solo vengan numeros o solo vengan simbolos
        System.out.println("finish "+g);
    }

    private static String verificarHexadecimal(Scanner expr) {
        String line = expr.nextLine();
        boolean g = Pattern.matches("-*0[x|X][0-9a-f]+[0-9]*", line);
        if (!g) {
            System.out.println(g); // false
        } else {
            System.out.println(g); // true
        }
        return null;
    }

    //Depurar expresión algebraica
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbols = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    private static int evaluarExpresion(String expr) throws MalFormatExp {

        String r = "";
        //String numeros = "0123456789";
        double d = 0;
        int i = 0;
//        
        StringTokenizer token = new StringTokenizer(expr);
        while (token.hasMoreTokens()) {
            i++;
            r = token.nextToken();
            System.out.println("token " + r);
            Utilidades.evaluarLexico(r);
        }
        return 0;
    }
}
