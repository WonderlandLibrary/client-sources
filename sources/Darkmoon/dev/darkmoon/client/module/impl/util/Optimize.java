package dev.darkmoon.client.module.impl.util;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;

import java.util.Arrays;

@ModuleAnnotation(name = "Optimize", category = Category.UTIL)
public class Optimize extends Module {
    public MultiBooleanSetting booleanSetting = new MultiBooleanSetting("Type", Arrays.asList("Grass", "Shadow", "Particles"));
    @Override
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
        super.onDisable();
    }
}
