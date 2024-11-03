package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class GuildOnline extends ToggleBase {
    private final Pattern joinPattern = Pattern.compile("Guild > (?<player>\\S{1,16})(\\s+)(joined\\.)");
    private final Pattern leavePattern = Pattern.compile("Guild > (?<player>\\S{1,16})(\\s+)(left\\.)");

    public GuildOnline() {
        super("Hide Guild Join/Leave Messages");
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.leavePattern.matcher(message).matches() || this.joinPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
