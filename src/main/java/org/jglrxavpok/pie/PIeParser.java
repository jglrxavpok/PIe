package org.jglrxavpok.pie;

import org.jglrxavpok.pie.io.Decimals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PIeParser {

    private static final int UNKNOWN_CHAR = -2;
    private static final int END_OF_FILE = -1;
    private final Map<Integer, Decimals> decimals;

    public PIeParser() {
        this("e", "pi");
    }

    public PIeParser(String... numbers) {
        decimals = new HashMap<>();
        for(String name : numbers) {
            String fileName = name+"_digits.txt";
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+fileName)));

            if(name.equals("pi"))
                name = "π";
            decimals.put(name.codePointAt(0), new Decimals(fileName));
        }
    }

    public int[] parse(String code) {
        List<Integer> values = new LinkedList<>();

        AtomicInteger currentIndex = new AtomicInteger();
        int nextNumber;
        do {
            nextNumber = readNextNumber(code, currentIndex);
            if(nextNumber >= 0) {
                values.add(nextNumber);
            }
        } while(nextNumber != END_OF_FILE); // while not at end of file

        int[] result = new int[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    private int readNextNumber(String code, AtomicInteger currentIndex) {
        int index = currentIndex.get();
        if(index >= code.length())
            return END_OF_FILE; // End of file
        int offset = 0;
        int number = 0;
        int firstCharacter = code.codePointAt(index);
        if(decimals.containsKey(firstCharacter)) {
            int startIndex = readRawNumber(code, index+1);
            offset = String.valueOf(startIndex).length(); // increments by the length of the read number, written with decimal digits
            int length = 1;
            if((index+offset+1) < code.length() && code.charAt(index+offset+1) == ',') {
                length = readRawNumber(code, index+offset+2);
                offset += String.valueOf(length).length(); // increments by the length of the read number, written with decimal digits
            }

            number = decimals.get(firstCharacter).readNumber(startIndex, length);
        } else {
            // unknown character, ignore
            offset = 1;
            number = UNKNOWN_CHAR;
        }

        currentIndex.addAndGet(offset);
        return number;
    }

    private int readRawNumber(String code, int index) {
        int value = 0;
        while(index < code.length() && Character.isDigit(code.charAt(index))) {
            value *= 10;
            value += code.charAt(index) - '0';
            index++;
        }
        return value;
    }
}
