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
import com.ibm.icu.text.UppercaseTransliterator;

class CaseFoldTransliterator
extends Transliterator {
    static final String _ID = "Any-CaseFold";
    private final UCaseProps csp = UCaseProps.INSTANCE;
    private ReplaceableContextIterator iter = new ReplaceableContextIterator();
    private StringBuilder result = new StringBuilder();
    static SourceTargetUtility sourceTargetUtility = null;

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new CaseFoldTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("CaseFold", "Upper", false);
    }

    public CaseFoldTransliterator() {
        super(_ID, null);
    }

    @Override
    protected synchronized void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        if (this.csp == null) {
            return;
        }
        if (position.start >= position.limit) {
            return;
        }
        this.iter.setText(replaceable);
        this.result.setLength(0);
        this.iter.setIndex(position.start);
        this.iter.setLimit(position.limit);
        this.iter.setContextLimits(position.contextStart, position.contextLimit);
        while ((n = this.iter.nextCaseMapCP()) >= 0) {
            int n2;
            n = this.csp.toFullFolding(n, this.result, 0);
            if (this.iter.didReachLimit() && bl) {
                position.start = this.iter.getCaseMapCPStart();
                return;
            }
            if (n < 0) continue;
            if (n <= 31) {
                n2 = this.iter.replace(this.result.toString());
                this.result.setLength(0);
            } else {
                n2 = this.iter.replace(UTF16.valueOf(n));
            }
            if (n2 == 0) continue;
            position.limit += n2;
            position.contextLimit += n2;
        }
        position.start = position.limit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        Class<UppercaseTransliterator> clazz = UppercaseTransliterator.class;
        synchronized (UppercaseTransliterator.class) {
            if (sourceTargetUtility == null) {
                sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>(this){
                    final CaseFoldTransliterator this$0;
                    {
                        this.this$0 = caseFoldTransliterator;
                    }

                    @Override
                    public String transform(String string) {
                        return UCharacter.foldCase(string, true);
                    }

                    @Override
                    public Object transform(Object object) {
                        return this.transform((String)object);
                    }
                });
            }
            // ** MonitorExit[var4_4] (shouldn't be in output)
            sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
            return;
        }
    }
}

