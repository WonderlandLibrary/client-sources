package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class EscapeTransliterator extends Transliterator
{
    private String prefix;
    private String suffix;
    private int radix;
    private int minDigits;
    private boolean grokSupplementals;
    private EscapeTransliterator supplementalHandler;
    
    static void register() {
        Transliterator.registerFactory("Any-Hex/Unicode", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Java", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/C", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, null));
            }
        });
        Transliterator.registerFactory("Any-Hex/XML", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/XML10", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Perl", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Plain", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, null);
            }
        });
    }
    
    EscapeTransliterator(final String ID, final String prefix, final String suffix, final int radix, final int minDigits, final boolean grokSupplementals, final EscapeTransliterator supplementalHandler) {
        super(ID, null);
        this.prefix = prefix;
        this.suffix = suffix;
        this.radix = radix;
        this.minDigits = minDigits;
        this.grokSupplementals = grokSupplementals;
        this.supplementalHandler = supplementalHandler;
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position pos, final boolean incremental) {
        int start = pos.start;
        int limit = pos.limit;
        final StringBuilder buf = new StringBuilder(this.prefix);
        final int prefixLen = this.prefix.length();
        boolean redoPrefix = false;
        while (start < limit) {
            final int c = this.grokSupplementals ? text.char32At(start) : text.charAt(start);
            final int charLen = this.grokSupplementals ? UTF16.getCharCount(c) : 1;
            if ((c & 0xFFFF0000) != 0x0 && this.supplementalHandler != null) {
                buf.setLength(0);
                buf.append(this.supplementalHandler.prefix);
                Utility.appendNumber(buf, c, this.supplementalHandler.radix, this.supplementalHandler.minDigits);
                buf.append(this.supplementalHandler.suffix);
                redoPrefix = true;
            }
            else {
                if (redoPrefix) {
                    buf.setLength(0);
                    buf.append(this.prefix);
                    redoPrefix = false;
                }
                else {
                    buf.setLength(prefixLen);
                }
                Utility.appendNumber(buf, c, this.radix, this.minDigits);
                buf.append(this.suffix);
            }
            text.replace(start, start + charLen, buf.toString());
            start += buf.length();
            limit += buf.length() - charLen;
        }
        pos.contextLimit += limit - pos.limit;
        pos.limit = limit;
        pos.start = start;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        sourceSet.addAll(this.getFilterAsUnicodeSet(inputFilter));
        for (EscapeTransliterator it = this; it != null; it = it.supplementalHandler) {
            if (inputFilter.size() != 0) {
                targetSet.addAll(it.prefix);
                targetSet.addAll(it.suffix);
                final StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < it.radix; ++i) {
                    Utility.appendNumber(buffer, i, it.radix, it.minDigits);
                }
                targetSet.addAll(buffer.toString());
            }
        }
    }
}
