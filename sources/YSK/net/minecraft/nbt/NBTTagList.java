package net.minecraft.nbt;

import java.util.*;
import com.google.common.collect.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class NBTTagList extends NBTBase
{
    private static final String[] I;
    private static final Logger LOGGER;
    private List<NBTBase> tagList;
    private byte tagType;
    
    public float getFloatAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            float float1;
            if (nbtBase.getId() == (0x81 ^ 0x84)) {
                float1 = ((NBTTagFloat)nbtBase).getFloat();
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else {
                float1 = 0.0f;
            }
            return float1;
        }
        return 0.0f;
    }
    
    public void appendTag(final NBTBase nbtBase) {
        if (nbtBase.getId() == 0) {
            NBTTagList.LOGGER.warn(NBTTagList.I["   ".length()]);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (this.tagType != nbtBase.getId()) {
                NBTTagList.LOGGER.warn(NBTTagList.I[0x11 ^ 0x15]);
                return;
            }
            this.tagList.add(nbtBase);
        }
    }
    
    public NBTBase removeTag(final int n) {
        return this.tagList.remove(n);
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagList list = new NBTTagList();
        list.tagType = this.tagType;
        final Iterator<NBTBase> iterator = this.tagList.iterator();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.tagList.add(iterator.next().copy());
        }
        return list;
    }
    
    public double getDoubleAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            double double1;
            if (nbtBase.getId() == (0xA5 ^ 0xA3)) {
                double1 = ((NBTTagDouble)nbtBase).getDouble();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                double1 = 0.0;
            }
            return double1;
        }
        return 0.0;
    }
    
    public NBTBase get(final int n) {
        NBTBase nbtBase;
        if (n >= 0 && n < this.tagList.size()) {
            nbtBase = this.tagList.get(n);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            nbtBase = new NBTTagEnd();
        }
        return nbtBase;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(296L);
        if (n > 195 + 189 - 133 + 261) {
            throw new RuntimeException(NBTTagList.I["".length()]);
        }
        this.tagType = dataInput.readByte();
        final int int1 = dataInput.readInt();
        if (this.tagType == 0 && int1 > 0) {
            throw new RuntimeException(NBTTagList.I[" ".length()]);
        }
        nbtSizeTracker.read(32L * int1);
        this.tagList = (List<NBTBase>)Lists.newArrayListWithCapacity(int1);
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < int1) {
            final NBTBase newByType = NBTBase.createNewByType(this.tagType);
            newByType.read(dataInput, n + " ".length(), nbtSizeTracker);
            this.tagList.add(newByType);
            ++i;
        }
    }
    
    public void set(final int n, final NBTBase nbtBase) {
        if (nbtBase.getId() == 0) {
            NBTTagList.LOGGER.warn(NBTTagList.I[0xB ^ 0xE]);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (n >= 0 && n < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (this.tagType != nbtBase.getId()) {
                NBTTagList.LOGGER.warn(NBTTagList.I[0x55 ^ 0x53]);
                return;
            }
            this.tagList.set(n, nbtBase);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            NBTTagList.LOGGER.warn(NBTTagList.I[0x5E ^ 0x59]);
        }
    }
    
    public int getTagType() {
        return this.tagType;
    }
    
    public String getStringTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            String s;
            if (nbtBase.getId() == (0xB9 ^ 0xB1)) {
                s = nbtBase.getString();
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                s = nbtBase.toString();
            }
            return s;
        }
        return NBTTagList.I[0x38 ^ 0x30];
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(NBTTagList.I["  ".length()]);
        int i = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (i < this.tagList.size()) {
            if (i != 0) {
                sb.append((char)(0xB6 ^ 0x9A));
            }
            sb.append(i).append((char)(0x23 ^ 0x19)).append(this.tagList.get(i));
            ++i;
        }
        return sb.append((char)(0xFD ^ 0xA0)).toString();
    }
    
    public int tagCount() {
        return this.tagList.size();
    }
    
    public NBTTagCompound getCompoundTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            NBTTagCompound nbtTagCompound;
            if (nbtBase.getId() == (0x40 ^ 0x4A)) {
                nbtTagCompound = (NBTTagCompound)nbtBase;
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                nbtTagCompound = new NBTTagCompound();
            }
            return nbtTagCompound;
        }
        return new NBTTagCompound();
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get("".length()).getId();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            this.tagType = (byte)"".length();
        }
        dataOutput.writeByte(this.tagType);
        dataOutput.writeInt(this.tagList.size());
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.tagList.size()) {
            this.tagList.get(i).write(dataOutput);
            ++i;
        }
    }
    
    @Override
    public byte getId() {
        return (byte)(0xC9 ^ 0xC0);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0xC8 ^ 0xC1])["".length()] = I("\u0002\u0004\u000e\u001f\u0012v\u0002\bZ\u00043\u0017\u0003Z8\u0014\"G\u000e\u00171V\u0010\u0013\u0002>V\u0013\u0015\u0019v\u001e\u000e\u001d\u001ev\u0015\b\u0017\u0006:\u0013\u001f\u0013\u0002/ZG\u001e\u0013&\u0002\u000fZHvCVH", "Vvgzv");
        NBTTagList.I[" ".length()] = I("\u0005\u0018=\u0016\u001b&\u0016n\u0011\u000b8\u0014n\n\u001ch='\u0016\u0006\u001c\u0010)", "HqNer");
        NBTTagList.I["  ".length()] = I("\u001c", "GmCcB");
        NBTTagList.I["   ".length()] = I("=\b\u00006=\u001d\u0002V\u00030\u0013#\u00183q\u0015\u0002\u001225T\u0012\u0019w\u001d\u001d\u0015\u0002\u00030\u0013", "tfvWQ");
        NBTTagList.I[0x41 ^ 0x45] = I("\u0018  1;>d)1&4%0;=0*#x!8#d,,)!7x!6d092y(-+!", "YDDXU");
        NBTTagList.I[0xA1 ^ 0xA4] = I(":/7\"\u000f\u001a%a\u0017\u0002\u0014\u0004/'C\u0012%%&\u0007S5.c/\u001a25\u0017\u0002\u0014", "sAACc");
        NBTTagList.I[0x8 ^ 0xE] = I("7\u0000\u0017>\u001c\u0011D\u001e>\u0001\u001b\u0005\u00074\u001a\u001f\n\u0014w\u0006\u0017\u0003S#\u000b\u0006\u0001\u0000w\u0006\u0019D\u00076\u0015V\b\u001a$\u0006", "vdsWr");
        NBTTagList.I[0x16 ^ 0x11] = I("\u0013\u000f3\"\nZ\u000e\"3R\u0015\u0007w%\u001d\u000f\u000f34R\u000e\u000ew4\u0017\u000eA#&\u0015Z\b9g\u0006\u001b\u0006w+\u001b\t\u0015", "zaWGr");
        NBTTagList.I[0xCC ^ 0xC4] = I("", "egDbo");
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    public int[] getIntArrayAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            int[] intArray;
            if (nbtBase.getId() == (0x73 ^ 0x78)) {
                intArray = ((NBTTagIntArray)nbtBase).getIntArray();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                intArray = new int["".length()];
            }
            return intArray;
        }
        return new int["".length()];
    }
    
    public NBTTagList() {
        this.tagList = (List<NBTBase>)Lists.newArrayList();
        this.tagType = (byte)"".length();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            final NBTTagList list = (NBTTagList)o;
            if (this.tagType == list.tagType) {
                return this.tagList.equals(list.tagList);
            }
        }
        return "".length() != 0;
    }
}
