package club.mega.module.impl.player;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "AutoRespawn", description = "Auto. respawn", category = Category.PLAYER)
public class AutoRespawn extends Module {

    @Handler
    public final void tick(final EventTick event) {
        if (!MC.thePlayer.isEntityAlive())
            MC.thePlayer.respawnPlayer();
    }

}
