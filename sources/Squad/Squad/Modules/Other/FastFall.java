package Squad.Modules.Other;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;

public class FastFall
extends Module
{
public FastFall()
{
  super("FastFall", 0, 311, Category.Other);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  setDisplayname("FastFall §7BUGGY");
  if (mc.thePlayer.fallDistance > 2.0F) {
    mc.timer.timerSpeed = 8.934536F;
  } else if (mc.thePlayer.onGround) {
    mc.timer.timerSpeed = 1.0F;
  }
}

public void onDisable()
{
  if (mc.thePlayer.fallDistance > 2.0F) {
    mc.timer.timerSpeed = 1.0F;
  }
}
}
