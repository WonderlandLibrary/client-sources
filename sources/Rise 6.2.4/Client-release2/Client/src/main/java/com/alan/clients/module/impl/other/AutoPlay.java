package com.alan.clients.module.impl.other;

import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@ModuleInfo(aliases = {"module.other.hypixelautoplay.name"}, description = "module.other.hypixelautoplay.description", category = Category.PLAYER)
public final class AutoPlay extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Hypixel"))
            .setDefault("Hypixel");

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat chat = ((S02PacketChat) packet);

            //switch (mode.getValue().getName()) {
            //case "Hypixel":
            if (mode.getValue().getName().equals("Hypixel")) {
                if (chat.isChat()) return;
                if (chat.getChatComponent().getFormattedText().contains("play again?")) {
                    for (IChatComponent iChatComponent : chat.getChatComponent().getSiblings()) {
                        for (String value : iChatComponent.toString().split("'")) {
                            if (value.startsWith("/play") && !value.contains(".")) {
                                ChatUtil.send(value);
                                NotificationComponent.post("Auto Play", "Joined a new game", 7000);
                                break;
                            }
                        }
                    }
                }
                // break;
            }
        }
    };
}
