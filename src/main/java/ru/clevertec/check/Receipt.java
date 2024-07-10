package ru.clevertec.check;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Receipt {
    Float balance;
    Integer discountCard;//discount card number
    String[] lines;//product info that goes into receipt
    Integer cardPercentage;//discount %
    Float totalPrice;
    Float totalDiscount;
    Float totalWDiscount;
    Boolean hasDiscount;
public Receipt(String application, FileWriter f) throws MyException, IOException {
    HashMap<Integer, Integer> products = new HashMap<>();
    try {
        products = productInput(application, f); //works
        lines = productLines(products, f);
    } catch (MyException e) {
        throw new MyException("ERROR\nINTERNAL SERVER ERROR", f);
    }
}
 public HashMap<Integer, Integer> productInput(String application, FileWriter f) throws MyException, IOException {//divides application string into words and creates a hashmap of products
        String[] parts = application.split(" ");
        int n = parts.length;
        if (n < 2) {
            throw new MyException("ERROR\nBAD REQUEST", f); //no products
        }
     ReadDC rdc = new ReadDC();

        try {
            balance = Float.parseFloat(parts[n - 1]);

            hasDiscount = !parts[n - 2].contains("-");
            if(hasDiscount) {
                discountCard = Integer.parseInt(parts[n - 2]); //discount card number
                cardPercentage = rdc.percentage(discountCard); //discount card percentage
            }
            else {
                discountCard = 0;
                cardPercentage = 0;
            }


        } catch (NumberFormatException e) {
            throw new MyException("ERROR\nBAD REQUEST", f);
        }
        HashMap<Integer, Integer> products = new HashMap<>();
        String[] pair;
        pair = parts[0].split("-");
        products.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));

        if (n > 3) {
            for (int i = 1; i < parts.length - 2; i++) {
                pair = parts[i].split("-");
                if(products.containsKey(Integer.parseInt(pair[0]))) {
                    products.put(Integer.parseInt(pair[0]), products.get(Integer.parseInt(pair[0]))+Integer.parseInt(pair[1]));
                }
                else
                    products.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
            }
        }
        return products;
    }

    public String[] productLines(HashMap<Integer, Integer> products, FileWriter f) throws MyException, IOException {
        String[] productL = new String[products.size()];
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat numberFormat = new DecimalFormat("#0.00"); // decimal format
        numberFormat.setDecimalFormatSymbols(dfs);
        Set<Integer> productSet = products.keySet();
        ArrayList<Integer> keysarray = new ArrayList<>(productSet); //product ids
        int j = keysarray.size(); //number of product types
        Float total;
        Float discount;
        Integer percentage;
        totalPrice = 0.0f;
        totalDiscount = 0.0f;
        totalWDiscount = 0.0f;

        for(int k = 0; k < j; k++) {
            //id of the product in question
            Integer id = keysarray.get(k);
            ReadProducts rp = new ReadProducts(id, f);
            total = products.get(id) * rp.price; //total (quantity * price)
            if(rp.wholesale && products.get(id) > 5)
                percentage = 10;
            else percentage = cardPercentage;
            discount = rp.price * percentage / 100;
            totalPrice += total;
            totalDiscount += discount;
            totalWDiscount += total;
            totalWDiscount -= discount;
            productL[k] = products.get(id).toString() + ";" + rp.description + ";" + numberFormat.format(rp.price) + "$;" + numberFormat.format(total).toString() + "$;" + numberFormat.format(discount).toString() + "$";//quantity, description, price, total and discount
        }
        return productL;
    }

}