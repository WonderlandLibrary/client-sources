package dev.darkmoon.client.module.impl.util;

import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "BetterTab", category = Category.UTIL)
public class BetterTab extends Module {
    public static BooleanSetting animation = new BooleanSetting("Animation", true);
}
