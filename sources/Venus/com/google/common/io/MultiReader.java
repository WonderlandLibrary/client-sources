/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.CharSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
class MultiReader
extends Reader {
    private final Iterator<? extends CharSource> it;
    private Reader current;

    MultiReader(Iterator<? extends CharSource> iterator2) throws IOException {
        this.it = iterator2;
        this.advance();
    }

    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.current = this.it.next().openStream();
        }
    }

    @Override
    public int read(@Nullable char[] cArray, int n, int n2) throws IOException {
        if (this.current == null) {
            return 1;
        }
        int n3 = this.current.read(cArray, n, n2);
        if (n3 == -1) {
            this.advance();
            return this.read(cArray, n, n2);
        }
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        Preconditions.checkArgument(l >= 0L, "n is negative");
        if (l > 0L) {
            while (this.current != null) {
                long l2 = this.current.skip(l);
                if (l2 > 0L) {
                    return l2;
                }
                this.advance();
            }
        }
        return 0L;
    }

    @Override
    public boolean ready() throws IOException {
        return this.current != null && this.current.ready();
    }

    @Override
    public void close() throws IOException {
        if (this.current != null) {
            try {
                this.current.close();
            } finally {
                this.current = null;
            }
        }
    }
}

