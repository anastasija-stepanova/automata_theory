package com.company;

import com.company.TokenType;

public class Token {
  public String name;
  public TokenType type;

  @Override
  public int hashCode() {
    // Если прога работает, но неправильно, то попробуй пофиксить баг здесь. А если всё ок, то и фиг с этим багом
    return name.hashCode() + type.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;

    if (!(obj instanceof Token)) return false;
    Token t = (Token)obj;

    return t.name.equals(name) && t.type.equals(type);
  }
}
