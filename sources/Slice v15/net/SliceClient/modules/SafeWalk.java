package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;

public class SafeWalk extends Module
{
  public SafeWalk()
  {
    super("SafeWalk", net.SliceClient.module.Category.MISC, 16376546);
  }
  
  public void onEnable() {
    gameSettingskeyBindSneak.pressed = true;
    
    super.onEnable();
  }
  
  public void onDisable()
  {
    gameSettingskeyBindSneak.pressed = false;
    super.onDisable();
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    net.minecraft.util.BlockPos pos = new net.minecraft.util.BlockPos(thePlayerposX, thePlayerposY - 0.3D, thePlayerposZ);
    net.minecraft.block.state.IBlockState state = thePlayerworldObj.getBlockState(pos);
    if (!thePlayeronGround) {
      return;
    }
    if ((state.getBlock() instanceof net.minecraft.block.BlockAir)) {
      gameSettingskeyBindSneak.pressed = true;
    } else {
      gameSettingskeyBindSneak.pressed = false;
    }
  }
}
