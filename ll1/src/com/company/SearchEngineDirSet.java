package com.company;

import com.company.token.Token;
import com.company.tokenType.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SearchEngineDirSet {

  public List<TokenInput> dirSet = new ArrayList<>();

  private HashMap<String, HashSet<String>> calculatedFollow = new HashMap<>();
  private HashMap<String, HashSet<String>> calculatedFirst = new HashMap<>();

  private HashSet<String> cycleDetectorFirst = new HashSet<>();
  private HashSet<String> cycleDetectorFollow = new HashSet<>();

  public SearchEngineDirSet(List<TokenIn> tokenIn) {
    for (TokenIn t : tokenIn) {
      TokenInput tI = new TokenInput();
      tI.token = t.token;
      tI.components = t.components;
      tI.dirSet = new HashSet<>();
      dirSet.add(tI);
    }
  }

  public HashSet<String> getFirst(String name)
  {
    HashSet<String> found = calculatedFirst.get(name);
    if (found != null) {
      return found;
    }
    if (cycleDetectorFirst.contains(name)) {
      System.out.println("Cycle was found");
      return new HashSet<>();
    }
    cycleDetectorFirst.add(name);

    if (name.equals("end")) {
      HashSet<String> myHashSet = new HashSet<>();
      myHashSet.add("end");
      return myHashSet;
    }
    HashSet<String> result = new HashSet<>();

    for (TokenInput tokenInput: dirSet) {
      Token first = tokenInput.components.get(0);
      if (tokenInput.token.name.equals(name))
      {
        if (first.type == TokenType.TERMINAL)
        {
          result.add(first.name);
        }
        else if (first.type == TokenType.NO_TERMINAL)
        {
          result.addAll(getFirst(first.name));
        }
        else
        {
          result.addAll(getFollow(tokenInput.token.name));
        }
      }
    }
    return result;
  }

  public HashSet<String> getFollow(String name)
  {
    HashSet<String> found = calculatedFollow.get(name);
    if (found != null) {
      return found;
    }
    if (cycleDetectorFollow.contains(name)) {
      System.out.println("Cycle was found");
      return new HashSet<>();
    }
    cycleDetectorFollow.add(name);

    HashSet<String> result = new HashSet<>();

    for (TokenInput tokenInput: dirSet) {
     for (Integer i = 0; i < tokenInput.components.size(); ++i)
     {
       if (tokenInput.components.get(i).name.equals(name))
       {
         if (i != tokenInput.components.size() - 1)
         {
           result.addAll(getFirst(tokenInput.components.get(i + 1).name));
         }
         else
         {
           if (!name.equals(tokenInput.token.name))
           {
             result.addAll((getFollow(tokenInput.token.name)));
           }
         }
       }
     }
    }

    return result;
  }

  public List<TokenInput> run()
  {
    for (TokenInput t: dirSet) {
      cycleDetectorFirst = new HashSet<>();
      cycleDetectorFollow = new HashSet<>();
      Token firstComponent = t.components.get(0);
      if (firstComponent.type == TokenType.TERMINAL)
      {
        t.dirSet.add(firstComponent.name);
      }
      else if (firstComponent.type == TokenType.NO_TERMINAL)
      {
        t.dirSet.addAll(getFirst(firstComponent.name));
      }
      else
      {
        t.dirSet.addAll(getFollow(t.token.name));
      }
    }

    return dirSet;
  }
}
