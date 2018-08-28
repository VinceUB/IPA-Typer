package io.github.vkb24312.IPA;

import java.util.Arrays;

class IPAConverter {

    /**
     * @param input The coded input
     * @return The key to be used in the {@link io.github.vkb24312.IPA.IPAConverter#symbol} method
     */
    static int[] toKey(String input){
        if(input.split("\\.").length==2) return toSpecialKey(input);
        //Determine if vowel, consonant or special symbol
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
            case "d":
                out[1] = 1;

                for (int i = 0; i < superscriptsShortcuts.length; i++) {
                    if (parts[1].equals(superscriptsShortcuts[i])) out[2] = i;
                }
                break;
            default:
                out[1] = 2;

                for (int i = 0; i < diacriticsShortcuts.length; i++) {
                    if (parts[1].equals(diacriticsShortcuts[i])) out[2] = i;
                }

                break;
        }

        return out;
    }

    //region Consonants
    private final static String[] placeShortcuts = {"b", "ld", "d", "a", "pa", "r", "p", "v", "u", "ph", "g", "lv", "lp", "eg", "ap"};
    //final static String[] placeFull = {"bilabial","labiodental","dental","alveolar","postalveolar","retroflex","palatal","velar","uvular","pharyngeal","glottal","labial-velar","labial-palatal","epiglottal","alveolo-palatal"};
    private final static String[] mannerShortcuts = {"p", "n", "t", "fl", "f", "lf", "a", "la", "lfl"};
    //final static String[] mannerFull = {"plosive", "nasal", "trill", "flap", "fricative", "lateral fricative", "approximant", "lateral approximant", "lateral flap"};
    private final static char[][][] ipaTableConsonants = new char[][][]{
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
    //final static String[] heightFull = new String[]{"close", "near-close", "close-mid", "mid", "open-mid", "near-open", "open"};
    private final static String[] heightShortcuts = new String[]{"c", "nc", "cm", "m", "om", "no", "o"};
    //final static String[] backnessFull = new String[]{"front", "central", "back"};
    private final static String[] backnessShortcuts = new String[]{"f", "c", "b"};
    private final static char[][][] ipaTableVowels = new char[][][]{
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

    /**
     * @param input The code numbers in the following order: Place of articulation, Manner of articulation, Voicing
     * @return the symbol corresponding to the input
     */
    static char symbol(int[] input) { //Consults array
        if(input[0]==0) {
            return ipaTableConsonants[input[1]][input[2]][input[3]];
        } else if(input[0]==1){
            return ipaTableVowels[input[2]][input[1]][input[3]];
        } else return ipaTableSpecials[input[1]][input[2]];
    }
}
