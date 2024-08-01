package wtf.diablo.client.scripting.impl.data;

import jdk.nashorn.api.scripting.JSObject;

import java.util.HashMap;
import java.util.Map;

public final class EventScripting {
    private final Map<String, JSObject> eventMap;

    public EventScripting() {
        this.eventMap = new HashMap<>();
    }

    public void registerEvent(final String eventName, JSObject event) {
        this.eventMap.put(eventName, event);
    }

    public Map<String, JSObject> getEventMap() {
        return this.eventMap;
    }
}