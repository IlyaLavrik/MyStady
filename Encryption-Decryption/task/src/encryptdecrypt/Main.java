package encryptdecrypt;
public class Main {
    public static void main(String[] args) {
        final String text = new Cription().checFile(args);
        final String method = new Cription().checMethod(args);
        final String algTypy = new Cription().checAlg(args);
        final int key = new Cription().checKey(args);
        final ShifrSelectAlgoritm alg = create(method, algTypy, key);
        final Shifr sfx = new Shifr();
        sfx.setAlgorithm(alg);
        final String newString = sfx.selectText(text);
        System.out.println(newString);
        new Cription().fileWrite(newString, args);
    }
    public static ShifrSelectAlgoritm create(String method, String algTypy, int key) {
        switch (method) {
            case "shift":
                switch (algTypy) {
                    case "enc":
                        return new TextEncrutionShift(key);
                    case "dec":
                        return new TextDecriptionShift(key);
                    default:
                        throw new IllegalArgumentException("Unknown algorithm type " + algTypy);
                }
            case "unicode":
                switch (algTypy) {
                    case "enc":
                        return new TextEncrutionUnicode(key);
                    case "dec":
                        return new TextDecriptionUnicode(key);
                    default:
                        throw new IllegalArgumentException("Unknown algorithm type " + algTypy);
                }
            default:
                throw new IllegalArgumentException("Unknown algorithm method " + algTypy);
        }
    }
}
class Shifr {
    private ShifrSelectAlgoritm algorithm;
    public void setAlgorithm(ShifrSelectAlgoritm algorithm) {
        this.algorithm = algorithm;
    }
    public String selectText (String text) {
        return algorithm.select(text);
    }
}
interface ShifrSelectAlgoritm {
    String select(String text);
}
class TextEncrutionShift implements ShifrSelectAlgoritm {
    int key;
    public TextEncrutionShift(int key) {
        this.key = key;
    }
    @Override
    public String select(String text) {
        int newCharPos;
        int originalCharPos;
        char newChar;
        StringBuilder result = new StringBuilder();
        for (char charester : text.toCharArray()) {
            if ((int)charester > 64 && (int)charester < 91) {
                originalCharPos = charester - 'A';
                newCharPos = (originalCharPos + key) % 26;
                newChar = (char) ('A' + newCharPos);
                result.append(newChar);
            } else if ((int)charester > 96 && (int)charester < 123) {
                originalCharPos = charester - 'a';
                newCharPos = (originalCharPos + key) % 26;
                newChar = (char) ('a' + newCharPos);
                result.append(newChar);
            } else {
                result.append(charester);
            }
        }
        return result.toString();
    }
}
class TextDecriptionShift implements ShifrSelectAlgoritm {
    int key;
    public TextDecriptionShift(int key) {
        this.key = key;
    }
    @Override
    public String select(String text) {

        return new TextEncrutionShift((26 -(key %26))).select(text);
    }
}
class TextEncrutionUnicode implements ShifrSelectAlgoritm {
    int key;
    public TextEncrutionUnicode(int key) {
        this.key = key;
    }
    @Override
    public String select(String text) {
        StringBuilder result = new StringBuilder();
        for (char charester : text.toCharArray()) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(charester);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                int originalCharPos = charester - ' ';
                int newCharPos = (originalCharPos + key) % 96;
                char newChar = (char) (' ' + newCharPos);
                result.append(newChar);
            } else {
                result.append(charester);
            }
        }
        return result.toString();
    }
}
class TextDecriptionUnicode implements ShifrSelectAlgoritm {
    int key;
    public TextDecriptionUnicode(int key) {
        this.key = key;
    }
    @Override
    public String select(String text) {

        return new TextEncrutionUnicode((96 -(key %96))).select(text);
    }
}
