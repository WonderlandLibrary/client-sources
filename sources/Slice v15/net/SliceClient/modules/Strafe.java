package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;

public class Strafe
  extends Module
{
  public Strafe()
  {
    super("Strafe", Category.MOVEMENT, 16376546);
  }
  


  public void onDisable() {}
  


  public void onEnable() {}
  


  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    float forward = 0.0F;
    float strafe = 0.0F;
    double speed = 0.2D;
    
    float var5 = MathHelper.sin(thePlayerrotationYaw * 3.1415927F / 180.0F);
    float var6 = MathHelper.cos(thePlayerrotationYaw * 3.1415927F / 180.0F);
    if (gameSettingskeyBindForward.pressed) {
      forward += 1.0F;
    }
    if (gameSettingskeyBindBack.pressed) {
      forward -= 1.0F;
    }
    if (gameSettingskeyBindLeft.pressed) {
      strafe += 1.0F;
    }
    if (gameSettingskeyBindRight.pressed) {
      strafe -= 1.0F;
    }
    double motionX = (strafe * var6 - forward * var5) * speed;
    double motionZ = (forward * var6 + strafe * var5) * speed;
    thePlayermotionX = motionX;
    thePlayermotionZ = motionZ;
  }
}
