package Hydro.module.modules.render;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

public class Animations extends Module {
    public Animations() {
        super("Animations", 0, true, Category.RENDER, "Custom sword animations");
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Spin");
        options.add("Exhibition");
        Client.settingsManager.rSetting(new Setting("AnimationsMode", "Mode", this, "Exhibition", options));
    }

}
