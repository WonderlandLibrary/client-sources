package net.minecraft.client.triton.impl.modules.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

@Mod(shown = false)
public class ESP extends Module {
	@Op
	public static boolean players;
	@Op
	public static boolean monsters;
	@Op
	public static boolean animals;
	private int state;
	private float r;
	private float g;
	private float b;
	private static float[] rainbowArray;

	public ESP() {
		this.players = true;
		this.r = 0.33f;
		this.g = 0.34f;
		this.b = 0.33f;
	}

	@EventTarget
	private void onPreUpdate(final UpdateEvent event) {
		if (event.getState() == event.getState().PRE) {
			ESP.rainbowArray = this.getRainbow();
		}
	}

	public static void renderOne() {
		GL11.glPushAttrib(1048575);
		GL11.glDisable(3008);
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glEnable(2960);
		GL11.glClear(1024);
		GL11.glClearStencil(15);
		GL11.glStencilFunc(512, 1, 15);
		GL11.glStencilOp(7681, 7681, 7681);
		GL11.glLineWidth(0.5F);
		GL11.glStencilOp(7681, 7681, 7681);
		GL11.glPolygonMode(1032, 6913);
	}

	public static void renderTwo() {
		GL11.glStencilFunc(512, 0, 15);
		GL11.glStencilOp(7681, 7681, 7681);
		GL11.glPolygonMode(1032, 6914);
	}

	public static void renderThree() {
		GL11.glStencilFunc(514, 1, 15);
		GL11.glStencilOp(7680, 7680, 7680);
		GL11.glPolygonMode(1032, 6913);
	}

	public static void renderFour() {
		GL11.glEnable(10754);
		GL11.glPolygonOffset(1.0F, -2000000.0F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}

	public static void renderFive() {
		GL11.glPolygonOffset(1.0F, 2000000.0F);
		GL11.glDisable(10754);
		GL11.glDisable(2960);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(3553);
		GL11.glEnable(3008);
		GL11.glPopAttrib();
	}

	private float[] getRainbow() {
		if (this.state == 0) {
			this.r += 0.02;
			this.b -= 0.02;
			if (this.r >= 0.85) {
				++this.state;
			}
		} else if (this.state == 1) {
			this.g += 0.02;
			this.r -= 0.02;
			if (this.g >= 0.85) {
				++this.state;
			}
		} else {
			this.b += 0.02;
			this.g -= 0.02;
			if (this.b >= 0.85) {
				this.state = 0;
			}
		}
		return new float[] { this.r, this.g, this.b };
	}
}
