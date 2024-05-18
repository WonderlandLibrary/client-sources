package mods.togglesprint.me.jannik.module.modules.combat;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.TimeHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;

public class AutoClicker
  extends Module
{
  public static boolean isClicking = false;
  TimeHelper time = new TimeHelper();
  
  public AutoClicker()
  {
    super("AutoClicker", Category.COMBAT);
  }
  
  @EventTarget
  private void onUpdate(EventUpdate event)
  {
    isClicking = false;
    if ((mc.gameSettings.keyBindAttack.pressed) && 
      (this.time.hasReached(Values.autoclicker_delay.getFloatValue())))
    {
      isClicking = false;
      mc.clickMouse();
      isClicking = true;
      mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
      this.time.reset();
    }
  }
}
