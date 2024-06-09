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

public class Sprint extends Mod {

	private BoolOption omniSprint = new BoolOption("Omni-Sprint");

	public Sprint() {
		super("Sprint", Category.HACKS);
		this.setBind(Keyboard.KEY_P);
	}

	public void onEnable() {
		Memeager.register(this);
	}

	public void onDisable() {
		Memeager.unregister(this);
	}

	@Memetarget
	public void onUpdate(EventUpdate event) {
		if (this.omniSprint.isEnabled()) {
			if ((this.mc.thePlayer.movementInput.moveForward != 0 || this.mc.thePlayer.movementInput.moveStrafe != 0)
					&& this.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
				this.mc.thePlayer.setSprinting(true);
			}
		} else if (this.mc.thePlayer.movementInput.moveForward > 0
				&& this.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
			this.mc.thePlayer.setSprinting(true);
		}
	}

}
