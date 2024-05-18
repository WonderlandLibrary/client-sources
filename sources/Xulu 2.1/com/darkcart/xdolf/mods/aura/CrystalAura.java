package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;

public class CrystalAura extends Module {

	public CrystalAura() {
		super("crystalAura", "NoCheat+", "Automatically hits nearby end crystals.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.COMBAT);
	}
	
	public static Value crystalSpeed = new Value("Crystal Speed");
	public static Value crystalRange = new Value("Crystal Range");
	public static Value crystalHit = new Value("Crystal AutoPlace Range");
	
	private long currentMS = 0L;
	private long lastMS = -1L;

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			newAura(player);
		}
	}
	
	public void newAura(EntityPlayerSP player) {
		currentMS = System.nanoTime() / 1000000;
		if(hasDelayRun((long)(1000 / crystalSpeed.getValue())))
		{
			for (Entity e : Wrapper.getWorld().loadedEntityList) {
				if (player.getDistanceToEntity(e) < crystalRange.getValue()) {
					if (e instanceof EntityEnderCrystal) {
						if (Wrapper.getPlayer().getDistanceToEntity(e) < crystalHit.getValue()) {
							Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = true;
							Wrapper.getMinecraft().playerController.attackEntity(player, e);
							player.swingArm(EnumHand.MAIN_HAND);
						}
						lastMS = System.nanoTime() / 1000000;
						break;
					}
				}
			}
		}
	}

	@Override
	public void onDisable(){
		Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
	}
	
	public boolean hasDelayRun(long time) {
		return (currentMS - lastMS) >= time;
	}
		
}
