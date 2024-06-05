package me.rich.module.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;

public class TriangleESP extends Feature {

	public TriangleESP() {
		super("TriangleESP", 0, Category.RENDER);

	}

	@EventTarget
	public void onRender2D(EventRender2D event) {
		ScaledResolution sr = new ScaledResolution(mc);
		
		for (int i = 0; i < mc.world.playerEntities.size(); i++) {
			EntityPlayer entity = mc.world.playerEntities.get(i);
			
			int noommom = 100;
			double xOffset = sr.getScaledWidth() / 2F - (noommom / 2.04);
			double yOffset = sr.getScaledHeight() / 2F - (noommom / 1.983);
			double noommomOffsetX = mc.player.posX;
			double noommomOffSetZ = mc.player.posZ;
			double loaddist = 0.2f;
			double pTicks = mc.timer.renderPartialTicks;
			double pos1 = (((entity.posX + (entity.posX - entity.lastTickPosX) * pTicks) - noommomOffsetX) * loaddist);
			double pos2 = (((entity.posZ + (entity.posZ - entity.lastTickPosZ) * pTicks) - noommomOffSetZ) * loaddist);
			double cos = Math.cos(mc.player.rotationYaw * (Math.PI * 2 / 360));
			double sin = Math.sin(mc.player.rotationYaw * (Math.PI * 2 / 360));
			double rotY = -(pos2 * cos - pos1 * sin);
			double rotX = -(pos1 * cos + pos2 * sin);
			double var7 = 0 - rotX;
			double var9 = 0 - rotY;
			if(!entity.isDead && entity.getHealth() > 0.0f && entity != null && entity != mc.player) {
			if (MathHelper.sqrt(var7 * var7 + var9 * var9) < noommom / 2F - 4) {
				double anglenoommom = (Math.atan2(rotY - 0, rotX - 0) * 180 / Math.PI);
				double x = ((noommom / 2F) * Math.cos(Math.toRadians(anglenoommom))) + xOffset + noommom / 2F;
				double y = ((noommom / 2F) * Math.sin(Math.toRadians(anglenoommom))) + yOffset + noommom / 2F;
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, 0);
				GlStateManager.rotate((float) anglenoommom, 0, 0, 1);
				GlStateManager.scale(1.5, 1.0, 1.0);

				triangle(0, 0, 2F, 3f, Main.getClientColor().getRGB());
				GlStateManager.popMatrix();
			}
			}
		}
	}

	private void triangle(float cx, float cy, float r, float n, int color) {
		cx *= 2.0;
		cy *= 2.0;
		float b = 6.2831852f / n;
		float p = (float) Math.cos(b);
		float s = (float) Math.sin(b);
		float x = r *= 3.0f;
		float y = 0.0f;
		GL11.glPushMatrix();
		RenderHelper.enableGL2D();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		RenderHelper.setColor(color);
		GL11.glBegin(2);
		int ii = 0;
		while (ii < n) {
			GL11.glVertex2f(x + cx, y + cy);
			float t = x;
			x = p * x - s * y;
			y = s * t + p * y;
			ii++;
		}
		GL11.glEnd();
		GL11.glScalef(5.0f, 5.0f, 5.0f);
		RenderHelper.disableGL2D();
		GL11.glPopMatrix();
	}
}