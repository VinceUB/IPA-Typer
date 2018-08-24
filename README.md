# Vincent's IPA typer

This program is supposed to get a coded (abbreviated) message as an input, and output the corresponding symbol

## How to run the program

The GUI is coming soon, but for now you have to use the command prompt.

1. Open the command prompt (Windows) or terminal (Mac & Linux)
2. Write ```cd <program directory>```
3. Write ```java -jar <jarfile name>.jar```

## How to correctly input

Each letter is split into separate sections, separated by periods (.), in the order as follows:

(For consonants): Voicing, Place of articulation, Manner of articulation

(For vowels): Height, Backness, Roundedness

Diacritics (e.g. ʲ, ʷ) are a planned feature

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

Example: `v.ld.f nc.f.u v.a.n vl.a.f om.f.u v.a.n vl.a.p` will return `vɪnsɛnt`, my name
