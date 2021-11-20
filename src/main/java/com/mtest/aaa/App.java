package com.mtest.aaa;

public class App {
    public static void main(String[] args) {
        OrderCalculate oneCheck = new OrderCalculate();
        try {
            if (!oneCheck.valid(args)){
                System.out.println(oneCheck.message);
            } else {
                oneCheck.loadPriceList();
                oneCheck.parse(args);
                oneCheck.fillingLines();

            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
}
