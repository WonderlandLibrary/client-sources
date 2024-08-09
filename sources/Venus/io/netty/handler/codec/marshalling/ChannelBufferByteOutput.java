/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jboss.marshalling.ByteOutput
 */
package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import org.jboss.marshalling.ByteOutput;

class ChannelBufferByteOutput
implements ByteOutput {
    private final ByteBuf buffer;

    ChannelBufferByteOutput(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {
    }

    public void write(int n) throws IOException {
        this.buffer.writeByte(n);
    }

    public void write(byte[] byArray) throws IOException {
        this.buffer.writeBytes(byArray);
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.buffer.writeBytes(byArray, n, n2);
    }

    ByteBuf getBuffer() {
        return this.buffer;
    }
}

