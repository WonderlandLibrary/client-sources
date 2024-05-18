package mods.togglesprint.me.jannik.module.modules.bedwars;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SafeWalk
  extends Module
{
  public static boolean safewalk = false;
  
  public SafeWalk()
  {
    super("SafeWalk", Category.BEDWARS);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    if ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
      safewalk = true;
    } else {
      safewalk = false;
    }
  }
}
