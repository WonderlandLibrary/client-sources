package Hydro.module.modules.cosmetics;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

public class Cape extends Module {

    public Cape() {
        super("Cape", 0, false, Category.COSMETICS, "Custom capes");
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Hydro");
        Client.instance.settingsManager.rSetting(new Setting("CapeMode", "Design", this, "Hydro", options));
    }
}
