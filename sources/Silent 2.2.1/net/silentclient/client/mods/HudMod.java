package net.silentclient.client.mods;

import net.silentclient.client.Client;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;

import java.awt.*;

public class HudMod extends ModDraggable {
	
	final boolean allowCustomText;
	
	public HudMod(String name, ModCategory category, String icon) {
		this(name, category, icon, true);
	}
	
	public HudMod(String name, ModCategory category, String icon, boolean allowCustomText) {
		super(name, category, icon);
		this.allowCustomText = allowCustomText;
	}
	
	@Override
	public void setup() {
		setupWithAfterValue();
		this.addInputSetting("Text After Value", this, getDefautPostText(), true);
	}

	public void setupWithAfterValue() {
		super.setup();
		this.addBooleanSetting("Background", this, true);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Brackets", this, false);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Rounded Corners", this, false);
		this.addSliderSetting("Rounding Strength", this, 3, 1, 6, true);
	}
	
	public String getText() {
		return "";
	}
	
	public String getTextForRender() {
		return getText();
	}
	
	@Override
	public int getWidth() {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();
		
		if(background) {
			return (font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) > 64 ? font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) + 2 : 64);
		}
		
		return font.getStringWidth((brackets ? "[" : "") + getTextForRender() + (brackets ? "]" : ""));
	}

	public int getRealWidth() {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();

		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);

		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();

		if(background) {
			return (font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) > 64 ? font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) + 2 : 64);
		}

		return font.getStringWidth((brackets ? "[" : "") + getTextForRender() + (brackets ? "]" : ""));
	}

	@Override
	public int getHeight() {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		
		return (fancyFont ? 12 - (background ? 0 : 3) : this.font.FONT_HEIGHT) + (background ? fancyFont ? 2 : 5 : 0);
	}
	
	public void drawCenteredString(String text, float x2, float y2, int color, boolean shadow) {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
        font.drawString(text, (int) x2 - (font.getStringWidth(text) / 2), (int) y2, color, shadow);
	}

	@Override
	public boolean render(ScreenPosition pos) {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		boolean roundedCorners = Client.getInstance().getSettingsManager().getSettingByName(this, "Rounded Corners").getValBoolean();
		Color backgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Background Color").getValColor();
		boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();
		Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Color").getValColor();
		preRender();
		if(background) {
			if(roundedCorners) {
				RenderUtil.drawRound(getX(), getY(), this.getRealWidth(), this.getHeight(), Client.getInstance().getSettingsManager().getSettingByName(this, "Rounding Strength").getValInt(), backgroundColor);
			} else {
				RenderUtils.drawRect(getX(), getY(), this.getRealWidth(), this.getHeight(), backgroundColor.getRGB());
			}
		}

		if(!background) {
			font.drawString((brackets ? "[" : "") + getTextForRender() + (brackets ? "]" : ""), getTextX(), getTextY(), color.getRGB(), fontShadow);
		} else {
			font.drawString((brackets ? "[" : "") + getTextForRender() + (brackets ? "]" : ""), getTextX(), getTextY() + (this.getHeight() / 2 - (fancyFont ? 4 : this.font.FONT_HEIGHT / 2)) + 0.5F, color.getRGB(), fontShadow);
		}

		return true;
	}
	
	public String getPostText() {
		return Client.getInstance().getSettingsManager().getSettingByName(this, "Text After Value").getValString();
	}
	
	public String getDefautPostText() {
		return "";
	}

	public void preRender() {

	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

	public int getTextX() {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();

		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);

		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();

		if(!background) {
			return 0;
		} else {
			return 0 + (this.getRealWidth() / 2) - (font.getStringWidth((brackets ? "[" : "") + getTextForRender() + (brackets ? "]" : "")) / 2);
		}
	}

	public int getTextY() {
		return 0;
	}
}
