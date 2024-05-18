package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0FPacketSpawnMob implements Packet
{
  private int field_149042_a;
  private int field_149040_b;
  private int field_149041_c;
  private int field_149038_d;
  private int field_149039_e;
  private int field_149036_f;
  private int field_149037_g;
  private int field_149047_h;
  private byte field_149048_i;
  private byte field_149045_j;
  private byte field_149046_k;
  private DataWatcher field_149043_l;
  private List field_149044_m;
  private static final String __OBFID = "CL_00001279";
  
  public S0FPacketSpawnMob() {}
  
  public S0FPacketSpawnMob(EntityLivingBase p_i45192_1_)
  {
    field_149042_a = p_i45192_1_.getEntityId();
    field_149040_b = ((byte)EntityList.getEntityID(p_i45192_1_));
    field_149041_c = MathHelper.floor_double(posX * 32.0D);
    field_149038_d = MathHelper.floor_double(posY * 32.0D);
    field_149039_e = MathHelper.floor_double(posZ * 32.0D);
    field_149048_i = ((byte)(int)(rotationYaw * 256.0F / 360.0F));
    field_149045_j = ((byte)(int)(rotationPitch * 256.0F / 360.0F));
    field_149046_k = ((byte)(int)(rotationYawHead * 256.0F / 360.0F));
    double var2 = 3.9D;
    double var4 = motionX;
    double var6 = motionY;
    double var8 = motionZ;
    
    if (var4 < -var2)
    {
      var4 = -var2;
    }
    
    if (var6 < -var2)
    {
      var6 = -var2;
    }
    
    if (var8 < -var2)
    {
      var8 = -var2;
    }
    
    if (var4 > var2)
    {
      var4 = var2;
    }
    
    if (var6 > var2)
    {
      var6 = var2;
    }
    
    if (var8 > var2)
    {
      var8 = var2;
    }
    
    field_149036_f = ((int)(var4 * 8000.0D));
    field_149037_g = ((int)(var6 * 8000.0D));
    field_149047_h = ((int)(var8 * 8000.0D));
    field_149043_l = p_i45192_1_.getDataWatcher();
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149042_a = data.readVarIntFromBuffer();
    field_149040_b = (data.readByte() & 0xFF);
    field_149041_c = data.readInt();
    field_149038_d = data.readInt();
    field_149039_e = data.readInt();
    field_149048_i = data.readByte();
    field_149045_j = data.readByte();
    field_149046_k = data.readByte();
    field_149036_f = data.readShort();
    field_149037_g = data.readShort();
    field_149047_h = data.readShort();
    field_149044_m = DataWatcher.readWatchedListFromPacketBuffer(data);
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_149042_a);
    data.writeByte(field_149040_b & 0xFF);
    data.writeInt(field_149041_c);
    data.writeInt(field_149038_d);
    data.writeInt(field_149039_e);
    data.writeByte(field_149048_i);
    data.writeByte(field_149045_j);
    data.writeByte(field_149046_k);
    data.writeShort(field_149036_f);
    data.writeShort(field_149037_g);
    data.writeShort(field_149047_h);
    field_149043_l.writeTo(data);
  }
  
  public void func_180721_a(INetHandlerPlayClient p_180721_1_)
  {
    p_180721_1_.handleSpawnMob(this);
  }
  
  public List func_149027_c()
  {
    if (field_149044_m == null)
    {
      field_149044_m = field_149043_l.getAllWatched();
    }
    
    return field_149044_m;
  }
  
  public int func_149024_d()
  {
    return field_149042_a;
  }
  
  public int func_149025_e()
  {
    return field_149040_b;
  }
  
  public int func_149023_f()
  {
    return field_149041_c;
  }
  
  public int func_149034_g()
  {
    return field_149038_d;
  }
  
  public int func_149029_h()
  {
    return field_149039_e;
  }
  
  public int func_149026_i()
  {
    return field_149036_f;
  }
  
  public int func_149033_j()
  {
    return field_149037_g;
  }
  
  public int func_149031_k()
  {
    return field_149047_h;
  }
  
  public byte func_149028_l()
  {
    return field_149048_i;
  }
  
  public byte func_149030_m()
  {
    return field_149045_j;
  }
  
  public byte func_149032_n()
  {
    return field_149046_k;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180721_a((INetHandlerPlayClient)handler);
  }
}
