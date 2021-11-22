package com.mtest.aaa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCalculate {
    public String validMessage;
    public Integer cardNumber = null;
    public Boolean isCard = false;
    public Integer bonus;
    public List<PosInCheck> linesOfCheck = new ArrayList<PosInCheck>();
    public Map<Integer, Product> priceList = new HashMap<>();
    public float sumCheck = 0;
    public float discountCheck = 0;


    public boolean valid(String[] ops) {
        int n = ops.length;
        if (n == 0) {
            validMessage = "No data, please correct!";
            return false;
        }
        if (ops[n - 1].matches("card.+")) {
            try {
                isCard = true;
                String[] buff = new String[2];
                buff = ops[n - 1].split("-", 2);
                cardNumber = Integer.parseInt(buff[1]);
                n = n - 1;
                bonus = WorkWithFile.findCard(new File("d:/temp", "bonusCardBase.txt"), cardNumber);
                if (bonus == null) {
                    validMessage = "This card number is not base. You haven't bonus";
                    return true;
                }

            } catch (NumberFormatException e) {

                validMessage = "Not correct card number";
                e.printStackTrace();
                return false;
            }
        }
        if ((n == 0) & (isCard)) {
            validMessage = "Data is not correct, you give only card!";
            return false;

        }
        for (int i = 0; i < n; i++) {
            if (!ops[i].matches("[0-9]+-[0-9]+")) {
                validMessage = "Data is not correct! \nChange product on " + (i + 1) + " position";
                return false;
            }
        }
        validMessage = "Initial data matches necessary form";
        return true;
    }

    public int parse(String[] ops) {
        int n = ops.length;
        if (isCard) {
            n = n - 1;
        }
        String[] buff = new String[2];
        for (int i = 0; i < n; i++) {
            buff = ops[i].split("-", 2);
            linesOfCheck.add(new PosInCheck(Integer.parseInt(buff[0]), Integer.parseInt(buff[1])));

        }
        return linesOfCheck.size();
    }

    public float fillingLines() {
        int n = linesOfCheck.size();
        Product tempProduct = new Product();
        for (int i = 0; i < n; i++) {
            tempProduct = priceList.get(linesOfCheck.get(i).getId());
            if (tempProduct != null) {
                linesOfCheck.get(i).name = tempProduct.name;
                linesOfCheck.get(i).price = tempProduct.price;
                linesOfCheck.get(i).discountPromoLine = tempProduct.discPromo;
                if (bonus==null){
                sumCheck = sumCheck + linesOfCheck.get(i).calculateLine(0);}
                else  sumCheck = sumCheck + linesOfCheck.get(i).calculateLine(bonus);
                discountCheck = discountCheck + linesOfCheck.get(i).discount;
            } else
                linesOfCheck.get(i).bonusLine = "Product with code " + linesOfCheck.get(i).getId() + " is not in price list!";

        }
        return sumCheck;
    }

    public String result() {
        boolean flag=true;
        System.out.println(PosInCheck.headCheck());
        for (int i = 0; i < linesOfCheck.size(); i++) {
            if (linesOfCheck.get(i).name!=null) {
                System.out.printf("%-3d  %-10s  %-3d ", i + 1, linesOfCheck.get(i).name, linesOfCheck.get(i).getAmount());
                System.out.printf("%7.2f   %9.2f \n", linesOfCheck.get(i).price, linesOfCheck.get(i).sumLine);
                if (linesOfCheck.get(i).discount > 0.001) {
                    System.out.println(linesOfCheck.get(i).bonusLine);

                }
            } else {flag = false; System.out.println(linesOfCheck.get(i).bonusLine);}
        }
        if (sumCheck > 0.0001) {
            if (discountCheck > 0.001) {
                System.out.printf("\nСheck amount                %12.2f", sumCheck);
                System.out.printf("\nTotal discount              %12.2f", discountCheck);
                System.out.printf("\nTOTAL PAYABLE               %12.2f", (sumCheck - discountCheck));

            } else System.out.printf("\nTOTAL PAYABLE               %12.2f", (sumCheck - discountCheck));
            if (!flag){
                   System.out.printf("\nWARRING!!! not all position is calculate ", (sumCheck - discountCheck));}
        } else {
            System.out.println("Sorry we can't make you check.\n" +
                    "Any of you products is not in our price list ");
        }
        if ((isCard)&(bonus==null)){
            System.out.println("\nWARRING!!! Sorry we can't find you card.\n" +
                    "This card number is not base. You haven't bonus");
        }


        return "";
    }

}
