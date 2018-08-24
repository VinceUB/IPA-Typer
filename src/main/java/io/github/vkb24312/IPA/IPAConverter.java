package io.github.vkb24312.IPA;

class IPAConverter {

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#symbol} method
     */
    static int[] toKey(String input){
        //Determine if vowel or consonant
        if(input.toLowerCase().split("\\.", 3)[0].equals("v") || input.toLowerCase().split("\\.", 3)[0].equals("vl")){
            return toConsonantKey(input);
        } else if(input.toLowerCase().split("\\.", 3)[2].equals("r") || input.toLowerCase().split("\\.", 3)[2].equals("u")) {
            return toVowelKey(input);
        } else throw new IllegalArgumentException("Input " + input + " was not recognized");
    }

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#symbol} method
     */
    private static int[] toVowelKey(String input) {
        String[] parts = input.toLowerCase().split("\\.", 3);

        int rounded; //The roundedness of the vowel. 0 means unrounded, 1 means rounded
        int height = -1; //The height of the vowel. From 0: Close, Near-close, Close-mid, Mid, Open-mid, Open
        int backness = -1; //The backness of the vowel. From 0: Front, Central, Back

        //region Determine roundedness
        if(parts[2].equals("r")) rounded = 1;
        else rounded = 0;
        //endregion

        //region Determine height
        for (int i = 0; i < heightShortcuts.length; i++) {
            if(parts[0].equals(heightShortcuts[i])) height = i;
        }
        if(height<0) throw new IllegalArgumentException("Height \"" + parts[0] + "\" is not valid. Please see readme");
        //endregion

        //region Determine backness
        for (int i = 0; i < backnessShortcuts.length; i++) {
            if(parts[1].equals(backnessShortcuts[i])) backness = i;
        }
        if(backness<0) throw new IllegalArgumentException("Height \"" + parts[0] + "\" is not valid. Please see readme");
        //endregion

        return new int[]{1, height, backness, rounded};
    }

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#symbol} method
     */
    private static int[] toConsonantKey(String input){
        int voicing; // 0 means voiced, 1 means voiceless

        int place = -1; //The place of articulation.
        //In order from 0: Bilabial, Labiodental, Dental, Alveolar, Postalveolar, Retroflex, Palatal, Velar, Uvular, Pharyngeal, Glottal, Labial-Velar, Labial-Palatal, Epiglottal, Alveo-Palatal

        int manner = -1; //The manner of articulation
        // In order from 0: Plosive, Nasal, Trill, Tap/Flap, Fricative, Lateral Fricative, Approximant, Lateral Approximant, Lateral Flap, Sibilant

        String[] parts = input.toLowerCase().split("\\.", 3);

        switch (parts[0]) {
            case "v":
                voicing = 0;
                break;
            case "vl":
                voicing = 1;
                break;
            default:
                throw new IllegalArgumentException("voicing " + parts[0] + " is not valid. Please see readme");
        }

        for (int i = 0; i < placeShortcuts.length; i++) {
            if(parts[1].equals(placeShortcuts[i])) place = i;
        }
        if(place<0) throw new IllegalArgumentException("Place \"" + parts[1] + "\" is not valid. Please see readme");

        for (int i = 0; i < mannerShortcuts.length; i++) {
            if(parts[2].equals(mannerShortcuts[i])) manner = i;
        }
        if(manner<0) throw new IllegalArgumentException("Manner \"" + parts[2] + "\" is not valid. Please see readme");
        else if(manner==4 && place==3) manner = 9; //If alveolar fricative, rename to alveolar sibilant

        return new int[]{0, place, manner, voicing};
    }

    //region Consonants
    private final static String[] placeShortcuts = {"b", "ld", "d", "a", "pa", "r", "p", "v", "u", "ph", "g", "lv", "lp", "eg", "ap"};
    //final static String[] placeFull = {"bilabial","labiodental","dental","alveolar","postalveolar","retroflex","palatal","velar","uvular","pharyngeal","glottal","labial-velar","labial-palatal","epiglottal","alveolo-palatal"};
    private final static String[] mannerShortcuts = {"p", "n", "t", "fl", "f", "lf", "a", "la", "lfl", "s"};
    //final static String[] mannerFull = {"plosive", "nasal", "trill", "flap", "fricative", "lateral fricative", "approximant", "lateral approximant", "lateral flap", "sibilant"};
    private final static String[][][] ipaTableConsonants = {{{"b", "p"}, {"m", "m̥"}, {"ʙ", "ʙ̥"}, {null, null}, {"β", "ɸ"}, {null, null}, {"β", "ɸ"}, {null, null}, {null, null}, {null, null}, },
            {{"b̪", "p̪"}, {"ɱ", "ɱ"}, {null, null}, {null, null}, {"v", "f"}, {null, null}, {"ʋ", "ʋ̥"}, {null, null}, {null, null}, {null, null}, },
            {{"d", "t"}, {"n", "n̥"}, {"r", null}, {"ɾ", "ɾ̥"}, {"ð", "θ"}, {"ɮ", "ɬ"}, {null, "θ"}, {"l", "l̥"}, {"ɺ", null}, {"z", "s"}, },
            {{"d", "t"}, {"n", "n̥"}, {"r", "r̥"}, {"ɾ", "ɾ̥"}, {"z", "s"}, {"ɮ", "ɬ"}, {"ɹ", "ɹ̥"}, {"l", "l̥"}, {"ɺ", null}, {"z", "s"}, },
            {{null, null}, {"n", "n̥"}, {"r", null}, {"ɾ", "ɾ̥"}, {"ʒ", "ʃ"}, {null, null}, {null, "ɹ̥"}, {"l", "l̥"}, {"ɺ", null}, {"ʒ", "ʃ"}, },
            {{"ɖ", "ʈ"}, {"ɳ", "ɳ̊"}, {"ɽ͡r", "ɽ͡r̥"}, {"ɽ", "ɽ̊"}, {"ʐ", "ʂ"}, {"ɭ˔", "ɭ̊˔"}, {"ɻ", "ɻ̊"}, {"ɭ", "ɭ̊"}, {"ɭ̆", null}, {"ʐ", "ʂ"}, },
            {{"ɟ", "c"}, {"ɲ", "ɲ̊"}, {null, null}, {null, null}, {"ʝ", "ç"}, {"ʎ̝", "ʎ̝̊"}, {"j", "j̊"}, {"ʎ", "ʎ̥"}, {null, null}, {"ʒ", "ʃ"}, },
            {{"ɡ", "k"}, {"ŋ", "ŋ̊"}, {null, null}, {null, null}, {"ɣ", "x"}, {"ʟ̝", "ʟ̝̊"}, {"ɰ", "ɰ̊"}, {"ʟ", "ʟ̥"}, {"ʟ̆", null}, {null, null}, },
            {{"ɢ", "q"}, {"ɴ", null}, {"ʀ", "ʀ̥"}, {"ɢ̆", null}, {"ʁ", "χ"}, {null, null}, {"ʁ", null}, {"ʟ̠", null}, {null, null}, {null, null}, },
            {{null, null}, {null, null}, {"ʢ", "ʜ"}, {null, null}, {"ʕ", "ħ"}, {null, null}, {"ʕ", null}, {null, null}, {null, null}, {null, null}, },
            {{null, "ʔ"}, {null, null}, {null, null}, {null, null}, {"ɦ", "h"}, {null, null}, {"ʔ̞", "h"}, {null, null}, {null, null}, {null, null}, },
            {{"ɡ͡b", "k͡p"}, {"ŋ͡m", null}, {null, null}, {null, null}, {null, "ʍ"}, {null, null}, {"w", "ʍ"}, {null, null}, {null, null}, {null, null}, },
            {{null, null}, {null, null}, {null, null}, {null, null}, {null, null}, {null, null}, {"ɥ", "ɥ̊"}, {null, null}, {null, null}, {null, null}, },
            {{"ʡ", null}, {null, null}, {"ʢ", "ʜ"}, {null, null}, {"ʢ", "ʜ"}, {null, null}, {null, null}, {null, null}, {null, null}, {null, null}, },
            {{"ɕ", "ɕ"}, {null, "ɲ̊"}, {null, null}, {null, null}, {"ʑ", "ɕ"}, {null, "ʎ̝̊"}, {null, null}, {null, "ʎ̥"}, {null, null}, {"ʑ", "ɕ"}}};
    //endregion

    //region Vowels
    //final static String[] heightFull = new String[]{"close", "near-close", "close-mid", "mid", "open-mid", "near-open", "open"};
    private final static String[] heightShortcuts = new String[]{"c", "nc", "cm", "m", "om", "no", "o"};
    //final static String[] backnessFull = new String[]{"front", "central", "back"};
    private final static String[] backnessShortcuts = new String[]{"f", "c", "b"};
    private final static String[][][] ipaTableVowels = new String[][][]{
            {{"i", "y"}, {"ɪ", "ʏ"}, {"e", "ø"}, {"e̞", "ø̞"}, {"ɛ", "œ"}, {"æ", null}, {"a", "ɶ"}},
            {{"ɨ", "ʉ"}, {"ɪ̈", "ʊ̈"}, {"ɘ", "ɵ"}, {"ə", "ə"}, {"ɜ", "ɞ"}, {"ɐ", "ɐ"}, {"ä", "ɒ̈"}},
            {{"ɯ", "u"}, {"ɯ̽", "ʊ"}, {"ɤ", "o"}, {"ɤ̞", "o̞"}, {"ʌ", "ɔ"}, {null, null}, {"ɑ", "ɒ"}}
    };
    //endregion

    /**
     * @param input The code numbers in the following order: Place of articulation, Manner of articulation, Voicing
     * @return the symbol corresponding to the input
     */
    static String symbol(int[] input) { //Consults array
        if(input.length!=4) throw new IllegalArgumentException("There have to be 4 inputs, not " + input.length);
        if(input[0]==0) {
            return ipaTableConsonants[input[1]][input[2]][input[3]];
        } else return ipaTableVowels[input[2]][input[1]][input[3]];
    }
}