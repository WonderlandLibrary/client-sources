package club.mega.module.impl.visual;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "NoBob", description = "Remove BOB effect", category = Category.VISUAL)
public class NoBob extends Module {

    @Handler
    public final void tick(final EventTick event) {
        if (MC.gameSettings.viewBobbing)
            MC.gameSettings.viewBobbing = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.gameSettings.viewBobbing = true;
    }

}
