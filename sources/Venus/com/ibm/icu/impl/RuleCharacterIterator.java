/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.UTF16;
import java.text.ParsePosition;

public class RuleCharacterIterator {
    private String text;
    private ParsePosition pos;
    private SymbolTable sym;
    private char[] buf;
    private int bufPos;
    private boolean isEscaped;
    public static final int DONE = -1;
    public static final int PARSE_VARIABLES = 1;
    public static final int PARSE_ESCAPES = 2;
    public static final int SKIP_WHITESPACE = 4;

    public RuleCharacterIterator(String string, SymbolTable symbolTable, ParsePosition parsePosition) {
        if (string == null || parsePosition.getIndex() > string.length()) {
            throw new IllegalArgumentException();
        }
        this.text = string;
        this.sym = symbolTable;
        this.pos = parsePosition;
        this.buf = null;
    }

    public boolean atEnd() {
        return this.buf == null && this.pos.getIndex() == this.text.length();
    }

    public int next(int n) {
        int n2;
        block6: {
            Object object;
            n2 = -1;
            this.isEscaped = false;
            while (true) {
                n2 = this._current();
                this._advance(UTF16.getCharCount(n2));
                if (n2 == 36 && this.buf == null && (n & 1) != 0 && this.sym != null) {
                    object = this.sym.parseReference(this.text, this.pos, this.text.length());
                    if (object != null) {
                        this.bufPos = 0;
                        this.buf = this.sym.lookup((String)object);
                        if (this.buf == null) {
                            throw new IllegalArgumentException("Undefined variable: " + (String)object);
                        }
                        if (this.buf.length != 0) continue;
                        this.buf = null;
                        continue;
                    }
                    break block6;
                }
                if ((n & 4) == 0 || !PatternProps.isWhiteSpace(n2)) break;
            }
            if (n2 == 92 && (n & 2) != 0) {
                object = new int[]{0};
                n2 = Utility.unescapeAt(this.lookahead(), object);
                this.jumpahead(object[0]);
                this.isEscaped = true;
                if (n2 < 0) {
                    throw new IllegalArgumentException("Invalid escape");
                }
            }
        }
        return n2;
    }

    public boolean isEscaped() {
        return this.isEscaped;
    }

    public boolean inVariable() {
        return this.buf != null;
    }

    public Object getPos(Object object) {
        if (object == null) {
            return new Object[]{this.buf, new int[]{this.pos.getIndex(), this.bufPos}};
        }
        Object[] objectArray = (Object[])object;
        objectArray[0] = this.buf;
        int[] nArray = (int[])objectArray[0];
        nArray[0] = this.pos.getIndex();
        nArray[1] = this.bufPos;
        return object;
    }

    public void setPos(Object object) {
        Object[] objectArray = (Object[])object;
        this.buf = (char[])objectArray[5];
        int[] nArray = (int[])objectArray[0];
        this.pos.setIndex(nArray[0]);
        this.bufPos = nArray[1];
    }

    public void skipIgnored(int n) {
        if ((n & 4) != 0) {
            int n2;
            while (PatternProps.isWhiteSpace(n2 = this._current())) {
                this._advance(UTF16.getCharCount(n2));
            }
        }
    }

    public String lookahead() {
        if (this.buf != null) {
            return new String(this.buf, this.bufPos, this.buf.length - this.bufPos);
        }
        return this.text.substring(this.pos.getIndex());
    }

    public void jumpahead(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (this.buf != null) {
            this.bufPos += n;
            if (this.bufPos > this.buf.length) {
                throw new IllegalArgumentException();
            }
            if (this.bufPos == this.buf.length) {
                this.buf = null;
            }
        } else {
            int n2 = this.pos.getIndex() + n;
            this.pos.setIndex(n2);
            if (n2 > this.text.length()) {
                throw new IllegalArgumentException();
            }
        }
    }

    public String toString() {
        int n = this.pos.getIndex();
        return this.text.substring(0, n) + '|' + this.text.substring(n);
    }

    private int _current() {
        if (this.buf != null) {
            return UTF16.charAt(this.buf, 0, this.buf.length, this.bufPos);
        }
        int n = this.pos.getIndex();
        return n < this.text.length() ? UTF16.charAt(this.text, n) : -1;
    }

    private void _advance(int n) {
        if (this.buf != null) {
            this.bufPos += n;
            if (this.bufPos == this.buf.length) {
                this.buf = null;
            }
        } else {
            this.pos.setIndex(this.pos.getIndex() + n);
            if (this.pos.getIndex() > this.text.length()) {
                this.pos.setIndex(this.text.length());
            }
        }
    }
}

