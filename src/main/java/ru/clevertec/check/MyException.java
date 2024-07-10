package ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;

class MyException extends Exception {
    public MyException(String s)
    {
        super(s);
    }

    public MyException(String s, FileWriter f) throws IOException {
        super(s);
        f.write(s);
    }
}