package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

class LowercaseTransliterator extends Transliterator
{
    static final String _ID = "Any-Lower";
    private final ULocale locale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private StringBuilder result;
    private int caseLocale;
    SourceTargetUtility sourceTargetUtility;
    
    static void register() {
        Transliterator.registerFactory("Any-Lower", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new LowercaseTransliterator(ULocale.US);
            }
        });
        Transliterator.registerSpecialInverse("Lower", "Upper", true);
    }
    
    public LowercaseTransliterator(final ULocale loc) {
        super("Any-Lower", null);
        this.sourceTargetUtility = null;
        this.locale = loc;
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
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
            c = this.csp.toFullLower(c, this.iter, this.result, this.caseLocale);
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
        synchronized (this) {
            if (this.sourceTargetUtility == null) {
                this.sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>() {
                    @Override
                    public String transform(final String source) {
                        return UCharacter.toLowerCase(LowercaseTransliterator.this.locale, source);
                    }
                });
            }
        }
        this.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
    }
}
