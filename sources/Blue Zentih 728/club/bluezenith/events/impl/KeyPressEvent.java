package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class KeyPressEvent extends Event {
    public int keyCode;

    public KeyPressEvent(int keyCode) {
        this.keyCode = keyCode;
    }
}
