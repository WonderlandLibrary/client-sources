package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

/**
 * Created by TimG on 16 June 2019
 * Last Updated 16 June 2019 by hub
 */
@Module.Info(name = "GMSpeed", category = Module.Category.HIDDEN, description = "Godmode Speed")
public class GMSpeed extends Module {

    private Setting<Double> gmspeed = register(Settings.doubleBuilder("Speed").withRange(0.1, 10.0).withValue(1.0).build());

    @Override
    public void onUpdate() {
        if ((mc.player.moveForward != 0 || mc.player.moveStrafing != 0)
                && !mc.player.isSneaking() && mc.player.onGround) {
            mc.player.motionX *= gmspeed.getValue();
            mc.player.motionZ *= gmspeed.getValue();
        }
    }

}
