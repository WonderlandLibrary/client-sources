package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprint for you.", Category.MOVEMENT);
    }


    @Override
    public void onUpdate() {
        if(mc.player.movementInput.moveForward > 0 && !mc.player.isSneaking() && !mc.player.collidedHorizontally) {
            mc.player.setSprinting(true);
        }
    }
}
