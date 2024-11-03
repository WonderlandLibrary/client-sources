package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class PartyInvites extends ToggleBase {
    private final Pattern expiredPattern = Pattern.compile(
            "The party invite (?<where>\\S{1,4}) (?<rank>\\[.+] )?(?<player>\\S{1,16}) has expired.");
    private final Pattern invitePattern = Pattern.compile(
            "(?<rank>\\[.+] )?(?<player>\\S{1,16}) has invited you to join (?<meme>\\[.+] )?(?<meme2>\\S{1,16}) party!");
    private final Pattern otherInvitePattern = Pattern.compile(
            "(?<inviteerank>\\[.+] )?(?<invitee>\\S{1,16}) invited (?<rank>\\[.+] )?(?<player>\\S{1,16}) to the party! They have 60 seconds to accept.");

    private final Pattern joinPattern = Pattern.compile(Pattern.quote("Click here to join! You have 60 seconds to accept."), Pattern.CASE_INSENSITIVE);

    private boolean wasLastMessageToggled;

    public PartyInvites() {
        super("Hide Party Invites");
    }

    @Override
    public boolean shouldToggle(String message) {
        if (this.wasLastMessageToggled && this.joinPattern.matcher(message).find()) {
            return true;
        }

        return this.wasLastMessageToggled =
                this.expiredPattern.matcher(message).matches() || this.invitePattern.matcher(message)
                        .matches() || this.otherInvitePattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
