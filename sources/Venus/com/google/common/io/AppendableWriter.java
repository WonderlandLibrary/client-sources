/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
class AppendableWriter
extends Writer {
    private final Appendable target;
    private boolean closed;

    AppendableWriter(Appendable appendable) {
        this.target = Preconditions.checkNotNull(appendable);
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(new String(cArray, n, n2));
    }

    @Override
    public void flush() throws IOException {
        this.checkNotClosed();
        if (this.target instanceof Flushable) {
            ((Flushable)((Object)this.target)).flush();
        }
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        if (this.target instanceof Closeable) {
            ((Closeable)((Object)this.target)).close();
        }
    }

    @Override
    public void write(int n) throws IOException {
        this.checkNotClosed();
        this.target.append((char)n);
    }

    @Override
    public void write(@Nullable String string) throws IOException {
        this.checkNotClosed();
        this.target.append(string);
    }

    @Override
    public void write(@Nullable String string, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(string, n, n + n2);
    }

    @Override
    public Writer append(char c) throws IOException {
        this.checkNotClosed();
        this.target.append(c);
        return this;
    }

    @Override
    public Writer append(@Nullable CharSequence charSequence) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence);
        return this;
    }

    @Override
    public Writer append(@Nullable CharSequence charSequence, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence, n, n2);
        return this;
    }

    private void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Cannot write to a closed writer.");
        }
    }

    @Override
    public Appendable append(char c) throws IOException {
        return this.append(c);
    }

    @Override
    public Appendable append(@Nullable CharSequence charSequence, int n, int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }

    @Override
    public Appendable append(@Nullable CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}

