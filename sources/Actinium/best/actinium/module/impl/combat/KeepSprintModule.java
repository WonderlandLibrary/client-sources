package best.actinium.module.impl.combat;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.ReduceEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;

@ModuleInfo(
        name = "Keep Sprint",
        description = "Prevents the player from losing sprint when attacking.",
        category = ModuleCategory.COMBAT
)
public class KeepSprintModule extends Module {
    @Callback
    public void onReduce(ReduceEvent event) {
        event.setCancelled(true);
    }
}
