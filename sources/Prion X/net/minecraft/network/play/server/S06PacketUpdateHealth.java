package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet
{
  private float health;
  private int foodLevel;
  private float saturationLevel;
  private static final String __OBFID = "CL_00001332";
  
  public S06PacketUpdateHealth() {}
  
  public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn)
  {
    health = healthIn;
    foodLevel = foodLevelIn;
    saturationLevel = saturationIn;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    health = data.readFloat();
    foodLevel = data.readVarIntFromBuffer();
    saturationLevel = data.readFloat();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeFloat(health);
    data.writeVarIntToBuffer(foodLevel);
    data.writeFloat(saturationLevel);
  }
  
  public void func_180750_a(INetHandlerPlayClient p_180750_1_)
  {
    p_180750_1_.handleUpdateHealth(this);
  }
  
  public float getHealth()
  {
    return health;
  }
  
  public int getFoodLevel()
  {
    return foodLevel;
  }
  
  public float getSaturationLevel()
  {
    return saturationLevel;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180750_a((INetHandlerPlayClient)handler);
  }
}
