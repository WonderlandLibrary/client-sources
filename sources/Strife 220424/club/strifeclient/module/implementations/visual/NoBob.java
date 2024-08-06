package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;

@ModuleInfo(name = "NoBob", description = "Disables \"View Bobbing\".", category = Category.VISUAL)
public final class NoBob extends Module {

    @Override
    protected void onEnable() {
        super.onEnable();
        mc.gameSettings.viewBobbing = false;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.gameSettings.viewBobbing = true;
    }
}
