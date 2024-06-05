package club.mega.module.impl.movement;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.NumberSetting;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Step", description = "Step up faster", category = Category.MOVEMENT)
public class Step extends Module {

    private final NumberSetting height = new NumberSetting("Height", this, 1, 10, 1, 1);

    @Handler
    public final void tick(final EventTick event) {
        MC.thePlayer.stepHeight = height.getAsFloat();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.thePlayer.stepHeight = 0.6F;
    }

}