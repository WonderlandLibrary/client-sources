// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.vanilla;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.client.settings.GameSettings;

public class LegitSpeed implements ISpeed {

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindJump.pressed = mc.thePlayer.offGroundTicks != 1;

        if (mc.thePlayer.offGroundTicks == 1) PrintUtil.message(Float.toString(MovementUtil.getSpeed()));
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
    }

    @Override
    public String toString() {
        return "Legit";
    }
}
