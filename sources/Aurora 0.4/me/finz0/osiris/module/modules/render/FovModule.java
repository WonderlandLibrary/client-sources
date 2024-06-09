package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class FovModule extends Module {
    public FovModule() {
        super("FOV", Category.RENDER, "Changes your fov");
        AuroraMod.getInstance().settingsManager.rSetting(fov = new Setting("Value", this, 90, 0, 180, true, "FovModValue"));
        setDrawn(false);
    }

    Setting fov;

    public void onUpdate(){
        mc.gameSettings.fovSetting = (float)fov.getValInt();
    }
}
