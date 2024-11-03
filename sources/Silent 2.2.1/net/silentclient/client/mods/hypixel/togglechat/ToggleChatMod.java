package net.silentclient.client.mods.hypixel.togglechat;

import java.util.ArrayList;

import net.minecraft.network.play.server.S02PacketChat;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.hypixel.togglechat.toggles.DirectMessages;
import net.silentclient.client.mods.hypixel.togglechat.toggles.FriendOnline;
import net.silentclient.client.mods.hypixel.togglechat.toggles.FriendRequests;
import net.silentclient.client.mods.hypixel.togglechat.toggles.GlobalMessages;
import net.silentclient.client.mods.hypixel.togglechat.toggles.GuildMessages;
import net.silentclient.client.mods.hypixel.togglechat.toggles.GuildOnline;
import net.silentclient.client.mods.hypixel.togglechat.toggles.LobbyJoin;
import net.silentclient.client.mods.hypixel.togglechat.toggles.PartyInvites;
import net.silentclient.client.mods.hypixel.togglechat.toggles.PartyMessages;
import net.silentclient.client.mods.hypixel.togglechat.toggles.ShoutMessages;
import net.silentclient.client.mods.hypixel.togglechat.toggles.TeamMessages;
import net.silentclient.client.utils.ChatColor;

public class ToggleChatMod extends Mod {

    private ArrayList<ToggleBase> toggles;

    public ToggleChatMod() {
        super("ToggleChat", ModCategory.MODS, "silentclient/icons/mods/togglechat.png");
    }

    @Override
    public void setup() {
        toggles = new ArrayList<ToggleBase>();
        toggles.add(new DirectMessages());
        toggles.add(new ShoutMessages());
        toggles.add(new TeamMessages());
        toggles.add(new GlobalMessages());
        toggles.add(new FriendOnline());
        toggles.add(new FriendRequests());
        toggles.add(new GuildMessages());
        toggles.add(new GuildOnline());
        toggles.add(new PartyMessages());
        toggles.add(new PartyInvites());
        toggles.add(new LobbyJoin());

        toggles.forEach(toggle -> {
            this.addBooleanSetting(toggle.getName(), this, false);
        });
    }

    @EventTarget
    public void onMessage(EventReceivePacket event) {
        if(isForceDisabled()) {
            return;
        }

        if(!(event.getPacket() instanceof S02PacketChat)) {
            return;
        }
        S02PacketChat e = (S02PacketChat) event.getPacket();

        toggles.forEach(toggle -> {
            if(toggle.isEnabled() && toggle.shouldToggle(ChatColor.stripColor(e.getChatComponent().getUnformattedText()))) {
                toggle.onTrigger(event);
            }
        });
    }
}
