package org.jglrxavpok.pie.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Decimals {

    private final List<String> digitLines;
    private final int lineLength;

    public Decimals(String fileName) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+fileName)));
        try {
            int skipped = 0;
            while(skipped < 2) { // skips first digit and the point
                skipped += reader.skip(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stream<String> lines = reader.lines();
        digitLines = lines.collect(Collectors.toList());
        lineLength = digitLines.get(0).length();
    }

    public int getDecimal(int index) {
        int line = index / lineLength;
        int column = index % lineLength;
        return digitLines.get(line).charAt(column) - '0';
    }

    public int readNumber(int start, int length) {
        int value = 0;
        int powerOfTen = length-1;
        for (int i = start; i < length + start; i++) {
            value += getDecimal(i) * Math.pow(10, powerOfTen);
            powerOfTen--;
        }
        return value;
    }
}
