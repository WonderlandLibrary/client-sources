package com.polarware.module.impl.player;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;

@ModuleInfo(name = "module.player.twerk.name", description = "module.player.twerk.description", category = Category.PLAYER)
public class TwerkModule extends Module {

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSneak.setPressed(Math.random() < 0.5 && mc.thePlayer.ticksExisted % 2 == 0);
    };


    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneakSlowDownMultiplier(0);
    };
}