package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class TeamMessages extends ToggleBase {
    private final Pattern teamPattern = Pattern
            .compile("\\[TEAM] (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public TeamMessages() {
        super("Hide In-Game Team Messages");
    }


    @Override
    public boolean shouldToggle(String message) {
        return this.teamPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
