package com.ibm.icu.text;

import com.ibm.icu.lang.*;

class UnicodeNameTransliterator extends Transliterator
{
    static final String _ID = "Any-Name";
    static final String OPEN_DELIM = "\\N{";
    static final char CLOSE_DELIM = '}';
    static final int OPEN_DELIM_LEN = 3;
    
    static void register() {
        Transliterator.registerFactory("Any-Name", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnicodeNameTransliterator(null);
            }
        });
    }
    
    public UnicodeNameTransliterator(final UnicodeFilter filter) {
        super("Any-Name", filter);
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position offsets, final boolean isIncremental) {
        int cursor = offsets.start;
        int limit = offsets.limit;
        final StringBuilder str = new StringBuilder();
        str.append("\\N{");
        while (cursor < limit) {
            final int c = text.char32At(cursor);
            final String name;
            if ((name = UCharacter.getExtendedName(c)) != null) {
                str.setLength(3);
                str.append(name).append('}');
                final int clen = UTF16.getCharCount(c);
                text.replace(cursor, cursor + clen, str.toString());
                final int len = str.length();
                cursor += len;
                limit += len - clen;
            }
            else {
                ++cursor;
            }
        }
        offsets.contextLimit += limit - offsets.limit;
        offsets.limit = limit;
        offsets.start = cursor;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        if (myFilter.size() > 0) {
            sourceSet.addAll(myFilter);
            targetSet.addAll(48, 57).addAll(65, 90).add(45).add(32).addAll("\\N{").add(125).addAll(97, 122).add(60).add(62).add(40).add(41);
        }
    }
}
