package com.alan.clients.module.impl.movement;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;

@ModuleInfo(aliases = {"module.movement.nojumpdelay.name"}, description = "module.movement.nojumpdelay.description", category = Category.MOVEMENT)
public class NoJumpDelay extends Module {
    @EventLink
    public final Listener<PreUpdateEvent> preUpdateEventListener = event -> {
        mc.thePlayer.jumpTicks = 0;
    };
}
