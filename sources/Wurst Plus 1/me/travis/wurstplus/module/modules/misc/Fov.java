package me.travis.wurstplus.module.modules.misc;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;

@Module.Info(name = "FOV Slider", category = Module.Category.MISC)
public class Fov extends Module {

    private Setting<Float> FOV = this.register(Settings.floatBuilder("FOV").withMinimum(90.0f).withValue(110.0f).withMaximum(200.0f).build());
    private float fov;

    @Override
    protected void onEnable() {
        this.fov = mc.gameSettings.fovSetting;
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.fovSetting = this.fov;
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || mc.world == null) {
            return;
        }
        mc.gameSettings.fovSetting = this.FOV.getValue();
    }

}