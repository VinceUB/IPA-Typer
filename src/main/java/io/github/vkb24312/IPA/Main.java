package io.github.vkb24312.IPA;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;


/**
 * This is meant to convert an abbreviated message to an IPA symbol.
 *
 * E.G., "v.b.p" returns b, the symbol for the Voiced Bilabial Plosive
 */
public class Main {
    public static void main(String... args) throws IOException {
        //region AppData setup
        File AppData;
        if(System.getProperty("os.name").toLowerCase().startsWith("win")) AppData = new File(System.getenv("appdata") + "/IPATyper");
        else AppData = new File(System.getProperty("user.home") + "/IPATyper");
        
        if(!AppData.exists() || !AppData.isDirectory()) AppData.mkdirs();
        System.out.println("Appdata is " + AppData.getAbsolutePath());
        
        //region GUI properties setup
        File guiProperties = new File(AppData, "gui.properties");
        if(!guiProperties.exists()) {
            InputStream guiPropertyInputStream = Main.class.getClassLoader().getResourceAsStream("gui.properties");
            FileOutputStream guiPropertyFileOutputStream = new FileOutputStream(guiProperties);
            
            guiPropertyInputStream.transferTo(guiPropertyFileOutputStream);
        }
        
        //region Check if properties are corrupted, and fix them if they are
        Properties defaults = new Properties();
        defaults.load(Main.class.getClassLoader().getResourceAsStream("gui.properties"));

        Properties userProperties = new Properties();
        userProperties.load(new FileInputStream(guiProperties));
        
        defaults.forEach((k, v) -> {
            if(userProperties.getProperty((String) k)==null){
                userProperties.setProperty((String) k, (String) v);
            }
        });
        userProperties.store(new FileWriter(guiProperties), null);
        
        System.out.println("GUI property file is " + guiProperties.getAbsolutePath());
        //endregion
        //endregion
        
        //region Fonts setup
        File fontsFolder = new File(AppData, "fonts");
        if(!fontsFolder.exists() || !fontsFolder.isDirectory()) fontsFolder.mkdirs();
        File chosenFont = new File(fontsFolder, (String) userProperties.get("font"));
        File defaultFont = new File(fontsFolder, (String) defaults.get("font"));
        
        if(!defaultFont.exists()) {
            FileOutputStream fontFileStream = new FileOutputStream(defaultFont);
            Main.class.getClassLoader().getResourceAsStream("fonts/" + defaults.get("font")).transferTo(fontFileStream);
        }
        
        if(!chosenFont.exists()) {
            Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
            
            boolean fontExist = false;
            for (Font font : allFonts) {
                if(font.getName().equals(userProperties.get("font"))) fontExist = true;
            }
            
            if(!fontExist) {
                userProperties.setProperty("font", defaults.getProperty("font"));
                userProperties.store(new FileWriter(guiProperties), "File \"" + chosenFont + "\" doesn't exist, so we changed your chosen font to the default");
            }
        }
        
        //endregion
        
        //region README setup
        File ReadmesDir = new File(AppData, "READMEs");
        if(!ReadmesDir.exists() || !ReadmesDir.isDirectory()){
            ReadmesDir.mkdirs();
        }
        File mdReadme = new File(AppData, "READMEs/README.md");
        File htmlReadme = new File(AppData, "READMEs/README.html");

        if(!mdReadme.exists()){
            InputStream readmeInputStream = Main.class.getClassLoader().getResourceAsStream("READMEs/README.md");
            FileOutputStream readmeOutputStream = new FileOutputStream(mdReadme);

            readmeInputStream.transferTo(readmeOutputStream);
        }

        if(!htmlReadme.exists()){
            InputStream readmeInputStream = Main.class.getClassLoader().getResourceAsStream("READMEs/README.html");
            FileOutputStream readmeOutputStream = new FileOutputStream(htmlReadme);

            readmeInputStream.transferTo(readmeOutputStream);
        }
        
        //endregion
        
        
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
