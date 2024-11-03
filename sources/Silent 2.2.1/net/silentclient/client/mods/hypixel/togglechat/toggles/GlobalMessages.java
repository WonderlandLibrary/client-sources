package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class GlobalMessages extends ToggleBase {
    private final Pattern chatPattern = Pattern
            .compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public GlobalMessages() {
        super("Hide In-Game Messages (Excludes Team Chat)");
    }


    @Override
    public boolean shouldToggle(String message) {
        Matcher matcher = this.chatPattern.matcher(message);

        return matcher.matches() && isNotOtherChat(matcher);
    }

    private boolean isNotOtherChat(Matcher input) {
        String rank = null;

        try {
            rank = input.group("rank");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return true;
        }

        // If the matcher returns null then we
        // need to stop before we cause a NPE :)
        if (rank == null) {
            return true;
        }

        switch (rank) {
            case "[TEAM] ":
            case "[SHOUT] ":
            case "[SPECTATOR] ":
                return false;
        }
        return true;
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
