package net.minecraft.nbt;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import java.io.*;

public class NBTTagCompound extends NBTBase
{
    private Map<String, NBTBase> tagMap;
    private static final String[] I;
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(384L);
        if (n > 430 + 276 - 351 + 157) {
            throw new RuntimeException(NBTTagCompound.I["".length()]);
        }
        this.tagMap.clear();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        byte type;
        while ((type = readType(dataInput, nbtSizeTracker)) != 0) {
            final String key = readKey(dataInput, nbtSizeTracker);
            nbtSizeTracker.read(67 + 218 - 101 + 40 + (0x2C ^ 0x3C) * key.length());
            if (this.tagMap.put(key, readNBT(type, key, dataInput, n + " ".length(), nbtSizeTracker)) != null) {
                nbtSizeTracker.read(288L);
            }
        }
    }
    
    static {
        I();
    }
    
    public float getFloat(final String s) {
        try {
            float float1;
            if (!this.hasKey(s, 0x46 ^ 0x25)) {
                float1 = 0.0f;
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                float1 = this.tagMap.get(s).getFloat();
            }
            return float1;
        }
        catch (ClassCastException ex) {
            return 0.0f;
        }
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setByte(final String s, final byte b) {
        this.tagMap.put(s, new NBTTagByte(b));
    }
    
    public byte getTagId(final String s) {
        final NBTBase nbtBase = this.tagMap.get(s);
        int n;
        if (nbtBase != null) {
            n = nbtBase.getId();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return (byte)n;
    }
    
    public Set<String> getKeySet() {
        return this.tagMap.keySet();
    }
    
    public String getString(final String s) {
        try {
            String string;
            if (!this.hasKey(s, 0x90 ^ 0x98)) {
                string = NBTTagCompound.I[" ".length()];
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                string = this.tagMap.get(s).getString();
            }
            return string;
        }
        catch (ClassCastException ex) {
            return NBTTagCompound.I["  ".length()];
        }
    }
    
    public short getShort(final String s) {
        try {
            int n;
            if (!this.hasKey(s, 0x52 ^ 0x31)) {
                n = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n = this.tagMap.get(s).getShort();
            }
            return (short)n;
        }
        catch (ClassCastException ex) {
            return (short)"".length();
        }
    }
    
    public int[] getIntArray(final String s) {
        try {
            int[] intArray;
            if (!this.hasKey(s, 0x5B ^ 0x50)) {
                intArray = new int["".length()];
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                intArray = this.tagMap.get(s).getIntArray();
            }
            return intArray;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.createCrashReport(s, 0xA8 ^ 0xA3, ex));
        }
    }
    
    public void setLong(final String s, final long n) {
        this.tagMap.put(s, new NBTTagLong(n));
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        final Iterator<String> iterator = this.tagMap.keySet().iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            writeEntry(s, this.tagMap.get(s), dataOutput);
        }
        dataOutput.writeByte("".length());
    }
    
    private static void writeEntry(final String s, final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataOutput.writeUTF(s);
            nbtBase.write(dataOutput);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(NBTTagCompound.I["   ".length()]);
        final Iterator<Map.Entry<String, NBTBase>> iterator = this.tagMap.entrySet().iterator();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, NBTBase> entry = iterator.next();
            if (sb.length() != " ".length()) {
                sb.append((char)(0x78 ^ 0x54));
            }
            sb.append(entry.getKey()).append((char)(0xA2 ^ 0x98)).append(entry.getValue());
        }
        return sb.append((char)(0xEB ^ 0x96)).toString();
    }
    
    private static byte readType(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readByte();
    }
    
    public byte getByte(final String s) {
        try {
            int n;
            if (!this.hasKey(s, 0x77 ^ 0x14)) {
                n = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n = this.tagMap.get(s).getByte();
            }
            return (byte)n;
        }
        catch (ClassCastException ex) {
            return (byte)"".length();
        }
    }
    
    public void setFloat(final String s, final float n) {
        this.tagMap.put(s, new NBTTagFloat(n));
    }
    
    @Override
    public byte getId() {
        return (byte)(0x6D ^ 0x67);
    }
    
    public int getInteger(final String s) {
        try {
            int n;
            if (!this.hasKey(s, 0xF1 ^ 0x92)) {
                n = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                n = this.tagMap.get(s).getInt();
            }
            return n;
        }
        catch (ClassCastException ex) {
            return "".length();
        }
    }
    
    public void setString(final String s, final String s2) {
        this.tagMap.put(s, new NBTTagString(s2));
    }
    
    public void setBoolean(final String s, final boolean b) {
        int n;
        if (b) {
            n = " ".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        this.setByte(s, (byte)n);
    }
    
    public void merge(final NBTTagCompound nbtTagCompound) {
        final Iterator<String> iterator = nbtTagCompound.tagMap.keySet().iterator();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final NBTBase nbtBase = nbtTagCompound.tagMap.get(s);
            if (nbtBase.getId() == (0xCD ^ 0xC7)) {
                if (this.hasKey(s, 0x6B ^ 0x61)) {
                    this.getCompoundTag(s).merge((NBTTagCompound)nbtBase);
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                    continue;
                }
                else {
                    this.setTag(s, nbtBase.copy());
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    continue;
                }
            }
            else {
                this.setTag(s, nbtBase.copy());
            }
        }
    }
    
    public void setByteArray(final String s, final byte[] array) {
        this.tagMap.put(s, new NBTTagByteArray(array));
    }
    
    public NBTBase getTag(final String s) {
        return this.tagMap.get(s);
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }
    
    public boolean hasKey(final String s, final int n) {
        final byte tagId = this.getTagId(s);
        if (tagId == n) {
            return " ".length() != 0;
        }
        if (n != (0x1A ^ 0x79)) {
            if (tagId > 0) {}
            return "".length() != 0;
        }
        if (tagId != " ".length() && tagId != "  ".length() && tagId != "   ".length() && tagId != (0xA5 ^ 0xA1) && tagId != (0x8B ^ 0x8E) && tagId != (0xBE ^ 0xB8)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void setDouble(final String s, final double n) {
        this.tagMap.put(s, new NBTTagDouble(n));
    }
    
    private static void I() {
        (I = new String[0xB0 ^ 0xBD])["".length()] = I("\u0011\u0007?\u00101e\u00019U' \u00142U\u001b\u0007!v\u00014\"U!\u001c!-U\"\u001a:e\u001d?\u0012=e\u00169\u0018%)\u0010.\u001c!<Yv\u001105\u0001>Uke@gG", "EuVuU");
        NBTTagCompound.I[" ".length()] = I("", "GCfIx");
        NBTTagCompound.I["  ".length()] = I("", "qyPgi");
        NBTTagCompound.I["   ".length()] = I(")", "RLgCQ");
        NBTTagCompound.I[0x2D ^ 0x29] = I("\u0004!\u0003\u0014\u00138#B>8\u0002d\u0006\u0011\u000e7", "VDbpz");
        NBTTagCompound.I[0xBE ^ 0xBB] = I("\f6#\u0013\u0019?-q/.\u001by%\u0000\u000b", "OYQal");
        NBTTagCompound.I[0x72 ^ 0x74] = I(">#\u0012D,\u00132\u0010D>\u00057\u001b\u0000", "jBudX");
        NBTTagCompound.I[0x9E ^ 0x99] = I("\f8!n.!)#n? )#-.==", "XYFNZ");
        NBTTagCompound.I[0x3E ^ 0x36] = I("\u0018\u0018&a--\u0014$", "LyAAC");
        NBTTagCompound.I[0xA9 ^ 0xA0] = I("\u0019  \"\u0004;(a\b/\u0001o%'\u00194", "UOAFm");
        NBTTagCompound.I[0x6A ^ 0x60] = I("#\u0013\u0010h\u0015\f6", "mQDHA");
        NBTTagCompound.I[0x93 ^ 0x98] = I("\u0011\u00156C-$\u00194", "EtQcC");
        NBTTagCompound.I[0xA8 ^ 0xA4] = I("-*\u0001k#\u0000;\u0003", "yKfKW");
    }
    
    public boolean getBoolean(final String s) {
        if (this.getByte(s) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public NBTTagCompound() {
        this.tagMap = (Map<String, NBTBase>)Maps.newHashMap();
    }
    
    public long getLong(final String s) {
        try {
            long long1;
            if (!this.hasKey(s, 0xFD ^ 0x9E)) {
                long1 = 0L;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                long1 = this.tagMap.get(s).getLong();
            }
            return long1;
        }
        catch (ClassCastException ex) {
            return 0L;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            return this.tagMap.entrySet().equals(((NBTTagCompound)o).tagMap.entrySet());
        }
        return "".length() != 0;
    }
    
    public boolean hasKey(final String s) {
        return this.tagMap.containsKey(s);
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final Iterator<String> iterator = this.tagMap.keySet().iterator();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            nbtTagCompound.setTag(s, this.tagMap.get(s).copy());
        }
        return nbtTagCompound;
    }
    
    public double getDouble(final String s) {
        try {
            double double1;
            if (!this.hasKey(s, 0x7B ^ 0x18)) {
                double1 = 0.0;
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                double1 = this.tagMap.get(s).getDouble();
            }
            return double1;
        }
        catch (ClassCastException ex) {
            return 0.0;
        }
    }
    
    private CrashReport createCrashReport(final String s, final int n, final ClassCastException ex) {
        final CrashReport crashReport = CrashReport.makeCrashReport(ex, NBTTagCompound.I[0x1D ^ 0x19]);
        final CrashReportCategory categoryDepth = crashReport.makeCategoryDepth(NBTTagCompound.I[0x8F ^ 0x8A], " ".length());
        categoryDepth.addCrashSectionCallable(NBTTagCompound.I[0x99 ^ 0x9F], new Callable<String>(this, s) {
            private final String val$key;
            final NBTTagCompound this$0;
            
            @Override
            public String call() throws Exception {
                return NBTBase.NBT_TYPES[NBTTagCompound.access$0(this.this$0).get(this.val$key).getId()];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (3 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        categoryDepth.addCrashSectionCallable(NBTTagCompound.I[0xBA ^ 0xBD], new Callable<String>(this, n) {
            private final int val$expectedType;
            final NBTTagCompound this$0;
            
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return NBTBase.NBT_TYPES[this.val$expectedType];
            }
        });
        categoryDepth.addCrashSection(NBTTagCompound.I[0x51 ^ 0x59], s);
        return crashReport;
    }
    
    static NBTBase readNBT(final byte b, final String s, final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final NBTBase newByType = NBTBase.createNewByType(b);
        try {
            newByType.read(dataInput, n, nbtSizeTracker);
            return newByType;
        }
        catch (IOException ex) {
            final CrashReport crashReport = CrashReport.makeCrashReport(ex, NBTTagCompound.I[0xB3 ^ 0xBA]);
            final CrashReportCategory category = crashReport.makeCategory(NBTTagCompound.I[0x2E ^ 0x24]);
            category.addCrashSection(NBTTagCompound.I[0x31 ^ 0x3A], s);
            category.addCrashSection(NBTTagCompound.I[0x2B ^ 0x27], b);
            throw new ReportedException(crashReport);
        }
    }
    
    private static String readKey(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readUTF();
    }
    
    public NBTTagCompound getCompoundTag(final String s) {
        try {
            NBTTagCompound nbtTagCompound;
            if (!this.hasKey(s, 0x9F ^ 0x95)) {
                nbtTagCompound = new NBTTagCompound();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            else {
                nbtTagCompound = this.tagMap.get(s);
            }
            return nbtTagCompound;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.createCrashReport(s, 0x1 ^ 0xB, ex));
        }
    }
    
    public void removeTag(final String s) {
        this.tagMap.remove(s);
    }
    
    public void setInteger(final String s, final int n) {
        this.tagMap.put(s, new NBTTagInt(n));
    }
    
    public void setTag(final String s, final NBTBase nbtBase) {
        this.tagMap.put(s, nbtBase);
    }
    
    public void setIntArray(final String s, final int[] array) {
        this.tagMap.put(s, new NBTTagIntArray(array));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
    
    public NBTTagList getTagList(final String s, final int n) {
        try {
            if (this.getTagId(s) != (0x35 ^ 0x3C)) {
                return new NBTTagList();
            }
            final NBTTagList list = this.tagMap.get(s);
            NBTTagList list2;
            if (list.tagCount() > 0 && list.getTagType() != n) {
                list2 = new NBTTagList();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                list2 = list;
            }
            return list2;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.createCrashReport(s, 0x84 ^ 0x8D, ex));
        }
    }
    
    public byte[] getByteArray(final String s) {
        try {
            byte[] byteArray;
            if (!this.hasKey(s, 0x7D ^ 0x7A)) {
                byteArray = new byte["".length()];
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                byteArray = this.tagMap.get(s).getByteArray();
            }
            return byteArray;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.createCrashReport(s, 0x7 ^ 0x0, ex));
        }
    }
    
    public void setShort(final String s, final short n) {
        this.tagMap.put(s, new NBTTagShort(n));
    }
    
    static Map access$0(final NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.tagMap;
    }
}
