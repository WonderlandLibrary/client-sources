package com.ibm.icu.text;

class RemoveTransliterator extends Transliterator
{
    private static final String _ID = "Any-Remove";
    
    static void register() {
        Transliterator.registerFactory("Any-Remove", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new RemoveTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("Remove", "Null", false);
    }
    
    public RemoveTransliterator() {
        super("Any-Remove", null);
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position index, final boolean incremental) {
        text.replace(index.start, index.limit, "");
        final int len = index.limit - index.start;
        index.contextLimit -= len;
        index.limit -= len;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        sourceSet.addAll(myFilter);
    }
}
