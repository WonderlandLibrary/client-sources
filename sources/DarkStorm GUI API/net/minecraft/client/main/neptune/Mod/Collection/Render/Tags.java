package net.minecraft.client.main.neptune.Mod.Collection.Render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventNameTagRender;
import net.minecraft.client.main.neptune.Events.EventRender3D;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.MathUtils;
import net.minecraft.client.main.neptune.Utils.RenderUtils;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Tags extends Mod {

	public Tags() {
		super("Nametags", Category.HACKS);
	}

	@Override
	public void onEnable() {
		Memeager.register(this);
		//ChatUtils.sendMessageToPlayer("TURN ON ESP OR HATS BEFORE TOGGLING NAMETAGS (if planning to use them)");
	}

	@Override
	public void onDisable() {
		Memeager.unregister(this);
	}

	@Memetarget
	public void onRender3D(EventRender3D event) {
		for (Object obj : mc.theWorld.playerEntities) {
			if (obj instanceof EntityPlayer && obj != this.mc.thePlayer && ((EntityPlayer) obj).isInvisible() == false) {
				//event.setCancelled(true);
				EntityPlayer entity = (EntityPlayer) obj;

				GL11.glPushMatrix();
				GlStateManager.translate(-mc.getRenderManager().renderPosX, -mc.getRenderManager().renderPosY,
						-mc.getRenderManager().renderPosZ);
				double x = entity.posX;
				double y = entity.posY + entity.getEyeHeight() + 0.2;
				double z = entity.posZ;

				String color = "";
				if (entity.getHealth() >= 15 && entity.getHealth() <= 20) {
					color = "§a";
				} else if (entity.getHealth() >= 10 && entity.getHealth() < 15) {
					color = "§6";
				} else if (entity.getHealth() >= 5 && entity.getHealth() < 10) {
					color = "§c";
				} else if (entity.getHealth() >= 0 && entity.getHealth() < 5) {
					color = "§4";
				}
				String meme = "";
				if (!Neptune.getWinter().friendUtils.isFriend(entity.getName())) {
					meme = "§f";
				} else if (Neptune.getWinter().friendUtils.isFriend(entity.getName())) {
					meme = "§f";
				}
				String dank = "";
				if (!entity.isSneaking()) {
					dank = "";
				} else if (entity.isSneaking()) {
					dank = "§f";
				}
				this.renderNameTag(color + MathUtils.round(entity.getHealth(), 2) / 2 + " " + dank + meme + entity.getName(), x, y, z);
				GlStateManager.translate(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
						mc.getRenderManager().renderPosZ);
				GL11.glPopMatrix();
			}
		}
	}

	public void renderNameTag(String tag, double pX, double pY, double pZ) {
		FontRenderer var12 = this.mc.fontRendererObj;

		float var13 = (float) (this.mc.thePlayer.getDistance(pX, pY, pZ) / 4F);
		if (var13 < 1.6F) {
			var13 = 1.6F;
		}
		RenderManager renderManager = this.mc.getRenderManager();
		float scale = var13;
		scale /= 70.0F;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) pX, (float) pY + 0.3f, (float) pZ);
		GL11.glNormal3f(0F, 1F, 0F);
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		GL11.glScalef(-scale, -scale, scale);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		Tessellator var14 = Tessellator.getInstance();
		WorldRenderer var15 = var14.getWorldRenderer();
		int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
		RenderUtils.drawBorderedRect(-width - 2, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2, 2,
				new Color(0, 0, 0, 0).getRGB(), 1275068416);
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		this.mc.fontRendererObj.drawStringWithShadow(tag, MathUtils.getMid(-width, width) - width,
				-(this.mc.fontRendererObj.FONT_HEIGHT - 1), -1);
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		GL11.glEnable(2896);
		GL11.glEnable(2929);
        GL11.glDisable(3042);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glPopMatrix();

	}

}
