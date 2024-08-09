/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArrayNBT
extends CollectionNBT<LongNBT> {
    public static final INBTType<LongArrayNBT> TYPE = new INBTType<LongArrayNBT>(){

        @Override
        public LongArrayNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(192L);
            int n2 = dataInput.readInt();
            nBTSizeTracker.read(64L * (long)n2);
            long[] lArray = new long[n2];
            for (int i = 0; i < n2; ++i) {
                lArray[i] = dataInput.readLong();
            }
            return new LongArrayNBT(lArray);
        }

        @Override
        public String getName() {
            return "LONG[]";
        }

        @Override
        public String getTagName() {
            return "TAG_Long_Array";
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private long[] data;

    public LongArrayNBT(long[] lArray) {
        this.data = lArray;
    }

    public LongArrayNBT(LongSet longSet) {
        this.data = longSet.toLongArray();
    }

    public LongArrayNBT(List<Long> list) {
        this(LongArrayNBT.toArray(list));
    }

    private static long[] toArray(List<Long> list) {
        long[] lArray = new long[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Long l = list.get(i);
            lArray[i] = l == null ? 0L : l;
        }
        return lArray;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        for (long l : this.data) {
            dataOutput.writeLong(l);
        }
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<LongArrayNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[L;");
        for (int i = 0; i < this.data.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.data[i]).append('L');
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public LongArrayNBT copy() {
        long[] lArray = new long[this.data.length];
        System.arraycopy(this.data, 0, lArray, 0, this.data.length);
        return new LongArrayNBT(lArray);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof LongArrayNBT && Arrays.equals(this.data, ((LongArrayNBT)object).data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("L").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iFormattableTextComponent2 = new StringTextComponent("[").append(iFormattableTextComponent).appendString(";");
        for (int i = 0; i < this.data.length; ++i) {
            IFormattableTextComponent iFormattableTextComponent3 = new StringTextComponent(String.valueOf(this.data[i])).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            iFormattableTextComponent2.appendString(" ").append(iFormattableTextComponent3).append(iFormattableTextComponent);
            if (i == this.data.length - 1) continue;
            iFormattableTextComponent2.appendString(",");
        }
        iFormattableTextComponent2.appendString("]");
        return iFormattableTextComponent2;
    }

    public long[] getAsLongArray() {
        return this.data;
    }

    @Override
    public int size() {
        return this.data.length;
    }

    @Override
    public LongNBT get(int n) {
        return LongNBT.valueOf(this.data[n]);
    }

    @Override
    public LongNBT set(int n, LongNBT longNBT) {
        long l = this.data[n];
        this.data[n] = longNBT.getLong();
        return LongNBT.valueOf(l);
    }

    @Override
    public void add(int n, LongNBT longNBT) {
        this.data = ArrayUtils.add(this.data, n, longNBT.getLong());
    }

    @Override
    public boolean setNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.data[n] = ((NumberNBT)iNBT).getLong();
            return false;
        }
        return true;
    }

    @Override
    public boolean addNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.data = ArrayUtils.add(this.data, n, ((NumberNBT)iNBT).getLong());
            return false;
        }
        return true;
    }

    @Override
    public LongNBT remove(int n) {
        long l = this.data[n];
        this.data = ArrayUtils.remove(this.data, n);
        return LongNBT.valueOf(l);
    }

    @Override
    public byte getTagType() {
        return 1;
    }

    @Override
    public void clear() {
        this.data = new long[0];
    }

    @Override
    public INBT remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, INBT iNBT) {
        this.add(n, (LongNBT)iNBT);
    }

    @Override
    public INBT set(int n, INBT iNBT) {
        return this.set(n, (LongNBT)iNBT);
    }

    @Override
    public INBT copy() {
        return this.copy();
    }

    @Override
    public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, Object object) {
        this.add(n, (LongNBT)object);
    }

    @Override
    public Object set(int n, Object object) {
        return this.set(n, (LongNBT)object);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }
}

