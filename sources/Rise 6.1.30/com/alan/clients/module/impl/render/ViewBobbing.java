package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.render.ViewBobbingEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;

@ModuleInfo(aliases = {"module.render.viewbobbing.name"}, description = "module.render.viewbobbing.description", category = Category.RENDER)
public final class ViewBobbing extends Module {

    public final ModeValue viewBobbingMode = new ModeValue("Mode", this)
            .add(new SubMode("Smooth"))
            .add(new SubMode("Meme"))
            .add(new SubMode("None"))
            .setDefault("None");

    @EventLink
    public final Listener<ViewBobbingEvent> onViewBobbing = event -> {
        if (viewBobbingMode.getValue().getName().equals("Smooth") && (event.getTime() == 0 || event.getTime() == 2)) {
            event.setCancelled();
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.viewBobbing = true;

        switch (viewBobbingMode.getValue().getName()) {
            case "Meme": {
                mc.thePlayer.cameraYaw = 0.5F;
                break;
            }

            case "None": {
                mc.thePlayer.distanceWalkedModified = 0.0F;
                break;
            }
        }
    };
}