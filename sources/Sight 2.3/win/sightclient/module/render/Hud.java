package win.sightclient.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ColorSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.TextboxSetting;

public class Hud extends Module {

	private ModeSetting color = new ModeSetting("Color", this, new String[] {"Sight", "Rainbow", "Static", "Astolfo"});
	private ModeSetting watermark = new ModeSetting("Watermark", this, new String[] {"Logo", "Text", "None"});
	private TextboxSetting watermarktext = new TextboxSetting("Water Mark Text", this, "Sight " + Sight.instance.version);
	private ModeSetting fontType = new ModeSetting("Font", this, new String[] {"SFUI", "Arial", "Minecraft"});
	private NumberSetting colorDif = new NumberSetting("ColorDifference", this, 6, 1, 20, false);
	private NumberSetting fontSize = new NumberSetting("FontSize", this, 20, 10, 22, true);
	private NumberSetting backAlpha = new NumberSetting("BackAlpha", this, 0.65F, 0F, 1F, false);
	private BooleanSetting border = new BooleanSetting("Border", this, true);
	private ColorSetting backcolor = new ColorSetting("BackColor", this, Color.black);
	private ColorSetting textcolor = new ColorSetting("TextColor", this, Color.orange);
	
	public Hud() {
		super("HUD", Category.RENDER);
		this.setToggled(true);
	}
	
	@Override
	public void updateSettings() {
		this.watermarktext.setVisible(this.watermark.getValue().equalsIgnoreCase("text"));
		this.fontSize.setVisible(!this.fontType.getValue().equalsIgnoreCase("Minecraft"));
		this.textcolor.setVisible(this.color.getValue().equals("Static"));
		this.colorDif.setVisible(!this.textcolor.isVisible());
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventRender2D) {
			if (this.watermark.getValue().equalsIgnoreCase("Logo")) {
				this.getFont().drawStringWithShadow(Sight.instance.version, 79, 21, -1);
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("sight/sight.png"));
				GlStateManager.enableAlpha();
				Gui.drawModalRectWithCustomSizedTexture(-32, -22, 0, 0, 1280 / 8, 720 / 8, 1280 / 8, 720 / 8);
				GlStateManager.disableAlpha();
			} else if (this.watermark.getValue().equalsIgnoreCase("Text") && !this.watermarktext.getValue().isEmpty()) {
				if (!this.fontType.getValue().equalsIgnoreCase("Minecraft")) {
					this.getFont().drawStringWithShadow(this.watermarktext.getValue().charAt(0) + (EnumChatFormatting.WHITE + this.watermarktext.getValue().substring(1)), 4, 4, new Color(255, 100, 0).getRGB());
				} else {
					mc.fontRendererObj.drawStringWithShadow(this.watermarktext.getValue().charAt(0) + (EnumChatFormatting.WHITE + this.watermarktext.getValue().substring(1)), 4, 4, new Color(255, 100, 0).getRGB());
				}
			}
			
			float animSpeed = 0.1f * (60F / (float)Minecraft.getMinecraft().getDebugFPS());
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			int yOffset = !this.fontType.getValue().equalsIgnoreCase("Minecraft") ? 1 : 2;
			ArrayList<Module> modules = new ArrayList<Module>(Sight.instance.mm.getModules());
			if (!this.fontType.getValue().equalsIgnoreCase("Minecraft")) {
				Collections.sort(modules, new Comparator<Module>() {
					@Override
					public int compare(Module m1, Module m2) {
						if (getFont().getStringWidth(m1.getDisplayName()) > getFont().getStringWidth(m2.getDisplayName())) {
							return -1;
						}
						if (getFont().getStringWidth(m1.getDisplayName()) < getFont().getStringWidth(m2.getDisplayName())) {
							return 1;
						}
						return 0;
					}
				});
			} else {
				Collections.sort(modules, new Comparator<Module>() {
					@Override
					public int compare(Module m1, Module m2) {
						if (mc.fontRendererObj.getStringWidth(m1.getDisplayName()) > mc.fontRendererObj.getStringWidth(m2.getDisplayName())) {
							return -1;
						}
						if (mc.fontRendererObj.getStringWidth(m1.getDisplayName()) < mc.fontRendererObj.getStringWidth(m2.getDisplayName())) {
							return 1;
						}
						return 0;
					}
				});
			}
			ArrayList<Module> correctModules = new ArrayList<Module>();
			for (Module m : modules) {
				if (m.isToggled() && !m.isHidden()) {
					m.renderSlide += animSpeed;
					if (m.renderSlide > 1) {
						m.renderSlide = 1;
					}
				} else {
					m.renderSlide -= animSpeed;
					if (m.renderSlide < 0) {
						m.renderSlide = 0;
					}
				}
				if (m.renderSlide > 0F && m.getCategory() != Category.TARGETS) {
					correctModules.add(m);
				}
			}
			int maxY = yOffset;
			for (Module m : correctModules) {
				if (!this.fontType.getValue().equalsIgnoreCase("Minecraft")) {
					maxY += (Math.floor(getFont().getHeight(m.getDisplayName())) + 1) * m.renderSlide;
				} else {
					maxY += (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide;
				}
			}
			for (Module m : correctModules) {
				int index = correctModules.indexOf(m);
				int color = this.getColor(yOffset, maxY);
				if (!this.fontType.getValue().equalsIgnoreCase("Minecraft")) {
					if (m == correctModules.get(0)) {
						Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 2)), yOffset - 2, sr.getScaledWidth(), (yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 1) * m.renderSlide), getBackColor());
					} else {
						Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 2)), yOffset, sr.getScaledWidth(), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 1) * m.renderSlide, getBackColor());
					}
					if (this.border.getValue()) {
						if (index != 0) {
							Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 3)), yOffset, sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 2)), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 2) * m.renderSlide, color);
						} else {
							Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 3)), 0, sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 2)), yOffset + (getFont().getHeight(m.getDisplayName()) + 1.5) * m.renderSlide, color);
						}
						if (index == correctModules.size() - 1) {
							Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 3)), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 1) * m.renderSlide, sr.getScaledWidth(), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 2) * m.renderSlide, color);
						} else {
							Gui.drawRect(sr.getScaledWidth() - ((getFont().getStringWidth(m.getDisplayName()) + 2)), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 1) * m.renderSlide, sr.getScaledWidth() - ((getFont().getStringWidth(correctModules.get(index + 1).getDisplayName()) + 2)), yOffset + (Math.floor(getFont().getHeight(m.getDisplayName())) + 2) * m.renderSlide, color);
						}
					}
					yOffset += (getFont().getHeight(m.getDisplayName()) + 1) * m.renderSlide;
				} else {
					if (m == correctModules.get(0)) {
						Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 4)), yOffset - 2, sr.getScaledWidth(), (yOffset + (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide), getBackColor());
					} else {
						Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 4)), yOffset, sr.getScaledWidth(), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide, getBackColor());
					}
					if (this.border.getValue()) {
						if (index != 0) {
							Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 5)), yOffset, sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 4)), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 2) * m.renderSlide, color);
						} else {
							Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 5)), 0, sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 4)), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 2) * m.renderSlide, color);
						}
						if (index == correctModules.size() - 1) {
							Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 5)), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide, sr.getScaledWidth(), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 2) * m.renderSlide, color);
						} else {
							Gui.drawRect(sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(m.getDisplayName()) + 4)), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide, sr.getScaledWidth() - ((mc.fontRendererObj.getStringWidth(correctModules.get(index + 1).getDisplayName()) + 5)), yOffset + (mc.fontRendererObj.FONT_HEIGHT + 2) * m.renderSlide, color);
						}
					}
					yOffset += (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide;
				}
			}
			yOffset = !this.fontType.getValue().equalsIgnoreCase("Minecraft") ? 1 : 2;
			for (Module m : correctModules) {
				int color = this.getColor(yOffset, maxY);
				Color toSet = new Color(color);
				Color toSet2 = new Color(toSet.getRed(), toSet.getGreen(), toSet.getBlue(), (int)(m.renderSlide * 255F));
				if (!this.fontType.getValue().equalsIgnoreCase("Minecraft")) {
					getFont().drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - (getFont().getStringWidth(m.getDisplayName()) * m.renderSlide), 0 + yOffset, toSet2.getRGB());
					yOffset += (getFont().getHeight(m.getDisplayName()) + 1) * m.renderSlide;
				} else {
					mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - (mc.fontRendererObj.getStringWidth(m.getDisplayName())  * m.renderSlide) - 1, 0 + yOffset, toSet2.getRGB());
					yOffset += (mc.fontRendererObj.FONT_HEIGHT + 1) * m.renderSlide;
				}
			}
		}
	}
	
	private TTFFontRenderer customFont;
	private String lastFont = "";
	
	public TTFFontRenderer getFont() {
		String set = this.fontType.getValue() + " " + fontSize.getValueInt();
		if (!set.equals(lastFont) || customFont == null) {
			this.lastFont = set;
			customFont = Sight.instance.fm.getFont(set);
		}
		return customFont;
	}
	
	public int getBackColor() {
		return new Color(this.backcolor.getValue().getRed(), this.backcolor.getValue().getGreen(), this.backcolor.getValue().getBlue(), (int)(this.backAlpha.getValueFloat() * 255F)).getRGB();
	}
	
	public int getColor(int yOffset, int maxY) {
		float hue = 0;
		float speed;
		switch (this.color.getValue()) {
			case "Sight":
				speed = 3000f;
				hue = (float) (System.currentTimeMillis() % (int)speed) + (yOffset * colorDif.getValueFloat());
				while (hue > speed) {
					hue -= speed;
				}
				hue /= speed;
				if (hue > 0.5) {
					hue = 0.5F - (hue - 0.5f);
				}
				return Color.HSBtoRGB(hue, 0.8F, 1F);
			case "Astolfo":
				speed = 3000f;
				hue = (float) (System.currentTimeMillis() % (int)speed) + ((maxY - yOffset) * colorDif.getValueFloat());
				while (hue > speed) {
					hue -= speed;
				}
				hue /= speed;
				if (hue > 0.5) {
					hue = 0.5F - (hue - 0.5f);
				}
				hue += 0.5F;
				return Color.HSBtoRGB(hue, 0.5F, 1F);
			case "Static":
				return this.textcolor.getValue().getRGB();
			case "Rainbow":
				speed = 3000f;
				hue = (float) (System.currentTimeMillis() % (int)speed) + (yOffset * colorDif.getValueFloat());
				while (hue > speed) {
					hue -= speed;
				}
				hue /= speed;
				return Color.HSBtoRGB(hue, 0.8F, 1F);
		}
		return 0;
	}
}
