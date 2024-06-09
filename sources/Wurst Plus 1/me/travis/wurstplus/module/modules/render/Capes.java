package me.travis.wurstplus.module.modules.render;

import me.travis.wurstplus.module.Module;

@Module.Info(name="Capes", category=Module.Category.RENDER)
public class Capes
extends Module {
    private static Capes INSTANCE;

    public Capes() {
        INSTANCE = this;
    }

    public static boolean isActive() {
        return INSTANCE.isEnabled();
    }
}