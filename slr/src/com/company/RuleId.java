package com.company;

import com.sun.javafx.css.Rule;

public class RuleId {
  public Integer rule;
  public Integer position;

  @Override
  public int hashCode() {
    // Рассчитываю на то что будет менее 1000 правил
    return rule.hashCode() * 1000 + position.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;

    if (!(obj instanceof RuleId)) return false;
    RuleId r = (RuleId)obj;

    return r.rule.equals(rule) && r.position.equals(position);
  }
}
