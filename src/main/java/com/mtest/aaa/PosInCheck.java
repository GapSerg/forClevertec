package com.mtest.aaa;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * It is class with information and function for one position of check
 */
public class PosInCheck {

    public String name;
    public float price;
    public float sumLine;
    public float discount = 0;
    public String bonusLine; //This line contains information on the size of the discount
                            // or information on the lack of goods in the database
    public Integer discountPromoLine; //This line contains information availability of a promotion for this product
                                      //If this=0 then no promotion
                                      //if this = null then any discounts cannot be applied to the product

    private Integer id;
    private int amount;


    public PosInCheck(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * This method make head of check with information about shop, cashier, date and time
     *
     * @return
     */
    public static String headCheck() {
        StringBuilder head = new StringBuilder();
        Shop shop = new Shop();
        head.append("\n           Your check for payment            \n");
        head.append(String.format("           %15s   N%-2d  \n", shop.name, shop.number));
        head.append(String.format("           %20s     \n\n", shop.address));
        String date = new SimpleDateFormat("dd/MM/yyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        head.append(String.format("You cashier             Date: %8s\n", date));
        head.append(String.format("%-11s             Time:   %8s\n", shop.cashiers.get(0).getName(), time));
        head.append("N    Product   Q-ty    Price        Tot.\n");

        return head.toString();
    }

    public Integer getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    /**
     *
     * This method get @param bonus and  @return sum for one line or one position of check
     */
    public float calculateLine(int bonus) {
        float rez = 0;//result of calculation one check line
        if (discountPromoLine != null) {
            if ((discountPromoLine != 0) & (amount > 4)) { //implementation of a check for a discount for this product
                                                          //according to the condition that there are 5 or more such goods
                rez = this.price * this.amount;
                this.discount = this.price * this.amount * discountPromoLine / 100;
                this.bonusLine = String.format("discount on promotional goods       %4.2f\n", this.discount);
                this.sumLine = rez;
                return rez;
            }
        }
        if ((bonus != 0) & (discountPromoLine != null)) {
            this.discount = this.price * this.amount * bonus / 100;
            rez = this.price * this.amount;
            this.bonusLine = String.format("discount on you bonus card          %4.2f\n", this.discount);
        } else {
            rez = this.price * this.amount;
            this.discount = 0;
        }
        this.sumLine = rez;
        return rez;


    }

}
