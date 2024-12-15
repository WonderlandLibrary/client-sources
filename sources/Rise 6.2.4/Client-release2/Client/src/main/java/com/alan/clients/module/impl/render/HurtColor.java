package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.HurtRenderEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.BooleanValue;

@ModuleInfo(aliases = {"module.render.hurtcolor.name"}, description = "module.render.hurtcolor.description", category = Category.RENDER)
public final class HurtColor extends Module {

    private final BooleanValue oldDamage = new BooleanValue("1.7 Damage Animation", this, true);

    @EventLink
    public final Listener<HurtRenderEvent> onHurtRender = event -> {
        event.setOldDamage(oldDamage.getValue());
    };
}