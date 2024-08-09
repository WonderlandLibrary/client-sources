/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArrayNBT
extends CollectionNBT<IntNBT> {
    public static final INBTType<IntArrayNBT> TYPE = new INBTType<IntArrayNBT>(){

        @Override
        public IntArrayNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(192L);
            int n2 = dataInput.readInt();
            nBTSizeTracker.read(32L * (long)n2);
            int[] nArray = new int[n2];
            for (int i = 0; i < n2; ++i) {
                nArray[i] = dataInput.readInt();
            }
            return new IntArrayNBT(nArray);
        }

        @Override
        public String getName() {
            return "INT[]";
        }

        @Override
        public String getTagName() {
            return "TAG_Int_Array";
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private int[] intArray;

    public IntArrayNBT(int[] nArray) {
        this.intArray = nArray;
    }

    public IntArrayNBT(List<Integer> list) {
        this(IntArrayNBT.toArray(list));
    }

    private static int[] toArray(List<Integer> list) {
        int[] nArray = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Integer n = list.get(i);
            nArray[i] = n == null ? 0 : n;
        }
        return nArray;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.intArray.length);
        for (int n : this.intArray) {
            dataOutput.writeInt(n);
        }
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<IntArrayNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[I;");
        for (int i = 0; i < this.intArray.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.intArray[i]);
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public IntArrayNBT copy() {
        int[] nArray = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, nArray, 0, this.intArray.length);
        return new IntArrayNBT(nArray);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof IntArrayNBT && Arrays.equals(this.intArray, ((IntArrayNBT)object).intArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("I").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iFormattableTextComponent2 = new StringTextComponent("[").append(iFormattableTextComponent).appendString(";");
        for (int i = 0; i < this.intArray.length; ++i) {
            iFormattableTextComponent2.appendString(" ").append(new StringTextComponent(String.valueOf(this.intArray[i])).mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER));
            if (i == this.intArray.length - 1) continue;
            iFormattableTextComponent2.appendString(",");
        }
        iFormattableTextComponent2.appendString("]");
        return iFormattableTextComponent2;
    }

    @Override
    public int size() {
        return this.intArray.length;
    }

    @Override
    public IntNBT get(int n) {
        return IntNBT.valueOf(this.intArray[n]);
    }

    @Override
    public IntNBT set(int n, IntNBT intNBT) {
        int n2 = this.intArray[n];
        this.intArray[n] = intNBT.getInt();
        return IntNBT.valueOf(n2);
    }

    @Override
    public void add(int n, IntNBT intNBT) {
        this.intArray = ArrayUtils.add(this.intArray, n, intNBT.getInt());
    }

    @Override
    public boolean setNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.intArray[n] = ((NumberNBT)iNBT).getInt();
            return false;
        }
        return true;
    }

    @Override
    public boolean addNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.intArray = ArrayUtils.add(this.intArray, n, ((NumberNBT)iNBT).getInt());
            return false;
        }
        return true;
    }

    @Override
    public IntNBT remove(int n) {
        int n2 = this.intArray[n];
        this.intArray = ArrayUtils.remove(this.intArray, n);
        return IntNBT.valueOf(n2);
    }

    @Override
    public byte getTagType() {
        return 0;
    }

    @Override
    public void clear() {
        this.intArray = new int[0];
    }

    @Override
    public INBT remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, INBT iNBT) {
        this.add(n, (IntNBT)iNBT);
    }

    @Override
    public INBT set(int n, INBT iNBT) {
        return this.set(n, (IntNBT)iNBT);
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
        this.add(n, (IntNBT)object);
    }

    @Override
    public Object set(int n, Object object) {
        return this.set(n, (IntNBT)object);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }
}

