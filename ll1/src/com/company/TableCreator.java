package com.company;

import com.company.token.Token;
import com.company.tokenType.TokenType;

import java.util.*;

public class TableCreator {
  HashMap<String, Integer> indexOfToken = new HashMap<>();
  HashMap<String, HashSet<String>> dirSetOfToken = new HashMap<>();
  List<String> termsInOrder = new ArrayList<>();
  List<Row> rows = new ArrayList<>();
  HashMap<String, List<TokenInput>> arrayOfInputByName = new HashMap<>();

  public List<Row> createTable(List<TokenInput> grammer) {
    createHashSet(grammer);
    termsInOrder.add("start");
    for (TokenInput line: grammer) {
      List<TokenInput> ti = new ArrayList<>();

      if (arrayOfInputByName.containsKey(line.token.name)) {
        termsInOrder.add(line.token.name);
        ti = arrayOfInputByName.get(line.token.name);
      }
      ti.add(line);
      arrayOfInputByName.put(line.token.name, ti);
    }

    Integer elementsCount = 0;

    for (TokenInput ti: grammer) {
      elementsCount += ti.components.size();
    }

    for (int i = 0; i < elementsCount; i++) {
      rows.add(new Row());
    }

    Integer endIndex = indexOfToken.get("start");
    endIndex++;
    rows.get(endIndex).isEnd = true;


    Integer index = 0;
    for (String key: termsInOrder) {
      Integer variantNumber = 0;
      for (TokenInput variant: arrayOfInputByName.get(key)) {
        Integer tokenInVariantCount = 0;

        if (variantNumber == 0 && variantNumber != arrayOfInputByName.get(key).size() - 1) {
          rows.get(index).errorTransition = index + variant.components.size();
        }

        for (Token t: variant.components) {
          if (t.type == TokenType.TERMINAL) {
            rows.get(index).directionSet.add(t.name);
            rows.get(index).shift = true;

            if (tokenInVariantCount != variant.components.size() - 1) {
              rows.get(index).goTo = index + 1;
            }

          } else if (t.type == TokenType.NO_TERMINAL) {
            rows.get(index).directionSet.addAll(dirSetOfToken.get(t.name));

            if (tokenInVariantCount != variant.components.size() - 1) {
              rows.get(index).stack = true;
            }

            rows.get(index).goTo = indexOfToken.get(t.name);
          } else {
            if (t.name.equals("end")) {
              rows.get(index).directionSet.add("end");
            } else {
              rows.get(index).directionSet.addAll(variant.dirSet);
            }
          }

          index++;
          tokenInVariantCount++;
        }

        variantNumber++;
      }
    }

    return rows;
  }

  private void createHashSet(List<TokenInput> grammer) {
    Integer counter = 0;
    for (TokenInput lineGrammer: grammer) {
      if (indexOfToken.containsKey(lineGrammer.token.name)) {
        HashSet<String> newDirSet = dirSetOfToken.get(lineGrammer.token.name);
        newDirSet.addAll(lineGrammer.dirSet);
        dirSetOfToken.put(lineGrammer.token.name, newDirSet);
      } else {
        indexOfToken.put(lineGrammer.token.name, counter);
        dirSetOfToken.put(lineGrammer.token.name, lineGrammer.dirSet);
      }

      for (Token t: lineGrammer.components) {
        counter++;
      }
    }
  }
}
