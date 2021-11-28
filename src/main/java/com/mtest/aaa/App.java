package com.mtest.aaa;

import java.io.File;

/**
 * It is a main class
 */
public class App {
    public static void main(String[] args) {
        OrderCalculate oneCheck = new OrderCalculate();
        WorkWithFile workWithFile = new WorkWithFile();
        workWithFile.storeToLog("\n\n------------next Test ------------------");


        try {
            //It is verification of initial data
            if (!oneCheck.validate(args)) {
                System.out.println(oneCheck.validMessage);
                workWithFile.storeToLog(oneCheck.validMessage);
            } else {
                workWithFile.storeToLog(oneCheck.validMessage);
                workWithFile.loadPriceList(oneCheck.PRICE_LIST_RESOURCE_TXT, oneCheck.priceList);
                WorkWithFile.storeToLog(workWithFile.validFileMessage);
                oneCheck.linesOfCheck = oneCheck.parse(args);
                oneCheck.fillingLines();
                System.out.println(oneCheck.result());
                File out=new File("check.txt");
                workWithFile.checkToFile(out.getPath(), oneCheck.result());


            }


        } catch (Exception e) {
            WorkWithFile.storeToLog(e.getMessage());
            System.out.println(e.getMessage());
        }


    }
}
