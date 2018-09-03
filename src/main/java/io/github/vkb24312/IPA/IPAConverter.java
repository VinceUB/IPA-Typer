package io.github.vkb24312.IPA;

class IPAConverter {

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#keyConvert} method
     */
    static int[] toKey(String input){
        input = input.toLowerCase();
        
        if(input.split("\\.").length==2) return toSpecialKey(input);
        //Determine if vowel, consonant or special keyConvert
        if(input.toLowerCase().split("\\.", 3)[0].equals("v") || input.toLowerCase().split("\\.", 3)[0].equals("vl")){
            return toConsonantKey(input);
        } else if(input.toLowerCase().split("\\.", 3)[2].equals("r") || input.toLowerCase().split("\\.", 3)[2].equals("u")) {
            return toVowelKey(input);
        } else throw new IllegalArgumentException("Input " + input + " was not recognized");
    }

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#keyConvert} method
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
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#keyConvert} method
     */
    private static int[] toConsonantKey(String input){
        int voicing; // 0 means voiced, 1 means voiceless

        int place = -1; //The place of articulation.
        //In order from 0: Bilabial, Labiodental, Dental, Alveolar, Postalveolar, Retroflex, Palatal, Velar, Uvular, Pharyngeal, Glottal, Labial-Velar, Labial-Palatal, Epiglottal, Alveo-Palatal

        int manner = -1; //The manner of articulation
        // In order from 0: Plosive, Nasal, Trill, Tap/Flap, Fricative, Lateral Fricative, Approximant, Lateral Approximant, Lateral Flap

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

        return new int[]{0, place, manner, voicing};
    }

    private static int[] toSpecialKey(String input){
        int[] out = new int[3];
        out[0] = 2;
        String[] parts = input.toLowerCase().split("\\.", 2);

        switch (parts[0]) {
            case "s":
                out[1] = 0;

                for (int i = 0; i < suprasegmentalsShortcuts.length; i++) {
                    if (parts[1].equals(suprasegmentalsShortcuts[i])) out[2] = i;
                }
                break;
            case "ss":
                out[1] = 2;

                for (int i = 0; i < superscriptsShortcuts.length; i++) {
                    if (parts[1].equals(superscriptsShortcuts[i])) out[2] = i;
                }
                break;
            case "d":
                out[1] = 1;

                for (int i = 0; i < diacriticsShortcuts.length; i++) {
                    if (parts[1].equals(diacriticsShortcuts[i])) out[2] = i;
                }

                break;
            default:
                throw new IllegalArgumentException("Input " + input + " was not recognized");
        }

        return out;
    }

    //region Consonants
    final static String[] voiceFull = {"voiced", "voiceless"};
    private final static String[] placeShortcuts = {"b", "ld", "d", "a", "pa", "r", "p", "v", "u", "ph", "g", "lv", "lp", "eg", "ap"};
    final static String[] placeFull = {"bilabial","labiodental","dental","alveolar","postalveolar","retroflex","palatal","velar","uvular","pharyngeal","glottal","labial-velar","labial-palatal","epiglottal","alveolo-palatal"};
    private final static String[] mannerShortcuts = {"p", "n", "t", "fl", "f", "lf", "a", "la", "lfl"};
    final static String[] mannerFull = {"plosive", "nasal", "trill", "flap", "fricative", "lateral fricative", "approximant", "lateral approximant", "lateral flap"};
    final static char[][][] ipaTableConsonants = new char[][][]{
        {{'b', 'p'}, {'m', '\u0000'}, {'ʙ', '\u0000'}, {'\u0000', '\u0000'}, {'β', 'ɸ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'\u0000', '\u0000'}, {'ɱ', '\u0000'}, {'\u0000', '\u0000'}, {'ⱱ', '\u0000'}, {'v', 'f'}, {'\u0000', '\u0000'}, {'ʋ', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'d', 't'}, {'n', '\u0000'}, {'r', '\u0000'}, {'ɾ', '\u0000'}, {'ð', 'θ'}, {'ɮ', 'ɬ'}, {'ɹ', '\u0000'}, {'l', '\u0000'}, {'ɺ', '\u0000'}},
        {{'d', 't'}, {'n', '\u0000'}, {'r', '\u0000'}, {'ɾ', '\u0000'}, {'z', 's'}, {'ɮ', 'ɬ'}, {'ɹ', '\u0000'}, {'l', '\u0000'}, {'ɺ', '\u0000'}},
        {{'\u0000', '\u0000'}, {'n', '\u0000'}, {'r', '\u0000'}, {'ɾ', '\u0000'}, {'ʒ', 'ʃ'}, {'ɮ', 'ɬ'}, {'ɹ', '\u0000'}, {'l', '\u0000'}, {'ɺ', '\u0000'}},
        {{'ɖ', 'ʈ'}, {'ɳ', '\u0000'}, {'\u0000', '\u0000'}, {'ɽ', '\u0000'}, {'ʐ', 'ʂ'}, {'\u0000', '\u0000'}, {'ɻ', '\u0000'}, {'ɭ', '\u0000'}, {'\u0000', '\u0000'}},
        {{'ɟ', 'c'}, {'ɲ', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ʝ', 'ç'}, {'\u0000', '\u0000'}, {'j', '\u0000'}, {'ʎ', '\u0000'}, {'\u0000', '\u0000'}},
        {{'ɡ', 'k'}, {'ŋ', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ɣ', 'x'}, {'\u0000', '\u0000'}, {'ɰ', '\u0000'}, {'ʟ', '\u0000'}, {'\u0000', '\u0000'}},
        {{'ɢ', 'q'}, {'ɴ', '\u0000'}, {'ʀ', '\u0000'}, {'\u0000', '\u0000'}, {'ʁ', 'χ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ʢ', 'ʜ'}, {'\u0000', '\u0000'}, {'ʕ', 'ħ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'ʔ', 'ʔ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ɦ', 'h'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', 'ʍ'}, {'\u0000', '\u0000'}, {'w', 'ʍ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ɥ', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'ʡ', 'ʡ'}, {'\u0000', '\u0000'}, {'ʢ', 'ʜ'}, {'\u0000', '\u0000'}, {'ʢ', 'ʜ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}},
        {{'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'ʑ', 'ɕ'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}, {'\u0000', '\u0000'}}
    };
    //endregion

    //region Vowels
    final static String[] heightFull = new String[]{"close", "near-close", "close-mid", "mid", "open-mid", "near-open", "open"};
    private final static String[] heightShortcuts = new String[]{"c", "nc", "cm", "m", "om", "no", "o"};
    final static String[] backnessFull = new String[]{"front", "central", "back"};
    private final static String[] backnessShortcuts = new String[]{"f", "c", "b"};
    final static String[] roundednessFull = new String[]{"unrounded", "rounded"};
    final static char[][][] ipaTableVowels = new char[][][]{
            {{'i', 'y'}, {'ɪ', 'ʏ'}, {'e', 'ø'}, {, }, {'ɛ', 'œ'}, {'æ', }, {'a', 'ɶ'}},
            {{'ɨ', 'ʉ'}, {, }, {'ɘ', 'ɵ'}, {'ə', 'ə'}, {'ɜ', 'ɞ'}, {'ɐ', 'ɐ'}, {, }},
            {{'ɯ', 'u'}, {'\u0000', 'ʊ'}, {'ɤ', 'o'}, {,}, {'ʌ', 'ɔ'}, {, }, {'ɑ', 'ɒ'}}
    };
    //endregion

    //region Specials
    private final static char[][] ipaTableSpecials = new char[][]{
            {'ˈ', 'ˌ', 'ː', 'ˑ', '̆', '|', '‖', '.', '͜'},
            {'̥', '̬', '̹', '̜', '̟', '̄', '̈', '̽', '̩', '̯', '˞', '̤', '̰', '̼', '̴', '̝', '̞', '̘', '̙', '̪', '̺', '̻'},
            {'ʰ', 'ʷ', 'ʲ', 'ˠ', 'ˤ', 'ⁿ', 'ˡ', '̚'}
    };

    private final static String[] suprasegmentalsShortcuts = new String[]{"sp", "ss", "lo", "hl", "es", "ming", "majg", "sb", "li"};
    private final static String[] diacriticsShortcuts = new String[]{"vl", "v", "mr", "lr", "ad", "re", "c", "mc", "s", "ns", "rh", "bv", "cv", "ll", "vph", "ra", "lo", "adt", "ret", "d", "a", "l", "n"};
    private final static String[] superscriptsShortcuts = new String[]{"a", "l", "p", "v", "ph", "nr", "lr", "nor"};
    //endregion
    
    //region X-SAMPA table
    private final static String[][][] xSampaTableConsonants = new String[][][]{
            {{"b", "p"},{"m", null},{"B\\", null},{null, null},{"B", "p\\"},{null, null},{"B", "p\\"},{null, null},{null, null},},
            {{"b_d", "p_d"},{"F", "F"},{"B\\_d", null},{null, null},{"v", "f"},{null, null},{"P", "P_0 or v\\_0 or f_o"},{null, null},{null, null},},
            {{"d", "t"},{"n", "n_0"},{"r", null},{"4", "4_0"},{"D", "T"},{"K\\", "K"},{null, "T"},{"l", "l_0"},{"l\\", null},},
            {{"d", "t"},{"n", "n_0"},{"r", "r_0"},{"4", "4_0"},{"z", "s"},{"K\\", "K"},{"r\\ or D_r_o", "r\\_0"},{"l", "l_0"},{"l\\", null},},
            {{null, null},{"n", "n_0"},{"r", null},{"4", "4_0"},{"Z", "S"},{null, null},{null, "r\\_0"},{"l", "l_0"},{"l\\", null},},
            {{"d`", "t`"},{"n`", null},{null, null},{"r`", "r`_0"},{"z`", "s`"},{"l`_r", null},{"r\\`", "r\\`_0"},{"l`", "l`_0"},{null, null},},
            {{"J\\", "c"},{"J", null},{null, null},{null, null},{"j\\", "C"},{"L_r", null},{"j", "j_0"},{"L", "L_0"},{null, null},},
            {{"g", "k"},{"N", null},{null, null},{null, null},{"G", "x"},{null, null},{"M\\", "M\\_0"},{"L\\", "L\\_0"},{null, null},},
            {{"G\\", "q"},{"N\\", null},{"R\\", "R\\_0"},{null, null},{"R", "X"},{null, null},{"R", null},{"L\\_-", null},{null, null},},
            {{null, null},{null, null},{"<\\", "H\\"},{null, null},{"?\\", "X\\"},{null, null},{"?\\", null},{null, null},{null, null},},
            {{null, "?"},{null, null},{null, null},{null, null},{"h\\", "h"},{null, null},{null, "h"},{null, null},{null, null},},
            {{null, null},{"Nm", null},{null, null},{null, null},{null, "W"},{null, null},{"w", "W"},{null, null},{null, null},},
            {{null, null},{null, null},{null, null},{null, null},{null, null},{null, null},{"H", null},{null, null},{null, null},},
            {{">\\", null},{null, null},{"<\\", "H\\"},{null, null},{"<\\", "H\\"},{null, null},{null, null},{null, null},{null, null},},
    };
    private final static String[][][] xSampaTableVowels = new String[][][]{
            {{"i", "y"},{"I", "Y"},{"e", "2"},{},{"E", "9"},{"{", },{"a or a_+ or {_o", "&"},},
            {{"1", "}"},{},{"@\\", "8"},{"@", "@"},{"3", "3\\"},{"6", "6"},{},},
            {{"M", "u"},{null, "U"},{"7", "o"},{},{"V", "O"},{},{"A", "Q"},}
    };
    //endregion

    /**
     * @param input The code numbers in the order described in the README. The first number represents the type of symbol: 0 for consonants, 1 for vowels, all else (2) for specials
     * @return the symbol corresponding to the input
     */
    static char keyConvert(int[] input) { //Consults array
        if(input[0]==0) {
            return ipaTableConsonants[input[1]][input[2]][input[3]];
        } else if(input[0]==1){
            return ipaTableVowels[input[2]][input[1]][input[3]];
        } else return ipaTableSpecials[input[1]][input[2]];
    }
    
    static char xSampaConvert(String input){
        input = input.replaceAll("v\\\\", "P");
        input = input.replaceAll("v\\\\_o", "P_o");
        input = input.replaceAll("f_o", "P_o");
        input = input.replaceAll("D_r_o", "r\\");
        
        if(input.contains(" ")) throw new IllegalArgumentException("There must only be one input at a time");
    
        for (int i = 0; i < xSampaTableConsonants.length; i++) {
            for (int j = 0; j < xSampaTableConsonants[i].length; j++) {
                for (int k = 0; k < xSampaTableConsonants[i][j].length; k++) {
                    try {
                        if (xSampaTableConsonants[i][j][k].equals(input)) return ipaTableConsonants[i][j][k];
                    } catch (NullPointerException ignored){}
                }
            }
        }
    
        for (int i = 0; i < xSampaTableVowels.length; i++) {
            for (int j = 0; j < xSampaTableVowels[i].length; j++) {
                for (int k = 0; k < xSampaTableVowels[i][j].length; k++) {
                    try{
                        if(xSampaTableVowels[i][j][k].equals(input)) return ipaTableVowels[i][j][k];
                    } catch (NullPointerException ignored){}
                }
            }
        }
        
        return '\u0000';
    }
}
