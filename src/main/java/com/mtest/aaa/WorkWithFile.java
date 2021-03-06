package com.mtest.aaa;

import java.io.*;
import java.util.Map;

/**
 * This class for work with files
 */
public class WorkWithFile {

    public String validFileMessage = "";
    public static void storeToLog(String message){
        try (BufferedWriter out=new BufferedWriter(new FileWriter("log.txt",true));){
            out.write(message+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void checkToFile(String path,String finalCheckToPrint){
        try (BufferedWriter out=new BufferedWriter(new FileWriter(path));){
            out.write(finalCheckToPrint);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Integer findCard(String path, int cardNumber) {


        try (   InputStream inStr = WorkWithFile.class.getResourceAsStream(path);
                BufferedReader in = new BufferedReader(new InputStreamReader(inStr));) {
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
                    WorkWithFile.storeToLog("Not correct data in bonusCardBase line number " + i);
                    e.printStackTrace();

                }
                i++;
            }

        } catch (Exception e) {
            WorkWithFile.storeToLog("File for load not found");
            System.out.println("File for load not found");
            e.printStackTrace();
            return null;
        }


        return null;
    }

    public boolean loadPriceList(String path, Map<Integer, Product> priceList) {

        boolean flag = true;
        try (InputStream inStr = WorkWithFile.class.getResourceAsStream(path);
             BufferedReader in = new BufferedReader(new InputStreamReader(inStr));) {
            String line;
            String divider = ",";
            int i = 1;
            String[] buff;

            while ((line = in.readLine()) != null) {
                try {
                    buff = line.split(divider, 4);
                    if (buff[3].equals("-")) {
                        priceList.put(Integer.parseInt(buff[0]), new Product(buff[1], Float.parseFloat(buff[2]), null));
                    } else {
                        priceList.put(Integer.parseInt(buff[0]), new Product(buff[1], Float.parseFloat(buff[2]), Integer.parseInt(buff[3])));
                    }


                } catch (RuntimeException e) {
                    flag = false;
                    this.validFileMessage = this.validFileMessage + "\nNot correct data in priceList file Line number " + i;
                    e.printStackTrace();

                }
                i++;
            }
            if (flag == true) {
                validFileMessage = "Data in priceListFile \"" + path + "\" is correct";
            }
            return flag;


        } catch (Exception e) {
            this.validFileMessage = "File \"" + path + "\" for load not found";
            e.printStackTrace();
            return false;
        }


    }


}
