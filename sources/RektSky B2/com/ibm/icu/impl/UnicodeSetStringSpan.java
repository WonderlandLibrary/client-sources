package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.text.*;

public class UnicodeSetStringSpan
{
    public static final int WITH_COUNT = 64;
    public static final int FWD = 32;
    public static final int BACK = 16;
    public static final int CONTAINED = 2;
    public static final int NOT_CONTAINED = 1;
    public static final int ALL = 127;
    public static final int FWD_UTF16_CONTAINED = 34;
    public static final int FWD_UTF16_NOT_CONTAINED = 33;
    public static final int BACK_UTF16_CONTAINED = 18;
    public static final int BACK_UTF16_NOT_CONTAINED = 17;
    static final short ALL_CP_CONTAINED = 255;
    static final short LONG_SPAN = 254;
    private UnicodeSet spanSet;
    private UnicodeSet spanNotSet;
    private ArrayList<String> strings;
    private short[] spanLengths;
    private final int maxLength16;
    private boolean someRelevant;
    private boolean all;
    private OffsetList offsets;
    
    public UnicodeSetStringSpan(final UnicodeSet set, final ArrayList<String> setStrings, final int which) {
        this.spanSet = new UnicodeSet(0, 1114111);
        this.strings = setStrings;
        this.all = (which == 127);
        this.spanSet.retainAll(set);
        if (0x0 != (which & 0x1)) {
            this.spanNotSet = this.spanSet;
        }
        this.offsets = new OffsetList();
        final int stringsLength = this.strings.size();
        int maxLength16 = 0;
        this.someRelevant = false;
        for (int i = 0; i < stringsLength; ++i) {
            final String string = this.strings.get(i);
            final int length16 = string.length();
            final int spanLength = this.spanSet.span(string, UnicodeSet.SpanCondition.CONTAINED);
            if (spanLength < length16) {
                this.someRelevant = true;
            }
            if (length16 > maxLength16) {
                maxLength16 = length16;
            }
        }
        this.maxLength16 = maxLength16;
        if (!this.someRelevant && (which & 0x40) == 0x0) {
            return;
        }
        if (this.all) {
            this.spanSet.freeze();
        }
        int allocSize;
        if (this.all) {
            allocSize = stringsLength * 2;
        }
        else {
            allocSize = stringsLength;
        }
        this.spanLengths = new short[allocSize];
        int spanBackLengthsOffset;
        if (this.all) {
            spanBackLengthsOffset = stringsLength;
        }
        else {
            spanBackLengthsOffset = 0;
        }
        for (int i = 0; i < stringsLength; ++i) {
            final String string2 = this.strings.get(i);
            final int length17 = string2.length();
            int spanLength = this.spanSet.span(string2, UnicodeSet.SpanCondition.CONTAINED);
            if (spanLength < length17) {
                if (0x0 != (which & 0x2)) {
                    if (0x0 != (which & 0x20)) {
                        this.spanLengths[i] = makeSpanLengthByte(spanLength);
                    }
                    if (0x0 != (which & 0x10)) {
                        spanLength = length17 - this.spanSet.spanBack(string2, length17, UnicodeSet.SpanCondition.CONTAINED);
                        this.spanLengths[spanBackLengthsOffset + i] = makeSpanLengthByte(spanLength);
                    }
                }
                else {
                    this.spanLengths[i] = (this.spanLengths[spanBackLengthsOffset + i] = 0);
                }
                if (0x0 != (which & 0x1)) {
                    if (0x0 != (which & 0x20)) {
                        final int c = string2.codePointAt(0);
                        this.addToSpanNotSet(c);
                    }
                    if (0x0 != (which & 0x10)) {
                        final int c = string2.codePointBefore(length17);
                        this.addToSpanNotSet(c);
                    }
                }
            }
            else if (this.all) {
                this.spanLengths[i] = (this.spanLengths[spanBackLengthsOffset + i] = 255);
            }
            else {
                this.spanLengths[i] = 255;
            }
        }
        if (this.all) {
            this.spanNotSet.freeze();
        }
    }
    
    public UnicodeSetStringSpan(final UnicodeSetStringSpan otherStringSpan, final ArrayList<String> newParentSetStrings) {
        this.spanSet = otherStringSpan.spanSet;
        this.strings = newParentSetStrings;
        this.maxLength16 = otherStringSpan.maxLength16;
        this.someRelevant = otherStringSpan.someRelevant;
        this.all = true;
        if (Utility.sameObjects(otherStringSpan.spanNotSet, otherStringSpan.spanSet)) {
            this.spanNotSet = this.spanSet;
        }
        else {
            this.spanNotSet = (UnicodeSet)otherStringSpan.spanNotSet.clone();
        }
        this.offsets = new OffsetList();
        this.spanLengths = otherStringSpan.spanLengths.clone();
    }
    
    public boolean needsStringSpanUTF16() {
        return this.someRelevant;
    }
    
    public boolean contains(final int c) {
        return this.spanSet.contains(c);
    }
    
    private void addToSpanNotSet(final int c) {
        if (Utility.sameObjects(this.spanNotSet, null) || Utility.sameObjects(this.spanNotSet, this.spanSet)) {
            if (this.spanSet.contains(c)) {
                return;
            }
            this.spanNotSet = this.spanSet.cloneAsThawed();
        }
        this.spanNotSet.add(c);
    }
    
    public int span(final CharSequence s, final int start, final UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(s, start, null);
        }
        final int spanLimit = this.spanSet.span(s, start, UnicodeSet.SpanCondition.CONTAINED);
        if (spanLimit == s.length()) {
            return spanLimit;
        }
        return this.spanWithStrings(s, start, spanLimit, spanCondition);
    }
    
    private synchronized int spanWithStrings(final CharSequence s, final int start, int spanLimit, final UnicodeSet.SpanCondition spanCondition) {
        int initSize = 0;
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            initSize = this.maxLength16;
        }
        this.offsets.setMaxLength(initSize);
        final int length = s.length();
        int pos = spanLimit;
        int rest = length - spanLimit;
        int spanLength = spanLimit - start;
        final int stringsLength = this.strings.size();
        while (true) {
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                for (int i = 0; i < stringsLength; ++i) {
                    int overlap = this.spanLengths[i];
                    if (overlap != 255) {
                        final String string = this.strings.get(i);
                        final int length2 = string.length();
                        if (overlap >= 254) {
                            overlap = length2;
                            overlap = string.offsetByCodePoints(overlap, -1);
                        }
                        if (overlap > spanLength) {
                            overlap = spanLength;
                        }
                        for (int inc = length2 - overlap; inc <= rest; ++inc) {
                            if (!this.offsets.containsOffset(inc) && matches16CPB(s, pos - overlap, length, string, length2)) {
                                if (inc == rest) {
                                    return length;
                                }
                                this.offsets.addOffset(inc);
                            }
                            if (overlap == 0) {
                                break;
                            }
                            --overlap;
                        }
                    }
                }
            }
            else {
                int maxInc = 0;
                int maxOverlap = 0;
                for (int i = 0; i < stringsLength; ++i) {
                    int overlap2 = this.spanLengths[i];
                    final String string2 = this.strings.get(i);
                    final int length3 = string2.length();
                    if (overlap2 >= 254) {
                        overlap2 = length3;
                    }
                    if (overlap2 > spanLength) {
                        overlap2 = spanLength;
                    }
                    for (int inc2 = length3 - overlap2; inc2 <= rest; ++inc2) {
                        if (overlap2 < maxOverlap) {
                            break;
                        }
                        if ((overlap2 > maxOverlap || inc2 > maxInc) && matches16CPB(s, pos - overlap2, length, string2, length3)) {
                            maxInc = inc2;
                            maxOverlap = overlap2;
                            break;
                        }
                        --overlap2;
                    }
                }
                if (maxInc != 0 || maxOverlap != 0) {
                    pos += maxInc;
                    rest -= maxInc;
                    if (rest == 0) {
                        return length;
                    }
                    spanLength = 0;
                    continue;
                }
            }
            if (spanLength != 0 || pos == 0) {
                if (this.offsets.isEmpty()) {
                    return pos;
                }
            }
            else if (this.offsets.isEmpty()) {
                spanLimit = this.spanSet.span(s, pos, UnicodeSet.SpanCondition.CONTAINED);
                spanLength = spanLimit - pos;
                if (spanLength == rest || spanLength == 0) {
                    return spanLimit;
                }
                pos += spanLength;
                rest -= spanLength;
                continue;
            }
            else {
                spanLength = spanOne(this.spanSet, s, pos, rest);
                if (spanLength > 0) {
                    if (spanLength == rest) {
                        return length;
                    }
                    pos += spanLength;
                    rest -= spanLength;
                    this.offsets.shift(spanLength);
                    spanLength = 0;
                    continue;
                }
            }
            final int minOffset = this.offsets.popMinimum(null);
            pos += minOffset;
            rest -= minOffset;
            spanLength = 0;
        }
    }
    
    public int spanAndCount(final CharSequence s, final int start, final UnicodeSet.SpanCondition spanCondition, final OutputInt outCount) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(s, start, outCount);
        }
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            return this.spanContainedAndCount(s, start, outCount);
        }
        final int stringsLength = this.strings.size();
        final int length = s.length();
        int pos = start;
        int rest = length - start;
        int count = 0;
        while (rest != 0) {
            final int cpLength = spanOne(this.spanSet, s, pos, rest);
            int maxInc = (cpLength > 0) ? cpLength : 0;
            for (int i = 0; i < stringsLength; ++i) {
                final String string = this.strings.get(i);
                final int length2 = string.length();
                if (maxInc < length2 && length2 <= rest && matches16CPB(s, pos, length, string, length2)) {
                    maxInc = length2;
                }
            }
            if (maxInc == 0) {
                outCount.value = count;
                return pos;
            }
            ++count;
            pos += maxInc;
            rest -= maxInc;
        }
        outCount.value = count;
        return pos;
    }
    
    private synchronized int spanContainedAndCount(final CharSequence s, final int start, final OutputInt outCount) {
        this.offsets.setMaxLength(this.maxLength16);
        final int stringsLength = this.strings.size();
        final int length = s.length();
        int pos = start;
        int rest = length - start;
        int count = 0;
        while (rest != 0) {
            final int cpLength = spanOne(this.spanSet, s, pos, rest);
            if (cpLength > 0) {
                this.offsets.addOffsetAndCount(cpLength, count + 1);
            }
            for (int i = 0; i < stringsLength; ++i) {
                final String string = this.strings.get(i);
                final int length2 = string.length();
                if (length2 <= rest && !this.offsets.hasCountAtOffset(length2, count + 1) && matches16CPB(s, pos, length, string, length2)) {
                    this.offsets.addOffsetAndCount(length2, count + 1);
                }
            }
            if (this.offsets.isEmpty()) {
                outCount.value = count;
                return pos;
            }
            final int minOffset = this.offsets.popMinimum(outCount);
            count = outCount.value;
            pos += minOffset;
            rest -= minOffset;
        }
        outCount.value = count;
        return pos;
    }
    
    public synchronized int spanBack(final CharSequence s, final int length, final UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNotBack(s, length);
        }
        int pos = this.spanSet.spanBack(s, length, UnicodeSet.SpanCondition.CONTAINED);
        if (pos == 0) {
            return 0;
        }
        int spanLength = length - pos;
        int initSize = 0;
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            initSize = this.maxLength16;
        }
        this.offsets.setMaxLength(initSize);
        final int stringsLength = this.strings.size();
        int spanBackLengthsOffset = 0;
        if (this.all) {
            spanBackLengthsOffset = stringsLength;
        }
        while (true) {
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                for (int i = 0; i < stringsLength; ++i) {
                    int overlap = this.spanLengths[spanBackLengthsOffset + i];
                    if (overlap != 255) {
                        final String string = this.strings.get(i);
                        final int length2 = string.length();
                        if (overlap >= 254) {
                            overlap = length2;
                            int len1 = 0;
                            len1 = string.offsetByCodePoints(0, 1);
                            overlap -= len1;
                        }
                        if (overlap > spanLength) {
                            overlap = spanLength;
                        }
                        for (int dec = length2 - overlap; dec <= pos; ++dec) {
                            if (!this.offsets.containsOffset(dec) && matches16CPB(s, pos - dec, length, string, length2)) {
                                if (dec == pos) {
                                    return 0;
                                }
                                this.offsets.addOffset(dec);
                            }
                            if (overlap == 0) {
                                break;
                            }
                            --overlap;
                        }
                    }
                }
            }
            else {
                int maxDec = 0;
                int maxOverlap = 0;
                for (int i = 0; i < stringsLength; ++i) {
                    int overlap2 = this.spanLengths[spanBackLengthsOffset + i];
                    final String string2 = this.strings.get(i);
                    final int length3 = string2.length();
                    if (overlap2 >= 254) {
                        overlap2 = length3;
                    }
                    if (overlap2 > spanLength) {
                        overlap2 = spanLength;
                    }
                    for (int dec2 = length3 - overlap2; dec2 <= pos; ++dec2) {
                        if (overlap2 < maxOverlap) {
                            break;
                        }
                        if ((overlap2 > maxOverlap || dec2 > maxDec) && matches16CPB(s, pos - dec2, length, string2, length3)) {
                            maxDec = dec2;
                            maxOverlap = overlap2;
                            break;
                        }
                        --overlap2;
                    }
                }
                if (maxDec != 0 || maxOverlap != 0) {
                    pos -= maxDec;
                    if (pos == 0) {
                        return 0;
                    }
                    spanLength = 0;
                    continue;
                }
            }
            if (spanLength != 0 || pos == length) {
                if (this.offsets.isEmpty()) {
                    return pos;
                }
            }
            else if (this.offsets.isEmpty()) {
                final int oldPos = pos;
                pos = this.spanSet.spanBack(s, oldPos, UnicodeSet.SpanCondition.CONTAINED);
                spanLength = oldPos - pos;
                if (pos == 0 || spanLength == 0) {
                    return pos;
                }
                continue;
            }
            else {
                spanLength = spanOneBack(this.spanSet, s, pos);
                if (spanLength > 0) {
                    if (spanLength == pos) {
                        return 0;
                    }
                    pos -= spanLength;
                    this.offsets.shift(spanLength);
                    spanLength = 0;
                    continue;
                }
            }
            pos -= this.offsets.popMinimum(null);
            spanLength = 0;
        }
    }
    
    private int spanNot(final CharSequence s, final int start, final OutputInt outCount) {
        final int length = s.length();
        int pos = start;
        int rest = length - start;
        final int stringsLength = this.strings.size();
        int count = 0;
        do {
            int spanLimit;
            if (outCount == null) {
                spanLimit = this.spanNotSet.span(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED);
            }
            else {
                spanLimit = this.spanNotSet.spanAndCount(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED, outCount);
                count = (outCount.value += count);
            }
            if (spanLimit == length) {
                return length;
            }
            pos = spanLimit;
            rest = length - spanLimit;
            final int cpLength = spanOne(this.spanSet, s, pos, rest);
            if (cpLength > 0) {
                return pos;
            }
            for (int i = 0; i < stringsLength; ++i) {
                if (this.spanLengths[i] != 255) {
                    final String string = this.strings.get(i);
                    final int length2 = string.length();
                    if (length2 <= rest && matches16CPB(s, pos, length, string, length2)) {
                        return pos;
                    }
                }
            }
            pos -= cpLength;
            rest += cpLength;
            ++count;
        } while (rest != 0);
        if (outCount != null) {
            outCount.value = count;
        }
        return length;
    }
    
    private int spanNotBack(final CharSequence s, final int length) {
        int pos = length;
        final int stringsLength = this.strings.size();
        do {
            pos = this.spanNotSet.spanBack(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED);
            if (pos == 0) {
                return 0;
            }
            final int cpLength = spanOneBack(this.spanSet, s, pos);
            if (cpLength > 0) {
                return pos;
            }
            for (int i = 0; i < stringsLength; ++i) {
                if (this.spanLengths[i] != 255) {
                    final String string = this.strings.get(i);
                    final int length2 = string.length();
                    if (length2 <= pos && matches16CPB(s, pos - length2, length, string, length2)) {
                        return pos;
                    }
                }
            }
            pos += cpLength;
        } while (pos != 0);
        return 0;
    }
    
    static short makeSpanLengthByte(final int spanLength) {
        return (short)((spanLength < 254) ? ((short)spanLength) : 254);
    }
    
    private static boolean matches16(final CharSequence s, final int start, final String t, int length) {
        int end = start + length;
        while (length-- > 0) {
            if (s.charAt(--end) != t.charAt(length)) {
                return false;
            }
        }
        return true;
    }
    
    static boolean matches16CPB(final CharSequence s, final int start, final int limit, final String t, final int tlength) {
        return matches16(s, start, t, tlength) && (0 >= start || !Character.isHighSurrogate(s.charAt(start - 1)) || !Character.isLowSurrogate(s.charAt(start))) && (start + tlength >= limit || !Character.isHighSurrogate(s.charAt(start + tlength - 1)) || !Character.isLowSurrogate(s.charAt(start + tlength)));
    }
    
    static int spanOne(final UnicodeSet set, final CharSequence s, final int start, final int length) {
        final char c = s.charAt(start);
        if (c >= '\ud800' && c <= '\udbff' && length >= 2) {
            final char c2 = s.charAt(start + 1);
            if (UTF16.isTrailSurrogate(c2)) {
                final int supplementary = Character.toCodePoint(c, c2);
                return set.contains(supplementary) ? 2 : -2;
            }
        }
        return set.contains(c) ? 1 : -1;
    }
    
    static int spanOneBack(final UnicodeSet set, final CharSequence s, final int length) {
        final char c = s.charAt(length - 1);
        if (c >= '\udc00' && c <= '\udfff' && length >= 2) {
            final char c2 = s.charAt(length - 2);
            if (UTF16.isLeadSurrogate(c2)) {
                final int supplementary = Character.toCodePoint(c2, c);
                return set.contains(supplementary) ? 2 : -2;
            }
        }
        return set.contains(c) ? 1 : -1;
    }
    
    private static final class OffsetList
    {
        private int[] list;
        private int length;
        private int start;
        
        public OffsetList() {
            this.list = new int[16];
        }
        
        public void setMaxLength(final int maxLength) {
            if (maxLength > this.list.length) {
                this.list = new int[maxLength];
            }
            this.clear();
        }
        
        public void clear() {
            int i = this.list.length;
            while (i-- > 0) {
                this.list[i] = 0;
            }
            final int n = 0;
            this.length = n;
            this.start = n;
        }
        
        public boolean isEmpty() {
            return this.length == 0;
        }
        
        public void shift(final int delta) {
            int i = this.start + delta;
            if (i >= this.list.length) {
                i -= this.list.length;
            }
            if (this.list[i] != 0) {
                this.list[i] = 0;
                --this.length;
            }
            this.start = i;
        }
        
        public void addOffset(final int offset) {
            int i = this.start + offset;
            if (i >= this.list.length) {
                i -= this.list.length;
            }
            assert this.list[i] == 0;
            this.list[i] = 1;
            ++this.length;
        }
        
        public void addOffsetAndCount(final int offset, final int count) {
            assert count > 0;
            int i = this.start + offset;
            if (i >= this.list.length) {
                i -= this.list.length;
            }
            if (this.list[i] == 0) {
                this.list[i] = count;
                ++this.length;
            }
            else if (count < this.list[i]) {
                this.list[i] = count;
            }
        }
        
        public boolean containsOffset(final int offset) {
            int i = this.start + offset;
            if (i >= this.list.length) {
                i -= this.list.length;
            }
            return this.list[i] != 0;
        }
        
        public boolean hasCountAtOffset(final int offset, final int count) {
            int i = this.start + offset;
            if (i >= this.list.length) {
                i -= this.list.length;
            }
            final int oldCount = this.list[i];
            return oldCount != 0 && oldCount <= count;
        }
        
        public int popMinimum(final OutputInt outCount) {
            int i = this.start;
            while (++i < this.list.length) {
                final int count = this.list[i];
                if (count != 0) {
                    this.list[i] = 0;
                    --this.length;
                    final int result = i - this.start;
                    this.start = i;
                    if (outCount != null) {
                        outCount.value = count;
                    }
                    return result;
                }
            }
            final int result = this.list.length - this.start;
            int count;
            for (i = 0; (count = this.list[i]) == 0; ++i) {}
            this.list[i] = 0;
            --this.length;
            this.start = i;
            if (outCount != null) {
                outCount.value = count;
            }
            return result + i;
        }
    }
}
