/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;

class UnescapeTransliterator
extends Transliterator {
    private char[] spec;
    private static final char END = '\uffff';

    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0006', 'U', '+', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/C", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\u0000', '\u0010', '\b', '\b', '\\', 'U', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[]{'\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0006', 'U', '+', '\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\u0000', '\u0010', '\b', '\b', '\\', 'U', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffff'});
            }
        });
    }

    UnescapeTransliterator(String string, char[] cArray) {
        super(string, null);
        this.spec = cArray;
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        block0: while (n < n2) {
            int n3 = 0;
            while (this.spec[n3] != '\uffff') {
                int n4;
                int n5;
                int n6 = this.spec[n3++];
                int n7 = this.spec[n3++];
                char c = this.spec[n3++];
                int n8 = this.spec[n3++];
                char c2 = this.spec[n3++];
                int n9 = n;
                boolean bl2 = true;
                for (n5 = 0; n5 < n6; ++n5) {
                    if (n9 >= n2 && n5 > 0) {
                        if (bl) break block0;
                        bl2 = false;
                        break;
                    }
                    if ((n4 = replaceable.charAt(n9++)) == this.spec[n3 + n5]) continue;
                    bl2 = false;
                    break;
                }
                if (bl2) {
                    int n10;
                    n4 = 0;
                    int n11 = 0;
                    do {
                        if (n9 >= n2) {
                            if (n9 > n && bl) {
                                break block0;
                            }
                            break;
                        }
                        n10 = replaceable.char32At(n9);
                        int n12 = UCharacter.digit(n10, c);
                        if (n12 < 0) break;
                        n9 += UTF16.getCharCount(n10);
                        n4 = n4 * c + n12;
                    } while (++n11 != c2);
                    boolean bl3 = bl2 = n11 >= n8;
                    if (bl2) {
                        for (n5 = 0; n5 < n7; ++n5) {
                            if (n9 >= n2) {
                                if (n9 > n && bl) break block0;
                                bl2 = false;
                                break;
                            }
                            if ((n10 = (int)replaceable.charAt(n9++)) == this.spec[n3 + n6 + n5]) continue;
                            bl2 = false;
                            break;
                        }
                        if (bl2) {
                            String string = UTF16.valueOf(n4);
                            replaceable.replace(n, n9, string);
                            n2 -= n9 - n - string.length();
                            break;
                        }
                    }
                }
                n3 += n6 + n7;
            }
            if (n >= n2) continue;
            n += UTF16.getCharCount(replaceable.char32At(n));
        }
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
        position.start = n;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        UnicodeSet unicodeSet5 = new UnicodeSet();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (this.spec[n] != '\uffff') {
            int n2;
            int n3 = n + this.spec[n] + this.spec[n + 1] + 5;
            int n4 = this.spec[n + 2];
            for (n2 = 0; n2 < n4; ++n2) {
                Utility.appendNumber(stringBuilder, n2, n4, 0);
            }
            for (n2 = n + 5; n2 < n3; ++n2) {
                unicodeSet5.add(this.spec[n2]);
            }
            n = n3;
        }
        unicodeSet5.addAll(stringBuilder.toString());
        unicodeSet5.retainAll(unicodeSet4);
        if (unicodeSet5.size() > 0) {
            unicodeSet2.addAll(unicodeSet5);
            unicodeSet3.addAll(0, 0x10FFFF);
        }
    }
}

