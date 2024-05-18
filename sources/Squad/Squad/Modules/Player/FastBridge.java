package Squad.Modules.Player;

import com.darkmagician6.eventapi.EventTarget;

import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class FastBridge
extends Module
{
public FastBridge()
{
  super("Eagle", 0, 273, Category.Other);
}

@EventTarget
public void onUpdate()
{
  if (getState()) {
    try
    {
      ItemStack item = mc.thePlayer.getCurrentEquippedItem();
      if ((item.getItem() instanceof ItemBlock))
      {
        mc.gameSettings.keyBindSneak.pressed = false;
        BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(bp).getBlock() == Blocks.air) {
          mc.gameSettings.keyBindSneak.pressed = true;
        }
      }
    }
    catch (Exception localException) {}
  }
}

public void onDisable()
{
  mc.gameSettings.keyBindSneak.pressed = false;
}
}
