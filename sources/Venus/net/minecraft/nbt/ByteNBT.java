/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteNBT
extends NumberNBT {
    public static final INBTType<ByteNBT> TYPE = new INBTType<ByteNBT>(){

        @Override
        public ByteNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(72L);
            return ByteNBT.valueOf(dataInput.readByte());
        }

        @Override
        public String getName() {
            return "BYTE";
        }

        @Override
        public String getTagName() {
            return "TAG_Byte";
        }

        @Override
        public boolean isPrimitive() {
            return false;
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    public static final ByteNBT ZERO = ByteNBT.valueOf((byte)0);
    public static final ByteNBT ONE = ByteNBT.valueOf((byte)1);
    private final byte data;

    private ByteNBT(byte by) {
        this.data = by;
    }

    public static ByteNBT valueOf(byte by) {
        return Cache.CACHE[128 + by];
    }

    public static ByteNBT valueOf(boolean bl) {
        return bl ? ONE : ZERO;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.data);
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<ByteNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return this.data + "b";
    }

    @Override
    public ByteNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof ByteNBT && this.data == ((ByteNBT)object).data;
    }

    public int hashCode() {
        return this.data;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("b").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return new StringTextComponent(String.valueOf(this.data)).append(iFormattableTextComponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    @Override
    public long getLong() {
        return this.data;
    }

    @Override
    public int getInt() {
        return this.data;
    }

    @Override
    public short getShort() {
        return this.data;
    }

    @Override
    public byte getByte() {
        return this.data;
    }

    @Override
    public double getDouble() {
        return this.data;
    }

    @Override
    public float getFloat() {
        return this.data;
    }

    @Override
    public Number getAsNumber() {
        return this.data;
    }

    @Override
    public INBT copy() {
        return this.copy();
    }

    static class Cache {
        private static final ByteNBT[] CACHE = new ByteNBT[256];

        Cache() {
        }

        static {
            for (int i = 0; i < CACHE.length; ++i) {
                Cache.CACHE[i] = new ByteNBT((byte)(i - 128));
            }
        }
    }
}

