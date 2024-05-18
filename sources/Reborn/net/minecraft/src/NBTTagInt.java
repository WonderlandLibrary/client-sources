package net.minecraft.src;

import java.io.*;

public class NBTTagInt extends NBTBase
{
    public int data;
    
    public NBTTagInt(final String par1Str) {
        super(par1Str);
    }
    
    public NBTTagInt(final String par1Str, final int par2) {
        super(par1Str);
        this.data = par2;
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.data);
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
        this.data = par1DataInput.readInt();
    }
    
    @Override
    public byte getId() {
        return 3;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(this.data).toString();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.getName(), this.data);
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (super.equals(par1Obj)) {
            final NBTTagInt var2 = (NBTTagInt)par1Obj;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
