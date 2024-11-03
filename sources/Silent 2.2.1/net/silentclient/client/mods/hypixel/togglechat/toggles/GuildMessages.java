package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class GuildMessages extends ToggleBase {
    private final Pattern guildPattern = Pattern
            .compile("(Guild|G) > (?<rank>\\[.+] )?(?<player>\\S{1,16})( \\[.+])?: (?<message>.*)");
    public GuildMessages() {
        super("Hide Guild Messages");
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.guildPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
