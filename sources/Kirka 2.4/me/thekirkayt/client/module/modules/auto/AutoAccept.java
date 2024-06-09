/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import java.io.PrintStream;
import java.util.ArrayList;
import me.thekirkayt.client.friend.Friend;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketReceiveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@Module.Mod
public class AutoAccept
extends Module {
    @EventTarget
    public void onPacket(PacketReceiveEvent event) {
        S02PacketChat message = (S02PacketChat)event.getPacket();
        System.out.println(message.func_148915_c().getFormattedText());
        if (message.func_148915_c().getFormattedText().contains("has requested to teleport to you")) {
            for (Friend friend : FriendManager.friendsList) {
                if (!message.func_148915_c().getFormattedText().contains(friend.name)) continue;
                AutoAccept.mc.thePlayer.sendChatMessage("/tpaccept");
                break;
            }
        }
    }
}

