package com.polarware.module.impl.ghost;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.render.MouseOverEvent;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import org.lwjgl.input.Mouse;

/**
 * @author Alan
 * @since 29/01/2021
 */
@ModuleInfo(name = "module.ghost.hitbox.name", description = "module.ghost.hitbox.description", category = Category.GHOST)
public class HitBoxModule extends Module {
    public final NumberValue expand = new NumberValue("Expand Amount", this, 0, 0, 6, 0.01);
    private final BooleanValue effectRange = new BooleanValue("Effect range", this, true);

    private int exempt;

    @EventLink()
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        if (Mouse.isButtonDown(1)) {
            exempt = 1;
        }

        if (exempt > 0) return;

        event.setExpand(this.expand.getValue().floatValue());

        if (!this.effectRange.getValue()) {
            event.setRange(event.getRange() - expand.getValue().doubleValue());
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        exempt--;
    };
}