package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {

  public List<TokenIn> readFromFile(String path)
  {
    List<TokenIn> listResult = new ArrayList<>();

    try{
      FileInputStream fstream = new FileInputStream(path);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
      String strLine;
      while ((strLine = br.readLine()) != null){
        listResult.add(parseOneLine(strLine));
      }
    }catch (IOException e){
      System.out.println("Ошибка");
    }
    return listResult;
  }

  private TokenIn parseOneLine(String str)
  {
    List<String> components = new ArrayList<>(Arrays.asList(str.split(Config.getInstance().inputDelimiter)));

    TokenIn result = new TokenIn();
    Token t = TokenParser.parseToken(components.get(0));
    result.token = t;

    List<String> rawConponents =  new ArrayList<>(Arrays.asList(components.get(1).split(" ")));

    result.components = new ArrayList<>();
    for (String rawComponent: rawConponents) {
      result.components.add(TokenParser.parseToken(rawComponent));
    }

    return result;
  }
}
