package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FirstFollowCalculator {
  private List<TokenIn> grammer;
  private HashMap<Token, HashSet<RuleId>> calculatedFirst = new HashMap<>();
  private HashMap<Token, HashSet<RuleId>> calculatedFollow = new HashMap<>();
  private HashSet<Token> recursionValidatorFirst = new HashSet<>();
  private HashSet<Token> recursionValidatorFollow = new HashSet<>();

  public FirstFollowCalculator(List<TokenIn> grammer) {
    this.grammer = grammer;
  }

  public HashSet<RuleId> getFirst(Token token) {
    if (calculatedFirst.containsKey(token)) {
      return calculatedFirst.get(token);
    }

    if (recursionValidatorFirst.contains(token)) {
      System.out.println("Cycle was found");
      return new HashSet<>();
    }

    recursionValidatorFirst.add(token);
    HashSet<RuleId> result = new HashSet<>();

    for (Integer ruleNumber = 0; ruleNumber < grammer.size(); ruleNumber++) {
      if (grammer.get(ruleNumber).token.equals(token)) {
        RuleId ruleId = new RuleId();
        ruleId.rule = ruleNumber;
        ruleId.position = 0;
        result.add(ruleId);
        if (!grammer.get(ruleNumber).components.get(0).equals(token))
        {
          result.addAll(getFirst(grammer.get(ruleNumber).components.get(0)));
        }

      }
    }
    calculatedFirst.put(token, result);
    recursionValidatorFirst.remove(token);
    return result;
  }

  public HashSet<RuleId> getFollow(Token token) {
    if (calculatedFollow.containsKey(token)) {
      return calculatedFollow.get(token);
    }

    if (recursionValidatorFollow.contains(token)) {
      System.out.println("Cycle was found");
      return new HashSet<>();
    }

    recursionValidatorFollow.add(token);
    HashSet<RuleId> result = new HashSet<>();

    for (Integer ruleNumber = 0; ruleNumber < grammer.size(); ruleNumber++) {
      TokenIn row = grammer.get(ruleNumber);
      for (Integer positionNumber = 0; positionNumber < row.components.size(); positionNumber++) {

        if (row.components.get(positionNumber).equals(token)) {
          if (positionNumber.equals(row.components.size() - 1)) {
            if (!row.token.equals(token)) {
              result.addAll(getFollow(row.token));
            }
          } else {
            Token tokenNext = row.components.get(positionNumber + 1);
            RuleId ruleId = new RuleId();
            ruleId.rule = ruleNumber;
            ruleId.position = positionNumber + 1;
            result.add(ruleId);

            if (tokenNext.type.equals(TokenType.NO_TERMINAL)) {
              result.addAll(getFirst(tokenNext));
            }
          }
        }
      }
    }

    calculatedFollow.put(token, result);
    recursionValidatorFollow.remove(token);
    return result;
  }
}
