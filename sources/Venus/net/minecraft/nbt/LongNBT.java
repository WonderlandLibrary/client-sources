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
public class LongNBT
extends NumberNBT {
    public static final INBTType<LongNBT> TYPE = new INBTType<LongNBT>(){

        @Override
        public LongNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(128L);
            return LongNBT.valueOf(dataInput.readLong());
        }

        @Override
        public String getName() {
            return "LONG";
        }

        @Override
        public String getTagName() {
            return "TAG_Long";
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
    private final long data;

    private LongNBT(long l) {
        this.data = l;
    }

    public static LongNBT valueOf(long l) {
        return l >= -128L && l <= 1024L ? Cache.CACHE[(int)l + 128] : new LongNBT(l);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.data);
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<LongNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return this.data + "L";
    }

    @Override
    public LongNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof LongNBT && this.data == ((LongNBT)object).data;
    }

    public int hashCode() {
        return (int)(this.data ^ this.data >>> 32);
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("L").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return new StringTextComponent(String.valueOf(this.data)).append(iFormattableTextComponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    @Override
    public long getLong() {
        return this.data;
    }

    @Override
    public int getInt() {
        return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
    }

    @Override
    public short getShort() {
        return (short)(this.data & 0xFFFFL);
    }

    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFFL);
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
        static final LongNBT[] CACHE = new LongNBT[1153];

        Cache() {
        }

        static {
            for (int i = 0; i < CACHE.length; ++i) {
                Cache.CACHE[i] = new LongNBT(-128 + i);
            }
        }
    }
}

