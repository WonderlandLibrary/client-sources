package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S03PacketTimeUpdate implements Packet
{
  private long field_149369_a;
  private long field_149368_b;
  private static final String __OBFID = "CL_00001337";
  
  public S03PacketTimeUpdate() {}
  
  public S03PacketTimeUpdate(long p_i45230_1_, long p_i45230_3_, boolean p_i45230_5_)
  {
    field_149369_a = p_i45230_1_;
    field_149368_b = p_i45230_3_;
    
    if (!p_i45230_5_)
    {
      field_149368_b = (-field_149368_b);
      
      if (field_149368_b == 0L)
      {
        field_149368_b = -1L;
      }
    }
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149369_a = data.readLong();
    field_149368_b = data.readLong();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeLong(field_149369_a);
    data.writeLong(field_149368_b);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleTimeUpdate(this);
  }
  
  public long func_149366_c()
  {
    return field_149369_a;
  }
  
  public long func_149365_d()
  {
    return field_149368_b;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
