/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

public class StringTokenIterator {
    private String _text;
    private String _dlms;
    private String _token;
    private int _start;
    private int _end;
    private boolean _done;

    public StringTokenIterator(String string, String string2) {
        this._text = string;
        this._dlms = string2;
        this.setStart(0);
    }

    public String first() {
        this.setStart(0);
        return this._token;
    }

    public String current() {
        return this._token;
    }

    public int currentStart() {
        return this._start;
    }

    public int currentEnd() {
        return this._end;
    }

    public boolean isDone() {
        return this._done;
    }

    public String next() {
        if (this.hasNext()) {
            this._start = this._end + 1;
            this._end = this.nextDelimiter(this._start);
            this._token = this._text.substring(this._start, this._end);
        } else {
            this._start = this._end;
            this._token = null;
            this._done = true;
        }
        return this._token;
    }

    public boolean hasNext() {
        return this._end < this._text.length();
    }

    public StringTokenIterator setStart(int n) {
        if (n > this._text.length()) {
            throw new IndexOutOfBoundsException();
        }
        this._start = n;
        this._end = this.nextDelimiter(this._start);
        this._token = this._text.substring(this._start, this._end);
        this._done = false;
        return this;
    }

    public StringTokenIterator setText(String string) {
        this._text = string;
        this.setStart(0);
        return this;
    }

    private int nextDelimiter(int n) {
        int n2;
        block0: for (n2 = n; n2 < this._text.length(); ++n2) {
            char c = this._text.charAt(n2);
            for (int i = 0; i < this._dlms.length(); ++i) {
                if (c == this._dlms.charAt(i)) break block0;
            }
        }
        return n2;
    }
}

