package com.company;

import com.company.token.Token;
import com.company.tokenType.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecurcionDeleter {
  Integer newStateCounter = 0;
  HashMap<String, List<TokenIn>> tokensByName = new HashMap<>();
  List<String> statesToRemoveRecursion = new ArrayList<>();

  public List<TokenIn> run(List<TokenIn> in) {
    for (TokenIn value : in) {
      if (tokensByName.containsKey(value.token.name)) {
        tokensByName.get(value.token.name).add(value);
      } else {

        List<TokenIn> a = new ArrayList<>();
        a.add(value);
        if (value.token.name.equals(value.components.get(0).name)) {
          statesToRemoveRecursion.add(value.token.name);
        }

        tokensByName.put(value.token.name, a);
      }
    }

    for (String state : statesToRemoveRecursion) {
      removeRecursion(state);
    }

    List<TokenIn> result = new ArrayList<>();

    for (List<TokenIn> component: tokensByName.values() ) {
      result.addAll(component);
    }

    return result;
  }

  private void removeRecursion(String name) {
    List<TokenIn> data = tokensByName.get(name);
    TokenIn recoursivePart = new TokenIn();
    List<TokenIn> noRecoursivePart = new ArrayList<>();


    for (TokenIn t: data ) {
      if (t.token.name.equals(t.components.get(0).name)) {
        recoursivePart = t;
      } else {
        noRecoursivePart.add(t);
      }
    }

    Token noTermA = new Token();
    Token noTermD = new Token();
    Token noTermB = new Token();
    noTermD.name = "Rec" + newStateCounter++;
    noTermB.name = "Rec" + newStateCounter++;
    noTermA.name = name;
    noTermA.type = TokenType.NO_TERMINAL;

    noTermB.type = TokenType.NO_TERMINAL;
    noTermD.type = TokenType.NO_TERMINAL;

    TokenIn A = new TokenIn();
    A.token = noTermA;
    A.components = new ArrayList<>();

    A.components.add(noTermD);
    A.components.add(noTermB);
    List<TokenIn> listFromA = new ArrayList<>();
    listFromA.add(A);
    tokensByName.put(name, listFromA);

    tokensByName.put(noTermD.name, new ArrayList<>());
    for (TokenIn t: noRecoursivePart) {
      TokenIn D = new TokenIn();
      D.token = noTermD;
      D.components = t.components;
      tokensByName.get(noTermD.name).add(D);
    }

    TokenIn B = new TokenIn();
    B.token = noTermB;

    Token empty = new Token();
    empty.type = TokenType.SPEC;
    empty.name = "empty";

    B.components = new ArrayList<>();
    B.components.add(empty);
    tokensByName.put(noTermB.name, new ArrayList<>());
    tokensByName.get(noTermB.name).add(B);

    TokenIn B1 = new TokenIn();
    B1.token = noTermB;

    B1.components = new ArrayList<>();
    B1.components.addAll(recoursivePart.components);
    B1.components.remove(0);
  }
}
