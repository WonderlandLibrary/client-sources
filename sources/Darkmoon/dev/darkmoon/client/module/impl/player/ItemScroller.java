package dev.darkmoon.client.module.impl.player;

import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "ItemScroller", category = Category.PLAYER)
public class ItemScroller extends Module {
    public static NumberSetting delay = new NumberSetting("Delay", 80, 0, 1000, 1);
}