package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;

public class Giant extends Module
{
  public Giant()
  {
    super("Giant", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.RENDER);
  }
  
  public void onPostUpdate()
  {
    mc.thePlayer.boundingBox.offset(0.0D, 1.0D, 0.0D);
    mc.thePlayer.posY += 1.0D;
  }
}
