package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.game.TickEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.render.FreeLookUtil;
import io.github.nevalackin.radbus.Listen;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "FreeLook",
        category = Category.PLAYER
)
public class FreeLook extends Module {

    private boolean freeLookingactivated;

    @Override
    public void onDisable() {
        freeLookingactivated = false;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onTick (TickEvent event) {
        if (mc.thePlayer.ticksExisted < 10) {
            stop();
        }
        if (Keyboard.isKeyDown(getKey())) {
            if (!freeLookingactivated) {
                freeLookingactivated = true;
                FreeLookUtil.enable();
                FreeLookUtil.cameraYaw += 180;
                mc.gameSettings.thirdPersonView = 1;
            }
        } else if (freeLookingactivated) {
            stop();
        }
    }

    private void stop() {
        toggle();
        FreeLookUtil.setFreelooking(false);
        freeLookingactivated = false;
        mc.gameSettings.thirdPersonView = 0;
    }
}
