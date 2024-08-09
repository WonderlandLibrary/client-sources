/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.Transform;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import java.util.HashSet;
import java.util.Set;

class SourceTargetUtility {
    final Transform<String, String> transform;
    final UnicodeSet sourceCache;
    final Set<String> sourceStrings;
    static final UnicodeSet NON_STARTERS = new UnicodeSet("[:^ccc=0:]").freeze();
    static Normalizer2 NFC = Normalizer2.getNFCInstance();

    public SourceTargetUtility(Transform<String, String> transform) {
        this(transform, null);
    }

    public SourceTargetUtility(Transform<String, String> transform, Normalizer2 normalizer2) {
        this.transform = transform;
        this.sourceCache = normalizer2 != null ? new UnicodeSet("[:^ccc=0:]") : new UnicodeSet();
        this.sourceStrings = new HashSet<String>();
        for (int i = 0; i <= 0x10FFFF; ++i) {
            String string;
            String string2 = transform.transform(UTF16.valueOf(i));
            boolean bl = false;
            if (!CharSequences.equals(i, string2)) {
                this.sourceCache.add(i);
                bl = true;
            }
            if (normalizer2 == null || (string = NFC.getDecomposition(i)) == null) continue;
            string2 = transform.transform(string);
            if (!string.equals(string2)) {
                this.sourceStrings.add(string);
            }
            if (bl || normalizer2.isInert(i)) continue;
            this.sourceCache.add(i);
        }
        this.sourceCache.freeze();
    }

    public void addSourceTargetSet(Transliterator transliterator, UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = transliterator.getFilterAsUnicodeSet(unicodeSet);
        UnicodeSet unicodeSet5 = new UnicodeSet(this.sourceCache).retainAll(unicodeSet4);
        unicodeSet2.addAll(unicodeSet5);
        for (String string : unicodeSet5) {
            unicodeSet3.addAll(this.transform.transform(string));
        }
        for (String string : this.sourceStrings) {
            String string2;
            if (!unicodeSet4.containsAll(string) || string.equals(string2 = this.transform.transform(string))) continue;
            unicodeSet3.addAll(string2);
            unicodeSet2.addAll(string);
        }
    }
}

