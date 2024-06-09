package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData implements net.minecraft.network.Packet
{
  private int field_149284_a;
  private int field_149282_b;
  private Extracted field_179758_c;
  private boolean field_149279_g;
  private static final String __OBFID = "CL_00001304";
  
  public S21PacketChunkData() {}
  
  public S21PacketChunkData(Chunk p_i45196_1_, boolean p_i45196_2_, int p_i45196_3_)
  {
    field_149284_a = xPosition;
    field_149282_b = zPosition;
    field_149279_g = p_i45196_2_;
    field_179758_c = func_179756_a(p_i45196_1_, p_i45196_2_, !getWorldprovider.getHasNoSky(), p_i45196_3_);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149284_a = data.readInt();
    field_149282_b = data.readInt();
    field_149279_g = data.readBoolean();
    field_179758_c = new Extracted();
    field_179758_c.field_150280_b = data.readShort();
    field_179758_c.field_150282_a = data.readByteArray();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeInt(field_149284_a);
    data.writeInt(field_149282_b);
    data.writeBoolean(field_149279_g);
    data.writeShort((short)(field_179758_c.field_150280_b & 0xFFFF));
    data.writeByteArray(field_179758_c.field_150282_a);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleChunkData(this);
  }
  
  public byte[] func_149272_d()
  {
    return field_179758_c.field_150282_a;
  }
  
  protected static int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_)
  {
    int var3 = p_180737_0_ * 2 * 16 * 16 * 16;
    int var4 = p_180737_0_ * 16 * 16 * 16 / 2;
    int var5 = p_180737_1_ ? p_180737_0_ * 16 * 16 * 16 / 2 : 0;
    int var6 = p_180737_2_ ? 256 : 0;
    return var3 + var4 + var5 + var6;
  }
  
  public static Extracted func_179756_a(Chunk p_179756_0_, boolean p_179756_1_, boolean p_179756_2_, int p_179756_3_)
  {
    ExtendedBlockStorage[] var4 = p_179756_0_.getBlockStorageArray();
    Extracted var5 = new Extracted();
    ArrayList var6 = com.google.common.collect.Lists.newArrayList();
    

    for (int var7 = 0; var7 < var4.length; var7++)
    {
      ExtendedBlockStorage var8 = var4[var7];
      
      if ((var8 != null) && ((!p_179756_1_) || (!var8.isEmpty())) && ((p_179756_3_ & 1 << var7) != 0))
      {
        field_150280_b |= 1 << var7;
        var6.add(var8);
      }
    }
    
    field_150282_a = new byte[func_180737_a(Integer.bitCount(field_150280_b), p_179756_2_, p_179756_1_)];
    var7 = 0;
    Iterator var15 = var6.iterator();
    int var12;
    int var13;
    for (; var15.hasNext(); 
        





        var13 < var12)
    {
      ExtendedBlockStorage var9 = (ExtendedBlockStorage)var15.next();
      char[] var10 = var9.getData();
      char[] var11 = var10;
      var12 = var10.length;
      
      var13 = 0; continue;
      
      char var14 = var11[var13];
      field_150282_a[(var7++)] = ((byte)(var14 & 0xFF));
      field_150282_a[(var7++)] = ((byte)(var14 >> '\b' & 0xFF));var13++;
    }
    


    ExtendedBlockStorage var9;
    

    for (var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.getBlocklightArray().getData(), field_150282_a, var7))
    {
      var9 = (ExtendedBlockStorage)var15.next();
    }
    
    if (p_179756_2_) {
      ExtendedBlockStorage var9;
      for (var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.getSkylightArray().getData(), field_150282_a, var7))
      {
        var9 = (ExtendedBlockStorage)var15.next();
      }
    }
    
    if (p_179756_1_)
    {
      func_179757_a(p_179756_0_.getBiomeArray(), field_150282_a, var7);
    }
    
    return var5;
  }
  
  private static int func_179757_a(byte[] p_179757_0_, byte[] p_179757_1_, int p_179757_2_)
  {
    System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
    return p_179757_2_ + p_179757_0_.length;
  }
  
  public int func_149273_e()
  {
    return field_149284_a;
  }
  
  public int func_149271_f()
  {
    return field_149282_b;
  }
  
  public int func_149276_g()
  {
    return field_179758_c.field_150280_b;
  }
  
  public boolean func_149274_i()
  {
    return field_149279_g;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
  
  public static class Extracted
  {
    public byte[] field_150282_a;
    public int field_150280_b;
    private static final String __OBFID = "CL_00001305";
    
    public Extracted() {}
  }
}
