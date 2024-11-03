package net.silentclient.client.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.accessors.FontRendererAccessor;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.render.NametagsMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.RenderTransformer;
import org.lwjgl.opengl.GL11;

public class NameTagRenderingHooks {
	 public static int drawNametagText(FontRenderer fontRenderer, String text, int x, int y, int color, boolean shadow) {
	        int render;
	  
	        if (shadow) {
	            GL11.glDepthMask(false);
	            render = ((FontRendererAccessor) fontRenderer).silent$renderString(text, x + 1.0F, y + 1.0F, color, true);
	            GL11.glDepthMask(true);

	            render = Math.max(render, ((FontRendererAccessor) fontRenderer).silent$renderString(text, x, y, color, false));

	            GL11.glColorMask(false, false, false, false);
	            render = Math.max(render, fontRenderer.drawString(text, x, y, color, true));
	            GL11.glColorMask(true, true, true, true);
	        } else {
	            render = ((FontRendererAccessor) fontRenderer).silent$renderString(text, x, y, color, false);
	        }

	        return render;
	 }

	 public static void renderNametag(Entity entityIn, String str, double x, double y, double z, int maxDistance, boolean isMainNametag) {
		 String username = "";
		 double d0 = entityIn.getDistanceSqToEntity(Minecraft.getMinecraft().getRenderManager().livingPlayer);

		 if (d0 <= (double)(maxDistance * maxDistance))
		 {
			 boolean fontShadow = Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Font Shadow").getValBoolean();
			 boolean icons = !Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Show Nametag Icons").getValBoolean();
			 boolean background = !Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() || Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Background").getValBoolean();
			 FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
			 float f = 1.6F;
			 float f1 = 0.016666668F * f;

			 GlStateManager.pushMatrix();
			 GlStateManager.translate((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
			 GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			 float viewY = Minecraft.getMinecraft().getRenderManager().playerViewY;
			 float viewX = Minecraft.getMinecraft().getRenderManager().playerViewX;
			 if(Client.getInstance().getModInstances().getSnaplookMod().isActive()) {
				 viewY = Client.getInstance().getModInstances().getSnaplookMod().getYaw();
				 viewX = Client.getInstance().getModInstances().getSnaplookMod().getPitch();
			 }
			 GlStateManager.rotate(-viewY, 0.0F, 1.0F, 0.0F);
			 GlStateManager.rotate(viewX * RenderTransformer.checkPerspective(), 1.0F, 0.0F, 0.0F);
			 GlStateManager.scale(-f1, -f1, f1);
			 GlStateManager.disableLighting();
			 GlStateManager.depthMask(false);
			 GlStateManager.disableDepth();
			 GlStateManager.enableBlend();
			 GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			 Tessellator tessellator = Tessellator.getInstance();
			 WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			 byte b0 = 0;

			 boolean status = false;
			 if(entityIn instanceof AbstractClientPlayer) {
				 try {
					 status = Boolean.parseBoolean(Players.getPlayerStatus(entityIn == Minecraft.getMinecraft().thePlayer, entityIn.getName().toString(), entityIn.getUniqueID(), (AbstractClientPlayer) entityIn));
				 } catch(Exception e) {
					 status = false;
				 }
			 }

			 username += str;



			 if (username.equals("deadmau5"))
			 {
				 b0 = -10;
			 }

			 int i = fontrenderer.getStringWidth(username) / 2;
			 GlStateManager.disableTexture2D();

			 if(background) {
				 worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
				 worldrenderer.pos((double)(-i - 1), (double)(-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				 worldrenderer.pos((double)(-i - 1), (double)(8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				 worldrenderer.pos((double)(i + 1), (double)(8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				 worldrenderer.pos((double)(i + 1), (double)(-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				 tessellator.draw();
				 if(status && isMainNametag && icons) {
					 float x1 = (-(fontrenderer.getStringWidth(entityIn.getDisplayName().getFormattedText()) >> 1) - 11);
					 float x2 = (-(fontrenderer.getStringWidth(entityIn.getDisplayName().getFormattedText()) >> 1) - 1);
					 worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
					 worldrenderer.pos(x1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
					 worldrenderer.pos(x1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
					 worldrenderer.pos(x2, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
					 worldrenderer.pos(x2, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
					 tessellator.draw();
				 }
			 }
			 GlStateManager.enableTexture2D();
			 NameTagRenderingHooks.drawNametagText(fontrenderer, username, -fontrenderer.getStringWidth(username) / 2, b0, 553648127, fontShadow);
			 GlStateManager.enableDepth();
			 GlStateManager.depthMask(true);
			 NameTagRenderingHooks.drawNametagText(fontrenderer, username, -fontrenderer.getStringWidth(username) / 2, b0, -1, fontShadow);

			 if(status && isMainNametag && icons) {
				 try {
					 GlStateManager.disableDepth();
					 GlStateManager.depthMask(false);
					 ColorUtils.setColor(-1, 0.2F);
					 Minecraft.getMinecraft().getTextureManager().bindTexture(((AbstractClientPlayerExt) entityIn).silent$getPlayerIcon().getLocation());
					 Gui.drawModalRectWithCustomSizedTexture(-fontrenderer.getStringWidth(entityIn.getDisplayName().getFormattedText()) / 2 - 11, -1, 9, 9, 9, 9, 9, 9);
					 GlStateManager.enableDepth();
					 GlStateManager.depthMask(true);
					 ColorUtils.setColor(-1);
					 Minecraft.getMinecraft().getTextureManager().bindTexture(((AbstractClientPlayerExt) entityIn).silent$getPlayerIcon().getLocation());
					 Gui.drawModalRectWithCustomSizedTexture(-fontrenderer.getStringWidth(entityIn.getDisplayName().getFormattedText()) / 2 - 11, -1, 9, 9, 9, 9, 9, 9);
				 } catch (Exception err) {

				 }
			 }
			 GlStateManager.enableLighting();
			 GlStateManager.disableBlend();

			 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			 GlStateManager.popMatrix();
		 }
	 }
}
