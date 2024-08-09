package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "ViewModel", category = Category.RENDER)
public class ViewModel extends Module {
    public static NumberSetting rightX = new NumberSetting("Right X", 0, -2, 2, 0.1F);
    public static NumberSetting rightY = new NumberSetting("Right Y", 0, -2, 2, 0.1F);
    public static NumberSetting rightZ = new NumberSetting("Right Z", 0, -2, 2, 0.1F);
    public static NumberSetting leftX = new NumberSetting("Left X", 0, -2, 2, 0.1F);
    public static NumberSetting leftY = new NumberSetting("Left Y", 0, -2, 2, 0.1F);
    public static NumberSetting leftZ = new NumberSetting("Left Z", 0, -2, 2, 0.1F);
    public static BooleanSetting onlyAura = new BooleanSetting("Only Aura", false);
    public static NumberSetting size = new NumberSetting("Size", 1, 0.1F, 1, 0.05F);
}
