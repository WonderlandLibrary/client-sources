package mods.togglesprint.me.imfr0zen.guiapi;

import net.minecraft.client.gui.Gui;

public final class RenderUtil {

	private RenderUtil() {};

	/**
	 * Checks if a Rectangle is hovered.
	 * 
	 * @param x
	 * @param y
	 * @param width Must be relative to x.
	 * @param height Must be relative to y.
	 * @param mouseX
	 * @param mouseY
	 * @return true if hovered
	 */
	public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
		return (mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY < y + height);
	}

	/**
	 * Draws a Rectangle, change the code if it is not compatible with your version of Minecraft.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param color
	 */
	public static void drawRect(int left, int top, int right, int bottom, int color) {
		Gui.drawRect(left, top, right, bottom, color);
	}

	/**
	 * Draws a 1 pixel wide horizontal line.
	 * 
	 * @param startX
	 * @param endX
	 * @param y
	 * @param color
	 */
	public static void drawHorizontalLine(int startX, int endX, int y, int color) {
		if (endX < startX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		drawRect(startX, y, endX + 1, y + 1, color);
	}

	/**
	 * Draws a 1 pixel wide vertical line.
	 * 
	 * @param x
	 * @param startY
	 * @param endY
	 * @param color
	 */
	public static void drawVerticalLine(int x, int startY, int endY, int color) {
		if (endY < startY) {
			int i = startY;
			startY = endY;
			endY = i;
		}

		drawRect(x, startY + 1, x + 1, endY, color);
	}

	public static int getWidth(String text) {
		return ClickGui.FONTRENDERER.getStringWidth(text);
	}

	public static void drawString(String text, int x, int y, int color) {
		ClickGui.FONTRENDERER.drawString(text, x, y, color);
	}

	public static void drawCenteredString(String text, int x, int y, int color) {
		ClickGui.FONTRENDERER.drawString(text, x - getWidth(text) / 2, y, color);
	}

}
