package ru.clevertec.check;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Receipt {
    Float balance;
    Integer discountCard;
    String[] lines;

public Receipt(String application) throws MyException {
    HashMap<Integer, Integer> products = new HashMap<>();
    try {
        products = productInput(application); //works
        lines = productLines(products);//doesn't work
    } catch (MyException e) {
        throw new RuntimeException(e);
    }
}
 public HashMap<Integer, Integer> productInput(String application) throws MyException {//devides application string into words and creates a hashmap of products
        String[] parts = application.split(" ");
        int n = parts.length;
        if (n < 3) {
            throw new MyException("BAD REQUEST"); //no products
        }

        balance = Float.parseFloat(parts[n - 1]);
        discountCard = Integer.parseInt(parts[n - 2]);

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

    public String[] productLines(HashMap<Integer, Integer> products) throws MyException {
        String[] productL = new String[products.size()];
            DecimalFormat numberFormat = new DecimalFormat("#.00"); // decimal format
        Set<Integer> productSet = products.keySet();
        Integer i = productSet.size(); //number of item request (NOT number of item types)
        ArrayList<Integer> keysarray = new ArrayList<Integer>(productSet); //product ids
        Integer id;
        Integer j = keysarray.size(); //number of product types
        for(Integer k = 0; k < j; k++) {
            id = keysarray.get(k); //id of the product in question
            ReadProducts rp = new ReadProducts(id);
            productL[k] = products.get(id).toString() + ";" + rp.description + ";" + numberFormat.format(rp.price).toString();//quantity, description and price
        }
        return productL;
    }

}