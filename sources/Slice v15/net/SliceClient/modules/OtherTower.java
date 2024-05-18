package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemBlock;

public class OtherTower extends Module
{
  public OtherTower()
  {
    super("OtherTower", net.SliceClient.module.Category.PLAYER, 16376546);
    
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  


  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    try
    {
      if ((Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock))
      {
        thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook(thePlayerrotationYaw, 90.0F, true));
        
        Minecraft.rightClickDelayTimer = 0;
        gameSettingskeyBindUseItem.pressed = true;
        thePlayermotionY = 0.30000001192092896D;
      }
    }
    catch (Exception localException) {}
  }
  
  public void onDisable() {
    gameSettingskeyBindUseItem.pressed = false;
  }
}
