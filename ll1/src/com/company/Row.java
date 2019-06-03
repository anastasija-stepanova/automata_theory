package com.company;

import java.util.HashSet;

public class Row {
  public Boolean isEnd = false;
  public HashSet<String> directionSet = new HashSet<>();
  public Integer errorTransition = -1;
  public Boolean shift = false;
  public Boolean stack = false;
  public Integer goTo = -1;
}
