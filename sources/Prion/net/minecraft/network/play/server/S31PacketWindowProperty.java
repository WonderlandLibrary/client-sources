package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S31PacketWindowProperty implements Packet
{
  private int field_149186_a;
  private int field_149184_b;
  private int field_149185_c;
  private static final String __OBFID = "CL_00001295";
  
  public S31PacketWindowProperty() {}
  
  public S31PacketWindowProperty(int p_i45187_1_, int p_i45187_2_, int p_i45187_3_)
  {
    field_149186_a = p_i45187_1_;
    field_149184_b = p_i45187_2_;
    field_149185_c = p_i45187_3_;
  }
  
  public void func_180733_a(INetHandlerPlayClient p_180733_1_)
  {
    p_180733_1_.handleWindowProperty(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149186_a = data.readUnsignedByte();
    field_149184_b = data.readShort();
    field_149185_c = data.readShort();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(field_149186_a);
    data.writeShort(field_149184_b);
    data.writeShort(field_149185_c);
  }
  
  public int func_149182_c()
  {
    return field_149186_a;
  }
  
  public int func_149181_d()
  {
    return field_149184_b;
  }
  
  public int func_149180_e()
  {
    return field_149185_c;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180733_a((INetHandlerPlayClient)handler);
  }
}
