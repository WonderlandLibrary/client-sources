package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class FriendRequests extends ToggleBase {
    private final Pattern friendPattern = Pattern.compile(
            "----------------------------------------------------\n" +
                    "Friend request from (?<rank>\\[.+] )?(?<player>\\S{1,16})\n" +
                    "\\[ACCEPT] - \\[DENY] - \\[IGNORE]\n" +
                    "----------------------------------------------------");

    // This is used for expiry messages
    private final Pattern oldPattern = Pattern
            .compile(Pattern.quote("Friend request from "), Pattern.CASE_INSENSITIVE);

    // Removal message
    private final Pattern friendRemovedMePattern = Pattern
            .compile("(?<rank>\\[.+?] )?(?<player>\\S{1,16}) removed you from their friends list!");

    // Added message
    private final Pattern areNowFriendsPattern = Pattern
            .compile("You are now friends with (?<rank>\\[.+?] )?(?<player>\\S{1,16})");

    public FriendRequests() {
        super("Hide Friend Requests");
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.oldPattern.matcher(message).find() || this.friendPattern.matcher(message)
                .matches() || this.friendRemovedMePattern
                .matcher(message).matches() || this.areNowFriendsPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
