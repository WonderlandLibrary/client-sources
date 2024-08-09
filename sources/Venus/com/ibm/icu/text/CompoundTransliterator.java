/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeSet;
import java.util.List;

class CompoundTransliterator
extends Transliterator {
    private Transliterator[] trans;
    private int numAnonymousRBTs = 0;

    CompoundTransliterator(List<Transliterator> list) {
        this(list, 0);
    }

    CompoundTransliterator(List<Transliterator> list, int n) {
        super("", null);
        this.trans = null;
        this.init(list, 0, false);
        this.numAnonymousRBTs = n;
    }

    CompoundTransliterator(String string, UnicodeFilter unicodeFilter, Transliterator[] transliteratorArray, int n) {
        super(string, unicodeFilter);
        this.trans = transliteratorArray;
        this.numAnonymousRBTs = n;
    }

    private void init(List<Transliterator> list, int n, boolean bl) {
        int n2;
        int n3 = list.size();
        this.trans = new Transliterator[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = n == 0 ? n2 : n3 - 1 - n2;
            this.trans[n2] = list.get(n4);
        }
        if (n == 1 && bl) {
            StringBuilder stringBuilder = new StringBuilder();
            for (n2 = 0; n2 < n3; ++n2) {
                if (n2 > 0) {
                    stringBuilder.append(';');
                }
                stringBuilder.append(this.trans[n2].getID());
            }
            this.setID(stringBuilder.toString());
        }
        this.computeMaximumContextLength();
    }

    public int getCount() {
        return this.trans.length;
    }

    public Transliterator getTransliterator(int n) {
        return this.trans[n];
    }

    private static void _smartAppend(StringBuilder stringBuilder, char c) {
        if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) != c) {
            stringBuilder.append(c);
        }
    }

    @Override
    public String toRules(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.numAnonymousRBTs >= 1 && this.getFilter() != null) {
            stringBuilder.append("::").append(this.getFilter().toPattern(bl)).append(';');
        }
        for (int i = 0; i < this.trans.length; ++i) {
            String string;
            if (this.trans[i].getID().startsWith("%Pass")) {
                string = this.trans[i].toRules(bl);
                if (this.numAnonymousRBTs > 1 && i > 0 && this.trans[i - 1].getID().startsWith("%Pass")) {
                    string = "::Null;" + string;
                }
            } else {
                string = this.trans[i].getID().indexOf(59) >= 0 ? this.trans[i].toRules(bl) : this.trans[i].baseToRules(bl);
            }
            CompoundTransliterator._smartAppend(stringBuilder, '\n');
            stringBuilder.append(string);
            CompoundTransliterator._smartAppend(stringBuilder, ';');
        }
        return stringBuilder.toString();
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = new UnicodeSet(this.getFilterAsUnicodeSet(unicodeSet));
        UnicodeSet unicodeSet5 = new UnicodeSet();
        for (int i = 0; i < this.trans.length; ++i) {
            unicodeSet5.clear();
            this.trans[i].addSourceTargetSet(unicodeSet4, unicodeSet2, unicodeSet5);
            unicodeSet3.addAll(unicodeSet5);
            unicodeSet4.addAll(unicodeSet5);
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        if (this.trans.length < 1) {
            position.start = position.limit;
            return;
        }
        int n = position.limit;
        int n2 = position.start;
        int n3 = 0;
        Object var7_7 = null;
        for (int i = 0; i < this.trans.length; ++i) {
            position.start = n2;
            int n4 = position.limit;
            if (position.start == position.limit) break;
            this.trans[i].filteredTransliterate(replaceable, position, bl);
            if (!bl && position.start != position.limit) {
                throw new RuntimeException("ERROR: Incomplete non-incremental transliteration by " + this.trans[i].getID());
            }
            n3 += position.limit - n4;
            if (!bl) continue;
            position.limit = position.start;
        }
        position.limit = n += n3;
    }

    private void computeMaximumContextLength() {
        int n = 0;
        for (int i = 0; i < this.trans.length; ++i) {
            int n2 = this.trans[i].getMaximumContextLength();
            if (n2 <= n) continue;
            n = n2;
        }
        this.setMaximumContextLength(n);
    }

    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter = this.getFilter();
        if (unicodeFilter != null && unicodeFilter instanceof UnicodeSet) {
            unicodeFilter = new UnicodeSet((UnicodeSet)unicodeFilter);
        }
        return new CompoundTransliterator(this.getID(), unicodeFilter, this.trans, this.numAnonymousRBTs);
    }
}

