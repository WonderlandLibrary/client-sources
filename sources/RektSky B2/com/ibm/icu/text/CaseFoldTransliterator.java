package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

class CaseFoldTransliterator extends Transliterator
{
    static final String _ID = "Any-CaseFold";
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private StringBuilder result;
    static SourceTargetUtility sourceTargetUtility;
    
    static void register() {
        Transliterator.registerFactory("Any-CaseFold", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new CaseFoldTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("CaseFold", "Upper", false);
    }
    
    public CaseFoldTransliterator() {
        super("Any-CaseFold", null);
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
    }
    
    @Override
    protected synchronized void handleTransliterate(final Replaceable text, final Position offsets, final boolean isIncremental) {
        if (this.csp == null) {
            return;
        }
        if (offsets.start >= offsets.limit) {
            return;
        }
        this.iter.setText(text);
        this.result.setLength(0);
        this.iter.setIndex(offsets.start);
        this.iter.setLimit(offsets.limit);
        this.iter.setContextLimits(offsets.contextStart, offsets.contextLimit);
        int c;
        while ((c = this.iter.nextCaseMapCP()) >= 0) {
            c = this.csp.toFullFolding(c, this.result, 0);
            if (this.iter.didReachLimit() && isIncremental) {
                offsets.start = this.iter.getCaseMapCPStart();
                return;
            }
            if (c < 0) {
                continue;
            }
            int delta;
            if (c <= 31) {
                delta = this.iter.replace(this.result.toString());
                this.result.setLength(0);
            }
            else {
                delta = this.iter.replace(UTF16.valueOf(c));
            }
            if (delta == 0) {
                continue;
            }
            offsets.limit += delta;
            offsets.contextLimit += delta;
        }
        offsets.start = offsets.limit;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        synchronized (UppercaseTransliterator.class) {
            if (CaseFoldTransliterator.sourceTargetUtility == null) {
                CaseFoldTransliterator.sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>() {
                    @Override
                    public String transform(final String source) {
                        return UCharacter.foldCase(source, true);
                    }
                });
            }
        }
        CaseFoldTransliterator.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
    }
    
    static {
        CaseFoldTransliterator.sourceTargetUtility = null;
    }
}
