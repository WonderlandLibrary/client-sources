package com.alan.clients.module.impl.ghost;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;

@ModuleInfo(aliases = {"module.ghost.noclickdelay.name"}, description = "module.ghost.noclickdelay.description", category = Category.GHOST)
public class NoClickDelay extends Module {

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer != null && mc.theWorld != null) {
            mc.leftClickCounter = 0;
        }
    };
}