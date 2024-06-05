package org.jsoup.parser;

import org.jsoup.helper.*;
import java.util.*;

public final class CharacterReader
{
    static final char EOF = '\uffff';
    private static final int maxCacheLen = 12;
    private final char[] input;
    private final int length;
    private int pos;
    private int mark;
    private final String[] stringCache;
    
    public CharacterReader(final String input) {
        this.pos = 0;
        this.mark = 0;
        this.stringCache = new String[512];
        Validate.notNull(input);
        this.input = input.toCharArray();
        this.length = this.input.length;
    }
    
    public int pos() {
        return this.pos;
    }
    
    public boolean isEmpty() {
        return this.pos >= this.length;
    }
    
    public char current() {
        return (this.pos >= this.length) ? '\uffff' : this.input[this.pos];
    }
    
    char consume() {
        final char val = (this.pos >= this.length) ? '\uffff' : this.input[this.pos];
        ++this.pos;
        return val;
    }
    
    void unconsume() {
        --this.pos;
    }
    
    public void advance() {
        ++this.pos;
    }
    
    void mark() {
        this.mark = this.pos;
    }
    
    void rewindToMark() {
        this.pos = this.mark;
    }
    
    String consumeAsString() {
        return new String(this.input, this.pos++, 1);
    }
    
    int nextIndexOf(final char c) {
        for (int i = this.pos; i < this.length; ++i) {
            if (c == this.input[i]) {
                return i - this.pos;
            }
        }
        return -1;
    }
    
    int nextIndexOf(final CharSequence seq) {
        final char startChar = seq.charAt(0);
        for (int offset = this.pos; offset < this.length; ++offset) {
            if (startChar != this.input[offset]) {
                while (++offset < this.length && startChar != this.input[offset]) {}
            }
            int i = offset + 1;
            final int last = i + seq.length() - 1;
            if (offset < this.length && last <= this.length) {
                for (int j = 1; i < last && seq.charAt(j) == this.input[i]; ++i, ++j) {}
                if (i == last) {
                    return offset - this.pos;
                }
            }
        }
        return -1;
    }
    
    public String consumeTo(final char c) {
        final int offset = this.nextIndexOf(c);
        if (offset != -1) {
            final String consumed = this.cacheString(this.pos, offset);
            this.pos += offset;
            return consumed;
        }
        return this.consumeToEnd();
    }
    
    String consumeTo(final String seq) {
        final int offset = this.nextIndexOf(seq);
        if (offset != -1) {
            final String consumed = this.cacheString(this.pos, offset);
            this.pos += offset;
            return consumed;
        }
        return this.consumeToEnd();
    }
    
    public String consumeToAny(final char... chars) {
        final int start = this.pos;
        final int remaining = this.length;
        final char[] val = this.input;
    Label_0083:
        while (this.pos < remaining) {
            for (final char c : chars) {
                if (val[this.pos] == c) {
                    break Label_0083;
                }
            }
            ++this.pos;
        }
        return (this.pos > start) ? this.cacheString(start, this.pos - start) : "";
    }
    
    String consumeToAnySorted(final char... chars) {
        final int start = this.pos;
        final int remaining = this.length;
        final char[] val = this.input;
        while (this.pos < remaining && Arrays.binarySearch(chars, val[this.pos]) < 0) {
            ++this.pos;
        }
        return (this.pos > start) ? this.cacheString(start, this.pos - start) : "";
    }
    
    String consumeData() {
        final int start = this.pos;
        final int remaining = this.length;
        final char[] val = this.input;
        while (this.pos < remaining) {
            final char c = val[this.pos];
            if (c == '&' || c == '<') {
                break;
            }
            if (c == '\0') {
                break;
            }
            ++this.pos;
        }
        return (this.pos > start) ? this.cacheString(start, this.pos - start) : "";
    }
    
    String consumeTagName() {
        final int start = this.pos;
        final int remaining = this.length;
        final char[] val = this.input;
        while (this.pos < remaining) {
            final char c = val[this.pos];
            if (c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == ' ' || c == '/' || c == '>') {
                break;
            }
            if (c == '\0') {
                break;
            }
            ++this.pos;
        }
        return (this.pos > start) ? this.cacheString(start, this.pos - start) : "";
    }
    
    String consumeToEnd() {
        final String data = this.cacheString(this.pos, this.length - this.pos);
        this.pos = this.length;
        return data;
    }
    
    String consumeLetterSequence() {
        final int start = this.pos;
        while (this.pos < this.length) {
            final char c = this.input[this.pos];
            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && !Character.isLetter(c)) {
                break;
            }
            ++this.pos;
        }
        return this.cacheString(start, this.pos - start);
    }
    
    String consumeLetterThenDigitSequence() {
        final int start = this.pos;
        while (this.pos < this.length) {
            final char c = this.input[this.pos];
            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && !Character.isLetter(c)) {
                break;
            }
            ++this.pos;
        }
        while (!this.isEmpty()) {
            final char c = this.input[this.pos];
            if (c < '0' || c > '9') {
                break;
            }
            ++this.pos;
        }
        return this.cacheString(start, this.pos - start);
    }
    
    String consumeHexSequence() {
        final int start = this.pos;
        while (this.pos < this.length) {
            final char c = this.input[this.pos];
            if ((c < '0' || c > '9') && (c < 'A' || c > 'F') && (c < 'a' || c > 'f')) {
                break;
            }
            ++this.pos;
        }
        return this.cacheString(start, this.pos - start);
    }
    
    String consumeDigitSequence() {
        final int start = this.pos;
        while (this.pos < this.length) {
            final char c = this.input[this.pos];
            if (c < '0' || c > '9') {
                break;
            }
            ++this.pos;
        }
        return this.cacheString(start, this.pos - start);
    }
    
    boolean matches(final char c) {
        return !this.isEmpty() && this.input[this.pos] == c;
    }
    
    boolean matches(final String seq) {
        final int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; ++offset) {
            if (seq.charAt(offset) != this.input[this.pos + offset]) {
                return false;
            }
        }
        return true;
    }
    
    boolean matchesIgnoreCase(final String seq) {
        final int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; ++offset) {
            final char upScan = Character.toUpperCase(seq.charAt(offset));
            final char upTarget = Character.toUpperCase(this.input[this.pos + offset]);
            if (upScan != upTarget) {
                return false;
            }
        }
        return true;
    }
    
    boolean matchesAny(final char... seq) {
        if (this.isEmpty()) {
            return false;
        }
        final char c = this.input[this.pos];
        for (final char seek : seq) {
            if (seek == c) {
                return true;
            }
        }
        return false;
    }
    
    boolean matchesAnySorted(final char[] seq) {
        return !this.isEmpty() && Arrays.binarySearch(seq, this.input[this.pos]) >= 0;
    }
    
    boolean matchesLetter() {
        if (this.isEmpty()) {
            return false;
        }
        final char c = this.input[this.pos];
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c);
    }
    
    boolean matchesDigit() {
        if (this.isEmpty()) {
            return false;
        }
        final char c = this.input[this.pos];
        return c >= '0' && c <= '9';
    }
    
    boolean matchConsume(final String seq) {
        if (this.matches(seq)) {
            this.pos += seq.length();
            return true;
        }
        return false;
    }
    
    boolean matchConsumeIgnoreCase(final String seq) {
        if (this.matchesIgnoreCase(seq)) {
            this.pos += seq.length();
            return true;
        }
        return false;
    }
    
    boolean containsIgnoreCase(final String seq) {
        final String loScan = seq.toLowerCase(Locale.ENGLISH);
        final String hiScan = seq.toUpperCase(Locale.ENGLISH);
        return this.nextIndexOf(loScan) > -1 || this.nextIndexOf(hiScan) > -1;
    }
    
    @Override
    public String toString() {
        return new String(this.input, this.pos, this.length - this.pos);
    }
    
    private String cacheString(final int start, final int count) {
        final char[] val = this.input;
        final String[] cache = this.stringCache;
        if (count > 12) {
            return new String(val, start, count);
        }
        int hash = 0;
        int offset = start;
        for (int i = 0; i < count; ++i) {
            hash = 31 * hash + val[offset++];
        }
        final int index = hash & cache.length - 1;
        String cached = cache[index];
        if (cached == null) {
            cached = new String(val, start, count);
            cache[index] = cached;
        }
        else {
            if (this.rangeEquals(start, count, cached)) {
                return cached;
            }
            cached = new String(val, start, count);
            cache[index] = cached;
        }
        return cached;
    }
    
    boolean rangeEquals(final int start, int count, final String cached) {
        if (count == cached.length()) {
            final char[] one = this.input;
            int i = start;
            int j = 0;
            while (count-- != 0) {
                if (one[i++] != cached.charAt(j++)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
