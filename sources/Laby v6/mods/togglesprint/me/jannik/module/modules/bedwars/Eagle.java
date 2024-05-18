package mods.togglesprint.me.jannik.module.modules.bedwars;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.TimeHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Eagle
  extends Module
{
  public TimeHelper time = new TimeHelper();
  public static boolean isRightClicking = false;
  
  public Eagle()
  {
    super("Eagle", Category.BEDWARS);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    isRightClicking = false;
    if ((mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air) && ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)))
    {
      if (Values.eagle_click.getBooleanValue())
      {
        mc.gameSettings.keyBindSneak.pressed = true;
        mc.rightClickMouse();
        isRightClicking = true;
      }
      else
      {
        mc.gameSettings.keyBindSneak.pressed = true;
      }
    }
    else {
      mc.gameSettings.keyBindSneak.pressed = false;
    }
  }
}
