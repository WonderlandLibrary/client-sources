package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport implements Packet
{
  private int field_149458_a;
  private int field_149456_b;
  private int field_149457_c;
  private int field_149454_d;
  private byte field_149455_e;
  private byte field_149453_f;
  private boolean field_179698_g;
  private static final String __OBFID = "CL_00001340";
  
  public S18PacketEntityTeleport() {}
  
  public S18PacketEntityTeleport(Entity p_i45233_1_)
  {
    field_149458_a = p_i45233_1_.getEntityId();
    field_149456_b = MathHelper.floor_double(posX * 32.0D);
    field_149457_c = MathHelper.floor_double(posY * 32.0D);
    field_149454_d = MathHelper.floor_double(posZ * 32.0D);
    field_149455_e = ((byte)(int)(rotationYaw * 256.0F / 360.0F));
    field_149453_f = ((byte)(int)(rotationPitch * 256.0F / 360.0F));
    field_179698_g = onGround;
  }
  
  public S18PacketEntityTeleport(int p_i45949_1_, int p_i45949_2_, int p_i45949_3_, int p_i45949_4_, byte p_i45949_5_, byte p_i45949_6_, boolean p_i45949_7_)
  {
    field_149458_a = p_i45949_1_;
    field_149456_b = p_i45949_2_;
    field_149457_c = p_i45949_3_;
    field_149454_d = p_i45949_4_;
    field_149455_e = p_i45949_5_;
    field_149453_f = p_i45949_6_;
    field_179698_g = p_i45949_7_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149458_a = data.readVarIntFromBuffer();
    field_149456_b = data.readInt();
    field_149457_c = data.readInt();
    field_149454_d = data.readInt();
    field_149455_e = data.readByte();
    field_149453_f = data.readByte();
    field_179698_g = data.readBoolean();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_149458_a);
    data.writeInt(field_149456_b);
    data.writeInt(field_149457_c);
    data.writeInt(field_149454_d);
    data.writeByte(field_149455_e);
    data.writeByte(field_149453_f);
    data.writeBoolean(field_179698_g);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleEntityTeleport(this);
  }
  
  public int func_149451_c()
  {
    return field_149458_a;
  }
  
  public int func_149449_d()
  {
    return field_149456_b;
  }
  
  public int func_149448_e()
  {
    return field_149457_c;
  }
  
  public int func_149446_f()
  {
    return field_149454_d;
  }
  
  public byte func_149450_g()
  {
    return field_149455_e;
  }
  
  public byte func_149447_h()
  {
    return field_149453_f;
  }
  
  public boolean func_179697_g()
  {
    return field_179698_g;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
