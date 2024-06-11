package Hydro.module.modules.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module {

	public Phase() {
		super("Phase", Keyboard.KEY_V, true, Category.PLAYER, "Allows you to phase through blocks");
		
		ArrayList<String> options = new ArrayList<>();
		options.add("Redesky");
		Client.settingsManager.rSetting(new Setting("PhaseMode", "Mode", this, "Redesky", options));
	}
	
	public void onEnable() {
        if (mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + -0.00000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
        }
    }
	public void onEvent(Event e) {
        float mult = 5f;
        if(e instanceof EventUpdate) {
        	this.toggle();	
        }
	}

}
