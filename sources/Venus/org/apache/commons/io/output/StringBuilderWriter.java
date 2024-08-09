/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringBuilderWriter
extends Writer
implements Serializable {
    private static final long serialVersionUID = -146927496096066153L;
    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    public StringBuilderWriter(int n) {
        this.builder = new StringBuilder(n);
    }

    public StringBuilderWriter(StringBuilder stringBuilder) {
        this.builder = stringBuilder != null ? stringBuilder : new StringBuilder();
    }

    @Override
    public Writer append(char c) {
        this.builder.append(c);
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) {
        this.builder.append(charSequence);
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n, int n2) {
        this.builder.append(charSequence, n, n2);
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    @Override
    public void write(String string) {
        if (string != null) {
            this.builder.append(string);
        }
    }

    @Override
    public void write(char[] cArray, int n, int n2) {
        if (cArray != null) {
            this.builder.append(cArray, n, n2);
        }
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    public String toString() {
        return this.builder.toString();
    }

    @Override
    public Appendable append(char c) throws IOException {
        return this.append(c);
    }

    @Override
    public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }

    @Override
    public Appendable append(CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}

