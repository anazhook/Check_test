package main.java.ru.clevertec.check;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CheckRunner {

    public static void main(String[] args) throws IOException {
        FileWriter receipt = new FileWriter("result.csv"); // receipt file

        System.out.println("Date;Time");
        receipt.write("Date;Time");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy;HH:mm:ss"); // date and time format
        LocalDateTime datetime = LocalDateTime.now();
        String dt = datetime.format(format);
        System.out.println(dt);
        receipt.write(dt);

        String application;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        application = in.readLine();

        Receipt r = new Receipt(application);
        System.out.println(r.balance + " " + r.discountCard + " ");

    }
}
