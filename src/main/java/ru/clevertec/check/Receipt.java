package main.java.ru.clevertec.check;

import java.util.HashMap;

public class Receipt {

    Float balance;
    int discountCard;
    HashMap<Integer, Integer> products;


    public Receipt(String application) {
        String[] parts = new StringBuilder(application).reverse().toString().split(" ");

        balance = Float.parseFloat(parts[0]);
        discountCard = Integer.parseInt(parts[1]);

        for (int i = 2; i < parts.length; i++) {
            String[] pair = parts[i].split("-");
            if(products.containsKey(Integer.parseInt(pair[0]))) {
                products.put(Integer.parseInt(pair[0]), products.get(Integer.parseInt(pair[0]))+Integer.parseInt(pair[1]));
            }
            else
                products.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
        }
    }
}