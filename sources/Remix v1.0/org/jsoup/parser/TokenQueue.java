package org.jsoup.parser;

import org.jsoup.helper.*;

public class TokenQueue
{
    private String queue;
    private int pos;
    private static final char ESC = '\\';
    
    public TokenQueue(final String data) {
        this.pos = 0;
        Validate.notNull(data);
        this.queue = data;
    }
    
    public boolean isEmpty() {
        return this.remainingLength() == 0;
    }
    
    private int remainingLength() {
        return this.queue.length() - this.pos;
    }
    
    public char peek() {
        return this.isEmpty() ? '\0' : this.queue.charAt(this.pos);
    }
    
    public void addFirst(final Character c) {
        this.addFirst(c.toString());
    }
    
    public void addFirst(final String seq) {
        this.queue = seq + this.queue.substring(this.pos);
        this.pos = 0;
    }
    
    public boolean matches(final String seq) {
        return this.queue.regionMatches(true, this.pos, seq, 0, seq.length());
    }
    
    public boolean matchesCS(final String seq) {
        return this.queue.startsWith(seq, this.pos);
    }
    
    public boolean matchesAny(final String... seq) {
        for (final String s : seq) {
            if (this.matches(s)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchesAny(final char... seq) {
        if (this.isEmpty()) {
            return false;
        }
        for (final char c : seq) {
            if (this.queue.charAt(this.pos) == c) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchesStartTag() {
        return this.remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1));
    }
    
    public boolean matchChomp(final String seq) {
        if (this.matches(seq)) {
            this.pos += seq.length();
            return true;
        }
        return false;
    }
    
    public boolean matchesWhitespace() {
        return !this.isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }
    
    public boolean matchesWord() {
        return !this.isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }
    
    public void advance() {
        if (!this.isEmpty()) {
            ++this.pos;
        }
    }
    
    public char consume() {
        return this.queue.charAt(this.pos++);
    }
    
    public void consume(final String seq) {
        if (!this.matches(seq)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
        final int len = seq.length();
        if (len > this.remainingLength()) {
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        this.pos += len;
    }
    
    public String consumeTo(final String seq) {
        final int offset = this.queue.indexOf(seq, this.pos);
        if (offset != -1) {
            final String consumed = this.queue.substring(this.pos, offset);
            this.pos += consumed.length();
            return consumed;
        }
        return this.remainder();
    }
    
    public String consumeToIgnoreCase(final String seq) {
        final int start = this.pos;
        final String first = seq.substring(0, 1);
        final boolean canScan = first.toLowerCase().equals(first.toUpperCase());
        while (!this.isEmpty() && !this.matches(seq)) {
            if (canScan) {
                final int skip = this.queue.indexOf(first, this.pos) - this.pos;
                if (skip == 0) {
                    ++this.pos;
                }
                else if (skip < 0) {
                    this.pos = this.queue.length();
                }
                else {
                    this.pos += skip;
                }
            }
            else {
                ++this.pos;
            }
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String consumeToAny(final String... seq) {
        final int start = this.pos;
        while (!this.isEmpty() && !this.matchesAny(seq)) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String chompTo(final String seq) {
        final String data = this.consumeTo(seq);
        this.matchChomp(seq);
        return data;
    }
    
    public String chompToIgnoreCase(final String seq) {
        final String data = this.consumeToIgnoreCase(seq);
        this.matchChomp(seq);
        return data;
    }
    
    public String chompBalanced(final char open, final char close) {
        int start = -1;
        int end = -1;
        int depth = 0;
        char last = '\0';
        boolean inQuote = false;
        while (true) {
            while (!this.isEmpty()) {
                final Character c = this.consume();
                Label_0166: {
                    if (last == '\0' || last != '\\') {
                        if ((c.equals('\'') || c.equals('\"')) && c != open) {
                            inQuote = !inQuote;
                        }
                        if (inQuote) {
                            break Label_0166;
                        }
                        if (c.equals(open)) {
                            ++depth;
                            if (start == -1) {
                                start = this.pos;
                            }
                        }
                        else if (c.equals(close)) {
                            --depth;
                        }
                    }
                    if (depth > 0 && last != '\0') {
                        end = this.pos;
                    }
                    last = c;
                }
                if (depth <= 0) {
                    final String out = (end >= 0) ? this.queue.substring(start, end) : "";
                    if (depth > 0) {
                        Validate.fail("Did not find balanced maker at " + out);
                    }
                    return out;
                }
            }
            continue;
        }
    }
    
    public static String unescape(final String in) {
        final StringBuilder out = new StringBuilder();
        char last = '\0';
        for (final char c : in.toCharArray()) {
            if (c == '\\') {
                if (last != '\0' && last == '\\') {
                    out.append(c);
                }
            }
            else {
                out.append(c);
            }
            last = c;
        }
        return out.toString();
    }
    
    public boolean consumeWhitespace() {
        boolean seen = false;
        while (this.matchesWhitespace()) {
            ++this.pos;
            seen = true;
        }
        return seen;
    }
    
    public String consumeWord() {
        final int start = this.pos;
        while (this.matchesWord()) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String consumeTagName() {
        final int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny(':', '_', '-'))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String consumeElementSelector() {
        final int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny("*|", "|", "_", "-"))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String consumeCssIdentifier() {
        final int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('-', '_'))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String consumeAttributeKey() {
        final int start = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('-', '_', ':'))) {
            ++this.pos;
        }
        return this.queue.substring(start, this.pos);
    }
    
    public String remainder() {
        final String remainder = this.queue.substring(this.pos, this.queue.length());
        this.pos = this.queue.length();
        return remainder;
    }
    
    @Override
    public String toString() {
        return this.queue.substring(this.pos);
    }
}
