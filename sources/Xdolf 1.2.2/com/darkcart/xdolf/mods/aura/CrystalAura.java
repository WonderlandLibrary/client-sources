package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class CrystalAura extends Module {

	public CrystalAura() {
		super("CrystalAura", "Automatically hits nearby end crystals.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.COMBAT);
	}
	
	public static Value crystalSpeed = new Value("Crystal Speed");
	public static Value crystalRange = new Value("Crystal Range");
	
	private long currentMS = 0L;
	private long lastMS = -1L;

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			currentMS = System.nanoTime() / 1000000;
			if(hasDelayRun((long)(1000 / crystalSpeed.getValue())))
			{
				for (Entity e : Wrapper.getWorld().loadedEntityList) {
					if (player.getDistanceToEntity(e) < crystalRange.getValue()) {
						if (e instanceof EntityEnderCrystal) {
							Wrapper.getMinecraft().playerController.attackEntity(player, e);
							player.swingArm(EnumHand.MAIN_HAND);
							lastMS = System.nanoTime() / 1000000;
							break;
						}
					}
				}
			}
		}
	}
	
	public boolean hasDelayRun(long time) {
		return (currentMS - lastMS) >= time;
	}
		
}
