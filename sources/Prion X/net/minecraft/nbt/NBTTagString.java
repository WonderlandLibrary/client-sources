package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString
  extends NBTBase
{
  private String data;
  private static final String __OBFID = "CL_00001228";
  
  public NBTTagString()
  {
    data = "";
  }
  
  public NBTTagString(String data)
  {
    this.data = data;
    
    if (data == null)
    {
      throw new IllegalArgumentException("Empty string not allowed");
    }
  }
  


  void write(DataOutput output)
    throws IOException
  {
    output.writeUTF(data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
  {
    data = input.readUTF();
    sizeTracker.read(16 * data.length());
  }
  



  public byte getId()
  {
    return 8;
  }
  
  public String toString()
  {
    return "\"" + data.replace("\"", "\\\"") + "\"";
  }
  



  public NBTBase copy()
  {
    return new NBTTagString(data);
  }
  



  public boolean hasNoTags()
  {
    return data.isEmpty();
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (!super.equals(p_equals_1_))
    {
      return false;
    }
    

    NBTTagString var2 = (NBTTagString)p_equals_1_;
    return ((data == null) && (data == null)) || ((data != null) && (data.equals(data)));
  }
  

  public int hashCode()
  {
    return super.hashCode() ^ data.hashCode();
  }
  
  public String getString()
  {
    return data;
  }
}
