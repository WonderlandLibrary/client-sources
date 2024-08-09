/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.ReplaceableContextIterator;
import com.ibm.icu.text.SourceTargetUtility;
import com.ibm.icu.text.Transform;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;

class TitlecaseTransliterator
extends Transliterator {
    static final String _ID = "Any-Title";
    private final ULocale locale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private StringBuilder result;
    private int caseLocale;
    SourceTargetUtility sourceTargetUtility = null;

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new TitlecaseTransliterator(ULocale.US);
            }
        });
        TitlecaseTransliterator.registerSpecialInverse("Title", "Lower", false);
    }

    public TitlecaseTransliterator(ULocale uLocale) {
        super(_ID, null);
        this.locale = uLocale;
        this.setMaximumContextLength(2);
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
    }

    @Override
    protected synchronized void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        if (position.start >= position.limit) {
            return;
        }
        boolean bl2 = true;
        for (int i = position.start - 1; i >= position.contextStart; i -= UTF16.getCharCount(n2)) {
            n2 = replaceable.char32At(i);
            n = this.csp.getTypeOrIgnorable(n2);
            if (n > 0) {
                bl2 = false;
                break;
            }
            if (n == 0) break;
        }
        this.iter.setText(replaceable);
        this.iter.setIndex(position.start);
        this.iter.setLimit(position.limit);
        this.iter.setContextLimits(position.contextStart, position.contextLimit);
        this.result.setLength(0);
        while ((n2 = this.iter.nextCaseMapCP()) >= 0) {
            int n3;
            n = this.csp.getTypeOrIgnorable(n2);
            if (n < 0) continue;
            n2 = bl2 ? this.csp.toFullTitle(n2, this.iter, this.result, this.caseLocale) : this.csp.toFullLower(n2, this.iter, this.result, this.caseLocale);
            boolean bl3 = bl2 = n == 0;
            if (this.iter.didReachLimit() && bl) {
                position.start = this.iter.getCaseMapCPStart();
                return;
            }
            if (n2 < 0) continue;
            if (n2 <= 31) {
                n3 = this.iter.replace(this.result.toString());
                this.result.setLength(0);
            } else {
                n3 = this.iter.replace(UTF16.valueOf(n2));
            }
            if (n3 == 0) continue;
            position.limit += n3;
            position.contextLimit += n3;
        }
        position.start = position.limit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        TitlecaseTransliterator titlecaseTransliterator = this;
        synchronized (titlecaseTransliterator) {
            if (this.sourceTargetUtility == null) {
                this.sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>(this){
                    final TitlecaseTransliterator this$0;
                    {
                        this.this$0 = titlecaseTransliterator;
                    }

                    @Override
                    public String transform(String string) {
                        return UCharacter.toTitleCase(TitlecaseTransliterator.access$000(this.this$0), string, null);
                    }

                    @Override
                    public Object transform(Object object) {
                        return this.transform((String)object);
                    }
                });
            }
        }
        this.sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
    }

    static ULocale access$000(TitlecaseTransliterator titlecaseTransliterator) {
        return titlecaseTransliterator.locale;
    }
}

