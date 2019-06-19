package com.company;

public class Config {
  private static Config ourInstance = new Config();

  public static Config getInstance() {
    return ourInstance;
  }

  private Config() {
  }

  public String inputDelimiter = ":";
  public String notermStart = "<";
  public String notermEnd = ">";
  public String specStart = "[";
  public String specEnd = "]";
}
