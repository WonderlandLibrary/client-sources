package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.render.EventRenderSwingArm;
import lol.point.returnclient.events.impl.render.EventRenderSmoothSwing;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "SwingAnimations",
        description = "customizes the sword swinging animation",
        category = Category.RENDER
)
public class SwingAnimations extends Module {

    public final NumberSetting swingSpeed = new NumberSetting("Swing speed", 1, 0.1f, 3.5f, 1);
    public final BooleanSetting smooth = new BooleanSetting("Smooth", false);

    public SwingAnimations() {
        addSettings(swingSpeed, smooth);
    }

    public String getSuffix() {
        return String.valueOf(swingSpeed.value.floatValue());
    }

    @Subscribe
    private final Listener<EventRenderSwingArm> onSwingArm1 = new Listener<>(renderSwingAnimation -> renderSwingAnimation.swingSpeed = swingSpeed.value.floatValue());

    @Subscribe
    private final Listener<EventRenderSmoothSwing> onSwingArm2 = new Listener<>(renderSwingAnimation -> renderSwingAnimation.renderSwingProgress = smooth.value ? 0 : renderSwingAnimation.renderSwingProgress);

}
