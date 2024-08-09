/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ListNBT
extends CollectionNBT<INBT> {
    public static final INBTType<ListNBT> TYPE = new INBTType<ListNBT>(){

        @Override
        public ListNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(296L);
            if (n > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            }
            byte by = dataInput.readByte();
            int n2 = dataInput.readInt();
            if (by == 0 && n2 > 0) {
                throw new RuntimeException("Missing type on ListTag");
            }
            nBTSizeTracker.read(32L * (long)n2);
            INBTType<?> iNBTType = NBTTypes.getGetTypeByID(by);
            ArrayList<INBT> arrayList = Lists.newArrayListWithCapacity(n2);
            for (int i = 0; i < n2; ++i) {
                arrayList.add((INBT)iNBTType.readNBT(dataInput, n + 1, nBTSizeTracker));
            }
            return new ListNBT(arrayList, by);
        }

        @Override
        public String getName() {
            return "LIST";
        }

        @Override
        public String getTagName() {
            return "TAG_List";
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private static final ByteSet typeSet = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
    private final List<INBT> tagList;
    private byte tagType;

    private ListNBT(List<INBT> list, byte by) {
        this.tagList = list;
        this.tagType = by;
    }

    public ListNBT() {
        this(Lists.newArrayList(), 0);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.tagType = this.tagList.isEmpty() ? (byte)0 : this.tagList.get(0).getId();
        dataOutput.writeByte(this.tagType);
        dataOutput.writeInt(this.tagList.size());
        for (INBT iNBT : this.tagList) {
            iNBT.write(dataOutput);
        }
    }

    @Override
    public byte getId() {
        return 0;
    }

    public INBTType<ListNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < this.tagList.size(); ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.tagList.get(i));
        }
        return stringBuilder.append(']').toString();
    }

    private void checkEmpty() {
        if (this.tagList.isEmpty()) {
            this.tagType = 0;
        }
    }

    @Override
    public INBT remove(int n) {
        INBT iNBT = this.tagList.remove(n);
        this.checkEmpty();
        return iNBT;
    }

    @Override
    public boolean isEmpty() {
        return this.tagList.isEmpty();
    }

    public CompoundNBT getCompound(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 10) {
            return (CompoundNBT)iNBT;
        }
        return new CompoundNBT();
    }

    public ListNBT getList(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 9) {
            return (ListNBT)iNBT;
        }
        return new ListNBT();
    }

    public short getShort(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 2) {
            return ((ShortNBT)iNBT).getShort();
        }
        return 1;
    }

    public int getInt(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 3) {
            return ((IntNBT)iNBT).getInt();
        }
        return 1;
    }

    public int[] getIntArray(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 11) {
            return ((IntArrayNBT)iNBT).getIntArray();
        }
        return new int[0];
    }

    public double getDouble(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 6) {
            return ((DoubleNBT)iNBT).getDouble();
        }
        return 0.0;
    }

    public float getFloat(int n) {
        INBT iNBT;
        if (n >= 0 && n < this.tagList.size() && (iNBT = this.tagList.get(n)).getId() == 5) {
            return ((FloatNBT)iNBT).getFloat();
        }
        return 0.0f;
    }

    public String getString(int n) {
        if (n >= 0 && n < this.tagList.size()) {
            INBT iNBT = this.tagList.get(n);
            return iNBT.getId() == 8 ? iNBT.getString() : iNBT.toString();
        }
        return "";
    }

    @Override
    public int size() {
        return this.tagList.size();
    }

    @Override
    public INBT get(int n) {
        return this.tagList.get(n);
    }

    @Override
    public INBT set(int n, INBT iNBT) {
        INBT iNBT2 = this.get(n);
        if (!this.setNBTByIndex(n, iNBT)) {
            throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", iNBT.getId(), this.tagType));
        }
        return iNBT2;
    }

    @Override
    public void add(int n, INBT iNBT) {
        if (!this.addNBTByIndex(n, iNBT)) {
            throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", iNBT.getId(), this.tagType));
        }
    }

    @Override
    public boolean setNBTByIndex(int n, INBT iNBT) {
        if (this.canInsert(iNBT)) {
            this.tagList.set(n, iNBT);
            return false;
        }
        return true;
    }

    @Override
    public boolean addNBTByIndex(int n, INBT iNBT) {
        if (this.canInsert(iNBT)) {
            this.tagList.add(n, iNBT);
            return false;
        }
        return true;
    }

    private boolean canInsert(INBT iNBT) {
        if (iNBT.getId() == 0) {
            return true;
        }
        if (this.tagType == 0) {
            this.tagType = iNBT.getId();
            return false;
        }
        return this.tagType == iNBT.getId();
    }

    @Override
    public ListNBT copy() {
        List<INBT> list = NBTTypes.getGetTypeByID(this.tagType).isPrimitive() ? this.tagList : Iterables.transform(this.tagList, INBT::copy);
        ArrayList<INBT> arrayList = Lists.newArrayList(list);
        return new ListNBT(arrayList, this.tagType);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof ListNBT && Objects.equals(this.tagList, ((ListNBT)object).tagList);
    }

    @Override
    public int hashCode() {
        return this.tagList.hashCode();
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        if (this.isEmpty()) {
            return new StringTextComponent("[]");
        }
        if (typeSet.contains(this.tagType) && this.size() <= 8) {
            String string2 = ", ";
            StringTextComponent stringTextComponent = new StringTextComponent("[");
            for (int i = 0; i < this.tagList.size(); ++i) {
                if (i != 0) {
                    stringTextComponent.appendString(", ");
                }
                stringTextComponent.append(this.tagList.get(i).toFormattedComponent());
            }
            stringTextComponent.appendString("]");
            return stringTextComponent;
        }
        StringTextComponent stringTextComponent = new StringTextComponent("[");
        if (!string.isEmpty()) {
            stringTextComponent.appendString("\n");
        }
        String string3 = String.valueOf(',');
        for (int i = 0; i < this.tagList.size(); ++i) {
            StringTextComponent stringTextComponent2 = new StringTextComponent(Strings.repeat(string, n + 1));
            stringTextComponent2.append(this.tagList.get(i).toFormattedComponent(string, n + 1));
            if (i != this.tagList.size() - 1) {
                stringTextComponent2.appendString(string3).appendString(string.isEmpty() ? " " : "\n");
            }
            stringTextComponent.append(stringTextComponent2);
        }
        if (!string.isEmpty()) {
            stringTextComponent.appendString("\n").appendString(Strings.repeat(string, n));
        }
        stringTextComponent.appendString("]");
        return stringTextComponent;
    }

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public void clear() {
        this.tagList.clear();
        this.tagType = 0;
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
        this.add(n, (INBT)object);
    }

    @Override
    public Object set(int n, Object object) {
        return this.set(n, (INBT)object);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }
}

