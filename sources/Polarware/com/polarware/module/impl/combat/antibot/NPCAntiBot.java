package com.polarware.module.impl.combat.antibot;

import com.polarware.Client;
import com.polarware.module.impl.combat.AntiBotModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public final class NPCAntiBot extends Mode<AntiBotModule> {

    public NPCAntiBot(String name, AntiBotModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            if (player instanceof EntityOtherPlayerMP) {
                if (((EntityOtherPlayerMP) player).getNameClear().contains("CIT-") ||
                   ((EntityOtherPlayerMP) player).getNameClear().contains("SHOP") ||
                   ((EntityOtherPlayerMP) player).getNameClear().contains("UPGRADES")) {
                    Client.INSTANCE.getBotComponent().add(player);
                }
            }
        });
    };
}