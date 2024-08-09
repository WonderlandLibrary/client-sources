/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Writer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NullWriter
extends Writer {
    public static final NullWriter NULL_WRITER = new NullWriter();

    @Override
    public Writer append(char c) {
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n, int n2) {
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) {
        return this;
    }

    @Override
    public void write(int n) {
    }

    @Override
    public void write(char[] cArray) {
    }

    @Override
    public void write(char[] cArray, int n, int n2) {
    }

    @Override
    public void write(String string) {
    }

    @Override
    public void write(String string, int n, int n2) {
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
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

