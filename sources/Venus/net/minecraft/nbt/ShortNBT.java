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
public class ShortNBT
extends NumberNBT {
    public static final INBTType<ShortNBT> TYPE = new INBTType<ShortNBT>(){

        @Override
        public ShortNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(80L);
            return ShortNBT.valueOf(dataInput.readShort());
        }

        @Override
        public String getName() {
            return "SHORT";
        }

        @Override
        public String getTagName() {
            return "TAG_Short";
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
    private final short data;

    private ShortNBT(short s) {
        this.data = s;
    }

    public static ShortNBT valueOf(short s) {
        return s >= -128 && s <= 1024 ? Cache.CACHE[s + 128] : new ShortNBT(s);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.data);
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<ShortNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return this.data + "s";
    }

    @Override
    public ShortNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof ShortNBT && this.data == ((ShortNBT)object).data;
    }

    public int hashCode() {
        return this.data;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("s").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
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
        static final ShortNBT[] CACHE = new ShortNBT[1153];

        Cache() {
        }

        static {
            for (int i = 0; i < CACHE.length; ++i) {
                Cache.CACHE[i] = new ShortNBT((short)(-128 + i));
            }
        }
    }
}

