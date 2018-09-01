# Vincent's IPA typer

This program is supposed to get a coded (abbreviated) message as an input, and output the corresponding symbol

## How to run the program

Perquisites:
1. You must have at least Java 9 installed ([Lastest JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html))
2. You must have a computer with:
    1. At least 50MB of RAM (More recommended)
    2. At least 1.6MB of free storage space (More recommended)
    3. A usable console or GUI
    4. Internet connection (optional)
3. If in GUI mode, a web browser is recommended

If you want to do it like a normal person, just double press the jarfile downloaded.

If you:
1. Don't have graphics
2. Have something against everything graphical
3. Are a nerd

You should:
1. Open the command prompt (Windows) or Terminal (Mac & Linux)
2. If you have graphics available (or like doing things the complicated way), type `java -jar <full path of the jarfile>/<the jarfile>.jar console`
3. Otherwise, type `java -jar <full path of the jarfile>/<the jarfile>.jar`
4. Press enter

## How to correctly input

Each letter is split into separate sections, separated by periods (.), in the order as follows:

(For consonants): Voicing, Place of articulation, Manner of articulation

(For vowels): Height, Backness, Roundedness

Diacritics, Suprasegmentals: Type, Symbol

In each of these sections, you put an abbreviation for the symbol's name (see below)

If you want to write multiple symbols in one input, just separate them with spaces
### Consonants
#### Voicing
| Full name | Abbreviation |
| :-------- | :-----------
| Voiced | v |
| Voiceless | vl |
#### Place of Articulation
| Full name      | Abbreviation   |
| :------------- | :------------- |
| Bilabial       | b              |
| Labiodental    | ld             |
| Dental         | d              |
| Alveolar       | a              |
| Postalveolar   | pa       |
| Retroflex      | r              |
| Palatal        | p              |
| Velar          | v              |
| Uvular         | u              |
| Pharyngeal     | ph             |
| Glottal        | g              |
| Labial-Velar   | lv             |
| Labial-Palatal | lp             |
| Epiglottal     | eg             |
| Aleolo-Palatal | ap             |
#### Manner of articulation
| Full name | Abbreviation     |
| :------------- | :------------- |
| Plosive/Stop       | p       |
| Nasal   | n  |
| Trill   | t  |
| Flap/Tap   | fl  |
| Fricative   | f  |
| Lateral Fricative   | lf  |
| Approximant   | a  |
| Lateral Flap   | lfl  |
| Sibilant*   | s  |

*Instead of Sibilant, use Fricative. The program will correct you

### Vowels
#### Height
| Full name     | American Notation | Abbreviation     |
| :------------- | :------------- | :--- |
| Close | High | c |
| Near-Close   | Near-High  | nc  |
| Close-Mid   | High-Mid  | cm  |
| Mid   | True Mid  | m  |
| Open-Mid   | Low-Mid  | lm  |
| Near-Open   | Near-Low  | nl  |
| Open   | Low  | l  |
#### Backness
| Full name | Abbreviation |
| :------------- | :------------- |
| Front | f |
| Central   | c  |
| Back   | b  |
#### Roundedness
| Full name | Abbreviation     |
| :------------- | :------------- |
| Rounded       | r      |
| Unrounded   | ur  |

### Diacritics & Suprasegmentals
#### Type
| Full Name | Abbreviation     |
| :------------- | :------------- |
| Suprasegmental       | s       |
| Superscript   | ss  |
| Diacritic  | d  |

#### Suprasegmentals
| Full Name | Abbreviation     |
| :------------- | :------------- |
| Primary Stress   | ps  |
| Secondary Stress   | ss  |
| Long   | lo  |
| Half-Long   | hl  |
| Extra-Short   | es  |
| Minor (foot) group   | ming   |
| Major (intonation) group   | majg  |
| Syllable break   | sb  |
| Link   | li  | (Links are put between the two letters)

#### Diacritics
| Full Name | Abbreviation     |
| :------------- | :------------- |
| Voiceless       | vl       |
| Voiced   | v  |
| More rounded   | mr  |
| Less rounded   | lr  |
| Advanced   | ad  |
| Retracted   | re  |
| Centralized   | c  |
| Mid-Centralized   | mc  |
| Syllabic   | s  |
| Non-Syllabic  | ns  |
| Rhoticity   | rh  |
| Breathy Voiced   | bv  |
| Creaky Voiced   | cv  |
| Linguolabial   | ll  |
| Velarized or Pharyngealized   | vph  |
| Raised   | ra  |
| Lowered   | lo  |
| Advanced Tongue Root   | adt  |
| Retracted Tongue Root   | ret  |
| Dental   | d  |
| Apical   | a  |
| Laminal   | l  |
| Nasalized   | n  |

#### Superscripted Letters
| Full Name | Abbreviation     |
| :------------- | :------------- |
| Aspirated       | a       |
| Labialized   | l  |
| Palatalized   | p  |
| Velarized   | v  |
| Pharyngealized   | ph  |

Example: `s.ps v.ld.f nc.f.u v.a.n s.sb vl.a.f om.f.u v.a.n vl.a.p` will return `ˈvɪn.sɛnt`, my name

## FAQ

### What on earth is an IPA? Are you talking about the beer?

The IPA (International Phonetic Alphabet) is an alphabet used by linguists, to make it possible to read in a language without having to study it's writing system(s) for months or years.

You can find it in dictionaries and the sort

## NSFAQAPNAMA (Not so frequently asked questions and problems nobody asked me about)

### The GUI is ugly, how do I change it around?

There are several things you can do:

#### The font is too big/small

The solution is simple. If you scroll your mouse wheel, the font size will change

#### The window is too big/small

Just as in any other program, you can resize the window by dragging the edge of it

#### The font is ugly

1. Locate the Appdata (Windows)/User home (Anything else) folder:
    1. Windows:
        1. Press the Window key and R together
        2. In the prompt that pops up, type `%appdata%\IPATyper`
    2. Mac:
        1. Open Finder
        2. In the menu bar, press `Go`
        3. Press `Go to folder`
        4. Write `~/IPATyper`
    3. Anything else:
        1. Find the user home folder
        2. Go to the IPATyper folder
2. Open the `gui.properties` file in a text editor (`notepad`, `textedit`, `vim`, etc.)
3. Find the line that starts with `font=`
4. Change the text after that to your font's name.
5. If you want to use a TTF file, put the file in the `fonts` folder, and change the text to the filename

### X isn't working, how do I fix it?

If a button isn't responding, or something, try restarting the program. If it fixes itself, it's probably nothing

Otherwise, open the program in the command prompt (`java -jar <jarfile>.jar`), try again, and copy all the output text.

Once all the text is copied, submit it as a new issue [here](https://github.com/vkb24312/IPA-Converter/issues), or send it as an email to `vincent.bechmann@gmail.com`

### I really really want a feature, but you aren't adding it

Send me an email (`vincent.bechmann@gmail.com`) or submit it as an issue [here](https://github.com/vkb24312/IPA-Converter/issues)
