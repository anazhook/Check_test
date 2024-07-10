package ru.clevertec.check;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CheckRunner {
    public static void main(String[] args) throws IOException, MyException {

        try (FileWriter receipt = new FileWriter("result.csv")) {// receipt file
            String application; //application line
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            application = in.readLine();

            Receipt r;
            r = new Receipt(application, receipt);

            if (r.balance < r.totalWDiscount) {
                throw new MyException("ERROR\nNOT ENOUGH MONEY", receipt);
            }

            System.out.println("Date;Time");
            receipt.write("Date;Time\n");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss"); // date and time format
            LocalDateTime datetime = LocalDateTime.now();
            String dt = datetime.format(format);
            System.out.println(dt);
            receipt.write(dt + "\n");

            System.out.println("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL");
            receipt.write("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

            for (int i = 0; i < r.lines.length; i++) {
                System.out.println(r.lines[i]);
                receipt.write(r.lines[i] + "\n");
            }
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat numberFormat = new DecimalFormat("#0.00"); // decimal format
            numberFormat.setDecimalFormatSymbols(dfs);
            if(r.hasDiscount) {
                System.out.println("DISCOUNT CARD;DISCOUNT PERCENTAGE");
                System.out.println(r.discountCard + ";" + r.cardPercentage + "%");

                receipt.write("DISCOUNT CARD;DISCOUNT PERCENTAGE\n");
                receipt.write(r.discountCard + ";" + r.cardPercentage + "%\n");
            }
            System.out.println("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
            System.out.println(numberFormat.format(r.totalPrice) + "$;" + numberFormat.format(r.totalDiscount) + "$;" + numberFormat.format(r.totalWDiscount) + "$");

            receipt.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            receipt.write(numberFormat.format(r.totalPrice) + "$;" + numberFormat.format(r.totalDiscount) + "$;" + numberFormat.format(r.totalWDiscount) + "$");
        }
        catch (MyException e) {
            File result = new File("result.csv");
            result.delete();
            try (FileWriter receipt = new FileWriter("result.csv")) {// receipt file
                receipt.write("ERROR\nINTERNAL SERVER ERROR");
            }
                System.out.println("ERROR\nINTERNAL SERVER ERROR");
        }
        }

    }