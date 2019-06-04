package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lexer {
  private List<List<Integer>> states;
  private Integer stateNumber = 28;
  private Integer keyNumber = 44;
  private Map<Character, Integer> keys;
  private Map<Integer, Integer> finalStates;
  private Integer currentState;

  public Lexer() {
    this.states = new ArrayList<>();
    this.readStates();
    this.keys = new HashMap<>();
    this.setUpKeys();
    this.currentState = 0;
    this.finalStates = new HashMap<>();
    this.setUpFinalStates();
  }

  public Integer nextStep(Character symbol) {
    if (!keys.containsKey(symbol)) {
      currentState = 0;
      return 0;
    }
    Integer col = keys.get(symbol);
    Integer nextState = states.get(currentState).get(col);

    if (nextState == -1) {
      currentState = 0;
      return 0;
    }

    if (finalStates.containsKey(nextState)) {
      currentState = 0;
      return finalStates.get(nextState);
    }
    currentState = nextState;

    return -1;
  }

  private void setUpKeys() {
    keys.put('0', 0);
    keys.put('1', 1);
    keys.put('2', 2);
    keys.put('3', 3);
    keys.put('4', 4);
    keys.put('5', 5);
    keys.put('6', 6);
    keys.put('7', 7);
    keys.put('8', 8);
    keys.put('9', 9);
    keys.put('a', 10);
    keys.put('b', 11);
    keys.put('c', 12);
    keys.put('d', 13);
    keys.put('e', 14);
    keys.put('f', 15);
    keys.put('x', 16);
    keys.put(' ', 17);
    keys.put('\n', 18);
    keys.put('\t', 19);
    keys.put('o', 20);
    keys.put('.', 21);
    keys.put('/', 22);
    keys.put('*', 23);
    keys.put('g', 24);
    keys.put('h', 25);
    keys.put('i', 26);
    keys.put('j', 27);
    keys.put('k', 28);
    keys.put('l', 29);
    keys.put('m', 30);
    keys.put('n', 31);
    keys.put('p', 32);
    keys.put('q', 33);
    keys.put('r', 34);
    keys.put('s', 35);
    keys.put('t', 36);
    keys.put('u', 37);
    keys.put('v', 38);
    keys.put('w', 39);
    keys.put('y', 40);
    keys.put('z', 41);
    keys.put('+', 42);
    keys.put('-', 43);
  }

  private void setUpFinalStates() {
    finalStates.put(4, 1);
    finalStates.put(6, 2);
    finalStates.put(9, 3);
    finalStates.put(12, 4);
    finalStates.put(15, 5);
    finalStates.put(19, 6);
    finalStates.put(21, 7);
    finalStates.put(24, 8);
    finalStates.put(27, 9);
  }

  private void readStates() {
    try {
      Scanner scanner = new Scanner(new File("C:\\Users\\Anastasia\\Desktop\\lexer2\\states.txt"));
      for (int i = 0; i < stateNumber; i++) {
        List<Integer> row = new ArrayList<>();
        for (int j = 0; j < keyNumber; j++) {
          row.add(scanner.nextInt());
        }
        scanner.nextLine();
        states.add(row);
      }
    } catch (
            FileNotFoundException ex) {
      System.out.println("File not found");
    }
    System.out.println();
  }
}
