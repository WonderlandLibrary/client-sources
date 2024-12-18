package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong
  extends NBTBase.NBTPrimitive
{
  private long data;
  private static final String __OBFID = "CL_00001225";
  
  NBTTagLong() {}
  
  public NBTTagLong(long data)
  {
    this.data = data;
  }
  
  void write(DataOutput output)
    throws IOException
  {
    output.writeLong(this.data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker)
    throws IOException
  {
    sizeTracker.read(64L);
    this.data = input.readLong();
  }
  
  public byte getId()
  {
    return 4;
  }
  
  public String toString()
  {
    return "" + this.data + "L";
  }
  
  public NBTBase copy()
  {
    return new NBTTagLong(this.data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagLong var2 = (NBTTagLong)p_equals_1_;
      return this.data == var2.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
  }
  
  public long getLong()
  {
    return this.data;
  }
  
  public int getInt()
  {
    return (int)(this.data & 0xFFFFFFFFFFFFFFFF);
  }
  
  public short getShort()
  {
    return (short)(int)(this.data & 0xFFFF);
  }
  
  public byte getByte()
  {
    return (byte)(int)(this.data & 0xFF);
  }
  
  public double getDouble()
  {
    return this.data;
  }
  
  public float getFloat()
  {
    return (float)this.data;
  }
}
