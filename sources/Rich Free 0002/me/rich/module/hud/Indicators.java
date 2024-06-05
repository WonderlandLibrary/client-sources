package me.rich.module.hud;

import java.awt.Color;
import java.util.ArrayList;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

public class Indicators extends Feature {
    private double cooldownBarWidth;
    private double cooldownHeight;

    private double hurttimeBarWidth;
    private double hurtHeight;
    
    private double bpsBarWidth;
    private double bpsHeight;
    
    private double hpBarWidth;
	private double healthBarWidth;

    public Indicators() {
        super("Indicators", 0, Category.HUD);
        Main.settingsManager.rSetting(new Setting("IndicatorX", this, 100, 0, 480, true));
        Main.settingsManager.rSetting(new Setting("IndicatorY", this, 60, 60, 400, true));
    }

    @EventTarget
    public void goroda(Event2D mamanooma) {
    	
        ScaledResolution sr = new ScaledResolution(mc);
        final float scaledWidth = sr.getScaledWidth();
        final float scaledHeight = sr.getScaledHeight();
        
        final float x = scaledWidth / 2.0f - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Indicators.class), "IndicatorX").getValFloat();
        final float y = scaledHeight / 2.0f + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Indicators.class), "IndicatorY").getValFloat();

    	double prevZ = mc.player.posZ - mc.player.prevPosZ;
		double prevX = mc.player.posX - mc.player.prevPosX;
		double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
		double currSpeed = lastDist * 15.3571428571D / 4;
        
        // &&
        RenderHelper.drawNewRect(x + 4.5, y  + 196.5 - 405, x + 100.5, y + 246.5 - 408, new Color(11, 11, 11, 255).getRGB());
        RenderHelper.drawNewRect(x + 5, y + 198 - 405, x + 100, y + 246 - 408, new Color(28, 28, 28, 255).getRGB());
        RenderHelper.drawNewRect(x + 5, y  + 198 - 405, x + 100, y + 208 - 408, new Color(21, 19, 20, 255).getRGB());
        RenderHelper.drawNewRect(x + 44, y + 210 - 406, x + 95, y + 213.5 - 406, new Color(41, 41, 41, 255).getRGB());
        RenderHelper.drawNewRect(x + 44, y + 219 - 406, x + 95, y + 222.5 - 406, new Color(41, 41, 41, 255).getRGB());
        RenderHelper.drawNewRect(x + 44, y + 228 - 406, x + 95, y + 231.5 - 406, new Color(41, 41, 41, 255).getRGB());
        RenderHelper.drawNewRect(x + 44, y + 237 - 406, x + 95, y + 240.5 - 406, new Color(41, 41, 41, 255).getRGB());
        RenderHelper.drawNewRect(x + 5, y + 197 - 405, x + 100, y + 198 - 405, Main.getClientColor().getRGB());

        Fonts.smallestpixel_14.drawString("INDICATORS", x + 37, y + 202 - 406, -1);
        
        // Cooldown
        double cooldownPercentage = MathHelper.clamp(mc.player.getCooledAttackStrength(1), 0.0, 1.0);
        final double cooldownWidth = 51 * cooldownPercentage;
        this.cooldownBarWidth = AnimationHelper.animate(cooldownWidth, this.cooldownBarWidth, 0.0229999852180481);
        Gui.drawRect(x + 44, y + 210 - 406, x + 44 + this.cooldownBarWidth, y + 213.5 - 406, Main.getClientColor().getRGB());
        Fonts.smallestpixel_14.drawString("COOLDOWN", x + 8, y + 211 - 406, -1);

        // HurtTime
        double hurttimePercentage = MathHelper.clamp(mc.player.hurtTime, 0.0, 0.6);
        final double hurttimeWidth = 51.0 * hurttimePercentage;
        this.hurttimeBarWidth = AnimationHelper.animate(hurttimeWidth, this.hurttimeBarWidth, 0.0429999852180481);
        Gui.drawRect(x + 44, y + 219 - 406, x + 44 + this.hurttimeBarWidth, y + 222.5 - 406, Main.getClientColor().getRGB());
        Fonts.smallestpixel_14.drawString("HURTTIME", x + 8, y + 220 - 406, -1);
    
    // HurtTime
    double bpsPercentage = MathHelper.clamp(currSpeed, 0.0, 1.0);
    final double bpsBarWidth = 51.0 * bpsPercentage;
    this.bpsBarWidth = AnimationHelper.animate(bpsBarWidth, this.bpsBarWidth, 0.0329999852180481);
    Gui.drawRect(x + 44, y + 228 - 406, x + 44 + this.bpsBarWidth, y + 231.5 - 406, Main.getClientColor().getRGB());
    Fonts.smallestpixel_14.drawString("BPS", x + 8, y + 229 - 406, -1);
    
    // HurtTime
	final float health = mc.player.getHealth();
	double hpPercentage = health / mc.player.getMaxHealth();
	hpPercentage = MathHelper.clamp(hpPercentage, 0.0, 1.0);
	final double hpWidth = 51.0 * hpPercentage;
	final String healthStr = String.valueOf((int) mc.player.getHealth() / 2.0f);
	if (timerHelper.hasReached(15L)) {
		this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.2029999852180481);
		timerHelper.reset();
	}
    Gui.drawRect(x + 44, y + 237 - 406, x + 44 + this.healthBarWidth, y + 240.5 - 406, Main.getClientColor().getRGB());
    Fonts.smallestpixel_14.drawString("HP", x + 8, y + 238 - 406, -1);
    }
}