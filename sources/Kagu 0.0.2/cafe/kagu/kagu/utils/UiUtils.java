/**
 * 
 */
package cafe.kagu.kagu.utils;

import java.awt.Color;

import javax.vecmath.Vector4d;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * @author lavaflowglow
 *
 */
public class UiUtils {
	
	/**
	 * Draws a rectangle with a gradient
	 * 
	 * @param left             The left of the rect
	 * @param top              The top of the triangle
	 * @param right            The right of the triangle
	 * @param bottom           The bottom of the triangle
	 * @param topLeftColor     The top left color of the rect
	 * @param topRightColor    The top right color of the rect
	 * @param bottomLeftColor  The bottom left color of the rect
	 * @param bottomRightColor The bottom right color of the rect
	 */
	public static void drawGradientRect(double left, double top, double right, double bottom, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor) {
		
        if (left < right)
        {
        	double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
        	double j = top;
            top = bottom;
            bottom = j;
        }
        
		// Colors
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
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
        Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(left, bottom, 0.0D).color(blRed, blGreen, blBlue, blAlpha).endVertex();
		worldrenderer.pos(right, bottom, 0.0D).color(brRed, brGreen, brBlue, brAlpha).endVertex();
		worldrenderer.pos(right, top, 0.0D).color(trRed, trGreen, trBlue, trAlpha).endVertex();
		worldrenderer.pos(left, top, 0.0D).color(tlRed, tlGreen, tlBlue, tlAlpha).endVertex();
		tessellator.draw();
        
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
		
	}
	
	/**
	 * Draws a rounded rect
	 * 
	 * @param left       The left of the rect
	 * @param top        The top of the rect
	 * @param right      The right of the rect
	 * @param bottom     The bottom of the rect
	 * @param topLeftColor     The top left color of the rect
	 * @param topRightColor    The top right color of the rect
	 * @param bottomLeftColor  The bottom left color of the rect
	 * @param bottomRightColor The bottom right color of the rect
	 * @param cornerSize How big the rounded corners should be
	 */
	public static void drawRoundedRect(double left, double top, double right, double bottom, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor, double cornerSize) {
		drawRoundedRect(left, top, right, bottom, topLeftColor, topRightColor, bottomLeftColor, bottomRightColor, cornerSize, cornerSize, cornerSize, cornerSize);
	}
	
	/**
	 * Draws a rounded rect
	 * 
	 * @param left       The left of the rect
	 * @param top        The top of the rect
	 * @param right      The right of the rect
	 * @param bottom     The bottom of the rect
	 * @param color      The color of the rect
	 * @param cornerSize How big the rounded corners should be
	 */
	public static void drawRoundedRect(double left, double top, double right, double bottom, int color, double cornerSize) {
		drawRoundedRect(left, top, right, bottom, color, color, color, color, cornerSize, cornerSize, cornerSize, cornerSize);
	}
	
	/**
	 * Draws a rounded rect
	 * 
	 * @param left         The left of the rect
	 * @param top          The top of the rect
	 * @param right        The right of the rect
	 * @param bottom       The bottom of the rect
	 * @param color        The color of the rect
	 * @param cornerSizeTl The corner size for the top left corner
	 * @param cornerSizeTr The corner size for the top right corner
	 * @param cornerSizeBl The corner size for the bottom left corner
	 * @param cornerSizeBr The corner size for the bottom right corner
	 */
	public static void drawRoundedRect(double left, double top, double right, double bottom, int color, double cornerSizeTl, double cornerSizeTr, double cornerSizeBl, double cornerSizeBr) {
		drawRoundedRect(left, top, right, bottom, color, color, color, color, cornerSizeTl, cornerSizeTr, cornerSizeBl, cornerSizeBr);
	}
	
	/**
	 * Draws a rounded rect
	 * 
	 * @param left         The left of the rect
	 * @param top          The top of the rect
	 * @param right        The right of the rect
	 * @param bottom       The bottom of the rect
	 * @param topLeftColor     The top left color of the rect
	 * @param topRightColor    The top right color of the rect
	 * @param bottomLeftColor  The bottom left color of the rect
	 * @param bottomRightColor The bottom right color of the rect
	 * @param cornerSizeTl The corner size for the top left corner
	 * @param cornerSizeTr The corner size for the top right corner
	 * @param cornerSizeBl The corner size for the bottom left corner
	 * @param cornerSizeBr The corner size for the bottom right corner
	 */
	public static void drawRoundedRect(double left, double top, double right, double bottom, int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor, double cornerSizeTl, double cornerSizeTr, double cornerSizeBl, double cornerSizeBr) {
		
		// Vars
		double stepsIncrement = 5;
		
		if (top > bottom) {
			double temp = top;
			top = bottom;
			bottom = temp;
		}
		
		if (left > right) {
			double temp = left;
			left = right;
			right = temp;
		}
		
		// Colors
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
		
		// Draw
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();

		worldRenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
		
		// Top left
		if (cornerSizeTl <= 0) {
			worldRenderer.pos(left, top, 0).color(tlRed, tlGreen, tlBlue, tlAlpha).endVertex();
		}else {
			for (int i = 0; i <= 90; i += stepsIncrement) {
				worldRenderer.pos(left + cornerSizeTl + Math.sin(i * Math.PI / 180) * cornerSizeTl * -1,
						top + cornerSizeTl + Math.cos(i * Math.PI / 180) * cornerSizeTl * -1, 0).color(tlRed, tlGreen, tlBlue, tlAlpha).endVertex();
			}
		}
		
		// Bottom left
		if (cornerSizeBl <= 0) {
			worldRenderer.pos(left, bottom, 0).color(blRed, blGreen, blBlue, blAlpha).endVertex();
		}else {
			for (int i = 90; i <= 180; i += stepsIncrement) {
				worldRenderer.pos(left + cornerSizeBl + Math.sin(i * Math.PI / 180) * cornerSizeBl * -1,
						bottom - cornerSizeBl + Math.cos(i * Math.PI / 180) * cornerSizeBl * -1, 0).color(blRed, blGreen, blBlue, blAlpha).endVertex();
			}
		}
		
		// Bottom right
		if (cornerSizeBr <= 0) {
			worldRenderer.pos(right, bottom, 0).color(brRed, brGreen, brBlue, brAlpha).endVertex();
		}else {
			for (int i = 0; i <= 90; i += stepsIncrement) {
				worldRenderer.pos(right - cornerSizeBr + Math.sin(i * Math.PI / 180) * cornerSizeBr,
						bottom - cornerSizeBr + Math.cos(i * Math.PI / 180) * cornerSizeBr, 0).color(brRed, brGreen, brBlue, brAlpha).endVertex();
			}
		}
		
		// Top right
		if (cornerSizeTr <= 0) {
			worldRenderer.pos(right, top, 0).color(trRed, trGreen, trBlue, trAlpha).endVertex();
		}else {
			for (int i = 90; i <= 180; i += stepsIncrement) {
				worldRenderer.pos(right - cornerSizeTr + Math.sin(i * Math.PI / 180) * cornerSizeTr,
						top + cornerSizeTr + Math.cos(i * Math.PI / 180) * cornerSizeTr, 0).color(trRed, trGreen, trBlue, trAlpha).endVertex();
			}
		}
		
        tessellator.draw();
        
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        
	}
	
	/**
	 * Enables wireframe mode
	 */
	public static void enableWireframe() {
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_LINE);
	}
	
	/**
	 * Disables wireframe mode
	 */
	public static void disableWireframe() {
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_FILL);
	}
	
	/**
	 * Turns a vector into a color that can be used with minecraft's draw utils
	 * @param vectorColor The color as a vector
	 * @return The color as an int
	 */
	public static int getColorFromVector(Vector4d vectorColor) {
		return new Color((float)vectorColor.getX(), (float)vectorColor.getY(), (float)vectorColor.getZ(), (float)vectorColor.getW()).getRGB();
	}
	
	/**
	 * Turns a float array into a color that can be used with minecraft's draw utils
	 * @param color The color in a float array as [r, g, b, a]
	 * @return The color as an int
	 */
	public static int getColorFromFloatArray(float[] color) {
		return new Color(color[0], color[1], color[2], color[3]).getRGB();
	}
	
	/**
	 * Enables gl scissor
	 * @param left The left side of the box
	 * @param top The top of the box
	 * @param right The right side of the box
	 * @param bottom The bottom of the box
	 */
	public static void enableScissor(double left, double top, double right, double bottom) {
		enableScissor((int)Math.floor(left), (int)Math.floor(top), (int)Math.ceil(right), (int)Math.ceil(bottom));
	}
	
	/**
	 * Enables gl scissor
	 * @param left The left side of the box
	 * @param top The top of the box
	 * @param right The right side of the box
	 * @param bottom The bottom of the box
	 */
	public static void enableScissor(int left, int top, int right, int bottom) {
		
		if (left > right) {
			int temp = left;
			left = right;
			right = temp;
		}
		
		if (top > bottom) {
			int temp = top;
			top = bottom;
			bottom = temp;
		}
		
		// Annoying gui scaling
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int scaleFactor = scaledResolution.getScaleFactor();
		
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(left * scaleFactor, (scaledResolution.getScaledHeight() - (top + (bottom - top))) * scaleFactor, (right - left) * scaleFactor, (bottom - top) * scaleFactor);
		
	}
	
	/**
	 * Disables gl scissor
	 */
	public static void disableScissor() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	/**
	 * Lerps a color
	 * @param startColor The start color
	 * @param endColor   The end color
	 * @param t          The lerp value
	 * @return The lerped color
	 */
	public static Vector4d lerpColor(Vector4d startColor, Vector4d endColor, double t) {
		t = MathHelper.clamp_double(t, 0, 1);
		double inverseT = 1 - t;
		
		return new Vector4d((startColor.getX() * t) + (endColor.getX() * inverseT), 
				(startColor.getY() * t) + (endColor.getY() * inverseT), 
				(startColor.getZ() * t) + (endColor.getZ() * inverseT), 
				(startColor.getW() * t) + (endColor.getW() * inverseT));
	}
	
	/**
	 * Calls glcolor with an int
	 * @param color The color to use
	 */
	public static void glColorWithInt(int color) {
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		GlStateManager.color(red, green, blue, alpha);
	}
	
	/**
	 * Checks if the mouse is inside of a rounded rect using a mix of circle and rect bounding boxes
	 * 
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @param left         The left of the rect
	 * @param top          The top of the rect
	 * @param right        The right of the rect
	 * @param bottom       The bottom of the rect
	 * @param cornerSizeT The corner size for the top corners
	 * @param cornerSizeB The corner size for the bottom corners
	 */
	public static boolean isMouseInsideRoundedRect(double mouseX, double mouseY, double left, double top, double right, double bottom, double cornerSizeT, double cornerSizeB) {
		
		if (!Mouse.isInsideWindow())
			return false;
		
		if (top > bottom) {
			double temp = top;
			top = bottom;
			bottom = temp;
		}
		
		if (left > right) {
			double temp = left;
			left = right;
			right = temp;
		}
		
		// Vertical rect check
		if (mouseX >= left && mouseX < right && mouseY >= top + cornerSizeT && mouseY <= bottom - cornerSizeT) {
			return true;
		}
		
		// Horizontal rect check
		if (mouseX >= left + cornerSizeB && mouseX <= right - cornerSizeB && mouseY >= top && mouseY <= bottom) {
			return true;
		}
		
		// Circle checks
		
		// Top checks
		if (mouseY <= top + cornerSizeT) {
			
			// TL check
			if (mouseX <= left + cornerSizeT && MathUtils.getDistance2D(mouseX, mouseY, left + cornerSizeT, top + cornerSizeT) <= cornerSizeT) {
				return true;
			}
			
			// TR check
			if (mouseX >= right - cornerSizeT && MathUtils.getDistance2D(mouseX, mouseY, right - cornerSizeT, top + cornerSizeT) <= cornerSizeT) {
				return true;
			}
			
		}
		
		// Bottom checks
		if (mouseY >= bottom - cornerSizeB) {
			
			// BL check
			if (mouseX <= left + cornerSizeB && MathUtils.getDistance2D(mouseX, mouseY, left + cornerSizeB, bottom - cornerSizeB) <= cornerSizeB) {
				return true;
			}
			
			// BR check
			if (mouseX >= right - cornerSizeB && MathUtils.getDistance2D(mouseX, mouseY, right - cornerSizeB, bottom - cornerSizeB) <= cornerSizeB) {
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * Checks if the mouse is inside of a rounded rect using a mix of circle and rect bounding boxes
	 * 
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @param left         The left of the rect
	 * @param top          The top of the rect
	 * @param right        The right of the rect
	 * @param bottom       The bottom of the rect
	 * @param cornerSize The corner size
	 */
	public static boolean isMouseInsideRoundedRect(double mouseX, double mouseY, double left, double top, double right, double bottom, double cornerSize) {
		return isMouseInsideRoundedRect(mouseX, mouseY, left, top, right, bottom, cornerSize, cornerSize);
	}
	
	/**
	 * Turns an int color into a vector
	 * @param color The color to convert
	 * @return The color in the form of a vector
	 */
	public static Vector4d getVectorFromColor(int color) {
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		return new Vector4d(red, green, blue, alpha);
	}
	
	/**
	 * Turns an int color into a float array
	 * @param color The color to convert
	 * @return The color in the form of a float array
	 */
	public static float[] getFloatArrayFromColor(int color) {
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		return new float[] {red, green, blue, alpha};
	}
	
	/**
	 * Converts a formatted color int to an mc color int, 
	 * this is because hex colors are formatted as #RRGGBBAA but mc formats them #AARRGGBB
	 * @param formattedColor The formatted color in an int form
	 * @return A new int that works with mc render code
	 */
	public static int convertFormattedColor(int formattedColor) {
		float[] rgba = UiUtils.getFloatArrayFromColor(formattedColor);
		return UiUtils.getColorFromFloatArray(new float[] {rgba[3], rgba[0], rgba[1], rgba[2]});
	}
	
	/**
	 * Converts a mc color int to an formatted color int, 
	 * this is because hex colors are formatted as #RRGGBBAA but mc formats them #AARRGGBB
	 * @param color The mc color in an int form
	 * @return A new int in the format of #AARRGGBB
	 */
	public static int convertMcColor(int color) {
		float[] rgba = UiUtils.getFloatArrayFromColor(color);
		return UiUtils.getColorFromFloatArray(new float[] {rgba[1], rgba[2], rgba[3], rgba[0]});
	}
	
}
