package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth
  implements Packet<INetHandler>
{
  private float health;
  private int foodLevel;
  private float saturationLevel;
  private static final String __OBFID = "CL_00001332";
  
  public S06PacketUpdateHealth() {}
  
  public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn)
  {
    this.health = healthIn;
    this.foodLevel = foodLevelIn;
    this.saturationLevel = saturationIn;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.health = data.readFloat();
    this.foodLevel = data.readVarIntFromBuffer();
    this.saturationLevel = data.readFloat();
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeFloat(this.health);
    data.writeVarIntToBuffer(this.foodLevel);
    data.writeFloat(this.saturationLevel);
  }
  
  public void func_180750_a(INetHandlerPlayClient p_180750_1_)
  {
    p_180750_1_.handleUpdateHealth(this);
  }
  
  public float getHealth()
  {
    return this.health;
  }
  
  public int getFoodLevel()
  {
    return this.foodLevel;
  }
  
  public float getSaturationLevel()
  {
    return this.saturationLevel;
  }
  
  public void processPacket(INetHandler handler)
  {
    func_180750_a((INetHandlerPlayClient)handler);
  }
}
