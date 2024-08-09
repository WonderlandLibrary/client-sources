/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Beta
@GwtIncompatible
public final class LittleEndianDataOutputStream
extends FilterOutputStream
implements DataOutput {
    public LittleEndianDataOutputStream(OutputStream outputStream) {
        super(new DataOutputStream(Preconditions.checkNotNull(outputStream)));
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        ((DataOutputStream)this.out).writeBoolean(bl);
    }

    @Override
    public void writeByte(int n) throws IOException {
        ((DataOutputStream)this.out).writeByte(n);
    }

    @Override
    @Deprecated
    public void writeBytes(String string) throws IOException {
        ((DataOutputStream)this.out).writeBytes(string);
    }

    @Override
    public void writeChar(int n) throws IOException {
        this.writeShort(n);
    }

    @Override
    public void writeChars(String string) throws IOException {
        for (int i = 0; i < string.length(); ++i) {
            this.writeChar(string.charAt(i));
        }
    }

    @Override
    public void writeDouble(double d) throws IOException {
        this.writeLong(Double.doubleToLongBits(d));
    }

    @Override
    public void writeFloat(float f) throws IOException {
        this.writeInt(Float.floatToIntBits(f));
    }

    @Override
    public void writeInt(int n) throws IOException {
        this.out.write(0xFF & n);
        this.out.write(0xFF & n >> 8);
        this.out.write(0xFF & n >> 16);
        this.out.write(0xFF & n >> 24);
    }

    @Override
    public void writeLong(long l) throws IOException {
        byte[] byArray = Longs.toByteArray(Long.reverseBytes(l));
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public void writeShort(int n) throws IOException {
        this.out.write(0xFF & n);
        this.out.write(0xFF & n >> 8);
    }

    @Override
    public void writeUTF(String string) throws IOException {
        ((DataOutputStream)this.out).writeUTF(string);
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}

