package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3i;


/**
 * created by noil on 11/7/19 at 7:33 PM
 * Updated 16 November 2019 by hub
 */
@Module.Info(name = "AutoQueueMain", category = Module.Category.HIDDEN, description = "Sends AutoQueueMain")
public class AutoQueueMain extends Module {

    private final Timer timer = new Timer();
    private Setting<Integer> delay = register(Settings.integerBuilder("Seconds Delay").withMinimum(0).withValue(9).withMaximum(90).build());
    private Setting<Boolean> info = register(Settings.b("Info Messages", false));

    @Override
    public void onUpdate() {

        // skip if delay not reached
        if (!shouldSendMessage(mc.player)) {
            return;
        }

        // info message
        if (info.getValue()) {
            Command.sendChatMessage("[AutoQueueMain] Sending message: /queue main");
        }

        // send the message
        mc.player.sendChatMessage("/queue main");

        // reset the timer
        timer.reset();

    }

    private boolean shouldSendMessage(EntityPlayer player) {

        // skip if not in the end
        if (player.dimension != 1) {
            return false;
        }

        // skip if timer not passed
        if (!timer.passed(delay.getValue() * 1000)) {
            return false;
        }

        // this is the position the 2b2t queue sets your entity at, ensure we are there
        return player.getPosition().equals(new Vec3i(0, 240, 0));

    }

    public static final class Timer {

        private long time;

        Timer() {
            time = -1;
        }

        boolean passed(double ms) {
            return System.currentTimeMillis() - time >= ms;
        }

        public void reset() {
            time = System.currentTimeMillis();
        }

    }

}
