package com.mtest.aaa;

import java.io.File;

/**
 * It is a main class
 */
public class App {
    public static void main(String[] args) {
        OrderCalculate oneCheck = new OrderCalculate();
        WorkWithFile workWithFile = new WorkWithFile();
        try {
            //It is verification of initial data
            if (!oneCheck.validate(args)) {
                System.out.println(oneCheck.validMessage); //1 log
            } else {
                workWithFile.loadPriceList(oneCheck.PRICE_LIST_RESOURCE_TXT, oneCheck.priceList);
                oneCheck.linesOfCheck = oneCheck.parse(args);
                oneCheck.fillingLines();
                System.out.println(oneCheck.result());
                File out=new File("check.txt");
                workWithFile.checkToFile(out.getPath(), oneCheck.result());


            }


        } catch (Exception e) {
            //log stack trace
            System.out.println(e.getMessage());
        }


    }
}
