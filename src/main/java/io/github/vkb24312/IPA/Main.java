package io.github.vkb24312.IPA;

import org.jsoup.HttpStatusException;

import java.io.IOException;

import java.util.Scanner;


/**
 * This is meant to convert an abbreviated message to an IPA symbol.
 *
 * I.E., "v.b.p" returns b, the symbol for the Voiced Bilabial Plosive
 */
public class Main {
    public static void main(String... args) throws IOException {
        if (args.length > 0) {
            StringBuilder output = new StringBuilder("\u0000");

            for (String in : args) {
                try {
                    output.append(IPAConverter.symbol(IPAConverter.toKey(in)));
                }/* catch (HttpStatusException e) {
                    System.out.println("Http Status Error " +
                        e.getStatusCode() + ", from website" + e.getUrl());

                    if (e.getStatusCode() == 404) {
                        System.out.println(IPAConverter.extend(in) +
                            " probably isn't a real symbol");
                    }

                    output.append("*");
                } */catch (IllegalArgumentException e) {
                    output.append("*");
                }
            }

            System.out.println(output);
        } else {
            System.out.println(fromConsole());
        }
    }

    /**
     *
     * @return The corresponding IPA symbol
     */
    private static String fromConsole() throws IOException {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.println(
            "Please enter the symbols in code form (see readme), separated by spaces");

        String[] input = s.next().split(" ");
        StringBuilder output = new StringBuilder("\u0000");

        for (String in : input) {
            try {
                output.append(IPAConverter.symbol(IPAConverter.toKey(in)));
            } /*catch (HttpStatusException e) {
                System.out.println("Http Status Error " + e.getStatusCode() +
                    ", from website" + e.getUrl());

                if (e.getStatusCode() == 404) {
                    System.out.println(IPAConverter.extend(in) +
                        " probably isn't a real symbol");
                }

                output.append("*");
            } */catch (IllegalArgumentException e) {
                output.append("*");
            }
        }

        return output.toString();
    }
}
