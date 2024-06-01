package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;

@ModuleInfo(
        name = "2D ESP",
        description = "Removes The HurtCam Effect in Minecraft.",
        category = ModuleCategory.VISUAL
)
public class TwoDESPModule extends Module {
    @Callback
    public void onRender3D(Render3DEvent event) {

    }
}
