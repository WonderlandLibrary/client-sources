package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0BPacketAnimation implements Packet
{
  private int entityId;
  private int type;
  private static final String __OBFID = "CL_00001282";
  
  public S0BPacketAnimation() {}
  
  public S0BPacketAnimation(Entity ent, int animationType)
  {
    entityId = ent.getEntityId();
    type = animationType;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    entityId = data.readVarIntFromBuffer();
    type = data.readUnsignedByte();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(entityId);
    data.writeByte(type);
  }
  
  public void func_180723_a(INetHandlerPlayClient p_180723_1_)
  {
    p_180723_1_.handleAnimation(this);
  }
  
  public int func_148978_c()
  {
    return entityId;
  }
  
  public int func_148977_d()
  {
    return type;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180723_a((INetHandlerPlayClient)handler);
  }
}
