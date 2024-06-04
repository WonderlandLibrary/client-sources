package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;

public class S01PacketJoinGame implements Packet
{
  private int field_149206_a;
  private boolean field_149204_b;
  private WorldSettings.GameType field_149205_c;
  private int field_149202_d;
  private EnumDifficulty field_149203_e;
  private int field_149200_f;
  private WorldType field_149201_g;
  private boolean field_179745_h;
  private static final String __OBFID = "CL_00001310";
  
  public S01PacketJoinGame() {}
  
  public S01PacketJoinGame(int p_i45976_1_, WorldSettings.GameType p_i45976_2_, boolean p_i45976_3_, int p_i45976_4_, EnumDifficulty p_i45976_5_, int p_i45976_6_, WorldType p_i45976_7_, boolean p_i45976_8_)
  {
    field_149206_a = p_i45976_1_;
    field_149202_d = p_i45976_4_;
    field_149203_e = p_i45976_5_;
    field_149205_c = p_i45976_2_;
    field_149200_f = p_i45976_6_;
    field_149204_b = p_i45976_3_;
    field_149201_g = p_i45976_7_;
    field_179745_h = p_i45976_8_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149206_a = data.readInt();
    short var2 = data.readUnsignedByte();
    field_149204_b = ((var2 & 0x8) == 8);
    int var3 = var2 & 0xFFFFFFF7;
    field_149205_c = WorldSettings.GameType.getByID(var3);
    field_149202_d = data.readByte();
    field_149203_e = EnumDifficulty.getDifficultyEnum(data.readUnsignedByte());
    field_149200_f = data.readUnsignedByte();
    field_149201_g = WorldType.parseWorldType(data.readStringFromBuffer(16));
    
    if (field_149201_g == null)
    {
      field_149201_g = WorldType.DEFAULT;
    }
    
    field_179745_h = data.readBoolean();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeInt(field_149206_a);
    int var2 = field_149205_c.getID();
    
    if (field_149204_b)
    {
      var2 |= 0x8;
    }
    
    data.writeByte(var2);
    data.writeByte(field_149202_d);
    data.writeByte(field_149203_e.getDifficultyId());
    data.writeByte(field_149200_f);
    data.writeString(field_149201_g.getWorldTypeName());
    data.writeBoolean(field_179745_h);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleJoinGame(this);
  }
  
  public int func_149197_c()
  {
    return field_149206_a;
  }
  
  public boolean func_149195_d()
  {
    return field_149204_b;
  }
  
  public WorldSettings.GameType func_149198_e()
  {
    return field_149205_c;
  }
  
  public int func_149194_f()
  {
    return field_149202_d;
  }
  
  public EnumDifficulty func_149192_g()
  {
    return field_149203_e;
  }
  
  public int func_149193_h()
  {
    return field_149200_f;
  }
  
  public WorldType func_149196_i()
  {
    return field_149201_g;
  }
  
  public boolean func_179744_h()
  {
    return field_179745_h;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
