package mods.togglesprint.me.jannik.module.modules.bedwars;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class FastPlace
  extends Module
{
  public FastPlace()
  {
    super("FastPlace", Category.BEDWARS);
  }
  
  @EventTarget
  private void onUpdate(EventUpdate event)
  {
    if ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
      mc.rightClickDelayTimer = 0;
    }
  }
}
