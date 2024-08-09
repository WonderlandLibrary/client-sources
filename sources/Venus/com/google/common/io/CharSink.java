/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

@GwtIncompatible
public abstract class CharSink {
    protected CharSink() {
    }

    public abstract Writer openStream() throws IOException;

    public Writer openBufferedStream() throws IOException {
        Writer writer = this.openStream();
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
    }

    public void write(CharSequence charSequence) throws IOException {
        Preconditions.checkNotNull(charSequence);
        try (Closer closer = Closer.create();){
            Writer writer = closer.register(this.openStream());
            writer.append(charSequence);
            writer.flush();
        }
    }

    public void writeLines(Iterable<? extends CharSequence> iterable) throws IOException {
        this.writeLines(iterable, System.getProperty("line.separator"));
    }

    public void writeLines(Iterable<? extends CharSequence> iterable, String string) throws IOException {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(string);
        try (Closer closer = Closer.create();){
            Writer writer = closer.register(this.openBufferedStream());
            for (CharSequence charSequence : iterable) {
                writer.append(charSequence).append(string);
            }
            writer.flush();
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(Readable readable) throws IOException {
        Preconditions.checkNotNull(readable);
        try (Closer closer = Closer.create();){
            Writer writer = closer.register(this.openStream());
            long l = CharStreams.copy(readable, writer);
            writer.flush();
            long l2 = l;
            return l2;
        }
    }
}

