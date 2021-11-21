package com.mtest.aaa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCalculate {
    public String message;
    public Integer cardNumber = null;
    public Boolean isCard = false;
    public Integer bonus;
    public List<PosInCheck> lines = new ArrayList<PosInCheck>();
    public Map<Integer, Product> priceList = new HashMap<>();
    public float sumCheck=0;
    public float discountCheck=0;

    public Integer findCard(int cardNumber) {
        File priceListFile = new File("d:/temp", "bonusCardBase.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(priceListFile));) {
            String line;
            String divider = "-";
            String[] buff = new String[2];
            int i = 1;
            while ((line = in.readLine()) != null) {
                try {
                    buff = line.split(divider, 2);
                    if (cardNumber == Integer.parseInt(buff[0])) {
                        return Integer.parseInt(buff[1]);
                    }


                } catch (RuntimeException e) {
                    System.out.println("Not correct data in bonusCardBase line number " + i);
                    e.printStackTrace();

                }
                i++;
            }

        } catch (IOException e) {
            System.out.println("File for load not found");
            e.printStackTrace();
            return null;
        }


        return null;
    }


    public boolean valid(String[] ops) {

        int n = ops.length;

        if (n == 0) {
            message = "No data, please correct!";
            return false;
        }
        if (ops[n - 1].matches("card.+")) {
            try {
                isCard = true;
                String[] buff = new String[2];
                buff = ops[n - 1].split("-", 2);
                cardNumber = Integer.parseInt(buff[1]);
                n = n - 1;
                bonus = findCard(cardNumber);
                if (bonus == null) {
                    message = "This card number is not base";
                    return false;
                }

            } catch (NumberFormatException e) {

                message = "Not correct card number";
                e.printStackTrace();
                return false;
            }
        }
        if ((n == 0) & (isCard)) {
            message = "Data is not correct, you give only card!";
            return false;

        }
        for (int i = 0; i < n; i++) {
            if (!ops[i].matches("[0-9]+-[0-9]+")) {
                message = "Data is not correct!";
                return false;
            }
        }
        return true;
    }

    public int loadPriceList() {
        File priceListFile = new File("d:/temp", "priceList.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(priceListFile));) {
            String line;
            String divider = ",";
            int i = 1;

            String[] buff = new String[4];

            while ((line = in.readLine()) != null) {
                try {
                    buff = line.split(divider, 4);
                    if (buff[3].equals("-")) {
                        priceList.put(Integer.parseInt(buff[0]), new Product(buff[1], Float.parseFloat(buff[2]), null));
                    } else {
                        priceList.put(Integer.parseInt(buff[0]), new Product(buff[1], Float.parseFloat(buff[2]), Integer.parseInt(buff[3])));
                    }


                } catch (RuntimeException e) {
                    System.out.println("Not correct data in priceList file Line number " + i);
                    e.printStackTrace();

                }
                i++;
            }
            return i - 1;


        } catch (IOException e) {
            System.out.println("File for load not found");
            e.printStackTrace();
            return 0;
        }


    }

    public int parse(String[] ops) {
        int n = ops.length;
        if (isCard) {
            n = n - 1;
        }
        String[] buff = new String[2];
        for (int i = 0; i < n; i++) {
            buff = ops[i].split("-", 2);
            lines.add(new PosInCheck(Integer.parseInt(buff[0]), Integer.parseInt(buff[1])));

        }
        return lines.size();
    }

    public int fillingLines() {
        int n = lines.size();
        Product tempProduct = new Product();
        if (bonus == null) {
            bonus = 0;
        }

        for (int i = 0; i < n; i++) {
            tempProduct = priceList.get(lines.get(i).getId());
            lines.get(i).name = tempProduct.name;
            lines.get(i).price = tempProduct.price;
            lines.get(i).discountPercent = tempProduct.discount;
            sumCheck=sumCheck+lines.get(i).calculateLine(bonus);
            discountCheck=discountCheck+lines.get(i).discount;

        }
        return 0;
    }

    public String result(){
        for (int i=0;i<lines.size();i++){
            System.out.print(lines.get(i).getAmount()+" "+lines.get(i).name+" ");
            System.out.println(lines.get(i).price+" "+lines.get(i).sumLine+" ");
            if (lines.get(i).discount>0.001){
                System.out.println(lines.get(i).bonusLine);

            }
        }
        return "";
    }

}
