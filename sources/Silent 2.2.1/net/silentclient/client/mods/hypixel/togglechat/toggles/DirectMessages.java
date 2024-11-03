package net.silentclient.client.mods.hypixel.togglechat.toggles;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class DirectMessages extends ToggleBase {
    public DirectMessages() {
        super("Hide Direct Messages");
    }


    @Override
    public boolean shouldToggle(String message) {
        return message.startsWith("To ") || message.startsWith("From ");
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
