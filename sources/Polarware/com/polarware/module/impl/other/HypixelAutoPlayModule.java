package com.polarware.module.impl.other;

import com.polarware.component.impl.render.NotificationComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.util.chat.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@ModuleInfo(name = "module.other.hypixelautoplay.name", description = "module.other.autogg.description", category = Category.OTHER)
public final class HypixelAutoPlayModule extends Module {

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat chat = ((S02PacketChat) packet);

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
        }
    };

}
