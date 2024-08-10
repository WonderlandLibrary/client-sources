package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.StrafeEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "Strafe",
        category = Category.MOVEMENT
)
public class Strafe extends Module {

    private final NumberValue<Float> strength = new NumberValue<>("Strength", 100F, 1F, 100F, 1F);

    public Strafe() {
        addSettings(strength);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onStrafe (StrafeEvent event) {
        MovementUtil.customStrafeStrength(strength.getValue());
    }

}
