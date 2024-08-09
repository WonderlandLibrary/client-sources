/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class MatchRatingApproachEncoder
implements StringEncoder {
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int ELEVEN = 11;
    private static final int TWELVE = 12;
    private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
    private static final String UNICODE = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171";
    private static final String[] DOUBLE_CONSONANT = new String[]{"BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ"};

    String cleanName(String string) {
        String[] stringArray;
        String string2 = string.toUpperCase(Locale.ENGLISH);
        for (String string3 : stringArray = new String[]{"\\-", "[&]", "\\'", "\\.", "[\\,]"}) {
            string2 = string2.replaceAll(string3, EMPTY);
        }
        string2 = this.removeAccents(string2);
        string2 = string2.replaceAll("\\s+", EMPTY);
        return string2;
    }

    @Override
    public final Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
        }
        return this.encode((String)object);
    }

    @Override
    public final String encode(String string) {
        if (string == null || EMPTY.equalsIgnoreCase(string) || SPACE.equalsIgnoreCase(string) || string.length() == 1) {
            return EMPTY;
        }
        string = this.cleanName(string);
        string = this.removeVowels(string);
        string = this.removeDoubleConsonants(string);
        string = this.getFirst3Last3(string);
        return string;
    }

    String getFirst3Last3(String string) {
        int n = string.length();
        if (n > 6) {
            String string2 = string.substring(0, 3);
            String string3 = string.substring(n - 3, n);
            return string2 + string3;
        }
        return string;
    }

    int getMinRating(int n) {
        int n2 = 0;
        n2 = n <= 4 ? 5 : (n <= 7 ? 4 : (n <= 11 ? 3 : (n == 12 ? 2 : 1)));
        return n2;
    }

    public boolean isEncodeEquals(String string, String string2) {
        if (string == null || EMPTY.equalsIgnoreCase(string) || SPACE.equalsIgnoreCase(string)) {
            return true;
        }
        if (string2 == null || EMPTY.equalsIgnoreCase(string2) || SPACE.equalsIgnoreCase(string2)) {
            return true;
        }
        if (string.length() == 1 || string2.length() == 1) {
            return true;
        }
        if (string.equalsIgnoreCase(string2)) {
            return false;
        }
        string = this.cleanName(string);
        string2 = this.cleanName(string2);
        string = this.removeVowels(string);
        string2 = this.removeVowels(string2);
        string = this.removeDoubleConsonants(string);
        string2 = this.removeDoubleConsonants(string2);
        string = this.getFirst3Last3(string);
        string2 = this.getFirst3Last3(string2);
        if (Math.abs(string.length() - string2.length()) >= 3) {
            return true;
        }
        int n = Math.abs(string.length() + string2.length());
        int n2 = 0;
        n2 = this.getMinRating(n);
        int n3 = this.leftToRightThenRightToLeftProcessing(string, string2);
        return n3 >= n2;
    }

    boolean isVowel(String string) {
        return string.equalsIgnoreCase("E") || string.equalsIgnoreCase("A") || string.equalsIgnoreCase("O") || string.equalsIgnoreCase("I") || string.equalsIgnoreCase("U");
    }

    int leftToRightThenRightToLeftProcessing(String string, String string2) {
        char[] cArray = string.toCharArray();
        char[] cArray2 = string2.toCharArray();
        int n = string.length() - 1;
        int n2 = string2.length() - 1;
        String string3 = EMPTY;
        String string4 = EMPTY;
        String string5 = EMPTY;
        String string6 = EMPTY;
        for (int i = 0; i < cArray.length && i <= n2; ++i) {
            string3 = string.substring(i, i + 1);
            string4 = string.substring(n - i, n - i + 1);
            string5 = string2.substring(i, i + 1);
            string6 = string2.substring(n2 - i, n2 - i + 1);
            if (string3.equals(string5)) {
                cArray[i] = 32;
                cArray2[i] = 32;
            }
            if (!string4.equals(string6)) continue;
            cArray[n - i] = 32;
            cArray2[n2 - i] = 32;
        }
        String string7 = new String(cArray).replaceAll("\\s+", EMPTY);
        String string8 = new String(cArray2).replaceAll("\\s+", EMPTY);
        if (string7.length() > string8.length()) {
            return Math.abs(6 - string7.length());
        }
        return Math.abs(6 - string8.length());
    }

    String removeAccents(String string) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            int n2 = UNICODE.indexOf(c);
            if (n2 > -1) {
                stringBuilder.append(PLAIN_ASCII.charAt(n2));
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    String removeDoubleConsonants(String string) {
        String string2 = string.toUpperCase(Locale.ENGLISH);
        for (String string3 : DOUBLE_CONSONANT) {
            if (!string2.contains(string3)) continue;
            String string4 = string3.substring(0, 1);
            string2 = string2.replace(string3, string4);
        }
        return string2;
    }

    String removeVowels(String string) {
        String string2 = string.substring(0, 1);
        string = string.replaceAll("A", EMPTY);
        string = string.replaceAll("E", EMPTY);
        string = string.replaceAll("I", EMPTY);
        string = string.replaceAll("O", EMPTY);
        string = string.replaceAll("U", EMPTY);
        string = string.replaceAll("\\s{2,}\\b", SPACE);
        if (this.isVowel(string2)) {
            return string2 + string;
        }
        return string;
    }
}

