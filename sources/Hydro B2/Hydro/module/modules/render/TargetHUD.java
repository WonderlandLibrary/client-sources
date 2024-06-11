package Hydro.module.modules.render;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

public class TargetHUD extends Module {

    public TargetHUD() {
        super("TargetHUD", 0, true, Category.RENDER, "Displays information about the aura target");
        Client.settingsManager.rSetting(new Setting("TargetHUDX", "X", this, mc.displayWidth / 2, 1, mc.displayWidth, true));
        Client.settingsManager.rSetting(new Setting("TargetHUDY", "Y", this, mc.displayHeight / 2 - 40, 1, mc.displayHeight, true));
    }

    //Code in aura class

}
