package com.mtest.aaa;

/**
 * This class contain information about any product
 */

public class Product {
    String name;
    float price;
    Integer discPromo;

    public Product(String name, float price, Integer discount) {
        this.name = name;
        this.price = price;
        this.discPromo = discount;
    }

    public Product() {
        this(null, 0, null);
    }


}
