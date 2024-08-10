package cc.slack.features.modules.impl.movement.nowebs.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.nowebs.INoWeb;
import cc.slack.utils.player.MovementUtil;

public class VerusNoWeb implements INoWeb {

    @Override
    public void onUpdate(UpdateEvent event) {
        MovementUtil.strafe(1.00f);
        if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = 0.00D;
        }
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = 4.42D;
        }
        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -4.42D;
        }
    }

    @Override
    public String toString() {
        return "Verus";
    }
}
