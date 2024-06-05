package club.mega.module.impl.visual;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "NoFov", description = "Remove FOV effect", category = Category.VISUAL)
public class NoFov extends Module {

    @Handler
    public final void tick(final EventTick event) {
        if (MC.gameSettings.ofDynamicFov)
            MC.gameSettings.ofDynamicFov = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.gameSettings.ofDynamicFov = true;
    }

}
