package ez.cloudclient.module.modules.movement;

import ez.cloudclient.module.Module;
import ez.cloudclient.setting.settings.BooleanSetting;

public class FastStop extends Module {

    public FastStop() {
        super("FastStop", Category.MOVEMENT, "Brings you to a stop instantly");
        this.settings.addSetting("Air Stop", new BooleanSetting(false));
    }

    @Override
    public void onTick() {
        if (!mc.gameSettings.keyBindForward.isPressed() && !mc.gameSettings.keyBindBack.isPressed() && !mc.gameSettings.keyBindLeft.isPressed() && !mc.gameSettings.keyBindRight.isPressed()) {
            if (mc.player.onGround) {
                mc.player.setVelocity(0, mc.player.motionY, 0);
            } else if (this.settings.getSetting("Air Stop", BooleanSetting.class).getValue() && !mc.player.onGround) {
                mc.player.setVelocity(0, mc.player.motionY, 0);
            }
        }
    }
}
