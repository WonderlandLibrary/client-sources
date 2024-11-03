package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class PartyMessages extends ToggleBase {
    private final Pattern partyPattern = Pattern
            .compile("(P|Party) > (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public PartyMessages() {
        super("Hide Party Messages");
    }


    @Override
    public boolean shouldToggle(String message) {
        return this.partyPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
