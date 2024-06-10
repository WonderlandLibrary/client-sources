package me.dinozoid.strife.module.implementations.visuals;

import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;

@ModuleInfo(name = "NoBob", renderName = "NoBob", description = "Disable bobbing.", aliases = "NoBobbing", category = Category.VISUALS)
public class NoBobModule extends Module {

    @Override
    public void onEnable() {
        mc.gameSettings.viewBobbing = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.viewBobbing = true;
        super.onDisable();
    }
}
