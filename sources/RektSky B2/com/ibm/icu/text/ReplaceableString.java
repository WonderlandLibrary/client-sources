package com.ibm.icu.text;

public class ReplaceableString implements Replaceable
{
    private StringBuffer buf;
    
    public ReplaceableString(final String str) {
        this.buf = new StringBuffer(str);
    }
    
    public ReplaceableString(final StringBuffer buf) {
        this.buf = buf;
    }
    
    public ReplaceableString() {
        this.buf = new StringBuffer();
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    public String substring(final int start, final int limit) {
        return this.buf.substring(start, limit);
    }
    
    @Override
    public int length() {
        return this.buf.length();
    }
    
    @Override
    public char charAt(final int offset) {
        return this.buf.charAt(offset);
    }
    
    @Override
    public int char32At(final int offset) {
        return UTF16.charAt(this.buf, offset);
    }
    
    @Override
    public void getChars(final int srcStart, final int srcLimit, final char[] dst, final int dstStart) {
        if (srcStart != srcLimit) {
            this.buf.getChars(srcStart, srcLimit, dst, dstStart);
        }
    }
    
    @Override
    public void replace(final int start, final int limit, final String text) {
        this.buf.replace(start, limit, text);
    }
    
    @Override
    public void replace(final int start, final int limit, final char[] chars, final int charsStart, final int charsLen) {
        this.buf.delete(start, limit);
        this.buf.insert(start, chars, charsStart, charsLen);
    }
    
    @Override
    public void copy(final int start, final int limit, final int dest) {
        if (start == limit && start >= 0 && start <= this.buf.length()) {
            return;
        }
        final char[] text = new char[limit - start];
        this.getChars(start, limit, text, 0);
        this.replace(dest, dest, text, 0, limit - start);
    }
    
    @Override
    public boolean hasMetaData() {
        return false;
    }
}
