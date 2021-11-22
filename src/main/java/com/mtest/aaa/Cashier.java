package com.mtest.aaa;

public class Cashier {
    int id;
    String name;

    public Cashier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Cashier() {
      this(123,"Irina");
    }
}
