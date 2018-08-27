package io.github.vkb24312.IPA;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


/**
 * This is meant to convert an abbreviated message to an IPA symbol.
 *
 * E.G., "v.b.p" returns b, the symbol for the Voiced Bilabial Plosive
 */
public class Main {
    public static void main(String... args) {
        if(GraphicsEnvironment.isHeadless() || (args.length>0 && args[0].equals("console"))) {
            System.out.println(fromConsole());
        } else {
            JFrame frame = new MainFrame("IPA typer", new Dimension(400, 300));
            frame.setVisible(true);
        }
    }

    /**
     *
     * @return The corresponding IPA symbol
     */
    private static String fromConsole() {
        //region Get input
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.println(
            "Please enter the symbols in code form (see readme), separated by spaces");
        //endregion

        String[] input = s.next().split(" ");
        StringBuilder output = new StringBuilder("\u0000");

        for (String in : input) {
            try {
                char symbol = IPAConverter.symbol(IPAConverter.toKey(in));
                if (symbol == '\u0000') output.append("*");
                else output.append(symbol);
            } catch (IllegalArgumentException e) {
                output.append("*");
            }
        }

        return output.toString();
    }
}
