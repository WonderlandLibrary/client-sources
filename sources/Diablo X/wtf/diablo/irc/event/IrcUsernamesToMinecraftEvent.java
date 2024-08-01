package wtf.diablo.irc.event;

import wtf.diablo.client.event.api.AbstractEvent;

import java.util.HashMap;
import java.util.Map;

public final class IrcUsernamesToMinecraftEvent extends AbstractEvent {
    private final Map<String, String> usernamesToMinecraft;

    public IrcUsernamesToMinecraftEvent(final String raw) {
        this.usernamesToMinecraft = new HashMap<>();
    }

}
