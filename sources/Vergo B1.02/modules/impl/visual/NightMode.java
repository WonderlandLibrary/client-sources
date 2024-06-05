package xyz.vergoclient.modules.impl.visual;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventGetSkyAndFogColor;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.potion.Potion;
import xyz.vergoclient.settings.ModeSetting;

import java.util.Arrays;

public class NightMode extends Module implements OnEventInterface {

	public NightMode() {
		super("Ambience", Category.VISUAL);
	}

	public ModeSetting dayTime = new ModeSetting("Time", "Sunrise", "Night", "Sunrise", "Sunset");

	@Override
	public void loadSettings() {

		dayTime.modes.clear();
		dayTime.modes.addAll(Arrays.asList("Sunrise", "Sunset", "Night"));

		addSettings(dayTime);

	}

	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventTick && e.isPre()) {
			if(dayTime.is("Night")) {
				mc.theWorld.setWorldTime(18000);
				mc.theWorld.setRainStrength(0);
				mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
			} else if(dayTime.is("Sunrise")) {
				mc.theWorld.setWorldTime(22200);
				mc.theWorld.setRainStrength(0);
				mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
			}else if(dayTime.is("Sunset")) {
				mc.theWorld.setWorldTime(13700);
				mc.theWorld.setRainStrength(0);
				mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
			}
		}
		else if (e instanceof EventReceivePacket && e.isPre()) {
			if (((EventReceivePacket)e).packet instanceof S03PacketTimeUpdate) {
				e.setCanceled(true);
			}
		}
		else if (e instanceof EventGetSkyAndFogColor && e.isPre()) {

		}
		
	}

}
