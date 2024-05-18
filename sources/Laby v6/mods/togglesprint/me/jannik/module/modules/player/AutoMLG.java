package mods.togglesprint.me.jannik.module.modules.player;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.TimeHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AutoMLG
  extends Module
{
  public static boolean isRightClicking = false;
  TimeHelper time = new TimeHelper();
  
  public AutoMLG()
  {
    super("AutoMLG", Category.PLAYER);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    boolean cobweb = Item.getIdFromItem(mc.thePlayer.getHeldItem().getItem()) == 30;
    boolean water = Item.getIdFromItem(mc.thePlayer.getHeldItem().getItem()) == 326;
    isRightClicking = false;
    if ((mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (mc.thePlayer.fallDistance >= 3.0F) && 
      (this.time.hasReached(mc.thePlayer.fallDistance * 5000.0F))) {
      if ((Values.automlg_cobweb.getBooleanValue()) && (Values.automlg_water.getBooleanValue()) && ((cobweb) || (water)))
      {
        mc.rightClickMouse();
        isRightClicking = true;
      }
      else if ((Values.automlg_cobweb.getBooleanValue()) && (cobweb))
      {
        mc.rightClickMouse();
        isRightClicking = true;
      }
      else if ((Values.automlg_water.getBooleanValue()) && (water))
      {
        mc.rightClickMouse();
        isRightClicking = true;
      }
    }
  }
}
