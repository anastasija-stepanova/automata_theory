package com.company;

import com.company.token.Token;
import com.company.token.TokenParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Reader {

  public List<TokenInput> readFromFile(String path)
  {
    List<TokenInput> listResult = new ArrayList<>();

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

  private TokenInput parseOneLine(String str)
  {
    List<String> components = new ArrayList<>(Arrays.asList(str.split(Config.getInstance().inputDelimiter)));

    TokenInput result = new TokenInput();
    Token t = TokenParser.parseToken(components.get(0));
    result.token = t;

    List<String> rawConponents =  new ArrayList<>(Arrays.asList(components.get(1).split(" ")));

    result.components = new ArrayList<>();
    for (String rawComponent: rawConponents) {
      result.components.add(TokenParser.parseToken(rawComponent));
    }

    result.dirSet = new HashSet<>();
    List<String> rawDirSet =  new ArrayList<>(Arrays.asList(components.get(2).split(" ")));

    for (String rawDir: rawDirSet) {
      result.dirSet.add(rawDir);
    }

    return result;
  }
}
