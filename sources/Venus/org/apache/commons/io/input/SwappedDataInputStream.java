/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.EndianUtils;
import org.apache.commons.io.input.ProxyInputStream;

public class SwappedDataInputStream
extends ProxyInputStream
implements DataInput {
    public SwappedDataInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public boolean readBoolean() throws IOException, EOFException {
        return 0 != this.readByte();
    }

    @Override
    public byte readByte() throws IOException, EOFException {
        return (byte)this.in.read();
    }

    @Override
    public char readChar() throws IOException, EOFException {
        return (char)this.readShort();
    }

    @Override
    public double readDouble() throws IOException, EOFException {
        return EndianUtils.readSwappedDouble(this.in);
    }

    @Override
    public float readFloat() throws IOException, EOFException {
        return EndianUtils.readSwappedFloat(this.in);
    }

    @Override
    public void readFully(byte[] byArray) throws IOException, EOFException {
        this.readFully(byArray, 0, byArray.length);
    }

    @Override
    public void readFully(byte[] byArray, int n, int n2) throws IOException, EOFException {
        int n3;
        for (int i = n2; i > 0; i -= n3) {
            int n4 = n + n2 - i;
            n3 = this.read(byArray, n4, i);
            if (-1 != n3) continue;
            throw new EOFException();
        }
    }

    @Override
    public int readInt() throws IOException, EOFException {
        return EndianUtils.readSwappedInteger(this.in);
    }

    @Override
    public String readLine() throws IOException, EOFException {
        throw new UnsupportedOperationException("Operation not supported: readLine()");
    }

    @Override
    public long readLong() throws IOException, EOFException {
        return EndianUtils.readSwappedLong(this.in);
    }

    @Override
    public short readShort() throws IOException, EOFException {
        return EndianUtils.readSwappedShort(this.in);
    }

    @Override
    public int readUnsignedByte() throws IOException, EOFException {
        return this.in.read();
    }

    @Override
    public int readUnsignedShort() throws IOException, EOFException {
        return EndianUtils.readSwappedUnsignedShort(this.in);
    }

    @Override
    public String readUTF() throws IOException, EOFException {
        throw new UnsupportedOperationException("Operation not supported: readUTF()");
    }

    @Override
    public int skipBytes(int n) throws IOException, EOFException {
        return (int)this.in.skip(n);
    }
}

