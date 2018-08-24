package io.github.vkb24312.IPA;

import java.io.IOException;

import java.util.Scanner;


/**
 * This is meant to convert an abbreviated message to an IPA symbol.
 *
 * I.E., "v.b.p" returns b, the symbol for the Voiced Bilabial Plosive
 */
public class Main {
    public static void main(String... args) {
        System.out.println(fromConsole());
    }

    /**
     *
     * @return The corresponding IPA symbol
     */
    private static String fromConsole() {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.println(
            "Please enter the symbols in code form (see readme), separated by spaces");

        String[] input = s.next().split(" ");
        StringBuilder output = new StringBuilder("\u0000");

        for (String in : input) {
            try {
                output.append(IPAConverter.symbol(IPAConverter.toKey(in)));
            } catch (IllegalArgumentException e) {
                output.append("*");
            }
        }

        return output.toString();
    }
}
