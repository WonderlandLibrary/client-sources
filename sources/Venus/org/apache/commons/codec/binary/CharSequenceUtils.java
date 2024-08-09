/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

public class CharSequenceUtils {
    static boolean regionMatches(CharSequence charSequence, boolean bl, int n, CharSequence charSequence2, int n2, int n3) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(bl, n, (String)charSequence2, n2, n3);
        }
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        while (n6-- > 0) {
            char c;
            char c2;
            if ((c2 = charSequence.charAt(n4++)) == (c = charSequence2.charAt(n5++))) continue;
            if (!bl) {
                return true;
            }
            if (Character.toUpperCase(c2) == Character.toUpperCase(c) || Character.toLowerCase(c2) == Character.toLowerCase(c)) continue;
            return true;
        }
        return false;
    }
}

