package me.hexxed.mercury.modules;

import java.util.Random;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class Triggerbot extends Module
{
  public Triggerbot()
  {
    super("Triggerbot", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.COMBAT);
  }
  
  TimeHelper delay = new TimeHelper();
  Random r = new Random();
  
  public void onPostMotionUpdate()
  {
    boolean wassprint = mc.thePlayer.isSprinting();
    if ((mc.objectMouseOver.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.ENTITY) && 
      (delay.isDelayComplete(70 + r.nextInt(66)))) {
      delay.setLastMS();
      Entity entity = mc.objectMouseOver.entityHit;
      mc.thePlayer.setSprinting(false);
      if (((entity instanceof EntityPlayer)) && (entity.isEntityAlive()) && (!mc.thePlayer.isUsingItem()) && (!mc.thePlayer.isDead)) {
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, entity);
        mc.thePlayer.setSprinting(wassprint);
      }
    }
  }
}
