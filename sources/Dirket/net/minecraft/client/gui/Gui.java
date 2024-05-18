package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui {
	public static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("textures/gui/options_background.png");
	public static final ResourceLocation STAT_ICONS = new ResourceLocation("textures/gui/container/stats_icons.png");
	public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
	protected float zLevel;

	/**
	 * Draws a thin horizontal line between two points.
	 */
	protected void drawHorizontalLine(int startX, int endX, int y, int color) {
		if (endX < startX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		drawRect(startX, y, endX + 1, y + 1, color);
	}

	/**
	 * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
	 */
	protected void drawVerticalLine(int x, int startY, int endY, int color) {
		if (endY < startY) {
			int i = startY;
			startY = endY;
			endY = i;
		}

		drawRect(x, startY + 1, x + 1, endY, color);
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color.
	 */
	public static void drawRect(int left, int top, int right, int bottom, int color) {
		if (left < right) {
			int i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			int j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = ((color >> 24) & 255) / 255.0F;
		float f = ((color >> 16) & 255) / 255.0F;
		float f1 = ((color >> 8) & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(left, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, top, 0.0D).endVertex();
		vertexbuffer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = ((startColor >> 24) & 255) / 255.0F;
		float f1 = ((startColor >> 16) & 255) / 255.0F;
		float f2 = ((startColor >> 8) & 255) / 255.0F;
		float f3 = (startColor & 255) / 255.0F;
		float f4 = ((endColor >> 24) & 255) / 255.0F;
		float f5 = ((endColor >> 16) & 255) / 255.0F;
		float f6 = ((endColor >> 8) & 255) / 255.0F;
		float f7 = (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
		vertexbuffer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	/**
	 * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
	 */
	public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawStringWithShadow(text, x - (fontRendererIn.getStringWidth(text) / 2), y, color);
	}

	/**
	 * Renders the specified text to the screen. Args : renderer, string, x, y, color
	 */
	public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawStringWithShadow(text, x, y, color);
	}

	/**
	 * Draws a textured rectangle at the current z-value.
	 */
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x + 0, y + height, this.zLevel).tex((textureX + 0) * 0.00390625F, (textureY + height) * 0.00390625F).endVertex();
		vertexbuffer.pos(x + width, y + height, this.zLevel).tex((textureX + width) * 0.00390625F, (textureY + height) * 0.00390625F).endVertex();
		vertexbuffer.pos(x + width, y + 0, this.zLevel).tex((textureX + width) * 0.00390625F, (textureY + 0) * 0.00390625F).endVertex();
		vertexbuffer.pos(x + 0, y + 0, this.zLevel).tex((textureX + 0) * 0.00390625F, (textureY + 0) * 0.00390625F).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a textured rectangle using the texture currently bound to the TextureManager
	 */
	public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xCoord + 0.0F, yCoord + maxV, this.zLevel).tex((minU + 0) * 0.00390625F, (minV + maxV) * 0.00390625F).endVertex();
		vertexbuffer.pos(xCoord + maxU, yCoord + maxV, this.zLevel).tex((minU + maxU) * 0.00390625F, (minV + maxV) * 0.00390625F).endVertex();
		vertexbuffer.pos(xCoord + maxU, yCoord + 0.0F, this.zLevel).tex((minU + maxU) * 0.00390625F, (minV + 0) * 0.00390625F).endVertex();
		vertexbuffer.pos(xCoord + 0.0F, yCoord + 0.0F, this.zLevel).tex((minU + 0) * 0.00390625F, (minV + 0) * 0.00390625F).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a texture rectangle using the texture currently bound to the TextureManager
	 */
	public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xCoord + 0, yCoord + heightIn, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
		vertexbuffer.pos(xCoord + widthIn, yCoord + heightIn, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
		vertexbuffer.pos(xCoord + widthIn, yCoord + 0, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
		vertexbuffer.pos(xCoord + 0, yCoord + 0, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
	 */
	public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		float f = 1.0F / textureWidth;
		float f1 = 1.0F / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
		vertexbuffer.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
		vertexbuffer.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
		vertexbuffer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).endVertex();
		vertexbuffer.pos(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
		vertexbuffer.pos(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).endVertex();
		vertexbuffer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
		tessellator.draw();
	}
}
