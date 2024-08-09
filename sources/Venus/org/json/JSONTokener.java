/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTokener {
    private long character;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private final Reader reader;
    private boolean usePrevious;
    private long characterPreviousLine;

    public JSONTokener(Reader reader) {
        this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = '\u0000';
        this.index = 0L;
        this.character = 1L;
        this.characterPreviousLine = 0L;
        this.line = 1L;
    }

    public JSONTokener(InputStream inputStream) {
        this(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
    }

    public JSONTokener(String string) {
        this(new StringReader(string));
    }

    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0L) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.decrementIndexes();
        this.usePrevious = true;
        this.eof = false;
    }

    private void decrementIndexes() {
        --this.index;
        if (this.previous == '\r' || this.previous == '\n') {
            --this.line;
            this.character = this.characterPreviousLine;
        } else if (this.character > 0L) {
            --this.character;
        }
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 55;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 87;
        }
        return 1;
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() throws JSONException {
        if (this.usePrevious) {
            return false;
        }
        try {
            this.reader.mark(1);
        } catch (IOException iOException) {
            throw new JSONException("Unable to preserve stream position", iOException);
        }
        try {
            if (this.reader.read() <= 0) {
                this.eof = true;
                return false;
            }
            this.reader.reset();
        } catch (IOException iOException) {
            throw new JSONException("Unable to read the next character from the stream", iOException);
        }
        return false;
    }

    public char next() throws JSONException {
        int n;
        if (this.usePrevious) {
            this.usePrevious = false;
            n = this.previous;
        } else {
            try {
                n = this.reader.read();
            } catch (IOException iOException) {
                throw new JSONException(iOException);
            }
        }
        if (n <= 0) {
            this.eof = true;
            return '\u0001';
        }
        this.incrementIndexes(n);
        this.previous = (char)n;
        return this.previous;
    }

    protected char getPrevious() {
        return this.previous;
    }

    private void incrementIndexes(int n) {
        if (n > 0) {
            ++this.index;
            if (n == 13) {
                ++this.line;
                this.characterPreviousLine = this.character;
                this.character = 0L;
            } else if (n == 10) {
                if (this.previous != '\r') {
                    ++this.line;
                    this.characterPreviousLine = this.character;
                }
                this.character = 0L;
            } else {
                ++this.character;
            }
        }
    }

    public char next(char c) throws JSONException {
        char c2 = this.next();
        if (c2 != c) {
            if (c2 > '\u0000') {
                throw this.syntaxError("Expected '" + c + "' and instead saw '" + c2 + "'");
            }
            throw this.syntaxError("Expected '" + c + "' and instead saw ''");
        }
        return c2;
    }

    public String next(int n) throws JSONException {
        if (n == 0) {
            return "";
        }
        char[] cArray = new char[n];
        for (int i = 0; i < n; ++i) {
            cArray[i] = this.next();
            if (!this.end()) continue;
            throw this.syntaxError("Substring bounds error");
        }
        return new String(cArray);
    }

    public char nextClean() throws JSONException {
        char c;
        while ((c = this.next()) != '\u0000' && c <= ' ') {
        }
        return c;
    }

    public String nextString(char c) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        block15: while (true) {
            char c2 = this.next();
            switch (c2) {
                case '\u0000': 
                case '\n': 
                case '\r': {
                    throw this.syntaxError("Unterminated string");
                }
                case '\\': {
                    c2 = this.next();
                    switch (c2) {
                        case 'b': {
                            stringBuilder.append('\b');
                            continue block15;
                        }
                        case 't': {
                            stringBuilder.append('\t');
                            continue block15;
                        }
                        case 'n': {
                            stringBuilder.append('\n');
                            continue block15;
                        }
                        case 'f': {
                            stringBuilder.append('\f');
                            continue block15;
                        }
                        case 'r': {
                            stringBuilder.append('\r');
                            continue block15;
                        }
                        case 'u': {
                            try {
                                stringBuilder.append((char)Integer.parseInt(this.next(4), 16));
                                continue block15;
                            } catch (NumberFormatException numberFormatException) {
                                throw this.syntaxError("Illegal escape.", numberFormatException);
                            }
                        }
                        case '\"': 
                        case '\'': 
                        case '/': 
                        case '\\': {
                            stringBuilder.append(c2);
                            continue block15;
                        }
                    }
                    throw this.syntaxError("Illegal escape.");
                }
            }
            if (c2 == c) {
                return stringBuilder.toString();
            }
            stringBuilder.append(c2);
        }
    }

    public String nextTo(char c) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            char c2;
            if ((c2 = this.next()) == c || c2 == '\u0000' || c2 == '\n' || c2 == '\r') {
                if (c2 != '\u0000') {
                    this.back();
                }
                return stringBuilder.toString().trim();
            }
            stringBuilder.append(c2);
        }
    }

    public String nextTo(String string) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            char c;
            if (string.indexOf(c = this.next()) >= 0 || c == '\u0000' || c == '\n' || c == '\r') {
                if (c != '\u0000') {
                    this.back();
                }
                return stringBuilder.toString().trim();
            }
            stringBuilder.append(c);
        }
    }

    public Object nextValue() throws JSONException {
        String string;
        char c = this.nextClean();
        switch (c) {
            case '\"': 
            case '\'': {
                return this.nextString(c);
            }
            case '{': {
                this.back();
                try {
                    return new JSONObject(this);
                } catch (StackOverflowError stackOverflowError) {
                    throw new JSONException("JSON Array or Object depth too large to process.", stackOverflowError);
                }
            }
            case '[': {
                this.back();
                try {
                    return new JSONArray(this);
                } catch (StackOverflowError stackOverflowError) {
                    throw new JSONException("JSON Array or Object depth too large to process.", stackOverflowError);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            stringBuilder.append(c);
            c = this.next();
        }
        if (!this.eof) {
            this.back();
        }
        if ("".equals(string = stringBuilder.toString().trim())) {
            throw this.syntaxError("Missing value");
        }
        return JSONObject.stringToValue(string);
    }

    public char skipTo(char c) throws JSONException {
        char c2;
        try {
            long l = this.index;
            long l2 = this.character;
            long l3 = this.line;
            this.reader.mark(1000000);
            do {
                if ((c2 = this.next()) != '\u0000') continue;
                this.reader.reset();
                this.index = l;
                this.character = l2;
                this.line = l3;
                return '\u0000';
            } while (c2 != c);
            this.reader.mark(1);
        } catch (IOException iOException) {
            throw new JSONException(iOException);
        }
        this.back();
        return c2;
    }

    public JSONException syntaxError(String string) {
        return new JSONException(string + this.toString());
    }

    public JSONException syntaxError(String string, Throwable throwable) {
        return new JSONException(string + this.toString(), throwable);
    }

    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }

    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
    }
}

