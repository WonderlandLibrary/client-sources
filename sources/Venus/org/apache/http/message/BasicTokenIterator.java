/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;
import org.apache.http.util.Args;

public class BasicTokenIterator
implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected final HeaderIterator headerIt;
    protected String currentHeader;
    protected String currentToken;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        this.headerIt = Args.notNull(headerIterator, "Header iterator");
        this.searchPos = this.findNext(-1);
    }

    @Override
    public boolean hasNext() {
        return this.currentToken != null;
    }

    @Override
    public String nextToken() throws NoSuchElementException, ParseException {
        if (this.currentToken == null) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        String string = this.currentToken;
        this.searchPos = this.findNext(this.searchPos);
        return string;
    }

    @Override
    public final Object next() throws NoSuchElementException, ParseException {
        return this.nextToken();
    }

    @Override
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }

    protected int findNext(int n) throws ParseException {
        int n2 = n;
        if (n2 < 0) {
            if (!this.headerIt.hasNext()) {
                return 1;
            }
            this.currentHeader = this.headerIt.nextHeader().getValue();
            n2 = 0;
        } else {
            n2 = this.findTokenSeparator(n2);
        }
        int n3 = this.findTokenStart(n2);
        if (n3 < 0) {
            this.currentToken = null;
            return 1;
        }
        int n4 = this.findTokenEnd(n3);
        this.currentToken = this.createToken(this.currentHeader, n3, n4);
        return n4;
    }

    protected String createToken(String string, int n, int n2) {
        return string.substring(n, n2);
    }

    protected int findTokenStart(int n) {
        int n2 = Args.notNegative(n, "Search position");
        boolean bl = false;
        while (!bl && this.currentHeader != null) {
            int n3 = this.currentHeader.length();
            while (!bl && n2 < n3) {
                char c = this.currentHeader.charAt(n2);
                if (this.isTokenSeparator(c) || this.isWhitespace(c)) {
                    ++n2;
                    continue;
                }
                if (this.isTokenChar(this.currentHeader.charAt(n2))) {
                    bl = true;
                    continue;
                }
                throw new ParseException("Invalid character before token (pos " + n2 + "): " + this.currentHeader);
            }
            if (bl) continue;
            if (this.headerIt.hasNext()) {
                this.currentHeader = this.headerIt.nextHeader().getValue();
                n2 = 0;
                continue;
            }
            this.currentHeader = null;
        }
        return bl ? n2 : -1;
    }

    protected int findTokenSeparator(int n) {
        int n2 = Args.notNegative(n, "Search position");
        boolean bl = false;
        int n3 = this.currentHeader.length();
        while (!bl && n2 < n3) {
            char c = this.currentHeader.charAt(n2);
            if (this.isTokenSeparator(c)) {
                bl = true;
                continue;
            }
            if (this.isWhitespace(c)) {
                ++n2;
                continue;
            }
            if (this.isTokenChar(c)) {
                throw new ParseException("Tokens without separator (pos " + n2 + "): " + this.currentHeader);
            }
            throw new ParseException("Invalid character after token (pos " + n2 + "): " + this.currentHeader);
        }
        return n2;
    }

    protected int findTokenEnd(int n) {
        int n2;
        Args.notNegative(n, "Search position");
        int n3 = this.currentHeader.length();
        for (n2 = n + 1; n2 < n3 && this.isTokenChar(this.currentHeader.charAt(n2)); ++n2) {
        }
        return n2;
    }

    protected boolean isTokenSeparator(char c) {
        return c == ',';
    }

    protected boolean isWhitespace(char c) {
        return c == '\t' || Character.isSpaceChar(c);
    }

    protected boolean isTokenChar(char c) {
        if (Character.isLetterOrDigit(c)) {
            return false;
        }
        if (Character.isISOControl(c)) {
            return true;
        }
        return this.isHttpSeparator(c);
    }

    protected boolean isHttpSeparator(char c) {
        return HTTP_SEPARATORS.indexOf(c) >= 0;
    }
}

