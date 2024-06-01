package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.HurtCamEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;

@ModuleInfo(
        name = "No Hurt Cam",
        description = "Removes The HurtCam Effect in Minecraft.",
        category = ModuleCategory.VISUAL
)
public class NoHurtCamModule extends Module {
    @Callback
    public void onHurt(HurtCamEvent event) {
        event.setCancelled(true);
    }
}
