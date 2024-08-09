/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteBufOutputStream
extends OutputStream
implements DataOutput {
    private final ByteBuf buffer;
    private final int startIndex;
    private final DataOutputStream utf8out = new DataOutputStream(this);

    public ByteBufOutputStream(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = byteBuf;
        this.startIndex = byteBuf.writerIndex();
    }

    public int writtenBytes() {
        return this.buffer.writerIndex() - this.startIndex;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (n2 == 0) {
            return;
        }
        this.buffer.writeBytes(byArray, n, n2);
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.buffer.writeBytes(byArray);
    }

    @Override
    public void write(int n) throws IOException {
        this.buffer.writeByte(n);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.buffer.writeBoolean(bl);
    }

    @Override
    public void writeByte(int n) throws IOException {
        this.buffer.writeByte(n);
    }

    @Override
    public void writeBytes(String string) throws IOException {
        this.buffer.writeCharSequence(string, CharsetUtil.US_ASCII);
    }

    @Override
    public void writeChar(int n) throws IOException {
        this.buffer.writeChar(n);
    }

    @Override
    public void writeChars(String string) throws IOException {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            this.buffer.writeChar(string.charAt(i));
        }
    }

    @Override
    public void writeDouble(double d) throws IOException {
        this.buffer.writeDouble(d);
    }

    @Override
    public void writeFloat(float f) throws IOException {
        this.buffer.writeFloat(f);
    }

    @Override
    public void writeInt(int n) throws IOException {
        this.buffer.writeInt(n);
    }

    @Override
    public void writeLong(long l) throws IOException {
        this.buffer.writeLong(l);
    }

    @Override
    public void writeShort(int n) throws IOException {
        this.buffer.writeShort((short)n);
    }

    @Override
    public void writeUTF(String string) throws IOException {
        this.utf8out.writeUTF(string);
    }

    public ByteBuf buffer() {
        return this.buffer;
    }
}

