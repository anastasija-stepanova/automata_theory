package com.company;

import com.company.token.Token;
import com.company.tokenType.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Factorization {
  private List<TokenIn> input;
  private Integer counter = 0;
  private HashMap<String, HashSet<String>> t = new HashMap<>();

  public Factorization(List<TokenIn> input) {
    this.input = input;
  }

  public List<TokenIn> factorize() {
    Boolean wasChanges = true;

    while (wasChanges) {
      t = new HashMap<>();
      wasChanges = false;

      Integer inputSize = input.size();

      for (int i = 0; i < inputSize; i++) {

        TokenIn in = input.get(i);
        if (!t.containsKey(in.token.name)) {
          HashSet<String> myHashSet = new HashSet<>();
          t.put(in.token.name, myHashSet);
        }
        if (t.get(in.token.name).contains(in.components.get(0).name)) {
          factorizeToken(in.token.name, in.components.get(0).name);
          wasChanges = true;
        } else {
          t.get(in.token.name).add(in.components.get(0).name);
        }
      }
    }
    return input;
  }

  private void factorizeToken(String name, String nameOfFirst) {
    List<TokenIn> allTokens = getAllInputsWithTokenName(name, nameOfFirst);

    Token oneMoreToken = new Token();
    String newName = "Fact" + counter++;
    oneMoreToken.name = newName;
    oneMoreToken.type = TokenType.NO_TERMINAL;
    TokenIn newToken = new TokenIn();
    newToken.token = new Token();
    newToken.token.name = name;
    newToken.token.type = TokenType.NO_TERMINAL;
    newToken.components = new ArrayList<>();
    newToken.components.add(allTokens.get(0).components.get(0));
    newToken.components.add(oneMoreToken);
    input.add(newToken);

    for (TokenIn in : allTokens) {
      in.token.name = newName;
      in.components.remove(0);
      if (in.components.isEmpty())
      {
        Token twoMoreToken = new Token();
        twoMoreToken.name = "empty";
        twoMoreToken.type = TokenType.SPEC;
        in.components.add(twoMoreToken);
      }
    }
  }

  private List<TokenIn> getAllInputsWithTokenName(String name, String nameOfFirst) {
    List<TokenIn> allTokens = new ArrayList<>();

    for (TokenIn in : input) {
      if (in.token.name.equals(name) && (in.components.get(0).name.equals(nameOfFirst))) {
        allTokens.add(in);
      }
    }

    return allTokens;
  }
}
