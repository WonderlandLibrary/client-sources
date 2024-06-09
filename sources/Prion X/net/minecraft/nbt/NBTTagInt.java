package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt
  extends NBTBase.NBTPrimitive
{
  private int data;
  private static final String __OBFID = "CL_00001223";
  
  NBTTagInt() {}
  
  public NBTTagInt(int data)
  {
    this.data = data;
  }
  


  void write(DataOutput output)
    throws IOException
  {
    output.writeInt(data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
  {
    sizeTracker.read(32L);
    data = input.readInt();
  }
  



  public byte getId()
  {
    return 3;
  }
  
  public String toString()
  {
    return data;
  }
  



  public NBTBase copy()
  {
    return new NBTTagInt(data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagInt var2 = (NBTTagInt)p_equals_1_;
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
    return (short)(data & 0xFFFF);
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
