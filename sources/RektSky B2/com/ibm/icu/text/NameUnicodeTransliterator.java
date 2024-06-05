package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;

class NameUnicodeTransliterator extends Transliterator
{
    static final String _ID = "Name-Any";
    static final String OPEN_PAT = "\\N~{~";
    static final char OPEN_DELIM = '\\';
    static final char CLOSE_DELIM = '}';
    static final char SPACE = ' ';
    
    static void register() {
        Transliterator.registerFactory("Name-Any", new Factory() {
            @Override
            public Transliterator getInstance(final String ID) {
                return new NameUnicodeTransliterator(null);
            }
        });
    }
    
    public NameUnicodeTransliterator(final UnicodeFilter filter) {
        super("Name-Any", filter);
    }
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position offsets, final boolean isIncremental) {
        final int maxLen = UCharacterName.INSTANCE.getMaxCharNameLength() + 1;
        final StringBuffer name = new StringBuffer(maxLen);
        final UnicodeSet legal = new UnicodeSet();
        UCharacterName.INSTANCE.getCharNameCharacters(legal);
        int cursor = offsets.start;
        int limit = offsets.limit;
        int mode = 0;
        int openPos = -1;
        while (cursor < limit) {
            int c = text.char32At(cursor);
            switch (mode) {
                case 0: {
                    if (c != 92) {
                        break;
                    }
                    openPos = cursor;
                    final int i = Utility.parsePattern("\\N~{~", text, cursor, limit);
                    if (i >= 0 && i < limit) {
                        mode = 1;
                        name.setLength(0);
                        cursor = i;
                        continue;
                    }
                    break;
                }
                case 1: {
                    if (PatternProps.isWhiteSpace(c)) {
                        if (name.length() <= 0 || name.charAt(name.length() - 1) == ' ') {
                            break;
                        }
                        name.append(' ');
                        if (name.length() > maxLen) {
                            mode = 0;
                            break;
                        }
                        break;
                    }
                    else {
                        if (c == 125) {
                            int len = name.length();
                            if (len > 0 && name.charAt(len - 1) == ' ') {
                                name.setLength(--len);
                            }
                            c = UCharacter.getCharFromExtendedName(name.toString());
                            if (c != -1) {
                                ++cursor;
                                final String str = UTF16.valueOf(c);
                                text.replace(openPos, cursor, str);
                                final int delta = cursor - openPos - str.length();
                                cursor -= delta;
                                limit -= delta;
                            }
                            mode = 0;
                            openPos = -1;
                            continue;
                        }
                        if (!legal.contains(c)) {
                            --cursor;
                            mode = 0;
                            break;
                        }
                        UTF16.append(name, c);
                        if (name.length() >= maxLen) {
                            mode = 0;
                            break;
                        }
                        break;
                    }
                    break;
                }
            }
            cursor += UTF16.getCharCount(c);
        }
        offsets.contextLimit += limit - offsets.limit;
        offsets.limit = limit;
        offsets.start = ((isIncremental && openPos >= 0) ? openPos : cursor);
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        if (!myFilter.containsAll("\\N{") || !myFilter.contains(125)) {
            return;
        }
        final UnicodeSet items = new UnicodeSet().addAll(48, 57).addAll(65, 70).addAll(97, 122).add(60).add(62).add(40).add(41).add(45).add(32).addAll("\\N{").add(125);
        items.retainAll(myFilter);
        if (items.size() > 0) {
            sourceSet.addAll(items);
            targetSet.addAll(0, 1114111);
        }
    }
}
