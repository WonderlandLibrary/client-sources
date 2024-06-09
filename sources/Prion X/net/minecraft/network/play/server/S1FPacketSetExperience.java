package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1FPacketSetExperience implements Packet
{
  private float field_149401_a;
  private int field_149399_b;
  private int field_149400_c;
  private static final String __OBFID = "CL_00001331";
  
  public S1FPacketSetExperience() {}
  
  public S1FPacketSetExperience(float p_i45222_1_, int p_i45222_2_, int p_i45222_3_)
  {
    field_149401_a = p_i45222_1_;
    field_149399_b = p_i45222_2_;
    field_149400_c = p_i45222_3_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149401_a = data.readFloat();
    field_149400_c = data.readVarIntFromBuffer();
    field_149399_b = data.readVarIntFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeFloat(field_149401_a);
    data.writeVarIntToBuffer(field_149400_c);
    data.writeVarIntToBuffer(field_149399_b);
  }
  
  public void func_180749_a(INetHandlerPlayClient p_180749_1_)
  {
    p_180749_1_.handleSetExperience(this);
  }
  
  public float func_149397_c()
  {
    return field_149401_a;
  }
  
  public int func_149396_d()
  {
    return field_149399_b;
  }
  
  public int func_149395_e()
  {
    return field_149400_c;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180749_a((INetHandlerPlayClient)handler);
  }
}
