/**
 * 
 */
package cafe.kagu.kagu.font;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.font.GlyphUtils.GlyphMap;
import cafe.kagu.kagu.mods.impl.ghost.ModHideName;
import cafe.kagu.kagu.mods.impl.player.ModUwuifier;
import cafe.kagu.kagu.utils.Uwuifier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author lavaflowglow
 *
 */
public class FontRenderer {
	
	/**
	 * @param font The font to use
	 * @param scale Allows you to change the scaling of the font, this way you can have really small text that still looks crisp
	 * @param forceLinear Whether or not the font renderer should use linear instead of nearest neighbor
	 */
	public FontRenderer(Font font, double scale, boolean forceLinear) {
		glyphMap = GlyphUtils.genereateGlyphBufferedImageFromFont(font, true, false);
		glyphsTexture = new DynamicTexture(glyphMap.getBufferedImage());
		this.scale = scale;
		this.fontHeight = glyphMap.getFontHeight();
		this.forceLinear = forceLinear;
	}
	
	/**
	 * @param font The font to use
	 * @param scale Allows you to change the scaling of the font, this way you can have really small text that still looks crisp
	 */
	public FontRenderer(Font font, double scale) {
		this(font, scale, false);
	}
	
	private GlyphMap glyphMap;
	private DynamicTexture glyphsTexture;
	private double scale;
	private float fontHeight;
	private boolean forceLinear;
	
	/**
	 * Draws a string that's centered on the x position
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param color The color to draw the string with
	 * @return The width of the string
	 */
	public double drawCenteredString(String string, double x, double y, int color) {
		return drawString(string, x - (getStringWidth(string) / 2), y, color, false);
	}
	
	/**
	 * Draws a string that's centered on the x position
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param color The color to draw the string with
	 * @param shadow Whether or not the string should have a shadow
	 * @return The width of the string
	 */
	public double drawCenteredString(String string, double x, double y, int color, boolean shadow) {
		return drawString(string, x - (getStringWidth(string) / 2), y, color, shadow);
	}
	
	/**
	 * Draws a string
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param color The color to draw the string with
	 * @return The width of the string
	 */
	public double drawString(String string, double x, double y, int color) {
		return drawString(string, x, y, color, false);
	}
	
	/**
	 * Draws a string
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param color The color to draw the string with
	 * @param shadow Whether or not the string should have a shadow
	 * @return The width of the string
	 */
	public double drawString(String string, double x, double y, int color, boolean shadow) {
		return drawString(string, x, y, color, color, color, color, shadow);
	}
	
	/**
	 * Draws a string
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param topLeftColor     Color for the top left
	 * @param topRightColor    Color for the top right
	 * @param bottomLeftColor  Color for the bottom left
	 * @param bottomRightColor Color for the bottom right
	 * @return The width of the string
	 */
	public double drawString(String string, double x, double y, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor) {
		return drawString(string, x, y, topLeftColor, topRightColor, bottomLeftColor, bottomRightColor, false);
	}
	
	/**
	 * Draws a string
	 * @param string The string to render
	 * @param x The x pos of the string
	 * @param y The y pos of the string
	 * @param topLeftColor     Color for the top left
	 * @param topRightColor    Color for the top right
	 * @param bottomLeftColor  Color for the bottom left
	 * @param bottomRightColor Color for the bottom right
	 * @param shadow Whether or not the string should have a shadow
	 * @return The width of the string
	 */
	public double drawString(String string, double x, double y, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor, boolean shadow) {
		
		// HideName module
		string = ModHideName.replaceNameInstances(string);
		
		// Uwuifier
    	if (Kagu.getModuleManager().getModule(ModUwuifier.class).isEnabled() && Kagu.getModuleManager().getModule(ModUwuifier.class).getWholeGame().isEnabled()) {
    		string = Uwuifier.uwuifyWithoutCuteFace(string);
    	}
		
		// Character offset, also string width after drawing the entire string
		double offset = 0;
		
		// If the text has a shadow
		if (shadow) {
			for (char c : string.toCharArray()) {
				offset += drawChar(c, x + offset + 1, y + 1, 0x80000000, 0x80000000, 0x80000000, 0x80000000);
			}
		}
		
		offset = 0;
		
		// Non shadow text
		for (char c : string.toCharArray()) {
			offset += drawChar(c, x + offset, y, topLeftColor, topRightColor, bottomLeftColor, bottomRightColor);
		}
		
		return offset;
	}
	
	/**
	 * Draws a single character from the glyph texture
	 * @param c                The character to draw
	 * @param x                The x pos to draw at
	 * @param y                The y post to draw at
	 * @param topLeftColor     Color for the top left
	 * @param topRightColor    Color for the top right
	 * @param bottomLeftColor  Color for the bottom left
	 * @param bottomRightColor Color for the bottom right
	 * @return The width of the char
	 */
	public double drawChar(char c, double x, double y, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor) {
		Glyph glyph = glyphMap.getMapping().get(c);
		
		// If the font doesn't have a glyph for that character than return
		if (glyph == null) {
			return 0;
		}
		
		y -= glyph.getRenderYOffset();
//		y -= glyph.getHeight() / 2;
		
		// Draw character
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		// Color
		float tlRed = (float)(topLeftColor >> 16 & 255) / 255.0F;
		float tlGreen = (float)(topLeftColor >> 8 & 255) / 255.0F;
		float tlBlue = (float)(topLeftColor & 255) / 255.0F;
		float tlAlpha = (float)(topLeftColor >> 24 & 255) / 255.0F;
		
		float trRed = (float)(topRightColor >> 16 & 255) / 255.0F;
		float trGreen = (float)(topRightColor >> 8 & 255) / 255.0F;
		float trBlue = (float)(topRightColor & 255) / 255.0F;
		float trAlpha = (float)(topRightColor >> 24 & 255) / 255.0F;
		
		float blRed = (float)(bottomLeftColor >> 16 & 255) / 255.0F;
		float blGreen = (float)(bottomLeftColor >> 8 & 255) / 255.0F;
		float blBlue = (float)(bottomLeftColor & 255) / 255.0F;
		float blAlpha = (float)(bottomLeftColor >> 24 & 255) / 255.0F;
		
		float brRed = (float)(bottomRightColor >> 16 & 255) / 255.0F;
		float brGreen = (float)(bottomRightColor >> 8 & 255) / 255.0F;
		float brBlue = (float)(bottomRightColor & 255) / 255.0F;
		float brAlpha = (float)(bottomRightColor >> 24 & 255) / 255.0F;
		
		// Scaling
		GlStateManager.scale(scale, scale, scale);
		x *= 1 / scale;
		y *= 1 / scale;
		x = Math.ceil(x);
		y = Math.ceil(y);
		
		// Render
		GL11.glEnable(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableTexture2D();
		
		// Should do stuff
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, GL11.GL_TRUE);
		
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        
		GlStateManager.bindTexture(glyphsTexture.getGlTextureId());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, forceLinear ? GL11.GL_LINEAR : GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, forceLinear ? GL11.GL_LINEAR : GL11.GL_NEAREST);
		
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(x, y, 0).tex(glyph.getScaledX(), glyph.getScaledY()).color(tlRed, tlGreen, tlBlue, tlAlpha).endVertex();
        worldrenderer.pos(x, y + glyph.getHeight(), 0).tex(glyph.getScaledX(), glyph.getScaledY() + glyph.getScaledHeight()).color(blRed, blGreen, blBlue, blAlpha).endVertex();
        worldrenderer.pos(x + glyph.getWidth(), y + glyph.getHeight(), 0).tex(glyph.getScaledX() + glyph.getScaledWidth(), glyph.getScaledY() + glyph.getScaledHeight()).color(brRed, brGreen, brBlue, brAlpha).endVertex();
        worldrenderer.pos(x + glyph.getWidth(), y, 0).tex(glyph.getScaledX() + glyph.getScaledWidth(), glyph.getScaledY()).color(trRed, trGreen, trBlue, trAlpha).endVertex();
        tessellator.draw();
        
        GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
		return glyph.getWidth() * scale;
	}

	/**
	 * @return the fontHeight
	 */
	public float getFontHeight() {
		return (float)(fontHeight * scale);
	}

	/**
	 * Calculates the width of a string and returns it
	 * @param str The string you want to measure
	 * @return The width of the string
	 */
	public double getStringWidth(String str) {
		
		// HideName module
		str = ModHideName.replaceNameInstances(str);
		
		// Uwuifier
    	if (Kagu.getModuleManager().getModule(ModUwuifier.class).isEnabled() && Kagu.getModuleManager().getModule(ModUwuifier.class).getWholeGame().isEnabled()) {
    		str = Uwuifier.uwuifyWithoutCuteFace(str);
    	}
		
		double width = 0;
		
		// Add the width of each char
		for (char c : str.toCharArray()) {
			Glyph glyph = glyphMap.getMapping().get(c);
			if (glyph == null) {
				continue;
			}
			width += glyph.getWidth();
		}
		
		return width * scale;
	}
	
}
