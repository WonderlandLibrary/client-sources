package com.kilo.mod.all;

import java.awt.Color;

import net.minecraft.item.ItemAxe;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class WorldEditESP extends Module {

	private BlockPos pos1;
	private BlockPos pos2;
	
	public WorldEditESP(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Red", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Green", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Blue", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Opacity", "", Interactable.TYPE.SLIDER, 96, new float[] {0, 255}, false);
	}
	
	public void onPlayerAttack() {
		if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem().getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 271) {
			if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit != MovingObjectType.MISS && mc.objectMouseOver.getBlockPos() != null) {
				pos1 = mc.objectMouseOver.getBlockPos();
			}
		}
	}
	
	public void onPlayerUse() {
		if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem().getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 271) {
			if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit != MovingObjectType.MISS && mc.objectMouseOver.getBlockPos() != null) {
				pos2 = mc.objectMouseOver.getBlockPos();
			}
		}
	}
	
	public void render3D() {
		if (!active || pos1 == null || pos2 == null) { return; }
		
		int r = Util.makeInteger(getOptionValue("red"));
		int g = Util.makeInteger(getOptionValue("green"));
		int b = Util.makeInteger(getOptionValue("blue"));
		int a = Util.makeInteger(getOptionValue("opacity"));
		
		Vec3 p1 = new Vec3(
				Math.min(pos1.getX(), pos2.getX()),
				Math.min(pos1.getY(), pos2.getY()),
				Math.min(pos1.getZ(), pos2.getZ())
				);
		Vec3 p2 = new Vec3(
				Math.max(pos1.getX(), pos2.getX()),
				Math.max(pos1.getY(), pos2.getY()),
				Math.max(pos1.getZ(), pos2.getZ())
				);
		Render.bbox(p1, p2, new Color(r, g, b, (a/2)+128).getRGB(), new Color(r, g, b, a/2).getRGB());
	}
}
