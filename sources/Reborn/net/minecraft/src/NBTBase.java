package net.minecraft.src;

import java.io.*;

public abstract class NBTBase
{
    public static final String[] NBTTypes;
    private String name;
    
    static {
        NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
    }
    
    abstract void write(final DataOutput p0) throws IOException;
    
    abstract void load(final DataInput p0) throws IOException;
    
    public abstract byte getId();
    
    protected NBTBase(final String par1Str) {
        if (par1Str == null) {
            this.name = "";
        }
        else {
            this.name = par1Str;
        }
    }
    
    public NBTBase setName(final String par1Str) {
        if (par1Str == null) {
            this.name = "";
        }
        else {
            this.name = par1Str;
        }
        return this;
    }
    
    public String getName() {
        return (this.name == null) ? "" : this.name;
    }
    
    public static NBTBase readNamedTag(final DataInput par0DataInput) throws IOException {
        final byte var1 = par0DataInput.readByte();
        if (var1 == 0) {
            return new NBTTagEnd();
        }
        final String var2 = par0DataInput.readUTF();
        final NBTBase var3 = newTag(var1, var2);
        try {
            var3.load(par0DataInput);
            return var3;
        }
        catch (IOException var5) {
            final CrashReport var4 = CrashReport.makeCrashReport(var5, "Loading NBT data");
            final CrashReportCategory var6 = var4.makeCategory("NBT Tag");
            var6.addCrashSection("Tag name", var2);
            var6.addCrashSection("Tag type", var1);
            throw new ReportedException(var4);
        }
    }
    
    public static void writeNamedTag(final NBTBase par0NBTBase, final DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeByte(par0NBTBase.getId());
        if (par0NBTBase.getId() != 0) {
            par1DataOutput.writeUTF(par0NBTBase.getName());
            par0NBTBase.write(par1DataOutput);
        }
    }
    
    public static NBTBase newTag(final byte par0, final String par1Str) {
        switch (par0) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte(par1Str);
            }
            case 2: {
                return new NBTTagShort(par1Str);
            }
            case 3: {
                return new NBTTagInt(par1Str);
            }
            case 4: {
                return new NBTTagLong(par1Str);
            }
            case 5: {
                return new NBTTagFloat(par1Str);
            }
            case 6: {
                return new NBTTagDouble(par1Str);
            }
            case 7: {
                return new NBTTagByteArray(par1Str);
            }
            case 8: {
                return new NBTTagString(par1Str);
            }
            case 9: {
                return new NBTTagList(par1Str);
            }
            case 10: {
                return new NBTTagCompound(par1Str);
            }
            case 11: {
                return new NBTTagIntArray(par1Str);
            }
            default: {
                return null;
            }
        }
    }
    
    public static String getTagName(final byte par0) {
        switch (par0) {
            case 0: {
                return "TAG_End";
            }
            case 1: {
                return "TAG_Byte";
            }
            case 2: {
                return "TAG_Short";
            }
            case 3: {
                return "TAG_Int";
            }
            case 4: {
                return "TAG_Long";
            }
            case 5: {
                return "TAG_Float";
            }
            case 6: {
                return "TAG_Double";
            }
            case 7: {
                return "TAG_Byte_Array";
            }
            case 8: {
                return "TAG_String";
            }
            case 9: {
                return "TAG_List";
            }
            case 10: {
                return "TAG_Compound";
            }
            case 11: {
                return "TAG_Int_Array";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
    
    public abstract NBTBase copy();
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof NBTBase)) {
            return false;
        }
        final NBTBase var2 = (NBTBase)par1Obj;
        return this.getId() == var2.getId() && ((this.name != null || var2.name == null) && (this.name == null || var2.name != null)) && (this.name == null || this.name.equals(var2.name));
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.getId();
    }
}
