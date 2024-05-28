package arsenic.event.impl;

import arsenic.event.types.Event;

public class EventKey implements Event {

    private final int keycode;

    public EventKey(int keycode) {
        this.keycode = keycode;
    }

    public int getKeycode() { return keycode; }

}
