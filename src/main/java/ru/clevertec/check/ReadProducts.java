package main.java.ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadProducts {
    String[] parts;
    Integer id;
    String description;
    Float price;
    Boolean wholesale;



    public ReadProducts() {
        id = 0;
        description = "";
        price = 0.0f;
        wholesale = false;
    }

    public String[] line_getter(int n) { // takes the line number and returns the array of instances separated by
        // semicolons
        if (n > 0 && n < 22) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Invalid line number: " + String.valueOf(n));
        }
        return null;
    }

    public ReadProducts(String[] parts) {
        id = Integer.parseInt(parts[0]);
        description = parts[1];
        price = Float.parseFloat(parts[2]);
        wholesale = Boolean.parseBoolean(parts[3]);
    }

    public ReadProducts(int n) { // the constructor that reads the n-th line and separates the instances
        id = Integer.parseInt(line_getter(n)[0]);
        description = line_getter(n)[1];
        price = Float.parseFloat(line_getter(n)[2]);
        wholesale = Boolean.parseBoolean(line_getter(n)[3]);
    }
}
