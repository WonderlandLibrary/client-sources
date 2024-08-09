/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeSet;

class UnicodeNameTransliterator
extends Transliterator {
    static final String _ID = "Any-Name";
    static final String OPEN_DELIM = "\\N{";
    static final char CLOSE_DELIM = '}';
    static final int OPEN_DELIM_LEN = 3;

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnicodeNameTransliterator(null);
            }
        });
    }

    public UnicodeNameTransliterator(UnicodeFilter unicodeFilter) {
        super(_ID, unicodeFilter);
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OPEN_DELIM);
        while (n < n2) {
            int n3 = replaceable.char32At(n);
            String string = UCharacter.getExtendedName(n3);
            if (string != null) {
                stringBuilder.setLength(3);
                stringBuilder.append(string).append('}');
                int n4 = UTF16.getCharCount(n3);
                replaceable.replace(n, n + n4, stringBuilder.toString());
                int n5 = stringBuilder.length();
                n += n5;
                n2 += n5 - n4;
                continue;
            }
            ++n;
        }
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
        position.start = n;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        if (unicodeSet4.size() > 0) {
            unicodeSet2.addAll(unicodeSet4);
            unicodeSet3.addAll(48, 57).addAll(65, 90).add(45).add(32).addAll(OPEN_DELIM).add(125).addAll(97, 122).add(60).add(62).add(40).add(41);
        }
    }
}

