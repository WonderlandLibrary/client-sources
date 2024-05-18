package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.aura.KillAura;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class HitSpheres extends Module {

	public HitSpheres() {
		super("HitSpheres", "Draws a sphere around players showing your kill aura range.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for(Entity ep : Wrapper.getWorld().loadedEntityList) {
				if(ep instanceof EntityPlayerSP) continue;
				if(ep instanceof EntityPlayer) {
					double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
					double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
					double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
					if(Wrapper.getFriends().isFriend(((EntityPlayer)ep).getName())) {
						GL11.glColor4f(0.15F, 0.15F, 1.0F, 1.0F);
					} else {
						if(Wrapper.getPlayer().getDistanceToEntity(ep) >= 64) {
							GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
						} else {
							GL11.glColor4f(1.0F, Wrapper.getPlayer().getDistanceToEntity(ep) / 150, 0.0F, 1.0F);
						}
					}
					RenderUtils.drawSphere(d, d1, d2, KillAura.auraRange.getValue(), 20, 15);
				}
			}
		}
	}
}