package net.minecraft.src;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class NBTTagCompound extends NBTBase
{
    private Map tagMap;
    
    public NBTTagCompound() {
        super("");
        this.tagMap = new HashMap();
    }
    
    public NBTTagCompound(final String par1Str) {
        super(par1Str);
        this.tagMap = new HashMap();
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
        for (final NBTBase var3 : this.tagMap.values()) {
            NBTBase.writeNamedTag(var3, par1DataOutput);
        }
        par1DataOutput.writeByte(0);
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
        this.tagMap.clear();
        NBTBase var2;
        while ((var2 = NBTBase.readNamedTag(par1DataInput)).getId() != 0) {
            this.tagMap.put(var2.getName(), var2);
        }
    }
    
    public Collection getTags() {
        return this.tagMap.values();
    }
    
    @Override
    public byte getId() {
        return 10;
    }
    
    public void setTag(final String par1Str, final NBTBase par2NBTBase) {
        this.tagMap.put(par1Str, par2NBTBase.setName(par1Str));
    }
    
    public void setByte(final String par1Str, final byte par2) {
        this.tagMap.put(par1Str, new NBTTagByte(par1Str, par2));
    }
    
    public void setShort(final String par1Str, final short par2) {
        this.tagMap.put(par1Str, new NBTTagShort(par1Str, par2));
    }
    
    public void setInteger(final String par1Str, final int par2) {
        this.tagMap.put(par1Str, new NBTTagInt(par1Str, par2));
    }
    
    public void setLong(final String par1Str, final long par2) {
        this.tagMap.put(par1Str, new NBTTagLong(par1Str, par2));
    }
    
    public void setFloat(final String par1Str, final float par2) {
        this.tagMap.put(par1Str, new NBTTagFloat(par1Str, par2));
    }
    
    public void setDouble(final String par1Str, final double par2) {
        this.tagMap.put(par1Str, new NBTTagDouble(par1Str, par2));
    }
    
    public void setString(final String par1Str, final String par2Str) {
        this.tagMap.put(par1Str, new NBTTagString(par1Str, par2Str));
    }
    
    public void setByteArray(final String par1Str, final byte[] par2ArrayOfByte) {
        this.tagMap.put(par1Str, new NBTTagByteArray(par1Str, par2ArrayOfByte));
    }
    
    public void setIntArray(final String par1Str, final int[] par2ArrayOfInteger) {
        this.tagMap.put(par1Str, new NBTTagIntArray(par1Str, par2ArrayOfInteger));
    }
    
    public void setCompoundTag(final String par1Str, final NBTTagCompound par2NBTTagCompound) {
        this.tagMap.put(par1Str, par2NBTTagCompound.setName(par1Str));
    }
    
    public void setBoolean(final String par1Str, final boolean par2) {
        this.setByte(par1Str, (byte)(par2 ? 1 : 0));
    }
    
    public NBTBase getTag(final String par1Str) {
        return this.tagMap.get(par1Str);
    }
    
    public boolean hasKey(final String par1Str) {
        return this.tagMap.containsKey(par1Str);
    }
    
    public byte getByte(final String par1Str) {
        try {
            return (byte)(this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0);
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 1, var3));
        }
    }
    
    public short getShort(final String par1Str) {
        try {
            return (short)(this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0);
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 2, var3));
        }
    }
    
    public int getInteger(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0;
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 3, var3));
        }
    }
    
    public long getLong(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0L;
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 4, var3));
        }
    }
    
    public float getFloat(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0.0f;
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 5, var3));
        }
    }
    
    public double getDouble(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : 0.0;
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 6, var3));
        }
    }
    
    public String getString(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).data : "";
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 8, var3));
        }
    }
    
    public byte[] getByteArray(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).byteArray : new byte[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 7, var3));
        }
    }
    
    public int[] getIntArray(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str).intArray : new int[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 11, var3));
        }
    }
    
    public NBTTagCompound getCompoundTag(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str) : new NBTTagCompound(par1Str);
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 10, var3));
        }
    }
    
    public NBTTagList getTagList(final String par1Str) {
        try {
            return this.tagMap.containsKey(par1Str) ? this.tagMap.get(par1Str) : new NBTTagList(par1Str);
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(par1Str, 9, var3));
        }
    }
    
    public boolean getBoolean(final String par1Str) {
        return this.getByte(par1Str) != 0;
    }
    
    public void removeTag(final String par1Str) {
        this.tagMap.remove(par1Str);
    }
    
    @Override
    public String toString() {
        String var1 = String.valueOf(this.getName()) + ":[";
        for (final String var3 : this.tagMap.keySet()) {
            var1 = String.valueOf(var1) + var3 + ":" + this.tagMap.get(var3) + ",";
        }
        return String.valueOf(var1) + "]";
    }
    
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }
    
    private CrashReport createCrashReport(final String par1Str, final int par2, final ClassCastException par3ClassCastException) {
        final CrashReport var4 = CrashReport.makeCrashReport(par3ClassCastException, "Reading NBT data");
        final CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
        var5.addCrashSectionCallable("Tag type found", new CallableTagCompound1(this, par1Str));
        var5.addCrashSectionCallable("Tag type expected", new CallableTagCompound2(this, par2));
        var5.addCrashSection("Tag name", par1Str);
        if (this.getName() != null && this.getName().length() > 0) {
            var5.addCrashSection("Tag parent", this.getName());
        }
        return var4;
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagCompound var1 = new NBTTagCompound(this.getName());
        for (final String var3 : this.tagMap.keySet()) {
            var1.setTag(var3, this.tagMap.get(var3).copy());
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (super.equals(par1Obj)) {
            final NBTTagCompound var2 = (NBTTagCompound)par1Obj;
            return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
    
    static Map getTagMap(final NBTTagCompound par0NBTTagCompound) {
        return par0NBTTagCompound.tagMap;
    }
}
