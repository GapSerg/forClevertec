package com.mtest.aaa;

import java.util.List;

public class Shop {
    String name;
    String address;
    int number;
    List<Cashier> cashiers;

    public Shop(String name, String address, int number, List<Cashier> cashiers) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.cashiers = cashiers;
    }

    public Shop() {
        this("Shop SuperSHOP", "Malorita, Lenina, 2", 1, List.of(new Cashier()));
    }
}
