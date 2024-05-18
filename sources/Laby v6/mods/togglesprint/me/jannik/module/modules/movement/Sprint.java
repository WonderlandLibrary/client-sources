package mods.togglesprint.me.jannik.module.modules.movement;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class Sprint
  extends Module
{
  public Sprint()
  {
    super("Sprint", Category.MOVEMENT);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    mc.gameSettings.keyBindSprint.pressed = true;
  }
}
