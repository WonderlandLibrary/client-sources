package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class StringReplacer implements UnicodeReplacer
{
    private String output;
    private int cursorPos;
    private boolean hasCursor;
    private boolean isComplex;
    private final RuleBasedTransliterator.Data data;
    
    public StringReplacer(final String theOutput, final int theCursorPos, final RuleBasedTransliterator.Data theData) {
        this.output = theOutput;
        this.cursorPos = theCursorPos;
        this.hasCursor = true;
        this.data = theData;
        this.isComplex = true;
    }
    
    public StringReplacer(final String theOutput, final RuleBasedTransliterator.Data theData) {
        this.output = theOutput;
        this.cursorPos = 0;
        this.hasCursor = false;
        this.data = theData;
        this.isComplex = true;
    }
    
    @Override
    public int replace(final Replaceable text, final int start, final int limit, final int[] cursor) {
        int newStart = 0;
        int outLen;
        if (!this.isComplex) {
            text.replace(start, limit, this.output);
            outLen = this.output.length();
            newStart = this.cursorPos;
        }
        else {
            final StringBuffer buf = new StringBuffer();
            this.isComplex = false;
            int destStart;
            final int tempStart = destStart = text.length();
            if (start > 0) {
                final int len = UTF16.getCharCount(text.char32At(start - 1));
                text.copy(start - len, start, tempStart);
                destStart += len;
            }
            else {
                text.replace(tempStart, tempStart, "\uffff");
                ++destStart;
            }
            int destLimit = destStart;
            int tempExtra = 0;
            int oOutput;
            int nextIndex;
            for (oOutput = 0; oOutput < this.output.length(); oOutput = nextIndex) {
                if (oOutput == this.cursorPos) {
                    newStart = buf.length() + destLimit - destStart;
                }
                final int c = UTF16.charAt(this.output, oOutput);
                nextIndex = oOutput + UTF16.getCharCount(c);
                if (nextIndex == this.output.length()) {
                    tempExtra = UTF16.getCharCount(text.char32At(limit));
                    text.copy(limit, limit + tempExtra, destLimit);
                }
                final UnicodeReplacer r = this.data.lookupReplacer(c);
                if (r == null) {
                    UTF16.append(buf, c);
                }
                else {
                    this.isComplex = true;
                    if (buf.length() > 0) {
                        text.replace(destLimit, destLimit, buf.toString());
                        destLimit += buf.length();
                        buf.setLength(0);
                    }
                    final int len2 = r.replace(text, destLimit, destLimit, cursor);
                    destLimit += len2;
                }
            }
            if (buf.length() > 0) {
                text.replace(destLimit, destLimit, buf.toString());
                destLimit += buf.length();
            }
            if (oOutput == this.cursorPos) {
                newStart = destLimit - destStart;
            }
            outLen = destLimit - destStart;
            text.copy(destStart, destLimit, start);
            text.replace(tempStart + outLen, destLimit + tempExtra + outLen, "");
            text.replace(start + outLen, limit + outLen, "");
        }
        if (this.hasCursor) {
            if (this.cursorPos < 0) {
                int n;
                for (newStart = start, n = this.cursorPos; n < 0 && newStart > 0; newStart -= UTF16.getCharCount(text.char32At(newStart - 1)), ++n) {}
                newStart += n;
            }
            else if (this.cursorPos > this.output.length()) {
                int n;
                for (newStart = start + outLen, n = this.cursorPos - this.output.length(); n > 0 && newStart < text.length(); newStart += UTF16.getCharCount(text.char32At(newStart)), --n) {}
                newStart += n;
            }
            else {
                newStart += start;
            }
            cursor[0] = newStart;
        }
        return outLen;
    }
    
    @Override
    public String toReplacerPattern(final boolean escapeUnprintable) {
        final StringBuffer rule = new StringBuffer();
        final StringBuffer quoteBuf = new StringBuffer();
        int cursor = this.cursorPos;
        if (this.hasCursor && cursor < 0) {
            while (cursor++ < 0) {
                Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
            }
        }
        for (int i = 0; i < this.output.length(); ++i) {
            if (this.hasCursor && i == cursor) {
                Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
            }
            final char c = this.output.charAt(i);
            final UnicodeReplacer r = this.data.lookupReplacer(c);
            if (r == null) {
                Utility.appendToRule(rule, c, false, escapeUnprintable, quoteBuf);
            }
            else {
                final StringBuffer buf = new StringBuffer(" ");
                buf.append(r.toReplacerPattern(escapeUnprintable));
                buf.append(' ');
                Utility.appendToRule(rule, buf.toString(), true, escapeUnprintable, quoteBuf);
            }
        }
        if (this.hasCursor && cursor > this.output.length()) {
            cursor -= this.output.length();
            while (cursor-- > 0) {
                Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
            }
            Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
        }
        Utility.appendToRule(rule, -1, true, escapeUnprintable, quoteBuf);
        return rule.toString();
    }
    
    @Override
    public void addReplacementSetTo(final UnicodeSet toUnionTo) {
        int ch;
        for (int i = 0; i < this.output.length(); i += UTF16.getCharCount(ch)) {
            ch = UTF16.charAt(this.output, i);
            final UnicodeReplacer r = this.data.lookupReplacer(ch);
            if (r == null) {
                toUnionTo.add(ch);
            }
            else {
                r.addReplacementSetTo(toUnionTo);
            }
        }
    }
}
