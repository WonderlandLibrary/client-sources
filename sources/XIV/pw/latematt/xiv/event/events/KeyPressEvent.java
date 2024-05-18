package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class KeyPressEvent extends Event {
    private final int keyCode;

    public KeyPressEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
