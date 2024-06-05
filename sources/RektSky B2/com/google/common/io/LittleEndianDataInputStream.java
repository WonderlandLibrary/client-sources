package com.google.common.io;

import com.google.common.annotations.*;
import com.google.common.base.*;
import com.google.common.primitives.*;
import java.io.*;

@Beta
public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput
{
    public LittleEndianDataInputStream(final InputStream in) {
        super(Preconditions.checkNotNull(in));
    }
    
    @Override
    public String readLine() {
        throw new UnsupportedOperationException("readLine is not supported");
    }
    
    @Override
    public void readFully(final byte[] b) throws IOException {
        ByteStreams.readFully(this, b);
    }
    
    @Override
    public void readFully(final byte[] b, final int off, final int len) throws IOException {
        ByteStreams.readFully(this, b, off, len);
    }
    
    @Override
    public int skipBytes(final int n) throws IOException {
        return (int)this.in.skip(n);
    }
    
    @Override
    public int readUnsignedByte() throws IOException {
        final int b1 = this.in.read();
        if (0 > b1) {
            throw new EOFException();
        }
        return b1;
    }
    
    @Override
    public int readUnsignedShort() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        return Ints.fromBytes((byte)0, (byte)0, b2, b1);
    }
    
    @Override
    public int readInt() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        final byte b3 = this.readAndCheckByte();
        final byte b4 = this.readAndCheckByte();
        return Ints.fromBytes(b4, b3, b2, b1);
    }
    
    @Override
    public long readLong() throws IOException {
        final byte b1 = this.readAndCheckByte();
        final byte b2 = this.readAndCheckByte();
        final byte b3 = this.readAndCheckByte();
        final byte b4 = this.readAndCheckByte();
        final byte b5 = this.readAndCheckByte();
        final byte b6 = this.readAndCheckByte();
        final byte b7 = this.readAndCheckByte();
        final byte b8 = this.readAndCheckByte();
        return Longs.fromBytes(b8, b7, b6, b5, b4, b3, b2, b1);
    }
    
    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public String readUTF() throws IOException {
        return new DataInputStream(this.in).readUTF();
    }
    
    @Override
    public short readShort() throws IOException {
        return (short)this.readUnsignedShort();
    }
    
    @Override
    public char readChar() throws IOException {
        return (char)this.readUnsignedShort();
    }
    
    @Override
    public byte readByte() throws IOException {
        return (byte)this.readUnsignedByte();
    }
    
    @Override
    public boolean readBoolean() throws IOException {
        return this.readUnsignedByte() != 0;
    }
    
    private byte readAndCheckByte() throws IOException, EOFException {
        final int b1 = this.in.read();
        if (-1 == b1) {
            throw new EOFException();
        }
        return (byte)b1;
    }
}
