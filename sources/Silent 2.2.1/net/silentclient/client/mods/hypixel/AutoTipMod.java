package net.silentclient.client.mods.hypixel;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.utils.TimerUtils;

public class AutoTipMod extends Mod {
	private TimerUtils timer = new TimerUtils();
	
	public AutoTipMod() {
		super("Auto Tip", ModCategory.MODS, "silentclient/icons/mods/autotip.png");
	}
	
	@Override
	public void onDisable() {
		try {
			this.timer.reset();
		} catch(Exception err) {
			
		}
	}
	
	@Override
	public void onEnable() {
		try {
			this.timer.reset();
		} catch(Exception err) {
			
		}
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event) {
		if(Server.isHypixel()) {
			if(timer.delay(600000)) {
				mc.thePlayer.sendChatMessage("/tip all");
				timer.reset();
			}
		}else {
			timer.reset();
		}
	}
}
