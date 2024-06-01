package com.polarware.module.impl.ghost;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.interfaces.InstanceAccess;

/**
 * @author Alan Jr. (Not Billionaire)
 * @since 19/9/2022
 */
@ModuleInfo(name = "module.ghost.noclickdelay.name", description = "module.ghost.noclickdelay.description", category = Category.GHOST)
public class NoClickDelayModule extends Module  {

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (InstanceAccess.mc.thePlayer != null && InstanceAccess.mc.theWorld != null) {
            InstanceAccess.mc.leftClickCounter = 0;
        }
    };
}