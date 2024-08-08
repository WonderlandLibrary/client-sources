package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;

/**
 * Created 24 November 2019 by hub
 * Updated 25 November 2019 by hub
 */
@Module.Info(name = "Capes", description = "Show fancy Capes", category = Module.Category.HIDDEN)
public class Capes extends Module {

    private static Capes INSTANCE;

    public Capes() {
        INSTANCE = this;
    }

    public static boolean isActive() {
        return INSTANCE.isEnabled();
    }

}
