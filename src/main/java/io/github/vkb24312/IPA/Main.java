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
        //region get AppData
        File appData;
        if(System.getProperty("os.name").toLowerCase().startsWith("win")) appData = new File(System.getenv("appdata"), "IPATyper");
        else appData = new File(System.getProperty("user.home"));
        if(!appData.exists()) appData.mkdirs();
        //endregion
        
        //region Check if properties file exists. If it doesn't, create it. If it does, fix it
        File propertiesFile = new File(appData, "gui.properties");
        if(!propertiesFile.exists()) {
            getDefaults().store(new FileWriter(propertiesFile), null);
        } else {
            Properties userProperties = new Properties();
            userProperties.load(new FileInputStream(propertiesFile));
            userProperties = getFixed(userProperties, getDefaults());
            userProperties.store(new FileWriter(propertiesFile), null);
        }
        Properties userProperties = new Properties();
        userProperties.load(new FileInputStream(propertiesFile));
        //endregion
        
        //region Make sure the fonts are valid. If they aren't, fix them
        if(userProperties.getProperty("font").toLowerCase().endsWith(".ttf")){
            if(!new File(appData, "fonts/" + userProperties.getProperty("font")).exists()){
                userProperties.setProperty("font", "DejaVuSans.ttf");
                
                if(!new File(appData, "fonts/" + userProperties.getProperty("font")).exists()){
                    if(!new File(appData, "fonts/").exists() || !new File(appData, "fonts/").isDirectory()) new File(appData, "fonts/").mkdirs();
                    InputStream fontInputStream = Main.class.getClassLoader().getResourceAsStream("fonts/DejaVuSans.ttf");
                    FileOutputStream fontOutputStream = new FileOutputStream(new File(appData, "fonts/DejaVuSans.ttf"));
                    fontInputStream.transferTo(fontOutputStream);
                    fontInputStream.close();
                    fontOutputStream.close();
                }
            }
        }
        //endregion
        
        //region Create README files if they don't exist
        File readMeDir = new File(appData, "READMEs");
        if(!readMeDir.exists() || !readMeDir.isDirectory()) readMeDir.mkdirs();
        
        File htmlReadme = new File(readMeDir, "README.html");
        File mdReadme = new File(readMeDir, "README.md");
    
        if(!htmlReadme.exists()){
            InputStream is = Main.class.getClassLoader().getResourceAsStream("READMEs/README.html");
            FileOutputStream fos = new FileOutputStream(htmlReadme);
            is.transferTo(fos);
            is.close();
            fos.close();
        }
        
        if(!mdReadme.exists()){
            InputStream is = Main.class.getClassLoader().getResourceAsStream("READMEs/README.md");
            FileOutputStream fos = new FileOutputStream(mdReadme);
            is.transferTo(fos);
            is.close();
            fos.close();
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
                char symbol = IPAConverter.keyConvert(IPAConverter.toKey(in));
                if (symbol == '\u0000') output.append("*");
                else output.append(symbol);
            } catch (IllegalArgumentException e) {
                output.append("*");
            }
        }

        return output.toString();
    }
    
    private static Properties getDefaults() throws IOException{
        Properties defaults = new Properties();
        defaults.load(Main.class.getClassLoader().getResourceAsStream("gui.properties"));
        return defaults;
    }
    
    private static Properties getFixed(Properties toFix, Properties defaults){
        defaults.forEach(toFix::putIfAbsent);
        return toFix;
    }
}
