package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;

public class PlayerJoinEvent extends Event {
    private final String name;

    public PlayerJoinEvent(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}