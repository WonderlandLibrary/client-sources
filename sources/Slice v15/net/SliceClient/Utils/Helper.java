package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;



public class Helper
{
  private static CombatUtils combatUtils = new CombatUtils();
  private static BlockUtils blockUtils = new BlockUtils();
  private static PlayerUtils playerUtils = new PlayerUtils();
  private static RenderUtil.R3DUtils r3DUtils = new RenderUtil.R3DUtils();
  private static Minecraft mc;
  private static MathUtils mathUtils = new MathUtils();
  
  public Helper() {}
  
  public static Minecraft mc() { return Minecraft.getMinecraft(); }
  

  public static EntityPlayerSP player()
  {
    mc();return Minecraft.thePlayer;
  }
  
  public static WorldClient world()
  {
    mc();return Minecraft.theWorld;
  }
  


  public static CombatUtils combatUtils()
  {
    return combatUtils;
  }
  
  public static BlockUtils blockUtils()
  {
    return blockUtils;
  }
  
  public static PlayerUtils playerUtils()
  {
    return playerUtils;
  }
  


  public static RenderUtil.R3DUtils get3DUtils()
  {
    return r3DUtils;
  }
  




  public static MathUtils mathUtils()
  {
    return mathUtils;
  }
  
  public static void sendPacket(Packet p)
  {
    mc();Minecraft.getNetHandler().addToSendQueue(p);
  }
}
