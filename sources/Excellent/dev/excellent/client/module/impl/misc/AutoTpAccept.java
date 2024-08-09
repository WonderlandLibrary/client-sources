package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.friend.Friend;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

@ModuleInfo(name = "Auto Tp Accept", description = "Автоматически принимает запросы на телепортацию", category = Category.MISC)
public class AutoTpAccept extends Module {
    private final BooleanValue onlyFriend = new BooleanValue("Только друзья", this, true);
    private final String[] teleportMessages = new String[]{
            "has requested teleport",
            "просит телепортироваться",
            "хочет телепортироваться к вам",
            "просит к вам телепортироваться"
    };
    private final Listener<PacketEvent> onPacket = event -> {
        if (event.isReceive() && event.getPacket() instanceof SChatPacket wrapper) {
            handleReceivePacket(wrapper);
        }
    };

    private void handleReceivePacket(SChatPacket packet) {
        String message = TextFormatting.getTextWithoutFormattingCodes(packet.getChatComponent().getString());
        if (isTeleportMessage(message)) {
            if (onlyFriendsEnabled()) {
                handleTeleportWithFriends(message);
                return;
            }
            acceptTeleport();
        }
    }

    private boolean isTeleportMessage(String message) {
        return Arrays.stream(this.teleportMessages)
                .map(String::toLowerCase)
                .anyMatch(message::contains);
    }

    private boolean onlyFriendsEnabled() {
        return onlyFriend.getValue();
    }

    private void handleTeleportWithFriends(String message) {
        for (Friend friend : excellent.getFriendManager()) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = message.toCharArray();
            for (int w = 0; w < buffer.length; w++) {
                char c = buffer[w];
                if (c == '\u00A7') {
                    w++;
                } else {
                    builder.append(c);
                }
            }

            if (builder.toString().contains(friend.getName()))
                acceptTeleport();
        }
    }

    private void acceptTeleport() {
        ChatUtil.sendText("/tpaccept");
    }
}
