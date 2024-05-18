package com.kilo.mod.all;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class NoHurtCam extends Module {

	public NoHurtCam(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onCameraTransform() {
		float p_78482_1_ = mc.timer.renderPartialTicks;
		
		EntityLivingBase var2 = (EntityLivingBase)mc.thePlayer;
        float var3 = (float)var2.hurtTime - p_78482_1_;
        float var4;

        if (var2.getHealth() <= 0.0F)
        {
            var4 = (float)var2.deathTime + p_78482_1_;
            GlStateManager.rotate(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
        }

        if (var3 < 0.0F)
        {
            return;
        }

        var3 /= (float)var2.maxHurtTime;
        var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float)Math.PI);
        var4 = var2.attackedAtYaw;
        GlStateManager.rotate(var4, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var3 * 14.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-var4, 0.0F, 1.0F, 0.0F);
	}
}
