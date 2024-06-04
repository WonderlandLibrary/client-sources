package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake implements Packet
{
  private int protocolVersion;
  private String ip;
  private int port;
  private EnumConnectionState requestedState;
  private static final String __OBFID = "CL_00001372";
  
  public C00Handshake() {}
  
  public C00Handshake(int p_i45266_1_, String p_i45266_2_, int p_i45266_3_, EnumConnectionState p_i45266_4_)
  {
    protocolVersion = p_i45266_1_;
    ip = p_i45266_2_;
    port = p_i45266_3_;
    requestedState = p_i45266_4_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    protocolVersion = data.readVarIntFromBuffer();
    ip = data.readStringFromBuffer(255);
    port = data.readUnsignedShort();
    requestedState = EnumConnectionState.getById(data.readVarIntFromBuffer());
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(protocolVersion);
    data.writeString(ip);
    data.writeShort(port);
    data.writeVarIntToBuffer(requestedState.getId());
  }
  
  public void func_180770_a(INetHandlerHandshakeServer p_180770_1_)
  {
    p_180770_1_.processHandshake(this);
  }
  
  public EnumConnectionState getRequestedState()
  {
    return requestedState;
  }
  
  public int getProtocolVersion()
  {
    return protocolVersion;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180770_a((INetHandlerHandshakeServer)handler);
  }
}
