package ru.clevertec.check;

import java.io.*;

public class ReadProducts {
    //String[] parts;
    Integer id;
    String description;
    Float price;
    Boolean wholesale;
    Integer quantity;

    public ReadProducts() {
        id = 0;
        description = "";
        price = 0.0f;
        wholesale = false;
        quantity = 0;
    }

    public String[] line_getter(int n, FileWriter fr) throws IOException, MyException { // takes the line number and returns the array of instances separated by
        // semicolons
        int number_of_products = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.csv"))) {
            String line1 = br.readLine();
            while ((line1 = br.readLine()) != null) {
                number_of_products++;
            }
        }
        if (n > 0 && n < number_of_products) {
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.csv"))) {
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber == n + 1) {
                        String[] part = line.split(";");
                        if (part.length == 5) {
                            try {
                                return part;
                            } catch (NumberFormatException e) {
                                System.err.println("Error parsing values from the line.");
                            }
                        } else {
                            System.err.println("Invalid line format: " + line);
                        }
                    }
                }
            }
        } else {
            throw new MyException("ERROR\nBAD REQUEST", fr);
        }
        return null;
    }

    public ReadProducts(String[] parts) {
        id = Integer.parseInt(parts[0]);
        description = parts[1];
        price = Float.parseFloat(parts[2]);
        wholesale = Boolean.parseBoolean(parts[3]);
    }

    public ReadProducts(int n, FileWriter f) throws MyException, IOException { // the constructor that reads the n-th line and separates the instances
        id = Integer.parseInt(line_getter(n, f)[0]);
        description = line_getter(n, f)[1];
        price = Float.parseFloat(line_getter(n, f)[2]);
        wholesale = Boolean.parseBoolean(line_getter(n, f)[3]);
    }
}
