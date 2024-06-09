package net.minecraft.client.main.neptune.Mod.Collection.Movement;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventUpdate;
import net.minecraft.client.main.neptune.Mod.BoolOption;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.potion.PotionEffect;

import org.lwjgl.input.Keyboard;

public class Glide extends Mod {


	public Glide() {
		super("Glide", Category.HACKS);
		this.setBind(Keyboard.KEY_F);
	}

	public void onEnable() {
		Memeager.register(this);
	}

	public void onDisable() {
		Memeager.unregister(this);
	}

	@Memetarget
	public void onUpdate(EventUpdate event) {
		if (mc.thePlayer.isAirBorne) {
			mc.thePlayer.motionY = -0.007;
		}
		if(mc.gameSettings.keyBindJump.pressed) {
			mc.thePlayer.motionY = 0.3;
		}
		if(mc.gameSettings.keyBindSneak.pressed) {
			mc.thePlayer.motionY = -0.3;
		}
	}

}
