package dev.excellent.client.module.impl.misc;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.behavior.IPathingBehavior;

@ModuleInfo(name = "Baritone", description = "Бот выполняющий разные задачи", category = Category.MISC)
public class Baritone extends Module {
    public static Singleton<Baritone> singleton = Singleton.create(() -> Module.link(Baritone.class));

    @Override
    protected void onDisable() {
        super.onDisable();
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        IPathingBehavior pathingBehavior = baritone.getPathingBehavior();
        pathingBehavior.cancelEverything();
        pathingBehavior.forceCancel();
        baritone.getSelectionManager().removeAllSelections();

        baritone.getCustomGoalProcess().path();
    }
}
