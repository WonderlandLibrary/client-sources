package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

class TitlecaseTransliterator extends Transliterator
{
    static final String _ID = "Any-Title";
    private final ULocale locale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private StringBuilder result;
    private int caseLocale;
    SourceTargetUtility sourceTargetUtility;
    
    static void register() {
        Transliterator.registerFactory("Any-Title", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new TitlecaseTransliterator(ULocale.US);
            }
        });
        Transliterator.registerSpecialInverse("Title", "Lower", false);
    }
    
    public TitlecaseTransliterator(final ULocale loc) {
        super("Any-Title", null);
        this.sourceTargetUtility = null;
        this.locale = loc;
        this.setMaximumContextLength(2);
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
    }
    
    @Override
    protected synchronized void handleTransliterate(final Replaceable text, final Position offsets, final boolean isIncremental) {
        if (offsets.start >= offsets.limit) {
            return;
        }
        boolean doTitle = true;
        int c;
        for (int start = offsets.start - 1; start >= offsets.contextStart; start -= UTF16.getCharCount(c)) {
            c = text.char32At(start);
            final int type = this.csp.getTypeOrIgnorable(c);
            if (type > 0) {
                doTitle = false;
                break;
            }
            if (type == 0) {
                break;
            }
        }
        this.iter.setText(text);
        this.iter.setIndex(offsets.start);
        this.iter.setLimit(offsets.limit);
        this.iter.setContextLimits(offsets.contextStart, offsets.contextLimit);
        this.result.setLength(0);
        while ((c = this.iter.nextCaseMapCP()) >= 0) {
            final int type = this.csp.getTypeOrIgnorable(c);
            if (type >= 0) {
                if (doTitle) {
                    c = this.csp.toFullTitle(c, this.iter, this.result, this.caseLocale);
                }
                else {
                    c = this.csp.toFullLower(c, this.iter, this.result, this.caseLocale);
                }
                doTitle = (type == 0);
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
                        return UCharacter.toTitleCase(TitlecaseTransliterator.this.locale, source, null);
                    }
                });
            }
        }
        this.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
    }
}
