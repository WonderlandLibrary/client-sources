/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;

public class LookupTranslator
extends CharSequenceTranslator {
    private final HashMap<String, String> lookupMap = new HashMap();
    private final HashSet<Character> prefixSet = new HashSet();
    private final int shortest;
    private final int longest;

    public LookupTranslator(CharSequence[] ... charSequenceArray) {
        int n = Integer.MAX_VALUE;
        int n2 = 0;
        if (charSequenceArray != null) {
            for (CharSequence[] charSequenceArray2 : charSequenceArray) {
                this.lookupMap.put(charSequenceArray2[0].toString(), charSequenceArray2[5].toString());
                this.prefixSet.add(Character.valueOf(charSequenceArray2[0].charAt(0)));
                int n3 = charSequenceArray2[0].length();
                if (n3 < n) {
                    n = n3;
                }
                if (n3 <= n2) continue;
                n2 = n3;
            }
        }
        this.shortest = n;
        this.longest = n2;
    }

    @Override
    public int translate(CharSequence charSequence, int n, Writer writer) throws IOException {
        if (this.prefixSet.contains(Character.valueOf(charSequence.charAt(n)))) {
            int n2 = this.longest;
            if (n + this.longest > charSequence.length()) {
                n2 = charSequence.length() - n;
            }
            for (int i = n2; i >= this.shortest; --i) {
                CharSequence charSequence2 = charSequence.subSequence(n, n + i);
                String string = this.lookupMap.get(charSequence2.toString());
                if (string == null) continue;
                writer.write(string);
                return i;
            }
        }
        return 1;
    }
}

