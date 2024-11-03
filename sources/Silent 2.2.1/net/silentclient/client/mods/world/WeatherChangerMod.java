package net.silentclient.client.mods.world;

import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EventPlaySound;
import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

import java.util.ArrayList;

public class WeatherChangerMod extends Mod {
	public WeatherChangerMod() {
		super("Weather", ModCategory.MODS, "silentclient/icons/mods/weatherchanger.png");
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Clear");
		options.add("Rain");
		options.add("Thunder");
		
		this.addModeSetting("Weather", this, "Clear", options);
		this.addBooleanSetting("Play Thunder Sound", this, true);
	}
	
	@EventTarget
	public void onPlaySound(EventPlaySound event) {
		if(event.getSoundName() == "ambient.weather.thunder") {
			if(!Client.getInstance().getSettingsManager().getSettingByName(this, "Play Thunder Sound").getValBoolean()) {
				event.setVolume(0);
			}
		}
	}
	
	@EventTarget
    public void onUpdate(ClientTickEvent event) {
    	if(mc.theWorld == null) {
    		return;
    	}
    	String mode = Client.getInstance().getSettingsManager().getSettingByName(this, "Weather").getValString();
    	
    	switch(mode) {
	    	default:
	            mc.theWorld.setRainStrength(0);
	            mc.theWorld.setThunderStrength(0);
	    		break;
	    	case "Rain":
	            mc.theWorld.setRainStrength(1);
	            mc.theWorld.setThunderStrength(0);
	    		break;
	    	case "Thunder":
	            mc.theWorld.setRainStrength(1);
	            mc.theWorld.setThunderStrength(1);
	    		break;
    	}
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if(event.getPacket() instanceof S2BPacketChangeGameState){
            S2BPacketChangeGameState S2BPacket=(S2BPacketChangeGameState) event.getPacket();
            if(S2BPacket.getGameState()==7||S2BPacket.getGameState()==8)
                event.setCancelled(true);
        }
    }
}
