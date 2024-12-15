package com.alan.clients.module.impl.combat.antibot;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.impl.combat.AntiBot;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.Mode;
import rip.vantage.commons.util.time.StopWatch;

public class HypixelTestCheck extends Mode<AntiBot> {
    public HypixelTestCheck(String name, AntiBot parent) {
        super(name, parent);
    }
    private final StopWatch delay = new StopWatch();

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.theWorld.playerEntities.forEach(player -> {
            String name = player.getCustomNameTag().toLowerCase();
            if (player.ticksExisted < 10) {
                if (delay.finished(5000)) {
                    if (!player.onGround) {
//                        ChatUtil.display("ADDING");
                        Client.INSTANCE.getBotManager().add(this, player);
                    }
                }
            } else if (player.onGround) {
//                ChatUtil.display("Removing");
                Client.INSTANCE.getBotManager().remove(this, player);
            }
            if (name.contains("§c") && name.contains("§r")) {
                Client.INSTANCE.getBotManager().add(this, player);
            }
        });
    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        delay.reset();
        Client.INSTANCE.getBotManager().clear(this);
    };
    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        delay.reset();
    };

    @Override
    public void onDisable() {
        Client.INSTANCE.getBotManager().clear(this);
    }
}
