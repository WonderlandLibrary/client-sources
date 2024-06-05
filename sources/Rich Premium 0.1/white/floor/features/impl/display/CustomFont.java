package white.floor.features.impl.display;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;

import java.util.ArrayList;

public class CustomFont extends Feature {
    public CustomFont() {
        super("CustomFont", "fdsfsd", 0, Category.DISPLAY);
        ArrayList<String> fontList = new ArrayList<>();
        fontList.add("Myseo");
        fontList.add("Sfui");
        fontList.add("Urw");
        Main.settingsManager.rSetting(new Setting("FontList", this, "Myseo", fontList));}

    @Override
    public void onEnable() {
        toggle();
        super.onEnable();
    }
}

