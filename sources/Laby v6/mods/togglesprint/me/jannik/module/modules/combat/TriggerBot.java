package mods.togglesprint.me.jannik.module.modules.combat;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.EntityHelper;
import mods.togglesprint.me.jannik.utils.TimeHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class TriggerBot
  extends Module
{
  TimeHelper time = new TimeHelper();
  public static boolean isClicking = false;
  public static boolean isRightClicking = false;
  
  public TriggerBot()
  {
    super("TriggerBot", Category.COMBAT);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    if ((mc.currentScreen == null) && (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY))
    {
      isClicking = false;
      isRightClicking = false;
      if ((!EntityHelper.isBot(mc.thePlayer)) && (this.time.hasReached(Values.triggerbot_delay.getFloatValue()))) {
        if ((legitAutoBlock()) && (Values.triggerbot_legitautoblock.getBooleanValue()))
        {
          mc.clickMouse();
          mc.rightClickMouse();
          isClicking = true;
          isRightClicking = true;
          this.time.reset();
        }
        else
        {
          mc.clickMouse();
          isClicking = true;
          this.time.reset();
        }
      }
    }
  }
  
  private boolean legitAutoBlock()
  {
    if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
      return true;
    }
    return false;
  }
}
