package me.valk.utils.render;

import me.valk.utils.Wrapper;
import net.minecraft.client.gui.FontRenderer;

public class DefaultFontRenderer extends VitalFontRenderer {

	private FontRenderer fontRenderer;
	
	public DefaultFontRenderer() {
		fontRenderer = Wrapper.getFontRenderer();
	}
	
	@Override
	public int drawString(String text, float x, float y, int color) {
		text = text.replaceAll("Â", "");
		return fontRenderer.drawString(text, x, y, color);
	}
	
	@Override
	public int drawStringWithShadow(String text, float x, float y, int color) {
		text = text.replaceAll("Â", "");
		return fontRenderer.drawStringWithShadow(text, x, y, color);
	}
	
	@Override
	public int getStringWidth(String text) {
		text = text.replaceAll("Â", "");
		return fontRenderer.getStringWidth(text);
	}
	
	@Override
	public int getHeight() {
		return fontRenderer.FONT_HEIGHT;
	}
	
}
