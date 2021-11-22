package com.mtest.aaa;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    String name;
    String adress;
    int number;
    List<Cashier> cashiers;

    public Shop(String name, String adress, int number, List<Cashier> cashiers) {
        this.name = name;
        this.adress = adress;
        this.number = number;
        cashiers.add(new Cashier());
        this.cashiers = cashiers;
    }

    public Shop() {

         this("Shop SuperSHOP","Malorita, Lenina, 2",1,new ArrayList<>());
    }
}
