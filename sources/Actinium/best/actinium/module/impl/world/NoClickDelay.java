package best.actinium.module.impl.world;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.IAccess;

@ModuleInfo(
        name = "No Click delay",
        description = "Removes the click delay from 1.8",
        category = ModuleCategory.WORLD
)
public class NoClickDelay extends Module {
    @Callback
    public void onUpdate(MotionEvent event) {
        if(IAccess.mc.thePlayer != null && IAccess.mc.theWorld != null) {
            IAccess.mc.leftClickCounter = 0;
        }
    }
}
