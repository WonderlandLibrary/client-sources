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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatNBT
extends NumberNBT {
    public static final FloatNBT ZERO = new FloatNBT(0.0f);
    public static final INBTType<FloatNBT> TYPE = new INBTType<FloatNBT>(){

        @Override
        public FloatNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(96L);
            return FloatNBT.valueOf(dataInput.readFloat());
        }

        @Override
        public String getName() {
            return "FLOAT";
        }

        @Override
        public String getTagName() {
            return "TAG_Float";
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
    private final float data;

    private FloatNBT(float f) {
        this.data = f;
    }

    public static FloatNBT valueOf(float f) {
        return f == 0.0f ? ZERO : new FloatNBT(f);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.data);
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<FloatNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return this.data + "f";
    }

    @Override
    public FloatNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof FloatNBT && this.data == ((FloatNBT)object).data;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.data);
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("f").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return new StringTextComponent(String.valueOf(this.data)).append(iFormattableTextComponent).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    @Override
    public long getLong() {
        return (long)this.data;
    }

    @Override
    public int getInt() {
        return MathHelper.floor(this.data);
    }

    @Override
    public short getShort() {
        return (short)(MathHelper.floor(this.data) & 0xFFFF);
    }

    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor(this.data) & 0xFF);
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
        return Float.valueOf(this.data);
    }

    @Override
    public INBT copy() {
        return this.copy();
    }
}

