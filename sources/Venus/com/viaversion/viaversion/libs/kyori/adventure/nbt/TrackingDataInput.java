/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagScope;
import java.io.DataInput;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TrackingDataInput
implements DataInput,
BinaryTagScope {
    private static final int MAX_DEPTH = 512;
    private final DataInput input;
    private final long maxLength;
    private long counter;
    private int depth;

    TrackingDataInput(DataInput dataInput, long l) {
        this.input = dataInput;
        this.maxLength = l;
    }

    public static BinaryTagScope enter(DataInput dataInput) throws IOException {
        if (dataInput instanceof TrackingDataInput) {
            return ((TrackingDataInput)dataInput).enter();
        }
        return BinaryTagScope.NoOp.INSTANCE;
    }

    public static BinaryTagScope enter(DataInput dataInput, long l) throws IOException {
        if (dataInput instanceof TrackingDataInput) {
            return ((TrackingDataInput)dataInput).enter(l);
        }
        return BinaryTagScope.NoOp.INSTANCE;
    }

    public DataInput input() {
        return this.input;
    }

    public TrackingDataInput enter(long l) throws IOException {
        if (this.depth++ > 512) {
            throw new IOException("NBT read exceeded maximum depth of 512");
        }
        this.ensureMaxLength(l);
        return this;
    }

    public TrackingDataInput enter() throws IOException {
        return this.enter(0L);
    }

    public void exit() throws IOException {
        --this.depth;
        this.ensureMaxLength(0L);
    }

    private void ensureMaxLength(long l) throws IOException {
        if (this.maxLength > 0L && this.counter + l > this.maxLength) {
            throw new IOException("The read NBT was longer than the maximum allowed size of " + this.maxLength + " bytes!");
        }
    }

    @Override
    public void readFully(byte @NotNull [] byArray) throws IOException {
        this.counter += (long)byArray.length;
        this.input.readFully(byArray);
    }

    @Override
    public void readFully(byte @NotNull [] byArray, int n, int n2) throws IOException {
        this.counter += (long)n2;
        this.input.readFully(byArray, n, n2);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return this.input.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        ++this.counter;
        return this.input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        ++this.counter;
        return this.input.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        ++this.counter;
        return this.input.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        this.counter += 2L;
        return this.input.readShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        this.counter += 2L;
        return this.input.readUnsignedShort();
    }

    @Override
    public char readChar() throws IOException {
        this.counter += 2L;
        return this.input.readChar();
    }

    @Override
    public int readInt() throws IOException {
        this.counter += 4L;
        return this.input.readInt();
    }

    @Override
    public long readLong() throws IOException {
        this.counter += 8L;
        return this.input.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        this.counter += 4L;
        return this.input.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        this.counter += 8L;
        return this.input.readDouble();
    }

    @Override
    @Nullable
    public String readLine() throws IOException {
        @Nullable String string = this.input.readLine();
        if (string != null) {
            this.counter += (long)(string.length() + 1);
        }
        return string;
    }

    @Override
    @NotNull
    public String readUTF() throws IOException {
        String string = this.input.readUTF();
        this.counter += (long)string.length() * 2L + 2L;
        return string;
    }

    @Override
    public void close() throws IOException {
        this.exit();
    }
}

