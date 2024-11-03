package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class FriendOnline extends ToggleBase {
    private final Pattern joinPattern = Pattern.compile("Friend > (?<player>\\S{1,16})(\\s+)(joined\\.)");
    private final Pattern legacyJoinPattern = Pattern.compile("(?<player>\\S{1,16})(\\s+)(joined\\.)");
    private final Pattern leavePattern = Pattern.compile("Friend > (?<player>\\S{1,16})(\\s+)(left\\.)");
    private final Pattern legacyLeavePattern = Pattern.compile("(?<player>\\S{1,16})(\\s+)(left\\.)");

    public FriendOnline() {
        super("Hide Friend Join/Leave Message");
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.joinPattern.matcher(message).matches() ||
                this.legacyJoinPattern.matcher(message).matches() ||
                this.leavePattern.matcher(message).matches() ||
                this.legacyLeavePattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
