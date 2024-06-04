package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.HurtRenderEvent;
import com.polarware.value.impl.BooleanValue;

/**
 * @author Alan
 * @since 28/05/2022
 */

@ModuleInfo(name = "module.render.hurtcolor.name", description = "module.render.hurtcolor.description", category = Category.RENDER)
public final class HurtColorModule extends Module {

    private final BooleanValue oldDamage = new BooleanValue("1.7 Damage Animation", this, true);


    @EventLink()
    public final Listener<HurtRenderEvent> onHurtRender = event -> {
        event.setOldDamage(oldDamage.getValue());
    };
}