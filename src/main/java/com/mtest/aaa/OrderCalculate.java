package com.mtest.aaa;
import java.util.*;

/**
 * This class contain methods with main logic and information which change from inital to answer
 */

public class OrderCalculate {
    public static final String BONUS_CARD_BASE_RESOURCE_TXT = "/bonusCardBase.txt";
    public static final String PRICE_LIST_RESOURCE_TXT="/priceList.txt";
    public String validMessage;
    public Integer cardNumber = null;
    public Boolean isCard = false;
    public Integer bonus;
    public List<PosInCheck> linesOfCheck = new ArrayList<PosInCheck>();
    public Map<Integer, Product> priceList = new HashMap<>();
    public float sumCheck = 0;
    public float discountCheck = 0;


    /**
     * Method validates input
     * @param ops bill params validation (command line args)
     * @return true if valid
     * also in this method we take information about discount card
     */
    public boolean validate(String[] ops) {
        int nPosionInChack = ops.length;
        StringBuilder initString=new StringBuilder();
        if (nPosionInChack == 0) {
            validMessage = "No data, please correct!";

            return false;
        }

        if (ops[nPosionInChack - 1].matches("card.+")) {
            try {
                isCard = true;
                String[] buff = new String[2];
                buff = ops[nPosionInChack - 1].split("-", 2);
                cardNumber = Integer.parseInt(buff[1]);
                nPosionInChack = nPosionInChack - 1;
                bonus = WorkWithFile.findCard(BONUS_CARD_BASE_RESOURCE_TXT, cardNumber);
                if (bonus == null) {
                    validMessage = "This card number is not base. You haven't bonus"; //loh

                }

            } catch (NumberFormatException e) {

                validMessage = "Not correct card number";
                e.printStackTrace();
                return false;
            }
        }
        if ((nPosionInChack == 0) & (isCard)) {
            validMessage = "Data is not correct, you give only card!";
            return false;

        }
        for (int i = 0; i < nPosionInChack; i++) {
            initString.append(ops[i]+" ");
            if (!ops[i].matches("[0-9]+-[0-9]+")) {

                validMessage = "Data is not correct! \nChange product on " + (i + 1) + " position";
                return false;
            }
        }
        if (isCard){initString.append(ops[nPosionInChack]);};
        WorkWithFile.storeToLog(initString.toString());
        validMessage = "Initial data matches necessary form";
        return true;
    }

    /**
     *
     * @param ops (command line args) parse to id and amount
     * @return List with start information about product which wil in check
     */
    public List<PosInCheck> parse(String[] ops) {
        int n = ops.length;
        if (isCard) {
            n--;
        }
        List<PosInCheck> linesOfCheck = new ArrayList<>();
        String[] buff;
        for (int i = 0; i < n; i++) {
            buff = ops[i].split("-", 2);
            linesOfCheck.add(new PosInCheck(Integer.parseInt(buff[0]), Integer.parseInt(buff[1])));

        }
        return linesOfCheck;
    }

    /**
     * This method calculate lines in check and create total sum for check
     * @return sum for check, but if id for any product is not in base then
     * it's information print in check.
     */
    public float fillingLines() {

        Product tempProduct = new Product();
        Iterator<PosInCheck> iter =linesOfCheck.iterator();
        int i=0;
        while (iter.hasNext()){
            tempProduct = priceList.get(iter.next().getId());
            if (tempProduct != null) {
                linesOfCheck.get(i).name = tempProduct.name;
                linesOfCheck.get(i).price = tempProduct.price;
                linesOfCheck.get(i).discountPromoLine = tempProduct.discPromo;
                if (bonus == null) {
                    sumCheck = sumCheck + linesOfCheck.get(i).calculateLine(0);
                } else {
                    sumCheck = sumCheck + linesOfCheck.get(i).calculateLine(bonus);
                }
                discountCheck = discountCheck + linesOfCheck.get(i).discount;
            } else
                linesOfCheck.get(i).bonusLine = "Product with code " + linesOfCheck.get(i).getId() + " is not in price list!\n";
         i++;
        }
        return sumCheck;
    }

    /**
     *
     * @return result check
     */
    public String result() {
        boolean flagProductInPriceList = true;

        StringBuilder check = new StringBuilder(PosInCheck.headCheck());

        for (int i = 0; i < linesOfCheck.size(); i++) { //use foreach
            if (linesOfCheck.get(i).name != null) {
                check.append(String.format("%-3d  %-10s  %-3d %7.2f   %9.2f \n",
                        i + 1, linesOfCheck.get(i).name, linesOfCheck.get(i).getAmount(),
                        linesOfCheck.get(i).price, linesOfCheck.get(i).sumLine));
                if (linesOfCheck.get(i).discount > 0.001) {
                    check.append(linesOfCheck.get(i).bonusLine);

                }
            } else {
                flagProductInPriceList = false;
                check.append(linesOfCheck.get(i).bonusLine);
            }
        }
        if (sumCheck > 0.0001) {
            if (discountCheck > 0.001) {
                check.append(String.format("\nСheck amount                %12.2f", sumCheck));
                check.append(String.format("\nTotal discount              %12.2f", discountCheck));
                check.append(String.format("\nTOTAL PAYABLE               %12.2f", (sumCheck - discountCheck)));

            } else check.append(String.format("\nTOTAL PAYABLE               %12.2f", (sumCheck - discountCheck)));
            if (!flagProductInPriceList) {
                check.append(String.format("\nWARRING!!! not all position is calculate ", (sumCheck - discountCheck)));
            }
        } else {
            check.append("Sorry we can't make you check.\n" +
                    "Any of you products is not in our price list ");
        }
        if ((isCard) & (bonus == null)) {
            check.append("\nWARRING!!! Sorry we can't find you card.\n" +
                    "This card number is not base. You haven't bonus");
        }


        return check.toString();
    }

}
