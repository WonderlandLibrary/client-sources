package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0FPacketConfirmTransaction implements Packet
{
  private int id;
  private short uid;
  private boolean accepted;
  private static final String __OBFID = "CL_00001351";
  
  public C0FPacketConfirmTransaction() {}
  
  public C0FPacketConfirmTransaction(int p_i45244_1_, short p_i45244_2_, boolean p_i45244_3_)
  {
    id = p_i45244_1_;
    uid = p_i45244_2_;
    accepted = p_i45244_3_;
  }
  



  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processConfirmTransaction(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    id = data.readByte();
    uid = data.readShort();
    accepted = (data.readByte() != 0);
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(id);
    data.writeShort(uid);
    data.writeByte(accepted ? 1 : 0);
  }
  
  public int getId()
  {
    return id;
  }
  
  public short getUid()
  {
    return uid;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayServer)handler);
  }
}
