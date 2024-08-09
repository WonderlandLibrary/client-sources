/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

final class SoundexUtils {
    SoundexUtils() {
    }

    static String clean(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        int n = string.length();
        char[] cArray = new char[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (!Character.isLetter(string.charAt(i))) continue;
            cArray[n2++] = string.charAt(i);
        }
        if (n2 == n) {
            return string.toUpperCase(Locale.ENGLISH);
        }
        return new String(cArray, 0, n2).toUpperCase(Locale.ENGLISH);
    }

    static int difference(StringEncoder stringEncoder, String string, String string2) throws EncoderException {
        return SoundexUtils.differenceEncoded(stringEncoder.encode(string), stringEncoder.encode(string2));
    }

    static int differenceEncoded(String string, String string2) {
        if (string == null || string2 == null) {
            return 1;
        }
        int n = Math.min(string.length(), string2.length());
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (string.charAt(i) != string2.charAt(i)) continue;
            ++n2;
        }
        return n2;
    }
}

