package me.travis.wurstplus.module.modules.render;

import me.travis.wurstplus.module.Module;

@Module.Info(name = "NoHurtCam", category = Module.Category.RENDER)
public class NoHurtCam extends Module {

    private static NoHurtCam INSTANCE;

    public NoHurtCam() {
        INSTANCE = this;
    }

    public static boolean shouldDisable() {
        return INSTANCE != null && INSTANCE.isEnabled();
    }

}
