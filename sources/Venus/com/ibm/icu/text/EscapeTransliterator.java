/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;

class EscapeTransliterator
extends Transliterator {
    private String prefix;
    private String suffix;
    private int radix;
    private int minDigits;
    private boolean grokSupplementals;
    private EscapeTransliterator supplementalHandler;

    static void register() {
        Transliterator.registerFactory("Any-Hex/Unicode", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Java", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/C", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, null));
            }
        });
        Transliterator.registerFactory("Any-Hex/XML", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/XML10", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Perl", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Plain", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, null);
            }
        });
    }

    EscapeTransliterator(String string, String string2, String string3, int n, int n2, boolean bl, EscapeTransliterator escapeTransliterator) {
        super(string, null);
        this.prefix = string2;
        this.suffix = string3;
        this.radix = n;
        this.minDigits = n2;
        this.grokSupplementals = bl;
        this.supplementalHandler = escapeTransliterator;
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        int n3 = position.start;
        StringBuilder stringBuilder = new StringBuilder(this.prefix);
        int n4 = this.prefix.length();
        boolean bl2 = false;
        for (n = position.limit; n3 < n; n3 += stringBuilder.length(), n += stringBuilder.length() - n2) {
            int n5 = this.grokSupplementals ? replaceable.char32At(n3) : (int)replaceable.charAt(n3);
            int n6 = n2 = this.grokSupplementals ? UTF16.getCharCount(n5) : 1;
            if ((n5 & 0xFFFF0000) != 0 && this.supplementalHandler != null) {
                stringBuilder.setLength(0);
                stringBuilder.append(this.supplementalHandler.prefix);
                Utility.appendNumber(stringBuilder, n5, this.supplementalHandler.radix, this.supplementalHandler.minDigits);
                stringBuilder.append(this.supplementalHandler.suffix);
                bl2 = true;
            } else {
                if (bl2) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(this.prefix);
                    bl2 = false;
                } else {
                    stringBuilder.setLength(n4);
                }
                Utility.appendNumber(stringBuilder, n5, this.radix, this.minDigits);
                stringBuilder.append(this.suffix);
            }
            replaceable.replace(n3, n3 + n2, stringBuilder.toString());
        }
        position.contextLimit += n - position.limit;
        position.limit = n;
        position.start = n3;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        unicodeSet2.addAll(this.getFilterAsUnicodeSet(unicodeSet));
        EscapeTransliterator escapeTransliterator = this;
        while (escapeTransliterator != null) {
            if (unicodeSet.size() != 0) {
                unicodeSet3.addAll(escapeTransliterator.prefix);
                unicodeSet3.addAll(escapeTransliterator.suffix);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < escapeTransliterator.radix; ++i) {
                    Utility.appendNumber(stringBuilder, i, escapeTransliterator.radix, escapeTransliterator.minDigits);
                }
                unicodeSet3.addAll(stringBuilder.toString());
            }
            escapeTransliterator = escapeTransliterator.supplementalHandler;
        }
    }
}

