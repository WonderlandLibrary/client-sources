package lunadevs.luna.module.combat;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.TickEvent;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class AutoRespawn extends Module{

	public AutoRespawn() {
		super("AutoRespawn", 0, Category.COMBAT, false);
	}
	
	  @EventTarget
	  public void onTick(TickEvent event)
	  {
	    if (!Minecraft.thePlayer.isEntityAlive()) {
	      Minecraft.thePlayer.respawnPlayer();
	    }
	  }
	
	public String getValue() {
		return null;
	}
	
}
