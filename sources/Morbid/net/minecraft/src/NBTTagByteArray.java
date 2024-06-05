package net.minecraft.src;

import java.io.*;
import java.util.*;

public class NBTTagByteArray extends NBTBase
{
    public byte[] byteArray;
    
    public NBTTagByteArray(final String par1Str) {
        super(par1Str);
    }
    
    public NBTTagByteArray(final String par1Str, final byte[] par2ArrayOfByte) {
        super(par1Str);
        this.byteArray = par2ArrayOfByte;
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.byteArray.length);
        par1DataOutput.write(this.byteArray);
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
        final int var2 = par1DataInput.readInt();
        par1DataInput.readFully(this.byteArray = new byte[var2]);
    }
    
    @Override
    public byte getId() {
        return 7;
    }
    
    @Override
    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }
    
    @Override
    public NBTBase copy() {
        final byte[] var1 = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, var1, 0, this.byteArray.length);
        return new NBTTagByteArray(this.getName(), var1);
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        return super.equals(par1Obj) && Arrays.equals(this.byteArray, ((NBTTagByteArray)par1Obj).byteArray);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }
}
