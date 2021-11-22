package com.mtest.aaa;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PosInCheck {


    private Integer id;
    private int amount;
    public String name;
    public float price;
    public float sumLine;
    public float discount = 0;
    public String bonusLine;
    public Integer discountPromoLine;


    public Integer getId() {
        return id;
    }


    public int getAmount() {
        return amount;
    }

    public PosInCheck(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public float calculateLine(int bonus) {
        float rez = 0;
        if (discountPromoLine != null) {
            if ((discountPromoLine != 0) & (amount > 4)) {
                rez = this.price * this.amount;
                this.discount = this.price * this.amount * discountPromoLine / 100;
                this.bonusLine = String.format("discount on promotional goods       %4.2f", this.discount);
                this.sumLine = rez;
                return rez;
            }
        }
        if ((bonus != 0)&(discountPromoLine!=null)) {
            this.discount = this.price * this.amount * bonus / 100;
            rez = this.price * this.amount;
            this.bonusLine = String.format("discount on you bonus card          %4.2f", this.discount);
        } else {
            rez = this.price * this.amount;
            this.discount = 0;
        }
        this.sumLine = rez;
        return rez;


    }
    public static String headCheck(){
        StringBuilder head = new StringBuilder();
        Shop shop = new Shop();
        head.append("\n           Your check for payment            \n");
        head.append(String.format("           %15s   N%-2d  \n",shop.name,shop.number));
        head.append(String.format("           %20s     \n\n",shop.adress));
        String date = new SimpleDateFormat("dd/MM/yyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        head.append(String.format("You cashier             Date: %8s\n",date));
        head.append(String.format("%-11s             Time:   %8s\n",shop.cashiers.get(0).name,time));
        head.append("N    Product   Q-ty    Price        Tot.");

        return head.toString();
    }

}
