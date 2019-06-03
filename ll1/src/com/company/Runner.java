package com.company;

import java.util.List;
import java.util.Stack;

public class Runner {
  List<Row> grammer;
  List<String> in;
  Integer pointer = 0;
  Integer grammerPointer = 0;
  Stack<Integer> stack = new Stack<>();

  public Runner(List<Row> grammer, List<String> in) {
    this.grammer = grammer;
    this.in = in;
  }

  public String run() {
    while (true) {
      if (grammer.get(pointer).directionSet.contains(in.get(grammerPointer))) {
        Row currentRow = grammer.get(pointer);

        if (currentRow.isEnd) {
          return "OK";
        }

        if (currentRow.shift) {
          grammerPointer++;
        }
        if (currentRow.stack) {
          stack.push(pointer + 1);
        }

        if (currentRow.goTo == -1) {
          pointer = stack.pop();
        } else {
          pointer = currentRow.goTo;
        }
      } else if (grammer.get(pointer).errorTransition != -1) {
        pointer = grammer.get(pointer).errorTransition;
      } else {
        return "Error";
      }
    }
  }
}
