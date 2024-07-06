package ru.clevertec.check;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckRunner {
//TODO solve the discount cards problem
    //TODO try to calculate the price with the discount
    public static void main(String[] args) throws IOException, MyException {
        try (FileWriter receipt = new FileWriter("result.csv")) {

            System.out.println("Date;Time");
            receipt.write("Date;Time");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss"); // date and time format
            LocalDateTime datetime = LocalDateTime.now();
            String dt = datetime.format(format);
            System.out.println(dt);
            receipt.write(dt);
        } // receipt file

        String application;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        application = in.readLine();

        Receipt r;
        r = new Receipt(application);
        System.out.println(r.balance + " " + r.discountCard);
        System.out.println(r.items);
        for(int i = 0; i < r.items; i++){
            System.out.println(r.lines[i]);
        }

    }
}
