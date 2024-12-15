package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

@ModuleInfo(aliases = {"module.player.polardetector.name"}, description = "module.player.polardetector.description", category = Category.PLAYER)
public class PolarDetector extends Module {
    boolean transaction = false;

    @Override
    public void onEnable() {
        NotificationComponent.post("Polar Detector","Join a game and this module will notify you of polars status");
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted == 30) {
            ChatUtil.display(transaction ? "Polar is enabled" : "Polar is disabled");
        }
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            transaction = true;
        }
    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        transaction = false;
    };
}