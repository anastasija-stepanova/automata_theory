package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Reader reader = new Reader();
        List<TokenIn> grammer = reader.readFromFile("./src/com/company/grammer.txt");

//        for (TokenIn g : grammer) {
//            System.out.println(g.token.name);
//            for (Token t : g.components) {
//                System.out.println(t.name);
//                System.out.println(t.type);
//            }
//        }

//        FirstFollowCalculator calc = new FirstFollowCalculator(grammer);
//
//        Token t = new Token();
//        t.name = "id_list";
//        t.type = TokenType.NO_TERMINAL;
//
//        HashSet<RuleId> result = calc.getFirst(t);

        TableCreator tableCreator = new TableCreator(grammer);
        tableCreator.loadTable();
        tableCreator.print();
        try {
            SLR slr = new SLR(tableCreator.table, Main.readInputString());
            slr.run();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public static List<String> readInputString() throws IOException {
        File file = new File("C:\\Users\\Anastasia\\source\\automata_theory\\slr\\src\\com\\company\\input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> result = new ArrayList<>();
        String st;
        while ((st = br.readLine()) != null)
        {
            result.addAll(Arrays.asList(st.split(" ")));
        }

        return result;
    }
}
