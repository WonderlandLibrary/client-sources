package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;

/**
 * Created by TimG on 16 June 2019
 * Last Updated 16 June 2019 by hub
 */
@Module.Info(name = "GMFly", category = Module.Category.HIDDEN, description = "Godmode Fly")
public class GMFly extends Module {

    @Override
    public void onEnable() {
        toggleFly(true);
    }

    @Override
    public void onDisable() {
        toggleFly(false);
    }

    @Override
    public void onUpdate() {
        toggleFly(true);
    }

    private void toggleFly(boolean b) {
        mc.player.capabilities.isFlying = b;
        if (mc.player.capabilities.isCreativeMode) {
            return;
        }
        mc.player.capabilities.allowFlying = b;
    }

}
