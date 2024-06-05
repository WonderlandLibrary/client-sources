package com.ibm.icu.text;

class NullTransliterator extends Transliterator
{
    static final String SHORT_ID = "Null";
    static final String _ID = "Any-Null";
    
    public NullTransliterator() {
        super("Any-Null", null);
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position offsets, final boolean incremental) {
        offsets.start = offsets.limit;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
    }
}
