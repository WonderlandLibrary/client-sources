package me.rich.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import org.lwjgl.opengl.GL11;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.math.MathHelper;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class TargetHUD extends Feature {
	
	private double healthBarWidth;
	private double healthBarWidth1;
	private double hudHeight;

	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER);
		ArrayList<String> thud = new ArrayList<String>();
		thud.add("Nursultan");
		thud.add("Celestial");
		thud.add("Akrien");
		Main.instance.settingsManager.rSetting(new Setting("TargetHUD Mode", this, "Nursultan", thud));
		Main.instance.settingsManager.rSetting(new Setting("PositionX", this, 360.0, 0.0, 500.0, true));
		Main.instance.settingsManager.rSetting(new Setting("PositionY", this, 150.0, 0.0, 170.0, true));
	}

	@EventTarget
	public void bloxa(Event2D sf) {
		ScaledResolution sr = new ScaledResolution(mc);
		boolean killaura = Main.moduleManager.getModule(KillAura.class).isToggled() && KillAura.target != null;
		String mode = Main.instance.settingsManager.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "TargetHUD Mode").getValString();
		if (mode.equalsIgnoreCase("Nursultan") && killaura) {
				final float scaledWidth = sr.getScaledWidth();
				final float scaledHeight = sr.getScaledHeight();
				final float x = scaledWidth / 2.0f - Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionX").getValFloat();
				final float y = scaledHeight / 2.0f + Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionY").getValFloat();
				final float health = KillAura.target.getHealth();
				double hpPercentage = health / KillAura.target.getMaxHealth();
				hpPercentage = MathHelper.clamp(hpPercentage, 0.0, 1.0);
				final double hpWidth = 141.0 * hpPercentage;
				final String healthStr = String.valueOf((int) KillAura.target.getHealth() / 2.0f);
				if (timerHelper.hasReached(15L)) {
					this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481);
					this.hudHeight = AnimationHelper.animate(40.0, this.hudHeight, 0.10000000149011612);
					timerHelper.reset();
				}

				Gui.drawRect(x + 85, y - 9, x + 230.0f, y + 41.0f, new Color(31, 31, 31, 255).getRGB());
				Gui.drawRect(x + 87, y + 32, x + 228.0f, y + 39.0f, new Color(40, 40, 40, 255).getRGB());
				RenderHelper.drawGradientSideways(x + 85.0f, y - 9, x + 127.0f, y - 8.5,
						(new Color(81, 149, 219, 255)).getRGB(), (new Color(180, 49, 218, 255)).getRGB());
				RenderHelper.drawGradientSideways(x + 127.0f, y - 9, x + 180.0f, y - 8.5,
						(new Color(180, 49, 218, 255)).getRGB(), (new Color(236, 93, 128, 255)).getRGB());
				RenderHelper.drawGradientSideways(x + 180.0f, y - 9, x + 230.0f, y - 8.5,
						(new Color(236, 93, 128, 255)).getRGB(), (new Color(235, 255, 0, 255)).getRGB());
				Gui.drawRect(x + 87.0f, y + 32.0f, x + 87.0f + this.healthBarWidth, y + 39.0f,
						new Color(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
								Main.getClientColor().getBlue() / 255.0f, 115.0f / 255.0f).getRGB());
				Gui.drawRect(x + 87.0f, y + 32.0f, x + 87.0f + hpWidth, y + 39.0f, Main.getClientColor().getRGB());
				Fonts.roboto_16.drawStringWithShadow(healthStr + " HP",
						x + 88.0f + 45.5f - Fonts.roboto_15.getStringWidth(healthStr) / 2.0f, y + 6.0f, -1);
				Fonts.roboto_16.drawStringWithShadow(KillAura.target.getName(), x + 126, y - 4f, -1);
				RenderHelper.renderItem(KillAura.target.getHeldItem(EnumHand.OFF_HAND), (int) x + 211,
						(int) (y) - (int) (2 / 6.0D) - 8);
				drawHead(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(KillAura.target.getUniqueID())
						.getLocationSkin(), (int) x + 87, (int) y - 7);
			} else {
				this.healthBarWidth = 92.0;
				this.hudHeight = 0.0;
			}
		
		if (mode.equalsIgnoreCase("Celestial") && killaura) {
				ScaledResolution sr1 = new ScaledResolution(mc);
				final float scaledWidth = sr1.getScaledWidth();
				final float scaledHeight = sr1.getScaledHeight();
				final float x = scaledWidth / 2.0f - Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionX").getValFloat();
				final float y = scaledHeight / 2.0f + Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionY").getValFloat();
				final float health = KillAura.target.getHealth();
				double hpPercentage = health / KillAura.target.getMaxHealth();
				hpPercentage = MathHelper.clamp(hpPercentage, 0.0, 1.0);
				final double hpWidth = 97.0 * hpPercentage;
				final String healthStr = String.valueOf((int) KillAura.target.getHealth() / 2.0f);
				if (timerHelper.hasReached(15L)) {
					this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481);
					this.hudHeight = AnimationHelper.animate(40.0, this.hudHeight, 0.10000000149011612);
					timerHelper.reset();
				}
				Gui.drawRect(x + 125.5, y - 9.5, x + 265, y + 30.5f, new Color(31, 31, 31, 255).getRGB());
				Gui.drawRect(x + 166.0f, y + 6.0f, x + 263.0f, y + 15.0f, new Color(40, 40, 40, 255).getRGB());
				Gui.drawRect(x + 166.0f, y + 6.0f, x + 166.0f + this.healthBarWidth, y + 15.0f,
						new Color(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
								Main.getClientColor().getBlue() / 255.0f, 115.0f / 255.0f).getRGB());
				Gui.drawRect(x + 166.0f, y + 6.0f, x + 166.0f + hpWidth, y + 15.0f, Main.getClientColor().getRGB());
				mc.fontRendererObj.drawStringWithShadow(healthStr,
						x + 128.0f + 46.0f - Fonts.roboto_15.getStringWidth(healthStr) / 2.0f, y + 19.5f, -1);
				mc.fontRendererObj.drawStringWithShadow("\u2764",
						x + 128.0f + 46.0f + Fonts.roboto_15.getStringWidth(healthStr), y + 19.5f,
						Main.getClientColor().getRGB());
				mc.fontRendererObj.drawStringWithShadow(KillAura.target.getName(), x + 167, y - 5.0f, -1);
				drawHead(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(KillAura.target.getUniqueID())
						.getLocationSkin(), (int) x + 127, (int) y - 8);
			} else {
				this.healthBarWidth = 92.0;
				this.hudHeight = 0.0;
			}
		
		if (mode.equalsIgnoreCase("Akrien") && killaura) {
				ScaledResolution sr1 = new ScaledResolution(mc);
				final float scaledWidth = sr1.getScaledWidth();
				final float scaledHeight = sr1.getScaledHeight();
				final float x = scaledWidth / 2.0f - Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionX").getValFloat();
				final float y = scaledHeight / 2.0f + Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetHUD.class), "PositionY").getValFloat();
				final float health = KillAura.target.getHealth();
				double hpPercentage = health / KillAura.target.getMaxHealth();
				hpPercentage = MathHelper.clamp(hpPercentage, 0.0, 1.0);
				final double hpWidth = 76 * hpPercentage;
				final String healthStr = String.valueOf((int) KillAura.target.getHealth() / 2.0f);
				if (timerHelper.hasReached(15L)) {
					this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481);
					this.hudHeight = AnimationHelper.animate(40.0, this.hudHeight, 0.10000000149011612);
					timerHelper.reset();
				}
				Gui.drawRect(x + 125.5, y - 9.5, x + 215, y + 17f, new Color(32, 31, 32, 255).getRGB());
				Gui.drawRect(x + 137f, y + 11.0f, x + 213f, y + 13f, new Color(40, 40, 40, 255).getRGB());
				Gui.drawRect(x + 137f, y + 11.0f, x + 137.0f + this.healthBarWidth, y + 13f,
						new Color(Main.getClientColor().getRed() / 255.0f, Main.getClientColor().getGreen() / 255.0f,
								Main.getClientColor().getBlue() / 255.0f, 115.0f / 255.0f).getRGB());
				Gui.drawRect(x + 137f, y + 11.0f, x + 137.0f + hpWidth, y + 13f, Main.getClientColor().getRGB());
				Fonts.roboto_15.drawStringWithShadow(healthStr + " HP",
						x + 106.5f + 46.0f - Fonts.roboto_15.getStringWidth(healthStr) / 2.0f, y + 3.5f, -1);
				Fonts.roboto_15.drawStringWithShadow(KillAura.target.getName(), x + 145, y - 5.0f, -1);
				Gui.drawRect(x + 126.5, y - 8.5, x + 142.5, y + 7.5f, Main.getClientColor().getRGB());
				mc.fontRendererObj.drawStringWithShadow("\u2764", x + 127.5f, y + 8.0f, -1);
				drawHead1(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(KillAura.target.getUniqueID())
						.getLocationSkin(), (int) x + 127, (int) y - 8);
			} else {
				this.healthBarWidth = 92.0;
				this.hudHeight = 0.0;
			}
		}
	

	public void drawHead(ResourceLocation skin, int width, int height) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(skin);
		Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 37, 37, 64.0f, 64.0f);
	}
	
	public void drawHead1(ResourceLocation skin, int width, int height) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(skin);
		Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 15, 15, 64.0f, 64.0f);
	}
}
