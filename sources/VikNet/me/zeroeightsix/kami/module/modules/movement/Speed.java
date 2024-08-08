package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

/**
 * Created by TimG on 11 June 2019.
 * Updated 24 November 2019 by hub
 */
@Module.Info(name = "Speed", description = "Modify player speed on ground.", category = Module.Category.HIDDEN)
public class Speed extends Module {

    private Setting<Float> speed = register(Settings.floatBuilder("Speed").withMinimum(0.0f).withValue(1.4f).withMaximum(10.0f).build());

    @Override
    public void onUpdate() {
        if ((mc.player.moveForward != 0 || mc.player.moveStrafing != 0) && !mc.player.isSneaking() && mc.player.onGround) {
            mc.player.jump();
            mc.player.motionX *= speed.getValue();
            mc.player.motionY *= 0.4;
            mc.player.motionZ *= speed.getValue();
        }
    }

}
