package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;

@ModuleInfo(label = "Memory Fix", category = ModuleCategory.OTHER)
public final class MemoryFix extends Module {

    public MemoryFix() {
        toggle();
        setHidden(true);
    }

    public static boolean cancelGarbageCollection() {
        return ModuleManager.getInstance(MemoryFix.class).isEnabled();
    }
}
