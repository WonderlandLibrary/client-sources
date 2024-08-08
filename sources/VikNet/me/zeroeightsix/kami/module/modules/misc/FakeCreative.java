package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.world.GameType;

/**
 * Created by hub on 29 June 2019.
 * Updated 25 November 2019 by hub
 */
@Module.Info(name = "FakeCreative", description = "Fake GMC", category = Module.Category.HIDDEN)
public class FakeCreative extends Module {

    @Override
    public void onEnable() {
        if (mc.player == null) {
            this.disable();
            return;
        }
        mc.playerController.setGameType(GameType.CREATIVE);
    }

    @Override
    public void onDisable() {
        if (mc.player == null) {
            return;
        }
        mc.playerController.setGameType(GameType.SURVIVAL);
    }

}
