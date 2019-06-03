package com.company;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Reader reader = new Reader();
        List<TokenInput> grammer = reader.readFromFile("C:\\Users\\Anastasia\\ll1\\src\\com\\company\\in.txt");
        TableCreator creator = new TableCreator();
        List<Row> table = creator.createTable(grammer);

        List<String> ins = new ArrayList<>();
        ins.add("a");
        ins.add("c");
        ins.add("b");
        ins.add("b");
        ins.add("end");

        Runner runner = new Runner(table, ins);
        System.out.println(runner.run());
    }
}
