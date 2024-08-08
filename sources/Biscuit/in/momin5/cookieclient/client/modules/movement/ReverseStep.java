package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;

public class ReverseStep extends Module {
    public ReverseStep(){
        super("ReverseStep", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (mc.player.onGround) {
            mc.player.motionY -= 1.0;
        }
    }
}
