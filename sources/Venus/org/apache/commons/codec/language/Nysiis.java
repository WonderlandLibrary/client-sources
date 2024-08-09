/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class Nysiis
implements StringEncoder {
    private static final char[] CHARS_A = new char[]{'A'};
    private static final char[] CHARS_AF = new char[]{'A', 'F'};
    private static final char[] CHARS_C = new char[]{'C'};
    private static final char[] CHARS_FF = new char[]{'F', 'F'};
    private static final char[] CHARS_G = new char[]{'G'};
    private static final char[] CHARS_N = new char[]{'N'};
    private static final char[] CHARS_NN = new char[]{'N', 'N'};
    private static final char[] CHARS_S = new char[]{'S'};
    private static final char[] CHARS_SSS = new char[]{'S', 'S', 'S'};
    private static final Pattern PAT_MAC = Pattern.compile("^MAC");
    private static final Pattern PAT_KN = Pattern.compile("^KN");
    private static final Pattern PAT_K = Pattern.compile("^K");
    private static final Pattern PAT_PH_PF = Pattern.compile("^(PH|PF)");
    private static final Pattern PAT_SCH = Pattern.compile("^SCH");
    private static final Pattern PAT_EE_IE = Pattern.compile("(EE|IE)$");
    private static final Pattern PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
    private static final char SPACE = ' ';
    private static final int TRUE_LENGTH = 6;
    private final boolean strict;

    private static boolean isVowel(char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    private static char[] transcodeRemaining(char c, char c2, char c3, char c4) {
        if (c2 == 'E' && c3 == 'V') {
            return CHARS_AF;
        }
        if (Nysiis.isVowel(c2)) {
            return CHARS_A;
        }
        if (c2 == 'Q') {
            return CHARS_G;
        }
        if (c2 == 'Z') {
            return CHARS_S;
        }
        if (c2 == 'M') {
            return CHARS_N;
        }
        if (c2 == 'K') {
            if (c3 == 'N') {
                return CHARS_NN;
            }
            return CHARS_C;
        }
        if (c2 == 'S' && c3 == 'C' && c4 == 'H') {
            return CHARS_SSS;
        }
        if (c2 == 'P' && c3 == 'H') {
            return CHARS_FF;
        }
        if (!(c2 != 'H' || Nysiis.isVowel(c) && Nysiis.isVowel(c3))) {
            return new char[]{c};
        }
        if (c2 == 'W' && Nysiis.isVowel(c)) {
            return new char[]{c};
        }
        return new char[]{c2};
    }

    public Nysiis() {
        this(true);
    }

    public Nysiis(boolean bl) {
        this.strict = bl;
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
        }
        return this.nysiis((String)object);
    }

    @Override
    public String encode(String string) {
        return this.nysiis(string);
    }

    public boolean isStrict() {
        return this.strict;
    }

    public String nysiis(String string) {
        char c;
        int n;
        if (string == null) {
            return null;
        }
        if ((string = SoundexUtils.clean(string)).length() == 0) {
            return string;
        }
        string = PAT_MAC.matcher(string).replaceFirst("MCC");
        string = PAT_KN.matcher(string).replaceFirst("NN");
        string = PAT_K.matcher(string).replaceFirst("C");
        string = PAT_PH_PF.matcher(string).replaceFirst("FF");
        string = PAT_SCH.matcher(string).replaceFirst("SSS");
        string = PAT_EE_IE.matcher(string).replaceFirst("Y");
        string = PAT_DT_ETC.matcher(string).replaceFirst("D");
        StringBuilder stringBuilder = new StringBuilder(string.length());
        stringBuilder.append(string.charAt(0));
        char[] cArray = string.toCharArray();
        int n2 = cArray.length;
        for (n = 1; n < n2; ++n) {
            c = n < n2 - 1 ? cArray[n + 1] : (char)' ';
            char c2 = n < n2 - 2 ? cArray[n + 2] : (char)' ';
            char[] cArray2 = Nysiis.transcodeRemaining(cArray[n - 1], cArray[n], c, c2);
            System.arraycopy(cArray2, 0, cArray, n, cArray2.length);
            if (cArray[n] == cArray[n - 1]) continue;
            stringBuilder.append(cArray[n]);
        }
        if (stringBuilder.length() > 1) {
            n = stringBuilder.charAt(stringBuilder.length() - 1);
            if (n == 83) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                n = stringBuilder.charAt(stringBuilder.length() - 1);
            }
            if (stringBuilder.length() > 2 && (c = stringBuilder.charAt(stringBuilder.length() - 2)) == 'A' && n == 89) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            }
            if (n == 65) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
        String string2 = stringBuilder.toString();
        return this.isStrict() ? string2.substring(0, Math.min(6, string2.length())) : string2;
    }
}

