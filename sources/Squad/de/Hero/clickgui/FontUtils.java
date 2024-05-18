package de.Hero.clickgui;
import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

public class FontUtils {
	private static FontRenderer fontRenderer;
	private static Squad.Utils.FontUtils ModuleButtons = new Squad.Utils.FontUtils("Thruster Regular", Font.PLAIN, 20);
	private static Squad.Utils.FontUtils Title = new Squad.Utils.FontUtils("Thruster Regular", Font.PLAIN, 25);


	public static void setupFontUtils() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}
	
	/*
	 * Vanilla Fonts
	 */

	public static float getStringWidth(String text) {
		return fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
	}

	public static float getFontHeight() {
		return fontRenderer.FONT_HEIGHT;
	}

	public static void drawString(String text, float x, float y, int color) {
	}

	public static void drawStringWithShadow(String text, float x, float y, int color) {
	}

	public static void drawCenteredString(String text, float x, float y, int color) {
		drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawCenteredStringWithShadow(String text, float x, float y, int color) {
		drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawTotalCenteredString(String text, float x, float y, int color) {
		drawString(text, x - fontRenderer.getStringWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2, color);
	}

	public static void drawTotalCenteredStringWithShadow(String text, float x, float y, int color) {
		drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2F, color);
	}
	
	/*
	 * Custom Font
	 */
	
	public static float getCustonStringWidth(String text) {
		return ModuleButtons.getWidth(StringUtils.stripControlCodes(text));
	}
	
	public static void drawCustomString(String text, float x, float y, int color) {
		ModuleButtons.drawString(text, x, y, color);
	}

	public static void drawCustomStringWithShadow(String text, float x, float y, int color) {
		ModuleButtons.drawStringWithShadow(text, (float) x, (float) y, color);
	}

	public static void drawCustomCenteredString(String text, float x, float y, int color) {
		ModuleButtons.drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawCustomCenteredStringWithShadow(String text, float x, float y, int color) {
		ModuleButtons.drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawCustomTotalCenteredString(String text, float x, float y, int color) {
		ModuleButtons.drawString(text, x - FontUtils.getCustonStringWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2, color);
	}

	public static void drawCustomTotalCenteredStringWithShadow(String text, float x, float y, int color) {
		ModuleButtons.drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2F, color);
	}
	
	public static void drawTitleString(String text, float x, float y, int color) {
		Title.drawString(text, x - Title.getWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2, color);
	}
	
}
