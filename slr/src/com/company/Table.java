package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Table {
  public List<TokenIn> rules;
  public HashMap<HashSet<RuleId>, HashMap<Token, HashSet<RuleId>>> forward;
  public HashMap<HashSet<RuleId>, HashMap<Token, Integer>> jump;
}
