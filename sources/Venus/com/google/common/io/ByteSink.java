/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.Closer;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

@GwtIncompatible
public abstract class ByteSink {
    protected ByteSink() {
    }

    public CharSink asCharSink(Charset charset) {
        return new AsCharSink(this, charset, null);
    }

    public abstract OutputStream openStream() throws IOException;

    public OutputStream openBufferedStream() throws IOException {
        OutputStream outputStream = this.openStream();
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream);
    }

    public void write(byte[] byArray) throws IOException {
        Preconditions.checkNotNull(byArray);
        try (Closer closer = Closer.create();){
            OutputStream outputStream = closer.register(this.openStream());
            outputStream.write(byArray);
            outputStream.flush();
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        try (Closer closer = Closer.create();){
            OutputStream outputStream = closer.register(this.openStream());
            long l = ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
            long l2 = l;
            return l2;
        }
    }

    private final class AsCharSink
    extends CharSink {
        private final Charset charset;
        final ByteSink this$0;

        private AsCharSink(ByteSink byteSink, Charset charset) {
            this.this$0 = byteSink;
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(this.this$0.openStream(), this.charset);
        }

        public String toString() {
            return this.this$0.toString() + ".asCharSink(" + this.charset + ")";
        }

        AsCharSink(ByteSink byteSink, Charset charset, 1 var3_3) {
            this(byteSink, charset);
        }
    }
}

