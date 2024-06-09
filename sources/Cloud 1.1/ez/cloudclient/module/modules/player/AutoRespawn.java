package ez.cloudclient.module.modules.player;

import ez.cloudclient.module.Module;

public class AutoRespawn extends Module {

    public AutoRespawn() {
        super("AutoRespawn", Category.COMBAT, "Automatically respawns you when you die");
    }

    @Override
    public void onTick() {
        if (mc.player.isDead) {
            mc.player.respawnPlayer();
        }
    }
}