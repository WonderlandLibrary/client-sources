package ez.cloudclient.module.modules.movement;

import ez.cloudclient.module.Module;

public class Flight extends Module {

    public Flight() {
        super("Flight", Category.MOVEMENT, "Creative Flight");
    }

    @Override
    protected void onEnable() {
        if (this.isEnabled()) {
            if (mc.player != null) {
                mc.player.capabilities.isFlying = true;
            }
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = false;
        }
    }

    @Override
    public void onTick() {
        mc.player.capabilities.isFlying = true;
    }
}
