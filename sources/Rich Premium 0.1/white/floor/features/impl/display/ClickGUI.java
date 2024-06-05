package white.floor.features.impl.display;

import csgogui.CSGOGui;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import java.awt.*;
import java.util.ArrayList;

public class ClickGUI extends Feature {

    public static int opacite;
    public static double animateoneheight;
    public static double animateonewidth;

    public ClickGUI() {
        super("ClickGUI", "Cheat menu.", Keyboard.KEY_RSHIFT, Category.DISPLAY);
        ArrayList<String> hui = new ArrayList<String>();
        hui.add("Panel");
        hui.add("CSGO");
        ArrayList<String> theme = new ArrayList<String>();
        theme.add("Dark");
        theme.add("White");
        theme.add("Blue");
        Main.settingsManager.rSetting(new Setting("ClickGui Mode", this, "CSGO", hui));
        Main.settingsManager.rSetting(new Setting("Theme Mode", this, "Dark", theme));
    }

    public void onEnable() {
        super.onEnable();

        String hui = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(ClickGUI.class), "ClickGui Mode").getValString();

        if(hui.equalsIgnoreCase("CSGO"))
        mc.displayGuiScreen(Main.csgoGui);
        else
            mc.displayGuiScreen(Main.clickGui1);

        mc.gameSettings.guiScale = 2;
        this.toggle();
    }

    @Override
    public void onDisable() {
        opacite = 0;
        animateoneheight = 0;
        animateonewidth = 0;
        CSGOGui.bg = 0;
        CSGOGui.anim = 0;
        super.onDisable();
    }
}
