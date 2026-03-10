package com.practice.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NameAndState {

  private String name;
  private String state;

  protected NameAndState() {}

  public NameAndState(String name, String state) {
    this.name = name;
    this.state = state;
  }
}
