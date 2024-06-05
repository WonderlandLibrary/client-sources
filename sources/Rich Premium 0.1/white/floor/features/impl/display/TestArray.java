package white.floor.features.impl.display;

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
import white.floor.helpers.render.AnimationHelper;
import white.floor.helpers.render.Translate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class TestArray extends Feature {

    private static ScaledResolution scaledResolution = new ScaledResolution(mc);

    public TestArray() {
        super("TestArray", "new lol pop bob xipxop", 0, Category.DISPLAY);
        ArrayList<String> color = new ArrayList();
        color.add("Astolfo");
        color.add("Pinky");
        color.add("Blood");
        color.add("Toxic");
        ArrayList<String> sort = new ArrayList();
        sort.add("Width");
        sort.add("Minus");
        Main.settingsManager.rSetting(new Setting("List Color", this, "Astolfo", color));
        Main.settingsManager.rSetting(new Setting("Sort Mode", this, "Width", sort));
        Main.settingsManager.rSetting(new Setting("Background", this, true));
        Main.settingsManager.rSetting(new Setting("RightBorder", this, true));
        Main.settingsManager.rSetting(new Setting("Offset", this, 1, 1, 10, true));
        Main.settingsManager.rSetting(new Setting("TextY", this, 1, 1, 10, false));
    }

    @EventTarget
    public void Event2D(Event2D event2D) {

        this.setModuleName("TestArray §7[" + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "List Color").getValString() + "]");

        int count = 0;
        int yTotal = 0;
        for (int i = 0; i < Main.featureDirector.getFeatures().size(); ++i) {
            yTotal += Fonts.getFontRender().getHeight() + 3;
        }

        switch (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "Sort Mode").getValString()) {

            case "Width":
                Main.featureDirector.getFeatures().sort(Comparator.comparingInt(s -> -Fonts.getFontRender().getStringWidth(s.getModuleName())));
                break;

            case "Minus":
                Main.featureDirector.getFeatures().sort(Comparator.comparingInt(s -> Fonts.getFontRender().getStringWidth(s.getModuleName())));
                break;
        }

        for (Feature feature : Main.featureDirector.getFeatures()) {

            if (feature.isToggled()) {

                if(feature.slidex != Fonts.getFontRender().getStringWidth(feature.getModuleName())) {
                    feature.slidex = MathHelper.lerp(feature.slidex, Fonts.getFontRender().getStringWidth(feature.getModuleName()), 6 * deltaTime());
                }

                if (feature.slidey != Fonts.getFontRender().getHeight()) {
                    feature.slidey = AnimationHelper.animation((float) feature.slidey, Fonts.getFontRender().getHeight(), 0.1f);
                }

                double x = 0;

                if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "RightBorder").getValBoolean())
                    x = event2D.getWidth() - feature.slidex - 3;
                else
                    x = event2D.getWidth() - feature.slidex - 1;

                if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "Background").getValBoolean())
                    Gui.drawRect(x - 1, count, event2D.getWidth(), Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "Offset").getValDouble() + count + feature.slidey, new Color(0, 0, 0, 125).getRGB());

                if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "RightBorder").getValBoolean())
                    Gui.drawRect(event2D.getWidth() - 2, count, event2D.getWidth(), Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "Offset").getValDouble() + count + feature.slidey, Main.getClientColor(count, yTotal, 9).getRGB());

                Fonts.getFontRender().drawStringWithShadow(feature.getModuleName(), x, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "TextY").getValDouble() + count, Main.getClientColor(count, yTotal, 9).getRGB());

                count += feature.slidey + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TestArray.class), "Offset").getValDouble();
            }
        }
    }
}
