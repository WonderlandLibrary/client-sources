package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class S10PacketSpawnPainting implements net.minecraft.network.Packet
{
  private int field_148973_a;
  private BlockPos field_179838_b;
  private EnumFacing field_179839_c;
  private String field_148968_f;
  private static final String __OBFID = "CL_00001280";
  
  public S10PacketSpawnPainting() {}
  
  public S10PacketSpawnPainting(EntityPainting p_i45170_1_)
  {
    field_148973_a = p_i45170_1_.getEntityId();
    field_179838_b = p_i45170_1_.func_174857_n();
    field_179839_c = field_174860_b;
    field_148968_f = art.title;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_148973_a = data.readVarIntFromBuffer();
    field_148968_f = data.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
    field_179838_b = data.readBlockPos();
    field_179839_c = EnumFacing.getHorizontal(data.readUnsignedByte());
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_148973_a);
    data.writeString(field_148968_f);
    data.writeBlockPos(field_179838_b);
    data.writeByte(field_179839_c.getHorizontalIndex());
  }
  
  public void func_180722_a(INetHandlerPlayClient p_180722_1_)
  {
    p_180722_1_.handleSpawnPainting(this);
  }
  
  public int func_148965_c()
  {
    return field_148973_a;
  }
  
  public BlockPos func_179837_b()
  {
    return field_179838_b;
  }
  
  public EnumFacing func_179836_c()
  {
    return field_179839_c;
  }
  
  public String func_148961_h()
  {
    return field_148968_f;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180722_a((INetHandlerPlayClient)handler);
  }
}
