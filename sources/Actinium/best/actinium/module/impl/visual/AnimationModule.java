package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;

/*
* Main part of the code is in ItemRenderer.java
*/
@ModuleInfo(
        name = "Animation",
        description = "Changes the block hitting animation.",
        category = ModuleCategory.VISUAL
)
public class AnimationModule extends Module {
    public ModeProperty mode = new ModeProperty("Block Hit Mode", this, new String[] {"None", "1.7", "Lunar","Tap", "Slide 2", "ZeroDay","Old", "Exhibition", "Wack", "Slide", "Xiv"}, "1.7");
    public ModeProperty swingMode = new ModeProperty("Swing Mode", this, new String[] {"1.8", "1.9+"}, "1.8");
    public NumberProperty swingSpeed = new NumberProperty("Swing Speed", this, 4, 6, 25, 1);

    public final NumberProperty x = new NumberProperty("X", this, -2.0F, 0, 2.0F, 0.05f);
    public final NumberProperty y = new NumberProperty("Y", this, -2.0F, 0, 2.0F, 0.05f);
    public final NumberProperty z = new NumberProperty("Z", this, -2.0F, 0, 2.0F, 0.05f);

    @Callback
    public void onTickEvent(TickEvent event) {
        setSuffix(mode.getMode() + " | " + swingMode.getMode());
    }
}