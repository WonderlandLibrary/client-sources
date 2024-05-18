package com.kilo.mod.all;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.entity.Entity;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class WallHack extends Module {
	
	public WallHack(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void render3DPost() {
		if (!active || mc.theWorld == null || mc.theWorld.loadedEntityList == null) { return; }
        RenderGlobal var5 = this.mc.renderGlobal;
        Frustrum var8 = new Frustrum();
        Entity var9 = this.mc.getRenderViewEntity();

        mc.entityRenderer.setupCameraTransformExt(mc.timer.renderPartialTicks, 2);
    	var5.renderEntities(var9, var8, mc.timer.renderPartialTicks);
	}
}
