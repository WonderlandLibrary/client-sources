/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Metaphone
implements StringEncoder {
    private static final String VOWELS = "AEIOU";
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private int maxCodeLen = 4;

    public String metaphone(String string) {
        int n;
        boolean bl = false;
        if (string == null || (n = string.length()) == 0) {
            return "";
        }
        if (n == 1) {
            return string.toUpperCase(Locale.ENGLISH);
        }
        char[] cArray = string.toUpperCase(Locale.ENGLISH).toCharArray();
        StringBuilder stringBuilder = new StringBuilder(40);
        StringBuilder stringBuilder2 = new StringBuilder(10);
        switch (cArray[0]) {
            case 'G': 
            case 'K': 
            case 'P': {
                if (cArray[1] == 'N') {
                    stringBuilder.append(cArray, 1, cArray.length - 1);
                    break;
                }
                stringBuilder.append(cArray);
                break;
            }
            case 'A': {
                if (cArray[1] == 'E') {
                    stringBuilder.append(cArray, 1, cArray.length - 1);
                    break;
                }
                stringBuilder.append(cArray);
                break;
            }
            case 'W': {
                if (cArray[1] == 'R') {
                    stringBuilder.append(cArray, 1, cArray.length - 1);
                    break;
                }
                if (cArray[1] == 'H') {
                    stringBuilder.append(cArray, 1, cArray.length - 1);
                    stringBuilder.setCharAt(0, 'W');
                    break;
                }
                stringBuilder.append(cArray);
                break;
            }
            case 'X': {
                cArray[0] = 83;
                stringBuilder.append(cArray);
                break;
            }
            default: {
                stringBuilder.append(cArray);
            }
        }
        int n2 = stringBuilder.length();
        int n3 = 0;
        while (stringBuilder2.length() < this.getMaxCodeLen() && n3 < n2) {
            char c = stringBuilder.charAt(n3);
            if (c != 'C' && this.isPreviousChar(stringBuilder, n3, c)) {
                ++n3;
            } else {
                switch (c) {
                    case 'A': 
                    case 'E': 
                    case 'I': 
                    case 'O': 
                    case 'U': {
                        if (n3 != 0) break;
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'B': {
                        if (this.isPreviousChar(stringBuilder, n3, 'M') && this.isLastChar(n2, n3)) break;
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'C': {
                        if (this.isPreviousChar(stringBuilder, n3, 'S') && !this.isLastChar(n2, n3) && FRONTV.indexOf(stringBuilder.charAt(n3 + 1)) >= 0) break;
                        if (this.regionMatch(stringBuilder, n3, "CIA")) {
                            stringBuilder2.append('X');
                            break;
                        }
                        if (!this.isLastChar(n2, n3) && FRONTV.indexOf(stringBuilder.charAt(n3 + 1)) >= 0) {
                            stringBuilder2.append('S');
                            break;
                        }
                        if (this.isPreviousChar(stringBuilder, n3, 'S') && this.isNextChar(stringBuilder, n3, 'H')) {
                            stringBuilder2.append('K');
                            break;
                        }
                        if (this.isNextChar(stringBuilder, n3, 'H')) {
                            if (n3 == 0 && n2 >= 3 && this.isVowel(stringBuilder, 2)) {
                                stringBuilder2.append('K');
                                break;
                            }
                            stringBuilder2.append('X');
                            break;
                        }
                        stringBuilder2.append('K');
                        break;
                    }
                    case 'D': {
                        if (!this.isLastChar(n2, n3 + 1) && this.isNextChar(stringBuilder, n3, 'G') && FRONTV.indexOf(stringBuilder.charAt(n3 + 2)) >= 0) {
                            stringBuilder2.append('J');
                            n3 += 2;
                            break;
                        }
                        stringBuilder2.append('T');
                        break;
                    }
                    case 'G': {
                        if (this.isLastChar(n2, n3 + 1) && this.isNextChar(stringBuilder, n3, 'H') || !this.isLastChar(n2, n3 + 1) && this.isNextChar(stringBuilder, n3, 'H') && !this.isVowel(stringBuilder, n3 + 2) || n3 > 0 && (this.regionMatch(stringBuilder, n3, "GN") || this.regionMatch(stringBuilder, n3, "GNED"))) break;
                        bl = this.isPreviousChar(stringBuilder, n3, 'G');
                        if (!this.isLastChar(n2, n3) && FRONTV.indexOf(stringBuilder.charAt(n3 + 1)) >= 0 && !bl) {
                            stringBuilder2.append('J');
                            break;
                        }
                        stringBuilder2.append('K');
                        break;
                    }
                    case 'H': {
                        if (this.isLastChar(n2, n3) || n3 > 0 && VARSON.indexOf(stringBuilder.charAt(n3 - 1)) >= 0 || !this.isVowel(stringBuilder, n3 + 1)) break;
                        stringBuilder2.append('H');
                        break;
                    }
                    case 'F': 
                    case 'J': 
                    case 'L': 
                    case 'M': 
                    case 'N': 
                    case 'R': {
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'K': {
                        if (n3 > 0) {
                            if (this.isPreviousChar(stringBuilder, n3, 'C')) break;
                            stringBuilder2.append(c);
                            break;
                        }
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'P': {
                        if (this.isNextChar(stringBuilder, n3, 'H')) {
                            stringBuilder2.append('F');
                            break;
                        }
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'Q': {
                        stringBuilder2.append('K');
                        break;
                    }
                    case 'S': {
                        if (this.regionMatch(stringBuilder, n3, "SH") || this.regionMatch(stringBuilder, n3, "SIO") || this.regionMatch(stringBuilder, n3, "SIA")) {
                            stringBuilder2.append('X');
                            break;
                        }
                        stringBuilder2.append('S');
                        break;
                    }
                    case 'T': {
                        if (this.regionMatch(stringBuilder, n3, "TIA") || this.regionMatch(stringBuilder, n3, "TIO")) {
                            stringBuilder2.append('X');
                            break;
                        }
                        if (this.regionMatch(stringBuilder, n3, "TCH")) break;
                        if (this.regionMatch(stringBuilder, n3, "TH")) {
                            stringBuilder2.append('0');
                            break;
                        }
                        stringBuilder2.append('T');
                        break;
                    }
                    case 'V': {
                        stringBuilder2.append('F');
                        break;
                    }
                    case 'W': 
                    case 'Y': {
                        if (this.isLastChar(n2, n3) || !this.isVowel(stringBuilder, n3 + 1)) break;
                        stringBuilder2.append(c);
                        break;
                    }
                    case 'X': {
                        stringBuilder2.append('K');
                        stringBuilder2.append('S');
                        break;
                    }
                    case 'Z': {
                        stringBuilder2.append('S');
                        break;
                    }
                }
                ++n3;
            }
            if (stringBuilder2.length() <= this.getMaxCodeLen()) continue;
            stringBuilder2.setLength(this.getMaxCodeLen());
        }
        return stringBuilder2.toString();
    }

    private boolean isVowel(StringBuilder stringBuilder, int n) {
        return VOWELS.indexOf(stringBuilder.charAt(n)) >= 0;
    }

    private boolean isPreviousChar(StringBuilder stringBuilder, int n, char c) {
        boolean bl = false;
        if (n > 0 && n < stringBuilder.length()) {
            bl = stringBuilder.charAt(n - 1) == c;
        }
        return bl;
    }

    private boolean isNextChar(StringBuilder stringBuilder, int n, char c) {
        boolean bl = false;
        if (n >= 0 && n < stringBuilder.length() - 1) {
            bl = stringBuilder.charAt(n + 1) == c;
        }
        return bl;
    }

    private boolean regionMatch(StringBuilder stringBuilder, int n, String string) {
        boolean bl = false;
        if (n >= 0 && n + string.length() - 1 < stringBuilder.length()) {
            String string2 = stringBuilder.substring(n, n + string.length());
            bl = string2.equals(string);
        }
        return bl;
    }

    private boolean isLastChar(int n, int n2) {
        return n2 + 1 == n;
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
        }
        return this.metaphone((String)object);
    }

    @Override
    public String encode(String string) {
        return this.metaphone(string);
    }

    public boolean isMetaphoneEqual(String string, String string2) {
        return this.metaphone(string).equals(this.metaphone(string2));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int n) {
        this.maxCodeLen = n;
    }
}

