package com.mtest.aaa;

/**
 * This class for make head in result check.
 * In general, it is possible to create a separate file or database
 * for storing information about cashiers
 */
public class Cashier {
    private  int id;
    private String name;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Cashier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Cashier() {
        this(123, "Irina");
    }
}
