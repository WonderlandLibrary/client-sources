package com.alan.clients.module.impl.ghost;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.RightClickEvent;
import com.alan.clients.event.impl.render.MouseOverEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;

@ModuleInfo(aliases = {"module.ghost.hitbox.name"}, description = "module.ghost.hitbox.description", category = Category.GHOST)
public class HitBox extends Module {
    public final NumberValue expand = new NumberValue("Expand Amount", this, 0, 0, 6, 0.01);
    private final BooleanValue effectRange = new BooleanValue("Effect range", this, true);

    @EventLink
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        event.setExpand(this.expand.getValue().floatValue());

        if (!this.effectRange.getValue()) {
            event.setRange(event.getRange() - expand.getValue().doubleValue());
        }
    };

    @EventLink
    public final Listener<RightClickEvent> onRightClick = event ->
            mc.objectMouseOver = RayCastUtil.rayCast(RotationComponent.rotations, 4.5);
}