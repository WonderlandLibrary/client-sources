/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import java.awt.Color;
import me.zane.basicbus.api.annotations.Listener;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.ColorOption;
import digital.rbq.module.option.impl.DoubleOption;

@Label(value="Glow")
@Category(value=ModuleCategory.VISUALS)
public final class GlowMod
extends Module {
    public static final DoubleOption lineWidth = new DoubleOption("Line Width", 3.0, 0.5, 5.0, 0.5);
    public static final ColorOption friendlyColor = new ColorOption("Friendly Color", new Color(127, 226, 72, 255));
    public static final ColorOption enemyColor = new ColorOption("Enemy Color", new Color(255, 50, 50, 120));

    public GlowMod() {
        this.addOptions(lineWidth, friendlyColor, enemyColor);
    }

    @Listener(value=TickEvent.class)
    public final void onTick() {
        GlowMod.mc.gameSettings.ofFastRender = false;
    }
}

