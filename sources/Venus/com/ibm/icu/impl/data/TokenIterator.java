/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.data;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.data.ResourceReader;
import com.ibm.icu.text.UTF16;
import java.io.IOException;

public class TokenIterator {
    private ResourceReader reader;
    private String line;
    private StringBuffer buf;
    private boolean done;
    private int pos;
    private int lastpos;

    public TokenIterator(ResourceReader resourceReader) {
        this.reader = resourceReader;
        this.line = null;
        this.done = false;
        this.buf = new StringBuffer();
        this.lastpos = -1;
        this.pos = -1;
    }

    public String next() throws IOException {
        if (this.done) {
            return null;
        }
        while (true) {
            if (this.line == null) {
                this.line = this.reader.readLineSkippingComments();
                if (this.line == null) {
                    this.done = true;
                    return null;
                }
                this.pos = 0;
            }
            this.buf.setLength(0);
            this.lastpos = this.pos;
            this.pos = this.nextToken(this.pos);
            if (this.pos >= 0) break;
            this.line = null;
        }
        return this.buf.toString();
    }

    public int getLineNumber() {
        return this.reader.getLineNumber();
    }

    public String describePosition() {
        return this.reader.describePosition() + ':' + (this.lastpos + 1);
    }

    private int nextToken(int n) {
        if ((n = PatternProps.skipWhiteSpace(this.line, n)) == this.line.length()) {
            return 1;
        }
        int n2 = n;
        char c = this.line.charAt(n++);
        char c2 = '\u0000';
        switch (c) {
            case '\"': 
            case '\'': {
                c2 = c;
                break;
            }
            case '#': {
                return 1;
            }
            default: {
                this.buf.append(c);
            }
        }
        int[] nArray = null;
        while (n < this.line.length()) {
            c = this.line.charAt(n);
            if (c == '\\') {
                int n3;
                if (nArray == null) {
                    nArray = new int[]{n + 1};
                }
                if ((n3 = Utility.unescapeAt(this.line, nArray)) < 0) {
                    throw new RuntimeException("Invalid escape at " + this.reader.describePosition() + ':' + n);
                }
                UTF16.append(this.buf, n3);
                n = nArray[0];
                continue;
            }
            if (c2 != '\u0000' && c == c2 || c2 == '\u0000' && PatternProps.isWhiteSpace(c)) {
                return ++n;
            }
            if (c2 == '\u0000' && c == '#') {
                return n;
            }
            this.buf.append(c);
            ++n;
        }
        if (c2 != '\u0000') {
            throw new RuntimeException("Unterminated quote at " + this.reader.describePosition() + ':' + n2);
        }
        return n;
    }
}

