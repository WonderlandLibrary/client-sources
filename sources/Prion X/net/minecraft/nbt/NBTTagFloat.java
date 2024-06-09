package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagFloat
  extends NBTBase.NBTPrimitive
{
  private float data;
  private static final String __OBFID = "CL_00001220";
  
  NBTTagFloat() {}
  
  public NBTTagFloat(float data)
  {
    this.data = data;
  }
  


  void write(DataOutput output)
    throws IOException
  {
    output.writeFloat(data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
  {
    sizeTracker.read(32L);
    data = input.readFloat();
  }
  



  public byte getId()
  {
    return 5;
  }
  
  public String toString()
  {
    return data + "f";
  }
  



  public NBTBase copy()
  {
    return new NBTTagFloat(data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagFloat var2 = (NBTTagFloat)p_equals_1_;
      return data == data;
    }
    

    return false;
  }
  

  public int hashCode()
  {
    return super.hashCode() ^ Float.floatToIntBits(data);
  }
  
  public long getLong()
  {
    return data;
  }
  
  public int getInt()
  {
    return MathHelper.floor_float(data);
  }
  
  public short getShort()
  {
    return (short)(MathHelper.floor_float(data) & 0xFFFF);
  }
  
  public byte getByte()
  {
    return (byte)(MathHelper.floor_float(data) & 0xFF);
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
