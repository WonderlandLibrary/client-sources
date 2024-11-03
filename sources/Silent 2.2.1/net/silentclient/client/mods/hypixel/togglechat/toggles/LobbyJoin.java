package net.silentclient.client.mods.hypixel.togglechat.toggles;

import java.util.regex.Pattern;

import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.hypixel.togglechat.ToggleBase;

public class LobbyJoin extends ToggleBase {

    private final Pattern lobbyJoinPattern = Pattern.compile("( >>> )??\\[MVP(\\+|\\+\\+)] \\S{1,16} (sled into|joined) the lobby!( <<<)??");

    public LobbyJoin() {
        super("Hide Lobby Join Messages");
    }

    @Override
    public boolean shouldToggle(String message) {
        if (!message.contains("the lobby")) {
            return false;
        }
        return this.lobbyJoinPattern.matcher(message).matches();
    }

    @Override
    public void onTrigger(EventReceivePacket event) {
        event.setCancelled(true);
    }
}
