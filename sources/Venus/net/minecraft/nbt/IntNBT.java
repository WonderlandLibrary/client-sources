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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntNBT
extends NumberNBT {
    public static final INBTType<IntNBT> TYPE = new INBTType<IntNBT>(){

        @Override
        public IntNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(96L);
            return IntNBT.valueOf(dataInput.readInt());
        }

        @Override
        public String getName() {
            return "INT";
        }

        @Override
        public String getTagName() {
            return "TAG_Int";
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
    private final int data;

    private IntNBT(int n) {
        this.data = n;
    }

    public static IntNBT valueOf(int n) {
        return n >= -128 && n <= 1024 ? Cache.CACHE[n + 128] : new IntNBT(n);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data);
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<IntNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    @Override
    public IntNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof IntNBT && this.data == ((IntNBT)object).data;
    }

    public int hashCode() {
        return this.data;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        return new StringTextComponent(String.valueOf(this.data)).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
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
        return (short)(this.data & 0xFFFF);
    }

    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFF);
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
        static final IntNBT[] CACHE = new IntNBT[1153];

        Cache() {
        }

        static {
            for (int i = 0; i < CACHE.length; ++i) {
                Cache.CACHE[i] = new IntNBT(-128 + i);
            }
        }
    }
}

