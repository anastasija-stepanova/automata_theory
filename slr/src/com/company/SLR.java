package com.company;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class SLR {
  private Stack<HashSet<RuleId>> states = new Stack<>();
  private Stack<Token> tokens = new Stack<>();
  private Stack<Token> input = new Stack<>();
  private Table table;

  public SLR(Table table, List<String> input) {
    this.table = table;
    for (Integer inputNumber = input.size() - 1; inputNumber >= 0; inputNumber--) {
      Token token = new Token();
      token.name = input.get(inputNumber);
      token.type = TokenType.TERMINAL;
      this.input.add(token);
    }
    this.states.add(new HashSet<>());
  }

  public void run() {
    Token startToken = new Token();
    startToken.name = "start";
    startToken.type = TokenType.SPEC;

    while (true) {
      Token token = popInInput();
      if (token.equals(startToken)) {
        System.out.println("OK");
        break;
      }
      HashSet<RuleId> state = states.pop();
      states.push(state);
      if (!table.forward.containsKey(state) || !table.forward.get(state).containsKey(token)) {
        if (!table.jump.containsKey(state) || !table.jump.get(state).containsKey(token)) {
          System.out.println("Error");
          break;
        }

        putInInput(token);

        if (!transit(table.rules.get(table.jump.get(state).get(token)))) {
          break;
        }
        continue;
      }

      states.push(table.forward.get(state).get(token));
      tokens.add(token);
    }
  }

  private Boolean transit(TokenIn tokenIn) {
    for (Integer i = tokenIn.components.size() - 1; i >= 0; i--) {
      Token component = tokenIn.components.get(i);
      if (states.isEmpty()) {
        System.out.println("Error");
        return false;
      }
      if (tokens.isEmpty()) {
        System.out.println("Error");
        return false;
      }
      states.pop();
      Token token = tokens.pop();
      if (!token.equals(component)) {
        System.out.println("Error");
        return false;
      }
    }
    input.push(tokenIn.token);
    return true;
  }

  private void putInInput(Token token) {
    this.input.push(token);
  }

  private Token popInInput() {
    if (this.input.isEmpty()) {
      Token token = new Token();
      token.name = "end";
      token.type = TokenType.SPEC;
      return token;
    }
    return input.pop();
  }
}