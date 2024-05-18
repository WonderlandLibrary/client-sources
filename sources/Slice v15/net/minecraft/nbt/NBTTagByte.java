package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte
  extends NBTBase.NBTPrimitive
{
  private byte data;
  private static final String __OBFID = "CL_00001214";
  
  NBTTagByte() {}
  
  public NBTTagByte(byte data)
  {
    this.data = data;
  }
  


  void write(DataOutput output)
    throws IOException
  {
    output.writeByte(data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
  {
    sizeTracker.read(8L);
    data = input.readByte();
  }
  



  public byte getId()
  {
    return 1;
  }
  
  public String toString()
  {
    return data + "b";
  }
  



  public NBTBase copy()
  {
    return new NBTTagByte(data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagByte var2 = (NBTTagByte)p_equals_1_;
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
    return (short)data;
  }
  
  public byte getByte()
  {
    return data;
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
