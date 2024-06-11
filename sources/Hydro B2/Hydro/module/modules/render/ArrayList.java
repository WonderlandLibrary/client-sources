package Hydro.module.modules.render;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

public class ArrayList extends Module {

    public ArrayList() {
        super("ArrayList", 0, false, Category.RENDER, "The module list on the HUD");
    }

    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("ArrayScale", "Scale", this, 2, 1, 10, false));
        Client.instance.settingsManager.rSetting(new Setting("ArrayBG", "Background", this, true));
        Client.instance.settingsManager.rSetting(new Setting("ArrayBar", "Bar", this, true));
        java.util.ArrayList<String> options = new java.util.ArrayList<>();
        options.add("Up");
        options.add("Down");
        Client.instance.settingsManager.rSetting(new Setting("ArrayDirMode", "Direction", this, "Down", options));
    }

    @Override
    public void onEnable() {
        this.enabled = false;

    }
}
