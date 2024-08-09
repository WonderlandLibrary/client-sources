/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteArrayNBT
extends CollectionNBT<ByteNBT> {
    public static final INBTType<ByteArrayNBT> TYPE = new INBTType<ByteArrayNBT>(){

        @Override
        public ByteArrayNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(192L);
            int n2 = dataInput.readInt();
            nBTSizeTracker.read(8L * (long)n2);
            byte[] byArray = new byte[n2];
            dataInput.readFully(byArray);
            return new ByteArrayNBT(byArray);
        }

        @Override
        public String getName() {
            return "BYTE[]";
        }

        @Override
        public String getTagName() {
            return "TAG_Byte_Array";
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private byte[] data;

    public ByteArrayNBT(byte[] byArray) {
        this.data = byArray;
    }

    public ByteArrayNBT(List<Byte> list) {
        this(ByteArrayNBT.toArray(list));
    }

    private static byte[] toArray(List<Byte> list) {
        byte[] byArray = new byte[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Byte by = list.get(i);
            byArray[i] = by == null ? (byte)0 : by;
        }
        return byArray;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<ByteArrayNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[B;");
        for (int i = 0; i < this.data.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.data[i]).append('B');
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public INBT copy() {
        byte[] byArray = new byte[this.data.length];
        System.arraycopy(this.data, 0, byArray, 0, this.data.length);
        return new ByteArrayNBT(byArray);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof ByteArrayNBT && Arrays.equals(this.data, ((ByteArrayNBT)object).data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("B").mergeStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
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

    public byte[] getByteArray() {
        return this.data;
    }

    @Override
    public int size() {
        return this.data.length;
    }

    @Override
    public ByteNBT get(int n) {
        return ByteNBT.valueOf(this.data[n]);
    }

    @Override
    public ByteNBT set(int n, ByteNBT byteNBT) {
        byte by = this.data[n];
        this.data[n] = byteNBT.getByte();
        return ByteNBT.valueOf(by);
    }

    @Override
    public void add(int n, ByteNBT byteNBT) {
        this.data = ArrayUtils.add(this.data, n, byteNBT.getByte());
    }

    @Override
    public boolean setNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.data[n] = ((NumberNBT)iNBT).getByte();
            return false;
        }
        return true;
    }

    @Override
    public boolean addNBTByIndex(int n, INBT iNBT) {
        if (iNBT instanceof NumberNBT) {
            this.data = ArrayUtils.add(this.data, n, ((NumberNBT)iNBT).getByte());
            return false;
        }
        return true;
    }

    @Override
    public ByteNBT remove(int n) {
        byte by = this.data[n];
        this.data = ArrayUtils.remove(this.data, n);
        return ByteNBT.valueOf(by);
    }

    @Override
    public byte getTagType() {
        return 0;
    }

    @Override
    public void clear() {
        this.data = new byte[0];
    }

    @Override
    public INBT remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, INBT iNBT) {
        this.add(n, (ByteNBT)iNBT);
    }

    @Override
    public INBT set(int n, INBT iNBT) {
        return this.set(n, (ByteNBT)iNBT);
    }

    @Override
    public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, Object object) {
        this.add(n, (ByteNBT)object);
    }

    @Override
    public Object set(int n, Object object) {
        return this.set(n, (ByteNBT)object);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }
}

