package com.company;

import com.company.Config;
import com.company.TokenType;
import com.company.Token;

public class TokenParser {
  public static Token parseToken(String rawToken) {
    Token token = new Token();

    String first = String.valueOf(rawToken.charAt(0));
    String last = String.valueOf(rawToken.charAt(rawToken.length() - 1));

    if (first.equals(Config.getInstance().notermStart) && last.equals(Config.getInstance().notermEnd))
    {
      token.type = TokenType.NO_TERMINAL;
      token.name = rawToken.substring(1, rawToken.length() - 1);
      return token;
    }

    if (first.equals(Config.getInstance().specStart) && last.equals(Config.getInstance().specEnd))
    {
      token.type = TokenType.SPEC;
      token.name = rawToken.substring(1, rawToken.length() - 1);
      return token;
    }

    token.type = TokenType.TERMINAL;
    token.name = rawToken;
    return token;
  }
}
