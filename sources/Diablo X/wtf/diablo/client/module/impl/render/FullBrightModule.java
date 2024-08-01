package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "Full Bright", description = "Makes the world brighter.", category = ModuleCategoryEnum.RENDER)
public final class FullBrightModule extends AbstractModule {
    @Override
    protected void onEnable() {
        mc.gameSettings.gammaSetting = 1000;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.gammaSetting = 1;
        super.onDisable();
    }
}
