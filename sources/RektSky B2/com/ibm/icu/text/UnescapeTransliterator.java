package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;

class UnescapeTransliterator extends Transliterator
{
    private char[] spec;
    private static final char END = '\uffff';
    
    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[] { '\u0002', '\0', '\u0010', '\u0004', '\u0006', 'U', '+', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[] { '\u0002', '\0', '\u0010', '\u0004', '\u0004', '\\', 'u', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/C", new char[] { '\u0002', '\0', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\0', '\u0010', '\b', '\b', '\\', 'U', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[] { '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[] { '\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[] { '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffff' });
            }
        });
        Transliterator.registerFactory("Hex-Any", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new UnescapeTransliterator("Hex-Any", new char[] { '\u0002', '\0', '\u0010', '\u0004', '\u0006', 'U', '+', '\u0002', '\0', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\0', '\u0010', '\b', '\b', '\\', 'U', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffff' });
            }
        });
    }
    
    UnescapeTransliterator(final String ID, final char[] spec) {
        super(ID, null);
        this.spec = spec;
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position pos, final boolean isIncremental) {
        int start = 0;
        int limit = 0;
    Label_0452:
        for (start = pos.start, limit = pos.limit; start < limit; start += UTF16.getCharCount(text.char32At(start))) {
            int prefixLen;
            int suffixLen;
            int radix;
            int minDigits;
            int maxDigits;
            int s;
            boolean match;
            int i;
            char c;
            int u;
            int digitCount;
            int ch;
            int digit;
            char c2;
            String str;
            Label_0271_Outer:Label_0413:
            for (int ipat = 0; this.spec[ipat] != '\uffff'; ipat += prefixLen + suffixLen) {
                prefixLen = this.spec[ipat++];
                suffixLen = this.spec[ipat++];
                radix = this.spec[ipat++];
                minDigits = this.spec[ipat++];
                maxDigits = this.spec[ipat++];
                s = start;
                match = true;
                i = 0;
                while (i < prefixLen) {
                    if (s >= limit && i > 0) {
                        if (isIncremental) {
                            break Label_0452;
                        }
                        match = false;
                        break;
                    }
                    else {
                        c = text.charAt(s++);
                        if (c != this.spec[ipat + i]) {
                            match = false;
                            break;
                        }
                        ++i;
                    }
                }
                if (match) {
                    u = 0;
                    digitCount = 0;
                    while (true) {
                        while (s < limit) {
                            ch = text.char32At(s);
                            digit = UCharacter.digit(ch, radix);
                            if (digit >= 0) {
                                s += UTF16.getCharCount(ch);
                                u = u * radix + digit;
                                if (++digitCount != maxDigits) {
                                    continue Label_0271_Outer;
                                }
                            }
                            match = (digitCount >= minDigits);
                            if (!match) {
                                continue Label_0413;
                            }
                            i = 0;
                            while (i < suffixLen) {
                                if (s >= limit) {
                                    if (s > start && isIncremental) {
                                        break Label_0452;
                                    }
                                    match = false;
                                    break;
                                }
                                else {
                                    c2 = text.charAt(s++);
                                    if (c2 != this.spec[ipat + prefixLen + i]) {
                                        match = false;
                                        break;
                                    }
                                    ++i;
                                }
                            }
                            if (match) {
                                str = UTF16.valueOf(u);
                                text.replace(start, s, str);
                                limit -= s - start - str.length();
                                break Label_0271_Outer;
                            }
                            continue Label_0413;
                        }
                        if (s > start && isIncremental) {
                            break Label_0452;
                        }
                        continue;
                    }
                }
            }
            if (start < limit) {}
        }
        pos.contextLimit += limit - pos.limit;
        pos.limit = limit;
        pos.start = start;
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        final UnicodeSet items = new UnicodeSet();
        final StringBuilder buffer = new StringBuilder();
        int end;
        for (int i = 0; this.spec[i] != '\uffff'; i = end) {
            end = i + this.spec[i] + this.spec[i + 1] + 5;
            for (int radix = this.spec[i + 2], j = 0; j < radix; ++j) {
                Utility.appendNumber(buffer, j, radix, 0);
            }
            for (int j = i + 5; j < end; ++j) {
                items.add(this.spec[j]);
            }
        }
        items.addAll(buffer.toString());
        items.retainAll(myFilter);
        if (items.size() > 0) {
            sourceSet.addAll(items);
            targetSet.addAll(0, 1114111);
        }
    }
}
