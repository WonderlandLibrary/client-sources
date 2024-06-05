package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

import java.util.ArrayList;

public class Cosmetics extends Feature {
    private final String CAPEMODE = "Cape Mode";
    private final String WINGMODE = "Wing Mode";
    private final String CAPE = "Cape";
    private final String WING = "Wing";
    private final String SCALE = "Scale";

    public static boolean cape;
    public static boolean wing;
    public static double scale;
    public static String capepng;
    public static String wingpng;

    public Cosmetics() {
        super("Cosmetics", "wings!",0, Category.VISUALS);
        ArrayList<String> wing = new ArrayList<>();
        ArrayList<String> cape = new ArrayList<String>();
        cape.add("Pink");
        cape.add("Cape");
        wing.add("White");
        wing.add("Gray");
        Main.instance.settingsManager.rSetting(new Setting(CAPEMODE, this, "Pink", cape));
        Main.instance.settingsManager.rSetting(new Setting(WINGMODE, this, "Gray", wing));
        Main.settingsManager.rSetting(new Setting(WING, this, false));
        Main.settingsManager.rSetting(new Setting(CAPE, this, false));
        Main.settingsManager.rSetting(new Setting(SCALE, this, 0.30, 0.40, 2.00, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        cape = Main.settingsManager.getSettingByName(CAPE).getValBoolean();
        wing = Main.settingsManager.getSettingByName(WING).getValBoolean();
        scale = Main.settingsManager.getSettingByName(SCALE).getValFloat();
        capepng = Main.settingsManager.getSettingByName(CAPEMODE).getValString();
        wingpng = Main.settingsManager.getSettingByName(WINGMODE).getValString();
        this.setModuleName("Cosmetics §7[" + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Cosmetics.class), SCALE).getValFloat() + "]");
    }
}
