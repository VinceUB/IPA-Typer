# Vincent's IPA typer

This program is supposed to get a coded (abbreviated) message as an input, and output the corresponding symbol

## How to run the program

Perquisites: You must have at least Java 8 installed

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
