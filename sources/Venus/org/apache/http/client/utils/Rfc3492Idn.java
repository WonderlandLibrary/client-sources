/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.util.StringTokenizer;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.utils.Idn;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class Rfc3492Idn
implements Idn {
    private static final int base = 36;
    private static final int tmin = 1;
    private static final int tmax = 26;
    private static final int skew = 38;
    private static final int damp = 700;
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final char delimiter = '-';
    private static final String ACE_PREFIX = "xn--";

    private int adapt(int n, int n2, boolean bl) {
        int n3 = n;
        n3 = bl ? (n3 /= 700) : (n3 /= 2);
        n3 += n3 / n2;
        int n4 = 0;
        while (n3 > 455) {
            n3 /= 35;
            n4 += 36;
        }
        return n4 + 36 * n3 / (n3 + 38);
    }

    private int digit(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 65;
        }
        if (c >= 'a' && c <= 'z') {
            return c - 97;
        }
        if (c >= '0' && c <= '9') {
            return c - 48 + 26;
        }
        throw new IllegalArgumentException("illegal digit: " + c);
    }

    @Override
    public String toUnicode(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        StringTokenizer stringTokenizer = new StringTokenizer(string, ".");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            if (stringBuilder.length() > 0) {
                stringBuilder.append('.');
            }
            if (string2.startsWith(ACE_PREFIX)) {
                string2 = this.decode(string2.substring(4));
            }
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    protected String decode(String string) {
        String string2 = string;
        int n = 128;
        int n2 = 0;
        int n3 = 72;
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        int n4 = string2.lastIndexOf(45);
        if (n4 != -1) {
            stringBuilder.append(string2.subSequence(0, n4));
            string2 = string2.substring(n4 + 1);
        }
        while (!string2.isEmpty()) {
            int n5 = n2;
            int n6 = 1;
            int n7 = 36;
            while (!string2.isEmpty()) {
                char c = string2.charAt(0);
                string2 = string2.substring(1);
                int n8 = this.digit(c);
                n2 += n8 * n6;
                int n9 = n7 <= n3 + 1 ? 1 : (n7 >= n3 + 26 ? 26 : n7 - n3);
                if (n8 < n9) break;
                n6 *= 36 - n9;
                n7 += 36;
            }
            n3 = this.adapt(n2 - n5, stringBuilder.length() + 1, n5 == 0);
            n += n2 / (stringBuilder.length() + 1);
            stringBuilder.insert(n2 %= stringBuilder.length() + 1, (char)n);
            ++n2;
        }
        return stringBuilder.toString();
    }
}

