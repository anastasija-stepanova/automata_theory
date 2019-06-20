package com.company;

import java.util.*;

public class TableCreator {

  private FirstFollowCalculator firstFollowCalculator;
  public Table table = new Table();
  private Queue<HashSet<RuleId>> queue = new ArrayDeque<>();
  private HashSet<HashSet<RuleId>> calculated = new HashSet<>();

  public TableCreator(List<TokenIn> grammer) {
    this.table.rules = grammer;
    firstFollowCalculator = new FirstFollowCalculator(grammer);
    table.forward = new HashMap<>();
    table.jump = new HashMap<>();
  }

  public void print()
  {
    System.out.println("Shift table");
    table.forward.forEach((key, value) -> {
      System.out.print("##### <");
      for (RuleId r: key) {
        System.out.print(r.rule + "." + r.position + " ");
      }
      System.out.println(">");

      value.forEach((k, v) -> {
        System.out.print("[ " + k.name + " ]: ");

        for (RuleId ruleId: v) {
          System.out.print(ruleId.rule + "." + ruleId.position + " ");
        }
        System.out.println();
      });
    });


    System.out.println("\n\n\n\nJump table");
    table.jump.forEach((key, value) -> {
      System.out.print("##### <");
      for (RuleId r: key) {
        System.out.print(r.rule + "." + r.position + " ");
      }
      System.out.println(">");

      value.forEach((k, v) -> {
        System.out.println("[ " + k.name + " ]: " + v);
      });
    });
  }

  public void loadTable() {
    setUpInitState();

    Token firstToken = new Token();
    firstToken.type = TokenType.SPEC;
    firstToken.name = "start";

    HashSet<RuleId> queueInit = firstFollowCalculator.getFirst(firstToken);
    queue.addAll(sortTokens(queueInit));

    while (!queue.isEmpty()) {
      HashSet<RuleId> cell = queue.remove();
      calculated.add(cell);

      calculateCell(cell);
    }

    RuleId endRule = new RuleId();
    endRule.rule = 0;
    endRule.position = table.rules.get(0).components.size() - 1;

    HashSet<RuleId> emptyState = new HashSet<>();
    emptyState.add(endRule);

    Token endToken = new Token();
    endToken.type = TokenType.SPEC;
    endToken.name = "end";

    table.jump.get(emptyState).put(endToken, 0);
  }

  private void calculateCell(HashSet<RuleId> cell) {
    for (RuleId ruleId: cell) {
      if (ruleId.position.equals(table.rules.get(ruleId.rule).components.size() - 1)) {
        calculateLastCell(cell, ruleId);
      } else {
        calculateMiddleCell(cell, ruleId);
      }
    }
    if (table.forward.containsKey(cell)) {
      HashMap<Token, HashSet<RuleId>> elements = table.forward.get(cell);

      for (HashSet<RuleId> r : elements.values()) {
        if (!calculated.contains(r))
        {
          queue.add(r);
        }
      }
    }

  }


  private void calculateLastCell(HashSet<RuleId> cell, RuleId ruleId) {
    Token currentToken = table.rules.get(ruleId.rule).components.get(ruleId.position);
    HashSet<RuleId> follow = firstFollowCalculator.getFollow(currentToken);

    for (HashSet<RuleId> item : sortTokens(follow)) {
      for (RuleId rule : item) {
        Token token = table.rules.get(rule.rule).components.get(rule.position);
        if (!table.jump.containsKey(cell))
        {
          table.jump.put(cell, new HashMap<>());
        }

        table.jump.get(cell).put(token, ruleId.rule);
      }
    }
  }

  private void calculateMiddleCell(HashSet<RuleId> cell, RuleId ruleId) {
    if (ruleId.rule.equals(2) && ruleId.position.equals(1)) {
      int a = 2;
    }
    Token tokenNext = table.rules.get(ruleId.rule).components.get(ruleId.position + 1);
    HashSet<RuleId> first = firstFollowCalculator.getFirst(tokenNext);
    RuleId newRule = new RuleId();
    newRule.position = ruleId.position + 1;
    newRule.rule = ruleId.rule;

    first = new HashSet<>(first);
    first.add(newRule);

    List<HashSet<RuleId>> r = sortTokens(first);
    for (HashSet<RuleId> item : sortTokens(first)) {

      for (RuleId rule : item) {
        Token token = table.rules.get(rule.rule).components.get(rule.position);
        if (!table.forward.containsKey(cell))
        {
          table.forward.put(cell, new HashMap<>());
        }

        if (!table.forward.get(cell).containsKey(token))
        {
          table.forward.get(cell).put(token, new HashSet<>());
        }

        table.forward.get(cell).get(token).add(rule);
      }
    }

  }


  private List<HashSet<RuleId>> sortTokens(HashSet<RuleId> rules)
  {
    HashMap<Token, HashSet<RuleId>> result = new HashMap<>();

    for (RuleId rule : rules) {
      Token token = table.rules.get(rule.rule).components.get(rule.position);
      if (!result.containsKey(token)) {
        result.put(token, new HashSet<>());
      }
      result.get(token).add(rule);
    }

    List<HashSet<RuleId>> resultForR = new ArrayList<>();
    for (HashSet<RuleId> r: result.values()) {
      resultForR.add(r);
    }

    return resultForR;
  }

  private void setUpInitState()
  {
    RuleId axiom = new RuleId();
    axiom.rule = 0;
    axiom.position = 0;
    Token token = table.rules.get(0).components.get(0);
    HashSet<RuleId> setOfFirst = new HashSet<>();
    setOfFirst.add(axiom);
    setOfFirst.addAll(firstFollowCalculator.getFirst(token));

    List<HashSet<RuleId>> initStates = sortTokens(setOfFirst);

    for (HashSet<RuleId> state: initStates) {
      for (RuleId ruleId: state) {
        Token component = table.rules.get(ruleId.rule).components.get(ruleId.position);

        HashSet<RuleId> emptyState = new HashSet<>();
        if (!table.forward.containsKey(emptyState)) {
          table.forward.put(emptyState, new HashMap<>());
        }
        if (!table.forward.get(emptyState).containsKey(component)) {
          table.forward.get(emptyState).put(component, new HashSet<>());
        }
        table.forward.get(emptyState).get(component).add(ruleId);

        HashSet<RuleId> endState = new HashSet<>();
        RuleId endRuleId = new RuleId();
        endRuleId.position = table.rules.get(0).components.size() - 1;
        endRuleId.rule = 0;
        endState.add(endRuleId);
        table.jump.put(endState, new HashMap<>());

        Token endToken = new Token();
        endToken.type = TokenType.SPEC;
        endToken.name = "end";
        table.jump.put(emptyState, new HashMap<>());
        table.jump.get(emptyState).put(endToken, 0);
      }
    }
  }
}
