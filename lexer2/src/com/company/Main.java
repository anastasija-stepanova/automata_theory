package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static HashSet<String> keywords = new HashSet<>();

    public static void main(String[] args) {
        String input = "";
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\Anastasia\\Desktop\\lexer2\\input.txt"));
            while (scanner.hasNextLine()) {
                input += (scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }


        String current = "";
        Lexer lexer = new Lexer();

//        keywords.add("for");
//        keywords.add("while");
//        keywords.add("if");
//        keywords.add("then");
//        keywords.add("else");
//        keywords.add("var");
//        keywords.add("let");
//        keywords.add("foreach");
        keywords.add("a");
        keywords.add("c");
        keywords.add("b");
        keywords.add("end");


        for (Character ch: input.toCharArray()) {
            if (ch != ' ' && ch != '\n' && ch != '\t') {
                current += ch;
            }
            Integer res = lexer.nextStep(ch);
            if (res == -1) {
                continue;
            } else if (res == 0) {
                System.out.println("Error: " + current);
            } else if (res == 7 && keywords.contains(current)) {
                System.out.println("KW: " + current);
            } else if (res != 9 && res != 8) {
                System.out.println("Id: " + res + " Val: " + current);
            }
            current = "";
        }
    }
}
