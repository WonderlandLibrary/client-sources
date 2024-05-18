package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S2CPacketSpawnGlobalEntity implements Packet
{
  private int field_149059_a;
  private int field_149057_b;
  private int field_149058_c;
  private int field_149055_d;
  private int field_149056_e;
  private static final String __OBFID = "CL_00001278";
  
  public S2CPacketSpawnGlobalEntity() {}
  
  public S2CPacketSpawnGlobalEntity(Entity p_i45191_1_)
  {
    field_149059_a = p_i45191_1_.getEntityId();
    field_149057_b = MathHelper.floor_double(posX * 32.0D);
    field_149058_c = MathHelper.floor_double(posY * 32.0D);
    field_149055_d = MathHelper.floor_double(posZ * 32.0D);
    
    if ((p_i45191_1_ instanceof EntityLightningBolt))
    {
      field_149056_e = 1;
    }
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149059_a = data.readVarIntFromBuffer();
    field_149056_e = data.readByte();
    field_149057_b = data.readInt();
    field_149058_c = data.readInt();
    field_149055_d = data.readInt();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_149059_a);
    data.writeByte(field_149056_e);
    data.writeInt(field_149057_b);
    data.writeInt(field_149058_c);
    data.writeInt(field_149055_d);
  }
  
  public void func_180720_a(INetHandlerPlayClient p_180720_1_)
  {
    p_180720_1_.handleSpawnGlobalEntity(this);
  }
  
  public int func_149052_c()
  {
    return field_149059_a;
  }
  
  public int func_149051_d()
  {
    return field_149057_b;
  }
  
  public int func_149050_e()
  {
    return field_149058_c;
  }
  
  public int func_149049_f()
  {
    return field_149055_d;
  }
  
  public int func_149053_g()
  {
    return field_149056_e;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180720_a((INetHandlerPlayClient)handler);
  }
}
