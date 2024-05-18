package net.minecraft.src;

import java.io.*;

public class NBTTagEnd extends NBTBase
{
    public NBTTagEnd() {
        super(null);
    }
    
    @Override
    void load(final DataInput par1DataInput) throws IOException {
    }
    
    @Override
    void write(final DataOutput par1DataOutput) throws IOException {
    }
    
    @Override
    public byte getId() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
}
