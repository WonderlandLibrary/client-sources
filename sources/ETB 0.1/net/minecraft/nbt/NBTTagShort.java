package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort
  extends NBTBase.NBTPrimitive
{
  private short data;
  private static final String __OBFID = "CL_00001227";
  
  public NBTTagShort() {}
  
  public NBTTagShort(short data)
  {
    this.data = data;
  }
  


  void write(DataOutput output)
    throws IOException
  {
    output.writeShort(data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
  {
    sizeTracker.read(16L);
    data = input.readShort();
  }
  



  public byte getId()
  {
    return 2;
  }
  
  public String toString()
  {
    return data + "s";
  }
  



  public NBTBase copy()
  {
    return new NBTTagShort(data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagShort var2 = (NBTTagShort)p_equals_1_;
      return data == data;
    }
    

    return false;
  }
  

  public int hashCode()
  {
    return super.hashCode() ^ data;
  }
  
  public long getLong()
  {
    return data;
  }
  
  public int getInt()
  {
    return data;
  }
  
  public short getShort()
  {
    return data;
  }
  
  public byte getByte()
  {
    return (byte)(data & 0xFF);
  }
  
  public double getDouble()
  {
    return data;
  }
  
  public float getFloat()
  {
    return data;
  }
}
