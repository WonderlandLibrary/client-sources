package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.BooleanSetting;

@ModuleInfo(name = "NoFov", description = "Disables FOV change.", aliases = "Fov", category = Category.VISUAL)
public final class NoFov extends Module {
    public final BooleanSetting noSpeedSetting = new BooleanSetting("No Speed", true);
}
