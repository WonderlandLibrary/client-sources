package com.alan.clients.module.impl.player.scaffold.downwards;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.value.Mode;
import org.lwjgl.input.Keyboard;

public class NormalDownwards extends Mode<Scaffold> {

    public NormalDownwards(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) return;

        getParent().offset = getParent().offset.add(0,-1,0);
    };
}
