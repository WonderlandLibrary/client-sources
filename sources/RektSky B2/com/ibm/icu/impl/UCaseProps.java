package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;
import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.util.*;

public final class UCaseProps
{
    private static final byte[] flagsOffset;
    public static final int MAX_STRING_LENGTH = 31;
    public static final int LOC_ROOT = 1;
    static final int LOC_TURKISH = 2;
    static final int LOC_LITHUANIAN = 3;
    static final int LOC_GREEK = 4;
    public static final int LOC_DUTCH = 5;
    private static final String iDot = "i\u0307";
    private static final String jDot = "j\u0307";
    private static final String iOgonekDot = "\u012f\u0307";
    private static final String iDotGrave = "i\u0307\u0300";
    private static final String iDotAcute = "i\u0307\u0301";
    private static final String iDotTilde = "i\u0307\u0303";
    static final int FOLD_CASE_OPTIONS_MASK = 7;
    public static final StringBuilder dummyStringBuilder;
    private int[] indexes;
    private String exceptions;
    private char[] unfold;
    private Trie2_16 trie;
    private static final String DATA_NAME = "ucase";
    private static final String DATA_TYPE = "icu";
    private static final String DATA_FILE_NAME = "ucase.icu";
    private static final int FMT = 1665225541;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_EXC_LENGTH = 3;
    private static final int IX_UNFOLD_LENGTH = 4;
    private static final int IX_TOP = 16;
    public static final int TYPE_MASK = 3;
    public static final int NONE = 0;
    public static final int LOWER = 1;
    public static final int UPPER = 2;
    public static final int TITLE = 3;
    static final int IGNORABLE = 4;
    private static final int EXCEPTION = 8;
    private static final int SENSITIVE = 16;
    private static final int DOT_MASK = 96;
    private static final int SOFT_DOTTED = 32;
    private static final int ABOVE = 64;
    private static final int OTHER_ACCENT = 96;
    private static final int DELTA_SHIFT = 7;
    private static final int EXC_SHIFT = 4;
    private static final int EXC_LOWER = 0;
    private static final int EXC_FOLD = 1;
    private static final int EXC_UPPER = 2;
    private static final int EXC_TITLE = 3;
    private static final int EXC_DELTA = 4;
    private static final int EXC_CLOSURE = 6;
    private static final int EXC_FULL_MAPPINGS = 7;
    private static final int EXC_DOUBLE_SLOTS = 256;
    private static final int EXC_NO_SIMPLE_CASE_FOLDING = 512;
    private static final int EXC_DELTA_IS_NEGATIVE = 1024;
    private static final int EXC_SENSITIVE = 2048;
    private static final int EXC_DOT_SHIFT = 7;
    private static final int EXC_CONDITIONAL_SPECIAL = 16384;
    private static final int EXC_CONDITIONAL_FOLD = 32768;
    private static final int FULL_LOWER = 15;
    private static final int CLOSURE_MAX_LENGTH = 15;
    private static final int UNFOLD_ROWS = 0;
    private static final int UNFOLD_ROW_WIDTH = 1;
    private static final int UNFOLD_STRING_WIDTH = 2;
    public static final UCaseProps INSTANCE;
    
    private UCaseProps() throws IOException {
        final ByteBuffer bytes = ICUBinary.getRequiredData("ucase.icu");
        this.readData(bytes);
    }
    
    private final void readData(final ByteBuffer bytes) throws IOException {
        ICUBinary.readHeader(bytes, 1665225541, new IsAcceptable());
        int count = bytes.getInt();
        if (count < 16) {
            throw new IOException("indexes[0] too small in ucase.icu");
        }
        (this.indexes = new int[count])[0] = count;
        for (int i = 1; i < count; ++i) {
            this.indexes[i] = bytes.getInt();
        }
        this.trie = Trie2_16.createFromSerialized(bytes);
        final int expectedTrieLength = this.indexes[2];
        final int trieLength = this.trie.getSerializedLength();
        if (trieLength > expectedTrieLength) {
            throw new IOException("ucase.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(bytes, expectedTrieLength - trieLength);
        count = this.indexes[3];
        if (count > 0) {
            this.exceptions = ICUBinary.getString(bytes, count, 0);
        }
        count = this.indexes[4];
        if (count > 0) {
            this.unfold = ICUBinary.getChars(bytes, count, 0);
        }
    }
    
    public final void addPropertyStarts(final UnicodeSet set) {
        final Iterator<Trie2.Range> trieIterator = this.trie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
    }
    
    private static final int getExceptionsOffset(final int props) {
        return props >> 4;
    }
    
    static final boolean propsHasException(final int props) {
        return (props & 0x8) != 0x0;
    }
    
    private static final boolean hasSlot(final int flags, final int index) {
        return (flags & 1 << index) != 0x0;
    }
    
    private static final byte slotOffset(final int flags, final int index) {
        return UCaseProps.flagsOffset[flags & (1 << index) - 1];
    }
    
    private final long getSlotValueAndOffset(final int excWord, final int index, int excOffset) {
        long value;
        if ((excWord & 0x100) == 0x0) {
            excOffset += slotOffset(excWord, index);
            value = this.exceptions.charAt(excOffset);
        }
        else {
            excOffset += 2 * slotOffset(excWord, index);
            value = this.exceptions.charAt(excOffset++);
            value = (value << 16 | (long)this.exceptions.charAt(excOffset));
        }
        return value | (long)excOffset << 32;
    }
    
    private final int getSlotValue(final int excWord, final int index, int excOffset) {
        int value;
        if ((excWord & 0x100) == 0x0) {
            excOffset += slotOffset(excWord, index);
            value = this.exceptions.charAt(excOffset);
        }
        else {
            excOffset += 2 * slotOffset(excWord, index);
            value = this.exceptions.charAt(excOffset++);
            value = (value << 16 | this.exceptions.charAt(excOffset));
        }
        return value;
    }
    
    public final int tolower(int c) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (isUpperOrTitleFromProps(props)) {
                c += getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            if (hasSlot(excWord, 4) && isUpperOrTitleFromProps(props)) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            if (hasSlot(excWord, 0)) {
                c = this.getSlotValue(excWord, 0, excOffset);
            }
        }
        return c;
    }
    
    public final int toupper(int c) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (getTypeFromProps(props) == 1) {
                c += getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            if (hasSlot(excWord, 4) && getTypeFromProps(props) == 1) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            if (hasSlot(excWord, 2)) {
                c = this.getSlotValue(excWord, 2, excOffset);
            }
        }
        return c;
    }
    
    public final int totitle(int c) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (getTypeFromProps(props) == 1) {
                c += getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            if (hasSlot(excWord, 4) && getTypeFromProps(props) == 1) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            int index;
            if (hasSlot(excWord, 3)) {
                index = 3;
            }
            else {
                if (!hasSlot(excWord, 2)) {
                    return c;
                }
                index = 2;
            }
            c = this.getSlotValue(excWord, index, excOffset);
        }
        return c;
    }
    
    public final void addCaseClosure(int c, final UnicodeSet set) {
        switch (c) {
            case 73: {
                set.add(105);
            }
            case 105: {
                set.add(73);
            }
            case 304: {
                set.add("i\u0307");
            }
            case 305: {}
            default: {
                final int props = this.trie.get(c);
                if (!propsHasException(props)) {
                    if (getTypeFromProps(props) != 0) {
                        final int delta = getDelta(props);
                        if (delta != 0) {
                            set.add(c + delta);
                        }
                    }
                }
                else {
                    int excOffset = getExceptionsOffset(props);
                    final int excWord = this.exceptions.charAt(excOffset++);
                    final int excOffset2 = excOffset;
                    for (int index = 0; index <= 3; ++index) {
                        if (hasSlot(excWord, index)) {
                            excOffset = excOffset2;
                            c = this.getSlotValue(excWord, index, excOffset);
                            set.add(c);
                        }
                    }
                    if (hasSlot(excWord, 4)) {
                        final int delta2 = this.getSlotValue(excWord, 4, excOffset);
                        set.add(((excWord & 0x400) == 0x0) ? (c + delta2) : (c - delta2));
                    }
                    int closureLength;
                    int closureOffset;
                    if (hasSlot(excWord, 6)) {
                        excOffset = excOffset2;
                        final long value = this.getSlotValueAndOffset(excWord, 6, excOffset);
                        closureLength = ((int)value & 0xF);
                        closureOffset = (int)(value >> 32) + 1;
                    }
                    else {
                        closureLength = 0;
                        closureOffset = 0;
                    }
                    if (hasSlot(excWord, 7)) {
                        excOffset = excOffset2;
                        final long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
                        int fullLength = (int)value;
                        excOffset = (int)(value >> 32) + 1;
                        fullLength &= 0xFFFF;
                        excOffset += (fullLength & 0xF);
                        fullLength >>= 4;
                        final int length = fullLength & 0xF;
                        if (length != 0) {
                            set.add(this.exceptions.substring(excOffset, excOffset + length));
                            excOffset += length;
                        }
                        fullLength >>= 4;
                        excOffset += (fullLength & 0xF);
                        fullLength >>= 4;
                        excOffset = (closureOffset = excOffset + fullLength);
                    }
                    for (int limit = closureOffset + closureLength, index = closureOffset; index < limit; index += UTF16.getCharCount(c)) {
                        c = this.exceptions.codePointAt(index);
                        set.add(c);
                    }
                }
            }
        }
    }
    
    private final int strcmpMax(final String s, int unfoldOffset, int max) {
        int length = s.length();
        max -= length;
        int i1 = 0;
        do {
            int c1 = s.charAt(i1++);
            final int c2 = this.unfold[unfoldOffset++];
            if (c2 == 0) {
                return 1;
            }
            c1 -= c2;
            if (c1 != 0) {
                return c1;
            }
        } while (--length > 0);
        if (max == 0 || this.unfold[unfoldOffset] == '\0') {
            return 0;
        }
        return -max;
    }
    
    public final boolean addStringCaseClosure(final String s, final UnicodeSet set) {
        if (this.unfold == null || s == null) {
            return false;
        }
        final int length = s.length();
        if (length <= 1) {
            return false;
        }
        final int unfoldRows = this.unfold[0];
        final int unfoldRowWidth = this.unfold[1];
        final int unfoldStringWidth = this.unfold[2];
        if (length > unfoldStringWidth) {
            return false;
        }
        int start = 0;
        int limit = unfoldRows;
        while (start < limit) {
            int i = (start + limit) / 2;
            final int unfoldOffset = (i + 1) * unfoldRowWidth;
            final int result = this.strcmpMax(s, unfoldOffset, unfoldStringWidth);
            if (result == 0) {
                int c;
                for (i = unfoldStringWidth; i < unfoldRowWidth && this.unfold[unfoldOffset + i] != '\0'; i += UTF16.getCharCount(c)) {
                    c = UTF16.charAt(this.unfold, unfoldOffset, this.unfold.length, i);
                    set.add(c);
                    this.addCaseClosure(c, set);
                }
                return true;
            }
            if (result < 0) {
                limit = i;
            }
            else {
                start = i + 1;
            }
        }
        return false;
    }
    
    public final int getType(final int c) {
        return getTypeFromProps(this.trie.get(c));
    }
    
    public final int getTypeOrIgnorable(final int c) {
        return getTypeAndIgnorableFromProps(this.trie.get(c));
    }
    
    public final int getDotType(final int c) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            return props & 0x60;
        }
        return this.exceptions.charAt(getExceptionsOffset(props)) >> 7 & 0x60;
    }
    
    public final boolean isSoftDotted(final int c) {
        return this.getDotType(c) == 32;
    }
    
    public final boolean isCaseSensitive(final int c) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            return (props & 0x10) != 0x0;
        }
        return (this.exceptions.charAt(getExceptionsOffset(props)) & '\u0800') != 0x0;
    }
    
    public static final int getCaseLocale(final Locale locale) {
        return getCaseLocale(locale.getLanguage());
    }
    
    public static final int getCaseLocale(final ULocale locale) {
        return getCaseLocale(locale.getLanguage());
    }
    
    private static final int getCaseLocale(final String language) {
        if (language.length() == 2) {
            if (language.equals("en") || language.charAt(0) > 't') {
                return 1;
            }
            if (language.equals("tr") || language.equals("az")) {
                return 2;
            }
            if (language.equals("el")) {
                return 4;
            }
            if (language.equals("lt")) {
                return 3;
            }
            if (language.equals("nl")) {
                return 5;
            }
        }
        else if (language.length() == 3) {
            if (language.equals("tur") || language.equals("aze")) {
                return 2;
            }
            if (language.equals("ell")) {
                return 4;
            }
            if (language.equals("lit")) {
                return 3;
            }
            if (language.equals("nld")) {
                return 5;
            }
        }
        return 1;
    }
    
    private final boolean isFollowedByCasedLetter(final ContextIterator iter, final int dir) {
        if (iter == null) {
            return false;
        }
        iter.reset(dir);
        int c;
        while ((c = iter.next()) >= 0) {
            final int type = this.getTypeOrIgnorable(c);
            if ((type & 0x4) != 0x0) {
                continue;
            }
            return type != 0;
        }
        return false;
    }
    
    private final boolean isPrecededBySoftDotted(final ContextIterator iter) {
        if (iter == null) {
            return false;
        }
        iter.reset(-1);
        int c;
        while ((c = iter.next()) >= 0) {
            final int dotType = this.getDotType(c);
            if (dotType == 32) {
                return true;
            }
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isPrecededBy_I(final ContextIterator iter) {
        if (iter == null) {
            return false;
        }
        iter.reset(-1);
        int c;
        while ((c = iter.next()) >= 0) {
            if (c == 73) {
                return true;
            }
            final int dotType = this.getDotType(c);
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isFollowedByMoreAbove(final ContextIterator iter) {
        if (iter == null) {
            return false;
        }
        iter.reset(1);
        int c;
        while ((c = iter.next()) >= 0) {
            final int dotType = this.getDotType(c);
            if (dotType == 64) {
                return true;
            }
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isFollowedByDotAbove(final ContextIterator iter) {
        if (iter == null) {
            return false;
        }
        iter.reset(1);
        int c;
        while ((c = iter.next()) >= 0) {
            if (c == 775) {
                return true;
            }
            final int dotType = this.getDotType(c);
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    public final int toFullLower(final int c, final ContextIterator iter, final Appendable out, final int caseLocale) {
        int result = c;
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (isUpperOrTitleFromProps(props)) {
                result = c + getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            final int excOffset2 = excOffset;
            if ((excWord & 0x4000) != 0x0) {
                Label_0268: {
                    if (caseLocale == 3) {
                        if (((c != 73 && c != 74 && c != 302) || !this.isFollowedByMoreAbove(iter)) && c != 204 && c != 205) {
                            if (c != 296) {
                                break Label_0268;
                            }
                        }
                        try {
                            switch (c) {
                                case 73: {
                                    out.append("i\u0307");
                                    return 2;
                                }
                                case 74: {
                                    out.append("j\u0307");
                                    return 2;
                                }
                                case 302: {
                                    out.append("\u012f\u0307");
                                    return 2;
                                }
                                case 204: {
                                    out.append("i\u0307\u0300");
                                    return 3;
                                }
                                case 205: {
                                    out.append("i\u0307\u0301");
                                    return 3;
                                }
                                case 296: {
                                    out.append("i\u0307\u0303");
                                    return 3;
                                }
                                default: {
                                    return 0;
                                }
                            }
                        }
                        catch (IOException e) {
                            throw new ICUUncheckedIOException(e);
                        }
                    }
                }
                if (caseLocale == 2 && c == 304) {
                    return 105;
                }
                if (caseLocale == 2 && c == 775 && this.isPrecededBy_I(iter)) {
                    return 0;
                }
                if (caseLocale == 2 && c == 73 && !this.isFollowedByDotAbove(iter)) {
                    return 305;
                }
                if (c == 304) {
                    try {
                        out.append("i\u0307");
                        return 2;
                    }
                    catch (IOException e) {
                        throw new ICUUncheckedIOException(e);
                    }
                }
                if (c == 931 && !this.isFollowedByCasedLetter(iter, 1) && this.isFollowedByCasedLetter(iter, -1)) {
                    return 962;
                }
            }
            else if (hasSlot(excWord, 7)) {
                final long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
                final int full = (int)value & 0xF;
                if (full != 0) {
                    excOffset = (int)(value >> 32) + 1;
                    try {
                        out.append(this.exceptions, excOffset, excOffset + full);
                        return full;
                    }
                    catch (IOException e2) {
                        throw new ICUUncheckedIOException(e2);
                    }
                }
            }
            if (hasSlot(excWord, 4) && isUpperOrTitleFromProps(props)) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            if (hasSlot(excWord, 0)) {
                result = this.getSlotValue(excWord, 0, excOffset2);
            }
        }
        return (result == c) ? (~result) : result;
    }
    
    private final int toUpperOrTitle(final int c, final ContextIterator iter, final Appendable out, final int loc, final boolean upperNotTitle) {
        int result = c;
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (getTypeFromProps(props) == 1) {
                result = c + getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            final int excOffset2 = excOffset;
            if ((excWord & 0x4000) != 0x0) {
                if (loc == 2 && c == 105) {
                    return 304;
                }
                if (loc == 3 && c == 775 && this.isPrecededBySoftDotted(iter)) {
                    return 0;
                }
            }
            else if (hasSlot(excWord, 7)) {
                final long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
                int full = (int)value & 0xFFFF;
                excOffset = (int)(value >> 32) + 1;
                excOffset += (full & 0xF);
                full >>= 4;
                excOffset += (full & 0xF);
                full >>= 4;
                if (upperNotTitle) {
                    full &= 0xF;
                }
                else {
                    excOffset += (full & 0xF);
                    full = (full >> 4 & 0xF);
                }
                if (full != 0) {
                    try {
                        out.append(this.exceptions, excOffset, excOffset + full);
                        return full;
                    }
                    catch (IOException e) {
                        throw new ICUUncheckedIOException(e);
                    }
                }
            }
            if (hasSlot(excWord, 4) && getTypeFromProps(props) == 1) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            int index;
            if (!upperNotTitle && hasSlot(excWord, 3)) {
                index = 3;
            }
            else {
                if (!hasSlot(excWord, 2)) {
                    return ~c;
                }
                index = 2;
            }
            result = this.getSlotValue(excWord, index, excOffset2);
        }
        return (result == c) ? (~result) : result;
    }
    
    public final int toFullUpper(final int c, final ContextIterator iter, final Appendable out, final int caseLocale) {
        return this.toUpperOrTitle(c, iter, out, caseLocale, true);
    }
    
    public final int toFullTitle(final int c, final ContextIterator iter, final Appendable out, final int caseLocale) {
        return this.toUpperOrTitle(c, iter, out, caseLocale, false);
    }
    
    public final int fold(int c, final int options) {
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (isUpperOrTitleFromProps(props)) {
                c += getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            if ((excWord & 0x8000) != 0x0) {
                if ((options & 0x7) == 0x0) {
                    if (c == 73) {
                        return 105;
                    }
                    if (c == 304) {
                        return c;
                    }
                }
                else {
                    if (c == 73) {
                        return 305;
                    }
                    if (c == 304) {
                        return 105;
                    }
                }
            }
            if ((excWord & 0x200) != 0x0) {
                return c;
            }
            if (hasSlot(excWord, 4) && isUpperOrTitleFromProps(props)) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            int index;
            if (hasSlot(excWord, 1)) {
                index = 1;
            }
            else {
                if (!hasSlot(excWord, 0)) {
                    return c;
                }
                index = 0;
            }
            c = this.getSlotValue(excWord, index, excOffset);
        }
        return c;
    }
    
    public final int toFullFolding(final int c, final Appendable out, final int options) {
        int result = c;
        final int props = this.trie.get(c);
        if (!propsHasException(props)) {
            if (isUpperOrTitleFromProps(props)) {
                result = c + getDelta(props);
            }
        }
        else {
            int excOffset = getExceptionsOffset(props);
            final int excWord = this.exceptions.charAt(excOffset++);
            final int excOffset2 = excOffset;
            Label_0237: {
                if ((excWord & 0x8000) != 0x0) {
                    if ((options & 0x7) == 0x0) {
                        if (c == 73) {
                            return 105;
                        }
                        if (c != 304) {
                            break Label_0237;
                        }
                        try {
                            out.append("i\u0307");
                            return 2;
                        }
                        catch (IOException e) {
                            throw new ICUUncheckedIOException(e);
                        }
                    }
                    if (c == 73) {
                        return 305;
                    }
                    if (c == 304) {
                        return 105;
                    }
                }
                else if (hasSlot(excWord, 7)) {
                    final long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
                    int full = (int)value & 0xFFFF;
                    excOffset = (int)(value >> 32) + 1;
                    excOffset += (full & 0xF);
                    full = (full >> 4 & 0xF);
                    if (full != 0) {
                        try {
                            out.append(this.exceptions, excOffset, excOffset + full);
                            return full;
                        }
                        catch (IOException e2) {
                            throw new ICUUncheckedIOException(e2);
                        }
                    }
                }
            }
            if ((excWord & 0x200) != 0x0) {
                return ~c;
            }
            if (hasSlot(excWord, 4) && isUpperOrTitleFromProps(props)) {
                final int delta = this.getSlotValue(excWord, 4, excOffset);
                return ((excWord & 0x400) == 0x0) ? (c + delta) : (c - delta);
            }
            int index;
            if (hasSlot(excWord, 1)) {
                index = 1;
            }
            else {
                if (!hasSlot(excWord, 0)) {
                    return ~c;
                }
                index = 0;
            }
            result = this.getSlotValue(excWord, index, excOffset2);
        }
        return (result == c) ? (~result) : result;
    }
    
    public final boolean hasBinaryProperty(final int c, final int which) {
        switch (which) {
            case 22: {
                return 1 == this.getType(c);
            }
            case 30: {
                return 2 == this.getType(c);
            }
            case 27: {
                return this.isSoftDotted(c);
            }
            case 34: {
                return this.isCaseSensitive(c);
            }
            case 49: {
                return 0 != this.getType(c);
            }
            case 50: {
                return this.getTypeOrIgnorable(c) >> 2 != 0;
            }
            case 51: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullLower(c, null, UCaseProps.dummyStringBuilder, 1) >= 0;
            }
            case 52: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullUpper(c, null, UCaseProps.dummyStringBuilder, 1) >= 0;
            }
            case 53: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullTitle(c, null, UCaseProps.dummyStringBuilder, 1) >= 0;
            }
            case 55: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullLower(c, null, UCaseProps.dummyStringBuilder, 1) >= 0 || this.toFullUpper(c, null, UCaseProps.dummyStringBuilder, 1) >= 0 || this.toFullTitle(c, null, UCaseProps.dummyStringBuilder, 1) >= 0;
            }
            default: {
                return false;
            }
        }
    }
    
    static Trie2_16 getTrie() {
        return UCaseProps.INSTANCE.trie;
    }
    
    static final int getTypeFromProps(final int props) {
        return props & 0x3;
    }
    
    private static final int getTypeAndIgnorableFromProps(final int props) {
        return props & 0x7;
    }
    
    static final boolean isUpperOrTitleFromProps(final int props) {
        return (props & 0x2) != 0x0;
    }
    
    static final int getDelta(final int props) {
        return (short)props >> 7;
    }
    
    static {
        flagsOffset = new byte[] { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8 };
        dummyStringBuilder = new StringBuilder();
        try {
            INSTANCE = new UCaseProps();
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] version) {
            return version[0] == 4;
        }
    }
    
    static final class LatinCase
    {
        static final char LIMIT = '\u0180';
        static final char LONG_S = '\u017f';
        static final byte EXC = Byte.MIN_VALUE;
        static final byte[] TO_LOWER_NORMAL;
        static final byte[] TO_LOWER_TR_LT;
        static final byte[] TO_UPPER_NORMAL;
        static final byte[] TO_UPPER_TR;
        
        static {
            TO_LOWER_NORMAL = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128 };
            TO_LOWER_TR_LT = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, -128, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128 };
            TO_UPPER_NORMAL = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128 };
            TO_UPPER_TR = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128 };
        }
    }
    
    public interface ContextIterator
    {
        void reset(final int p0);
        
        int next();
    }
}
