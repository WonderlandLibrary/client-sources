package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState implements Packet
{
  public static final String[] MESSAGE_NAMES = { "tile.bed.notValid" };
  private int state;
  private float field_149141_c;
  private static final String __OBFID = "CL_00001301";
  
  public S2BPacketChangeGameState() {}
  
  public S2BPacketChangeGameState(int stateIn, float p_i45194_2_)
  {
    state = stateIn;
    field_149141_c = p_i45194_2_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    state = data.readUnsignedByte();
    field_149141_c = data.readFloat();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(state);
    data.writeFloat(field_149141_c);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleChangeGameState(this);
  }
  
  public int func_149138_c()
  {
    return state;
  }
  
  public float func_149137_d()
  {
    return field_149141_c;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
