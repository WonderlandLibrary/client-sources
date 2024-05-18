package net.minecraft.src;

import java.io.*;
import java.util.*;

public class NBTTagList extends NBTBase
{
    private List tagList;
    private byte tagType;
    
    public NBTTagList() {
        super("");
        this.tagList = new ArrayList();
    }
    
    public NBTTagList(final String par1Str) {
        super(par1Str);
        this.tagList = new ArrayList();
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getId();
        }
        else {
            this.tagType = 1;
        }
        par1DataOutput.writeByte(this.tagType);
        par1DataOutput.writeInt(this.tagList.size());
        for (int var2 = 0; var2 < this.tagList.size(); ++var2) {
            this.tagList.get(var2).write(par1DataOutput);
        }
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
        this.tagType = par1DataInput.readByte();
        final int var2 = par1DataInput.readInt();
        this.tagList = new ArrayList();
        for (int var3 = 0; var3 < var2; ++var3) {
            final NBTBase var4 = NBTBase.newTag(this.tagType, null);
            var4.load(par1DataInput);
            this.tagList.add(var4);
        }
    }
    
    @Override
    public byte getId() {
        return 9;
    }
    
    @Override
    public String toString() {
        return this.tagList.size() + " entries of type " + NBTBase.getTagName(this.tagType);
    }
    
    public void appendTag(final NBTBase par1NBTBase) {
        this.tagType = par1NBTBase.getId();
        this.tagList.add(par1NBTBase);
    }
    
    public NBTBase removeTag(final int par1) {
        return this.tagList.remove(par1);
    }
    
    public NBTBase tagAt(final int par1) {
        return this.tagList.get(par1);
    }
    
    public int tagCount() {
        return this.tagList.size();
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagList var1 = new NBTTagList(this.getName());
        var1.tagType = this.tagType;
        for (final NBTBase var3 : this.tagList) {
            final NBTBase var4 = var3.copy();
            var1.tagList.add(var4);
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (super.equals(par1Obj)) {
            final NBTTagList var2 = (NBTTagList)par1Obj;
            if (this.tagType == var2.tagType) {
                return this.tagList.equals(var2.tagList);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
}
