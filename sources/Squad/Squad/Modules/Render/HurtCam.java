package Squad.Modules.Render;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;

public class HurtCam
extends Module
{
public HurtCam()
{
  super("HurtCam", 0, 136, Category.Render);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  if (mc.thePlayer.hurtTime > 0) {
	  mc.thePlayer.maxHurtTime = 0;
  }
}
}
