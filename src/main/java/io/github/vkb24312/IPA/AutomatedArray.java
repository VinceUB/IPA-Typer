package io.github.vkb24312.IPA;

import org.jsoup.HttpStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AutomatedArray extends IPAConverter /*This cleans the code slightly up*/{
    public static void main(String... args) throws IOException{
        String[][][] IPATable = new String[placeFull.length][mannerFull.length][2 /*Voiced and voiceless*/];
        StringBuilder output = new StringBuilder("{");

        for (int i = 0; i < IPATable.length; i++) {
            output.append("{");
            for (int j = 0; j < IPATable[i].length; j++) {
                output.append("{");
                for (int k = 0; k < IPATable[i][j].length; k++) {
                    output.append("\"");
                    boolean voicing = k == 0;
                    try {
                        IPATable[i][j][k] = symbol(voicing(voicing) + " " + placeFull[i] + " " + mannerFull[j]);
                        System.out.print(IPATable[i][j][k]);
                    } catch (HttpStatusException e){
                        if(e.getStatusCode()==404) {
                            System.out.print("*");
                            IPATable[i][j][k] = null;
                        } else e.printStackTrace();
                    }
                    output.append(IPATable[i][j][k]);
                    if(k == 0) output.append("\", ");
                    else output.append("\"");
                }
                output.append("}, ");
            }
            output.append("}, \n");
            System.out.print("\n");
        }
        output.append("}");

        File outputFile = new File("output");
        FileWriter fw = new FileWriter(outputFile);
        fw.write(output.toString());
        fw.close();

        System.out.println("DONE!");
    }
}
