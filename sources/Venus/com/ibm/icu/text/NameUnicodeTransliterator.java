/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.UCharacterName;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeSet;

class NameUnicodeTransliterator
extends Transliterator {
    static final String _ID = "Name-Any";
    static final String OPEN_PAT = "\\N~{~";
    static final char OPEN_DELIM = '\\';
    static final char CLOSE_DELIM = '}';
    static final char SPACE = ' ';

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NameUnicodeTransliterator(null);
            }
        });
    }

    public NameUnicodeTransliterator(UnicodeFilter unicodeFilter) {
        super(_ID, unicodeFilter);
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = UCharacterName.INSTANCE.getMaxCharNameLength() + 1;
        StringBuffer stringBuffer = new StringBuffer(n);
        UnicodeSet unicodeSet = new UnicodeSet();
        UCharacterName.INSTANCE.getCharNameCharacters(unicodeSet);
        int n2 = position.start;
        int n3 = position.limit;
        int n4 = 0;
        int n5 = -1;
        block4: while (n2 < n3) {
            int n6 = replaceable.char32At(n2);
            switch (n4) {
                case 0: {
                    if (n6 != 92) break;
                    n5 = n2;
                    int n7 = Utility.parsePattern(OPEN_PAT, replaceable, n2, n3);
                    if (n7 < 0 || n7 >= n3) break;
                    n4 = 1;
                    stringBuffer.setLength(0);
                    n2 = n7;
                    continue block4;
                }
                case 1: {
                    int n7;
                    if (PatternProps.isWhiteSpace(n6)) {
                        if (stringBuffer.length() <= 0 || stringBuffer.charAt(stringBuffer.length() - 1) == ' ') break;
                        stringBuffer.append(' ');
                        if (stringBuffer.length() <= n) break;
                        n4 = 0;
                        break;
                    }
                    if (n6 == 125) {
                        n7 = stringBuffer.length();
                        if (n7 > 0 && stringBuffer.charAt(n7 - 1) == ' ') {
                            stringBuffer.setLength(--n7);
                        }
                        if ((n6 = UCharacter.getCharFromExtendedName(stringBuffer.toString())) != -1) {
                            String string = UTF16.valueOf(n6);
                            replaceable.replace(n5, ++n2, string);
                            int n8 = n2 - n5 - string.length();
                            n2 -= n8;
                            n3 -= n8;
                        }
                        n4 = 0;
                        n5 = -1;
                        continue block4;
                    }
                    if (unicodeSet.contains(n6)) {
                        UTF16.append(stringBuffer, n6);
                        if (stringBuffer.length() < n) break;
                        n4 = 0;
                        break;
                    }
                    --n2;
                    n4 = 0;
                }
            }
            n2 += UTF16.getCharCount(n6);
        }
        position.contextLimit += n3 - position.limit;
        position.limit = n3;
        position.start = bl && n5 >= 0 ? n5 : n2;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        if (!unicodeSet4.containsAll("\\N{") || !unicodeSet4.contains(0)) {
            return;
        }
        UnicodeSet unicodeSet5 = new UnicodeSet().addAll(48, 57).addAll(65, 70).addAll(97, 122).add(60).add(62).add(40).add(41).add(45).add(32).addAll("\\N{").add(125);
        unicodeSet5.retainAll(unicodeSet4);
        if (unicodeSet5.size() > 0) {
            unicodeSet2.addAll(unicodeSet5);
            unicodeSet3.addAll(0, 0x10FFFF);
        }
    }
}

