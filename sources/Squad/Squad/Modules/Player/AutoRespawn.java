package Squad.Modules.Player;

import Squad.Events.EventUpdate;
import Squad.Utils.Wrapper;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;

public class AutoRespawn
extends Module
{
public AutoRespawn()
{
  super("AutoRespawn", 0, 136, Category.Other);
}

public void onUpdate(EventUpdate e)
{
	if(this.isEnabled()) {
		Minecraft.getMinecraft();
		if(mc.thePlayer.getHealth() <= 0.0F);
		Minecraft.getMinecraft();
		mc.thePlayer.respawnPlayer();
	}
}

public void onDisable()
{
  Wrapper.getMC().thePlayer.forceSpawn = false;
}
}
