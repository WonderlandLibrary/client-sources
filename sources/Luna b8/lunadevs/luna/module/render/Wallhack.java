package lunadevs.luna.module.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventRender3D;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.MathUtils;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class Wallhack extends Module{
	
	public static boolean active;

	public Wallhack() {
		super("Wallhack", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	
	@EventTarget
	public void onRender3D(EventRender3D event) {
		if (!this.isEnabled) return;
		for (Object obj : mc.theWorld.playerEntities) {
			if (obj instanceof EntityPlayer && obj != this.mc.thePlayer) {
				EntityPlayer entity = (EntityPlayer) obj;

				GL11.glPushMatrix();
				GlStateManager.translate(-mc.getRenderManager().renderPosX, -mc.getRenderManager().renderPosY,
						-mc.getRenderManager().renderPosZ);
				double x = entity.posX;
				double y = entity.posY + entity.getEyeHeight() + 0.3;
				double z = entity.posZ;

				String color = "";
				if (entity.getHealth() >= 15 && entity.getHealth() <= 20) {
					color = " §a";
				} else if (entity.getHealth() >= 10 && entity.getHealth() < 15) {
					color = " §6";
				} else if (entity.getHealth() >= 5 && entity.getHealth() < 10) {
					color = " §c";
				} else if (entity.getHealth() >= 0 && entity.getHealth() < 5) {
					color = " §4";
				}
				String meme = "";
				if ((FriendManager.isFriend(meme))) {
					meme = "§7";
				} else if (FriendManager.isFriend(meme)) {
					meme = "§3[F] §3";
				}
				String dank = "";
				if (!entity.isSneaking()) {
					dank = "";
				} else if (entity.isSneaking()) {
					dank = "§6[S] ";
				}
				int kush = 17;
				int c = 0;
				if(!entity.isOnSameTeam(mc.thePlayer)) {
					c = new Color(255, 102, 0, 255).getRGB();
				}
				else if(entity.isOnSameTeam(mc.thePlayer)) {
					c = new Color(0, 166, 22, 255).getRGB();
				}
				this.renderNameTag(color + MathUtils.round(entity.getHealth(), 2), x, y, z, c);
				GlStateManager.translate(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
						mc.getRenderManager().renderPosZ);
				GL11.glPopMatrix();
			}
		}
	}
	
	@EventTarget
	public void onRender3DDD(EventRender3D event) {
		for (Object obj : mc.theWorld.playerEntities) {
			if (obj instanceof EntityPlayer && obj != this.mc.thePlayer) {
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
					color = " §a";
				} else if (entity.getHealth() >= 10 && entity.getHealth() < 15) {
					color = " §6";
				} else if (entity.getHealth() >= 5 && entity.getHealth() < 10) {
					color = " §c";
				} else if (entity.getHealth() >= 0 && entity.getHealth() < 5) {
					color = " §4";
				}
				String meme = "";
				if ((FriendManager.isFriend(meme))) {
					meme = "§7";
				} else if ((FriendManager.isFriend(meme))) {
					meme = "§3[F] §3";
				}
				String dank = "";
				if (!entity.isSneaking()) {
					dank = "";
				} else if (entity.isSneaking()) {
					dank = "§6[S] ";
				}
		//		this.renderWhName(entity.getName(), x, y, z);
		//		GlStateManager.translate(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
		//				mc.getRenderManager().renderPosZ);
				GL11.glPopMatrix();
			}
		}
	}
	
	@EventTarget
	public void onRender3DD(EventRender3D event) {
		if (!this.isEnabled) return;
		for (Object obj : mc.theWorld.playerEntities) {
			if (obj instanceof EntityPlayer && obj != this.mc.thePlayer) {
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
					color = " §a";
				} else if (entity.getHealth() >= 10 && entity.getHealth() < 15) {
					color = " §6";
				} else if (entity.getHealth() >= 5 && entity.getHealth() < 10) {
					color = " §c";
				} else if (entity.getHealth() >= 0 && entity.getHealth() < 5) {
					color = " §4";
				}
				String meme = "";
				if (FriendManager.isFriend(meme)) {
					meme = "§7";
				} else if (FriendManager.isFriend(meme)) {
					meme = "§3[F] §3";
				}
				String dank = "";
				if (!entity.isSneaking()) {
					dank = "";
				} else if (entity.isSneaking()) {
					dank = "§6[S] ";
				}
			//	this.renderWhTag(color + MathUtils.round(entity.getHealth(), 2), x, y, z);
			//	GlStateManager.translate(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
			//			mc.getRenderManager().renderPosZ);
				GL11.glPopMatrix();
			}
		}
	}

	public void renderNameTag(String tag, double pX, double pY, double pZ, int Color) {
		if (!this.isEnabled) return;
		FontRenderer var12 = this.mc.fontRendererObj;

		float var13 = (float) (this.mc.thePlayer.getDistance(pX, pY, pZ) / 4F);
		if (var13 < 1.6F) {
			var13 = 1.6F;
		}
		
		RenderManager renderManager = this.mc.getRenderManager();
		float scale = var13;
		scale /= 50.0F;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) pX, (float) pY + 0.3f, (float) pZ);
		GL11.glNormal3f(0F, 1F, 0F);
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		//GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		//GL11.glScalef(-0.233f, -0.233f, 0.233f);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		Tessellator var14 = Tessellator.getInstance();
		WorldRenderer var15 = var14.getWorldRenderer();
		int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
		int kush = 17;
		int meme = 0;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
		GL11.glScalef(0.033f / 2, 0.033f / 2, 0.033f / 2);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		RenderUtils.drawBorderRect(-kush - 2 - 18, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 2 - 5, kush + 2 + 18, -5 - 65 - 65,
				new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB() , 1);
		//GL11.glScalef(0.033f / 2 * 2, 0.033f / 2 * 2, 0.033f / 2 * 2);
		RenderUtils.drawBorderRect(-kush - 1 - 18, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 2 - 1 - 1 - 5 - 1, kush + 1 + 18, -5 - 65 - 65 + 1 + 1 + 1,
				Color, new Color(0, 0, 0, 0).getRGB() , 3);
		RenderUtils.drawBorderRect(-kush + 2 - 18, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 2 - 5 - 4, kush + 2 + 18 - 4, -5 - 65 - 65 + 4,
				new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 0).getRGB() , 1);
		RenderUtils.drawRect(-kush + 2 + 18 - 3 - 36, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 2 - 1 - 1 - 5 - 1 + 3, -kush + 2 + 18 - 36, -5 - 65 - 64 - 1, Color);
		GL11.glScalef(-1f, -1f, -1f);
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		//RenderUtils.drawRect(kush + 3, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 17, kush + 2 + (Wrapper.fr.getStringWidth(tag)), -5 + 22, new Color(0, 0, 0, 155).getRGB());
		//this.mc.fontRendererObj.drawStringWithShadow(tag, kush + 1, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 13 + 5, 0xFFFFFFFF);
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		GL11.glEnable(2896);
		GL11.glEnable(2929);
        GL11.glDisable(3042);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glPopMatrix();

	}
	
	public void renderWhTag(String tag, double pX, double pY, double pZ) {
		if (!this.isEnabled) return;
		FontRenderer var12 = this.mc.fontRendererObj;

		float var13 = (float) (this.mc.thePlayer.getDistance(pX, pY, pZ) / 4F);
		if (var13 < 1.6F) {
			var13 = 1.6F;
		}
		RenderManager renderManager = this.mc.getRenderManager();
		float scale = var13;
		scale /= 50.0F;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) pX, (float) pY + 0.3f, (float) pZ);
		GL11.glNormal3f(0F, 1F, 0F);
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		GL11.glScalef(-0.033f, -0.033f, 0.033f);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		Tessellator var14 = Tessellator.getInstance();
		WorldRenderer var15 = var14.getWorldRenderer();
		int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
		int kush = 17;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
		//RenderUtils.drawBorderedRect(-width - 2, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2, 2,
			//	new Color(0, 0, 0, 155).getRGB(), new Color(0, 0, 0, 100).getRGB());
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		this.mc.fontRendererObj.drawString(tag, kush + 1, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 23 + 1, 0xFFFFFFFF);
		//this.mc.fontRendererObj.drawStringWithShadow(tag, MathUtils.getMid(-width, width) - width,
				//-(this.mc.fontRendererObj.FONT_HEIGHT - 1), -1);
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		GL11.glEnable(2896);
		GL11.glEnable(2929);
        GL11.glDisable(3042);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glPopMatrix();

	}

	
	public void renderWhName(String tag, double pX, double pY, double pZ) {
		FontRenderer var12 = this.mc.fontRendererObj;

		float var13 = (float) (this.mc.thePlayer.getDistance(pX, pY, pZ) / 4F);
		if (var13 < 1.6F) {
			var13 = 1.6F;
		}
		RenderManager renderManager = this.mc.getRenderManager();
		float scale = var13;
		scale /= 50.0F;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) pX, (float) pY + 0.3f, (float) pZ);
		GL11.glNormal3f(0F, 1F, 0F);
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		GL11.glScalef(-0.033f, -0.033f, 0.033f);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		Tessellator var14 = Tessellator.getInstance();
		WorldRenderer var15 = var14.getWorldRenderer();
		int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
		int kush = 17;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
		//RenderUtils.drawBorderedRect(-width - 2, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2, 2,
			//	new Color(0, 0, 0, 155).getRGB(), new Color(0, 0, 0, 100).getRGB());
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		this.mc.fontRendererObj.drawString(" " + tag, kush + 1, (int) -(this.mc.fontRendererObj.FONT_HEIGHT + 1) + 13 + 1, 0xFFFFFFFF);
		//this.mc.fontRendererObj.drawStringWithShadow(tag, MathUtils.getMid(-width, width) - width,
				//-(this.mc.fontRendererObj.FONT_HEIGHT - 1), -1);
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		GL11.glEnable(2896);
		GL11.glEnable(2929);
        GL11.glDisable(3042);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glPopMatrix();


}
	
	
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Flux";
	}

}
