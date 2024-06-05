package white.floor.features.impl.display;

import java.awt.Color;
import java.util.ArrayList;

import clickgui.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event2D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;
import white.floor.helpers.render.AnimationHelper;

public class Indicators extends Feature {
    private double cooldownBarWidth;
    private double hurttimeBarWidth;

    public Indicators() {
        super("Indicators", "Show ur cooldown && hurttime and more.", 0, Category.DISPLAY);
        Main.settingsManager.rSetting(new Setting("IndicatorX", this, 100, 0, 480, true));
        Main.settingsManager.rSetting(new Setting("IndicatorY", this, 60, 60, 400, true));
    }

    @EventTarget
    public void goroda(Event2D mamanooma) {

        ScaledResolution sr = new ScaledResolution(mc);
        String theme = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(ClickGUI.class), "Theme Mode").getValString();

        final float scaledWidth = sr.getScaledWidth();
        final float scaledHeight = sr.getScaledHeight();

        final float x = scaledWidth / 2.0f - Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Indicators.class), "IndicatorX").getValFloat();
        final float y = scaledHeight / 2.0f + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Indicators.class), "IndicatorY").getValFloat();
        int colortext = -1;
        int colortext1 = -1;
        int color = -1;
        int colorrect = -1;
        int colorrectnext = -1;

        if(theme.equalsIgnoreCase("White")) {
            color = new Color(60, 61, 60).getRGB();
            colortext = new Color(245, 245, 245, 155).getRGB();
            colortext1 = new Color(245, 245, 245, 145).getRGB();
            colorrect = new Color(91, 91, 91, 255).getRGB();
            colorrectnext = new Color(131, 131, 131).getRGB();
        } else if(theme.equalsIgnoreCase("Dark")) {
            color = -1;
            colortext = new Color(33, 33, 33, 215).getRGB();
            colortext1 = new Color(33, 33, 33, 145).getRGB();
            colorrect = new Color(194, 194, 194, 255).getRGB();
            colorrectnext = new Color(133, 133, 133).getRGB();

        } else if(theme.equalsIgnoreCase("Blue")) {
            color = -1;
            colortext = new Color(9, 144, 255, 215).getRGB();
            colortext1 = new Color(60, 135, 250, 145).getRGB();
            colorrect = new Color(19, 113, 253, 255).getRGB();
            colorrectnext = new Color(0, 165, 255).getRGB();
        }



        // &&
        DrawHelper.drawNewRect(x + 4.5, y + 196.5 - 403, x + 100.5, y + 246.5 - 445, colortext);
        DrawHelper.drawNewRect(x + 5, y + 199.5 - 398, x + 100, y + 246 - 424, colortext1);
        DrawHelper.drawNewRect(x + 46, y + 210 - 404, x + 97, y + 213.5 - 404, new Color(41, 41, 41, 255).getRGB());
        DrawHelper.drawNewRect(x + 46, y + 219 - 404, x + 97, y + 222.5 - 404, new Color(41, 41, 41, 255).getRGB());
        DrawHelper.drawGradientRect(x + 5, y + 246.5 - 445, x + 100, y + 248.5 - 445, new Color(1, 1, 1, 90).getRGB(), new Color(0,0,0,0).getRGB());

        Fonts.sfui14.drawString("Indicators", x + 6, y + 202 - 406, color);

        // Cooldown.
        double cooldownPercentage = MathHelper.clamp(mc.player.getCooledAttackStrength(1), 0.0, 1.0);
        final double cooldownWidth = 51 * cooldownPercentage;
        this.cooldownBarWidth = AnimationHelper.animate(cooldownWidth, this.cooldownBarWidth, 0.0229999852180481);
        Fonts.sfui14.drawString("Cooldown", x + 8, y + 211 - 405, color);
        DrawHelper.drawGradientRect(x + 46, y + 210 - 404, x + 46 + this.cooldownBarWidth, y + 213.5 - 404, colorrect, colorrectnext);

        // HurtTime.
        double hurttimePercentage = MathHelper.clamp(mc.player.hurtTime, 0.0, 0.6);
        final double hurttimeWidth = 51.0 * hurttimePercentage;
        this.hurttimeBarWidth = AnimationHelper.animate(hurttimeWidth, this.hurttimeBarWidth, 0.0429999852180481);
        DrawHelper.drawGradientRect(x + 46, y + 219 - 404, x + 46 + this.hurttimeBarWidth, y + 222.5 - 404, colorrect, colorrectnext);
        Fonts.sfui14.drawString("HurtTime", x + 8, y + 220 - 405, color);

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}