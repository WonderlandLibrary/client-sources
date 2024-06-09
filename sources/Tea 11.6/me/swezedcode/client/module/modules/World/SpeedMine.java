package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SpeedMine extends Module {

	public SpeedMine() {
		super("SpeedMine", Keyboard.KEY_NONE, 0xFFF51862, ModCategory.World);
	}
	
	@EventListener
	public void onMine(EventPreMotionUpdates event) {
		mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, Integer.MAX_VALUE, 0));
		mc.playerController.blockHitDelay = Integer.MIN_VALUE;
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.removePotionEffect(Potion.digSpeed.id);
	}
	
}
