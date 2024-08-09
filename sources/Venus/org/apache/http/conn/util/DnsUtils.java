/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.util;

public class DnsUtils {
    private DnsUtils() {
    }

    private static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static String normalize(String string) {
        int n;
        if (string == null) {
            return null;
        }
        int n2 = 0;
        for (n = string.length(); n > 0 && !DnsUtils.isUpper(string.charAt(n2)); --n) {
            ++n2;
        }
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder(string.length());
            stringBuilder.append(string, 0, n2);
            while (n > 0) {
                char c = string.charAt(n2);
                if (DnsUtils.isUpper(c)) {
                    stringBuilder.append((char)(c + 32));
                } else {
                    stringBuilder.append(c);
                }
                ++n2;
                --n;
            }
            return stringBuilder.toString();
        }
        return string;
    }
}

