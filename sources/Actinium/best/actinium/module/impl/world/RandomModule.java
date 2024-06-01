package best.actinium.module.impl.world;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.math.NewTimeUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.render.ChatUtil;

@ModuleInfo(
        name = "Random Test",
        description = "Used for testing advanced randomization.",
        category = ModuleCategory.WORLD
)
public class RandomModule extends Module {

    private final NewTimeUtil timeUtil = new NewTimeUtil();

    @Callback
    public void onTickEvent(TickEvent event) {
        if (timeUtil.elapsed(5000)) {
            timeUtil.reset();
        }
    }

}
