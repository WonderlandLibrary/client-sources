package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class SlowdownEvent extends Event {

    public float reducer;
    public SlowdownEvent(float reducer) {
        this.reducer = reducer;
    }
}
