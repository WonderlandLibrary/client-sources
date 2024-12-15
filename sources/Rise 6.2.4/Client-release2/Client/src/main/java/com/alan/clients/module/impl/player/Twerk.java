package com.alan.clients.module.impl.player;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;

@ModuleInfo(aliases = {"module.player.twerk.name"}, description = "module.player.twerk.description", category = Category.PLAYER)
public class Twerk extends Module {

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSneak.setPressed(Math.random() < 0.5);
    };

}