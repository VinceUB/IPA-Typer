package io.github.vkb24312.IPA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class IPAConverter {

    /**
     * @see io.github.vkb24312.IPA.IPAConverter#extend
     */
    static int[] toKey(String input){
        boolean voicing; //true means voiced, false means voiceless

        int place = -1; //The place of articulation.
        //In order from 0: Bilabial, Labiodental, Dental, Alveolar, Postalveolar, Retroflex, Palatal, Velar, Uvular, Pharyngeal, Glottal, Labial-Velar, Labial-Palatal, Epiglottal, Alveo-Palatal

        int manner = -1; //The manner of articulation
        // In order from 0: Plosive, Nasal, Trill, Tap/Flap, Fricative, Lateral Fricative, Approximant, Lateral Approximant, Lateral Flap, Sibilant

        String[] parts = input.toLowerCase().split("\\.", 3);

        if(parts[0].equals("v")) voicing = true;
        else if(parts[0].equals("vl")) voicing = false;
        else throw new IllegalArgumentException("voicing " + parts[0] + " is not valid. Please see readme");

        for (int i = 0; i < placeShortcuts.length; i++) {
            if(parts[1].equals(placeShortcuts[i])) place = i;
        }
        if(place<0) throw new IllegalArgumentException("Place \"" + parts[1] + "\" is not valid. Please see readme");

        for (int i = 0; i < mannerShortcuts.length; i++) {
            if(parts[2].equals(mannerShortcuts[i])) manner = i;
        }
        if(manner<0) throw new IllegalArgumentException("Manner \"" + parts[2] + "\" is not valid. Please see readme");
        else if(manner==4 && place==3) manner = 9; //If alveolar fricative, rename to alveolar sibilant

        int voice;
        if(voicing) voice = 0;
        else voice = 1;

        return new int[]{place, manner, voice};
    }

    /**
     *
     * @param input The string that
     * @return The input, converted into an IPA value
     */

    static String extend(String input) {
        boolean voicing; //true means voiced, false means voiceless

        int place = -1; //The place of articulation.
                   //In order from 0: Bilabial, Labiodental, Dental, Alveolar, Postalveolar, Retroflex, Palatal, Velar, Uvular, Pharyngeal, Glottal, Labial-Velar, Labial-Palatal, Epiglottal, Alveo-Palatal

        int manner = -1; //The manner of articulation
                    // In order from 0: Plosive, Nasal, Trill, Tap/Flap, Fricative, Lateral Fricative, Approximant, Lateral Approximant, Lateral Flap, Sibilant

        String[] parts = input.toLowerCase().split("\\.", 3);

        voicing = parts[0].equals("v");

        for (int i = 0; i < placeShortcuts.length; i++) {
            if(parts[1].equals(placeShortcuts[i])) place = i;
        }
        if(place<0) throw new IllegalArgumentException("Place \"" + parts[1] + "\" is not valid. Please see readme");

        for (int i = 0; i < mannerShortcuts.length; i++) {
            if(parts[2].equals(mannerShortcuts[i])) manner = i;
        }
        if(manner<0) throw new IllegalArgumentException("Manner \"" + parts[2] + "\" is not valid. Please see readme");
        else if(manner==4 && place==3) manner = 9; //If alveolar fricative, rename to alveolar sibilant

        return voicing(voicing) + " " + placeFull[place] + " " + mannerFull[manner];
    }

    final static String[] placeShortcuts = {"b", "ld", "d", "a", "pa", "r", "p", "v", "u", "ph", "g", "lv", "lp", "eg", "ap"};
    final static String[] placeFull = {"bilabial","labiodental","dental","alveolar","postalveolar","retroflex","palatal","velar","uvular","pharyngeal","glottal","labial-velar","labial-palatal","epiglottal","alveolo-palatal"};
    final static String[] mannerShortcuts = {"p", "n", "t", "fl", "f", "lf", "a", "la", "lfl", "s"};
    final static String[] mannerFull = {"plosive", "nasal", "trill", "flap", "fricative", "lateral fricative", "approximant", "lateral approximant", "lateral flap", "sibilant"};
    final static String[][][] ipaTable = {{{"b", "p"}, {"m", "m̥"}, {"ʙ", "ʙ̥"}, {"null", "null"}, {"β", "ɸ"}, {"null", "null"}, {"β", "ɸ"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"b̪", "p̪"}, {"ɱ", "ɱ"}, {"null", "null"}, {"null", "null"}, {"v", "f"}, {"null", "null"}, {"ʋ", "ʋ̥"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"d", "t"}, {"n", "n̥"}, {"r", "null"}, {"ɾ", "ɾ̥"}, {"ð", "θ"}, {"ɮ", "ɬ"}, {"null", "θ"}, {"l", "l̥"}, {"ɺ", "null"}, {"z", "s"}, },
            {{"d", "t"}, {"n", "n̥"}, {"r", "r̥"}, {"ɾ", "ɾ̥"}, {"z", "s"}, {"ɮ", "ɬ"}, {"ɹ", "ɹ̥"}, {"l", "l̥"}, {"ɺ", "null"}, {"z", "s"}, },
            {{"null", "null"}, {"n", "n̥"}, {"r", "null"}, {"ɾ", "ɾ̥"}, {"ʒ", "ʃ"}, {"null", "null"}, {"null", "ɹ̥"}, {"l", "l̥"}, {"ɺ", "null"}, {"ʒ", "ʃ"}, },
            {{"ɖ", "ʈ"}, {"ɳ", "ɳ̊"}, {"ɽ͡r", "ɽ͡r̥"}, {"ɽ", "ɽ̊"}, {"ʐ", "ʂ"}, {"ɭ˔", "ɭ̊˔"}, {"ɻ", "ɻ̊"}, {"ɭ", "ɭ̊"}, {"ɭ̆", "null"}, {"ʐ", "ʂ"}, },
            {{"ɟ", "c"}, {"ɲ", "ɲ̊"}, {"null", "null"}, {"null", "null"}, {"ʝ", "ç"}, {"ʎ̝", "ʎ̝̊"}, {"j", "j̊"}, {"ʎ", "ʎ̥"}, {"null", "null"}, {"ʒ", "ʃ"}, },
            {{"ɡ", "k"}, {"ŋ", "ŋ̊"}, {"null", "null"}, {"null", "null"}, {"ɣ", "x"}, {"ʟ̝", "ʟ̝̊"}, {"ɰ", "ɰ̊"}, {"ʟ", "ʟ̥"}, {"ʟ̆", "null"}, {"null", "null"}, },
            {{"ɢ", "q"}, {"ɴ", "null"}, {"ʀ", "ʀ̥"}, {"ɢ̆", "null"}, {"ʁ", "χ"}, {"null", "null"}, {"ʁ", "null"}, {"ʟ̠", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"null", "null"}, {"null", "null"}, {"ʢ", "ʜ"}, {"null", "null"}, {"ʕ", "ħ"}, {"null", "null"}, {"ʕ", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"null", "ʔ"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"ɦ", "h"}, {"null", "null"}, {"ʔ̞", "h"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"ɡ͡b", "k͡p"}, {"ŋ͡m", "null"}, {"null", "null"}, {"null", "null"}, {"null", "ʍ"}, {"null", "null"}, {"w", "ʍ"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"ɥ", "ɥ̊"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"ʡ", "null"}, {"null", "null"}, {"ʢ", "ʜ"}, {"null", "null"}, {"ʢ", "ʜ"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, },
            {{"ɕ", "ɕ"}, {"null", "ɲ̊"}, {"null", "null"}, {"null", "null"}, {"ʑ", "ɕ"}, {"null", "ʎ̝̊"}, {"null", "null"}, {"null", "ʎ̥"}, {"null", "null"}, {"ʑ", "ɕ"}}};
    static String voicing(boolean isVoiced){
        if(isVoiced) return "voiced";
        else return "voiceless";
    }

    /**
     *
     * @param input The input string, delimited by spaces, in the order: Voicing, Place of Articulation, Manner of Articulation
     * @return The symbol corresponding to the input
     */
    static String symbol(String input) throws IOException { //To be used for an online search
        input = input.replaceAll(" ", "_");
        Document page = Jsoup.connect("https://en.wikipedia.org/wiki/"+input).get();
        return Jsoup.parse(page.getElementsByTag("th").html()).getElementsByClass("IPA").first().text();
    }

    /**
     * @param input The code numbers in the following order: Place of articulation, Manner of articulation, Voicing
     * @return the symbol corresponding to the input
     */
    static String symbol(int[] input) { //Consults array
        if(input.length!=3) throw new IllegalArgumentException("There have to be 3 inputs, not " + input.length);
        return ipaTable[input[0]][input[1]][input[2]];
    }
}


//TODO: Make it work with vowels
//TODO: Make the actual README.MD