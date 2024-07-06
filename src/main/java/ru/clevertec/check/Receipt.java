package ru.clevertec.check;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Receipt {
    Integer items;
    Float balance;
    Integer discountCard;
    String[] lines;

public Receipt(String application) throws MyException {
    HashMap<Integer, Integer> products = new HashMap<>();
    try {
        products = productInput(application);
        items = products.size();
        lines = productLines(products);
    } catch (MyException e) {
        throw new RuntimeException(e);
    }
}

//TODO product input calculates number of items incorrectly (that sucks)
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
        String[] productL = new String[products.size() + 3];

        for (Integer i : products.keySet()) { // going through the products and composing the line
            productL[i] = products.get(i).toString() + ";";
            Integer id = i;
            ReadProducts rp = new ReadProducts(id);
            DecimalFormat numberFormat = new DecimalFormat("#.00"); // decimal format
            productL[i].concat(rp.description + ";" + numberFormat.format(rp.price).toString());
            //TODO check how that line works
        }
        return productL;
    }

}