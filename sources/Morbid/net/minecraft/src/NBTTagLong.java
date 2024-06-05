package net.minecraft.src;

import java.io.*;

public class NBTTagLong extends NBTBase
{
    public long data;
    
    public NBTTagLong(final String par1Str) {
        super(par1Str);
    }
    
    public NBTTagLong(final String par1Str, final long par2) {
        super(par1Str);
        this.data = par2;
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeLong(this.data);
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
        this.data = par1DataInput.readLong();
    }
    
    @Override
    public byte getId() {
        return 4;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(this.data).toString();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.getName(), this.data);
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (super.equals(par1Obj)) {
            final NBTTagLong var2 = (NBTTagLong)par1Obj;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }
}
