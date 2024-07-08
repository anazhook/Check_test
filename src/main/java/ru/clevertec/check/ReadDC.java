package ru.clevertec.check;

import java.util.HashMap; // import the HashMap class
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDC {
    HashMap<Integer, Integer> disc_percentage;

    public ReadDC() throws IOException {
        disc_percentage = this.number_reader();
    }

    public HashMap<Integer, Integer> number_reader() throws IOException { // a hashmap of discount cards
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/discountCards.csv"));
        String line = reader.readLine();
        HashMap<Integer, Integer> disc_cards = new HashMap<Integer, Integer>();
        String[] parts;
        line = reader.readLine();
        while (line != null) {
            parts = line.split(";");
            disc_cards.put(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            line = reader.readLine();
        }
        return disc_cards;
    }

    public int percentage(int n) throws IOException {// looks up the percentage for specific card
        HashMap<Integer, Integer> disc_cards = this.number_reader();
        int percentage;
        if (disc_cards.get(n) != null) {
            percentage = disc_percentage.get(n);
            return percentage;
        } else
            return 2;
    }

}