package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;

@ModuleInfo(label = "No Hurt Camera", category = ModuleCategory.RENDER)
public final class NoHurtCamera extends Module {

    public static boolean isEnabledCached() {
        return ModuleManager.getInstance(NoHurtCamera.class).isEnabled();
    }
}
