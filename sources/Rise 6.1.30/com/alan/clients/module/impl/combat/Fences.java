package com.alan.clients.module.impl.combat;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.SafeWalkEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;

@ModuleInfo(aliases = {"module.combat.fences.name"}, description = "module.combat.fences.description", category = Category.COMBAT)
public final class Fences extends Module {
    @Override
    public void onDisable() {
        mc.thePlayer.safeWalk = false;
    }

    @EventLink
    public final Listener<SafeWalkEvent> onSafeWalk = event -> {
        mc.thePlayer.safeWalk = true;
        event.setHeight(-5);
    };
}
