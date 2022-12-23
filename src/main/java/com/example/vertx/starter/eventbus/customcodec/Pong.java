package com.example.vertx.starter.eventbus.customcodec;

public class Pong {
  private Integer id;

  public Pong() {
    //Default constructor for Jackson
  }

  public Pong(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
