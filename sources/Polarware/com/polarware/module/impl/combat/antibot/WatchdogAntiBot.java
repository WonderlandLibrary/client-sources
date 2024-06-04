package com.polarware.module.impl.combat.antibot;

import com.polarware.Client;
import com.polarware.module.impl.combat.AntiBotModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class WatchdogAntiBot extends Mode<AntiBotModule> {

    public WatchdogAntiBot(String name, AntiBotModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());

            if (info == null) {
                Client.INSTANCE.getBotComponent().add(player);
            } else {
                Client.INSTANCE.getBotComponent().remove(player);
            }
        });
    };
}