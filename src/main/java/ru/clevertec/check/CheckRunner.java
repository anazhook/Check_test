package ru.clevertec.check;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CheckRunner {
    //TODO try to calculate the price with the discount
    //TODO handle the not enough money situation
    //TODO INTERNAL SERVER ERROR FOR ALL EXCEPTIONS??
    //TODO commas instead of dots as float point in all prices
    //TODO all prices include dollar signs
    //TODO if an error occurs the only thing that shows in file is the error description
    public static void main(String[] args) throws IOException, MyException {
        try (FileWriter receipt = new FileWriter("result.csv")) {// receipt file

            System.out.println("Date;Time");
            receipt.write("Date;Time");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss"); // date and time format
            LocalDateTime datetime = LocalDateTime.now();
            String dt = datetime.format(format);
            System.out.println(dt);
            receipt.write(dt);

            String application;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            application = in.readLine();

            Receipt r;
            r = new Receipt(application);
            for (int i = 0; i < r.lines.length; i++) {
                System.out.println(r.lines[i]);
                receipt.write(r.lines[i] + "\n");
            }

            System.out.println("DISCOUNT CARD;DISCOUNT PERCENTAGE");
            System.out.println(r.discountCard + ";" + r.cardPercentage);

            receipt.write("DISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            receipt.write(r.discountCard + ";" + r.cardPercentage + "\n");

            System.out.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
            System.out.println(r.totalPrice + ";" + r.totalDiscount + ";" + r.totalWDiscount);

            receipt.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            receipt.write(r.totalPrice + ";" + r.totalDiscount + ";" + r.totalWDiscount + "\n");
        }
    }
}