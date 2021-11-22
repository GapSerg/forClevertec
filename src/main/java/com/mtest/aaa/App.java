package com.mtest.aaa;

import java.io.File;

public class App {
    public static void main(String[] args) {
        OrderCalculate oneCheck = new OrderCalculate();
        WorkWithFile workWithFile=new WorkWithFile();
        try {
            if (!oneCheck.valid(args)){
                System.out.println(oneCheck.validMessage);
            } else {
                workWithFile.loadPriceList("/priceList.txt",oneCheck.priceList);
                oneCheck.parse(args);
                oneCheck.fillingLines();
                oneCheck.result();


            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
}
