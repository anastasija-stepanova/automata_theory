package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

//    private static HashSet<String> keywords = new HashSet<>();
    public static void main(String[] args) {
//        String input = "";
//        try {
//            Scanner scanner = new Scanner(new File("./src/com/company/input.txt"));
//            while (scanner.hasNextLine()) {
//                input += (scanner.nextLine() + "\n");
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println(ex.getLocalizedMessage());
//        }
//
//
//        String current = "";
//        Lexer lexer = new Lexer();

//        keywords.add("for");
//        keywords.add("while");
//        keywords.add("if");
//        keywords.add("then");
//        keywords.add("else");
//        keywords.add("var");
//        keywords.add("let");
//        keywords.add("foreach");
//        keywords.add("a");
//        keywords.add("c");
//        keywords.add("b");
//        keywords.add("end");
//
//
//        try(FileWriter writer = new FileWriter("./src/com/company/out.txt", false))
//        {
//            for (Character ch: input.toCharArray()) {
//                if (ch != ' ' && ch != '\n' && ch != '\t') {
//                    current += ch;
//                }
//                Integer res = lexer.nextStep(ch);
//                if (res == -1) {
//                    continue;
//                } else if (res == 0) {
//                    System.out.println("Error: " + current);
//                } else if (res == 7 && keywords.contains(current)) {
//                    System.out.println("KW: " + current);
//                    writer.append(current);
//                    writer.append("\n");
//                } else if (res != 9 && res != 8) {
//                    System.out.println("Id: " + res + " Val: " + current);
//                }
//                current = "";
//            }
//            writer.flush();
//        }
//        catch(IOException ex){
//
//            System.out.println(ex.getMessage());
//        }

        Reader reader = new Reader();
        List<TokenInput> grammer = reader.readFromFile("./src/com/company/grammer.txt");
        TableCreator creator = new TableCreator();
        List<Row> table = creator.createTable(grammer);

        List<String> ins = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("./src/com/company/out.txt"));
            while (scanner.hasNextLine()) {
                ins.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

//        ins.add("a");
//        ins.add("c");
//        ins.add("b");
//        ins.add("b");
//        ins.add("end");

        Runner runner = new Runner(table, ins);
        System.out.println(runner.run());
    }
}
