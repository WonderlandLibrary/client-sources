package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S04PacketEntityEquipment implements Packet
{
  private int field_149394_a;
  private int field_149392_b;
  private ItemStack field_149393_c;
  private static final String __OBFID = "CL_00001330";
  
  public S04PacketEntityEquipment() {}
  
  public S04PacketEntityEquipment(int p_i45221_1_, int p_i45221_2_, ItemStack p_i45221_3_)
  {
    field_149394_a = p_i45221_1_;
    field_149392_b = p_i45221_2_;
    field_149393_c = (p_i45221_3_ == null ? null : p_i45221_3_.copy());
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149394_a = data.readVarIntFromBuffer();
    field_149392_b = data.readShort();
    field_149393_c = data.readItemStackFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_149394_a);
    data.writeShort(field_149392_b);
    data.writeItemStackToBuffer(field_149393_c);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleEntityEquipment(this);
  }
  
  public ItemStack func_149390_c()
  {
    return field_149393_c;
  }
  
  public int func_149389_d()
  {
    return field_149394_a;
  }
  
  public int func_149388_e()
  {
    return field_149392_b;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
