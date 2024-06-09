package com.kilo.mod.all;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.network.play.client.C0BPacketEntityAction;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;
import com.kilo.util.Timer;

public class Retard extends Module {

	private float[] oldRotation = new float[] {0, 0};
	private float[] newRotation = new float[] {0, 0};
	private Timer timer = new Timer();
	
	public Retard(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		List<ModuleSubOption> spin = new ArrayList<ModuleSubOption>();
		spin.add(new ModuleSubOption("Speed", "Speed to spin", Interactable.TYPE.SLIDER, -35, new float[] {-50, 50}, false));
		
		addOption("Spin", "Spin around in circles", Interactable.TYPE.CHECKBOX, false, null, false, spin);
		addOption("Derp", "Make yourself look derpy", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Headless", "Shove your head into your body", Interactable.TYPE.CHECKBOX, false, null, false);
		
		List<ModuleSubOption> twerk = new ArrayList<ModuleSubOption>();
		twerk.add(new ModuleSubOption("Speed", "Speed to twerk at", Interactable.TYPE.SLIDER, 5, new float[] {1, 10}, true));
		
		addOption("Twerk", "Twerk like \u00a76Miley \u00a7cCyrus", Interactable.TYPE.CHECKBOX, false, null, false, twerk);
	}
	
	public void onDisable() {
		super.onDisable();
        mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}
	
	public void onPlayerPreUpdate() {
		oldRotation[0] = mc.thePlayer.rotationYaw;
		oldRotation[1] = mc.thePlayer.rotationPitch;
		
		boolean a = false, b = false;
		
		Random rand = new Random();
		if (Util.makeBoolean(getOptionValue("spin"))) {
			newRotation[0]+= Util.makeFloat(getSubOptionValue("spin", "speed"));
			a = true;
		}
		if (Util.makeBoolean(getOptionValue("derp"))) {
			newRotation[0] = rand.nextFloat()*360;
			newRotation[1] = (rand.nextFloat()*180)-90;
			a = true;
		}
		if (Util.makeBoolean(getOptionValue("headless"))) {
			newRotation[1] = -90;
			a = true;
		}
		if (Util.makeBoolean(getOptionValue("twerk"))) {
	        mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
		}
		
		if (a) {
			mc.thePlayer.rotationYaw = newRotation[0];
			mc.thePlayer.rotationPitch = newRotation[1];
		}
	}
	
	public void onPlayerPostUpdate() {
		if (Util.makeBoolean(getOptionValue("twerk"))) {
			float time = Util.makeFloat(getSubOptionValue("twerk", "speed"));
			if (timer.isTime(1-(time/10))) {
				mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
			}
		}
		
		mc.thePlayer.rotationYaw = oldRotation[0];
		mc.thePlayer.rotationPitch = oldRotation[1];
	}
}
