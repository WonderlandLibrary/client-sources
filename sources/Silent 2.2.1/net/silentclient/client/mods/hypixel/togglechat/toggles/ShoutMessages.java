package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class ShoutMessages extends ToggleBase {
    private final Pattern shoutPattern = Pattern
            .compile("\\[SHOUT] (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public ShoutMessages() {
        super("Hide Shout Messages");
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.shoutPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
