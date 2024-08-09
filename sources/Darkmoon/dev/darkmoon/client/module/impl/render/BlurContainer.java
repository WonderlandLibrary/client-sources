package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;

import java.util.Arrays;

@ModuleAnnotation(name = "BlurContainer", category = Category.RENDER)
public class BlurContainer extends Module {
    public MultiBooleanSetting multiBooleanSetting = new MultiBooleanSetting("List of:", Arrays.asList("Visuals", "Other"));
    public NumberSetting blurVisual = new NumberSetting("Visual",20.0f, 5.0f, 50.0f, 1.0f, () -> multiBooleanSetting.get(0));
    public NumberSetting blurStrength = new NumberSetting("Other",20.0f, 5.0f, 50.0f, 1.0f, () -> multiBooleanSetting.get(1));
}
