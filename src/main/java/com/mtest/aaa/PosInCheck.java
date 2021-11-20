package com.mtest.aaa;

public class PosInCheck {


    private long id;
    private int amount;
    public String name;
    public float price;
    public float sumLine;
    public String bonusLine;
    public Integer discount;


    public long getId() {
        return id;
    }


    public int getAmount() {
        return amount;
    }

    public PosInCheck(long id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public float calculateLine(){
        float rez=0;

        this.sumLine=rez;
        return rez;
    }


}
