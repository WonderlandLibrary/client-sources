package me.vaziak.sensation.client.api.event.events;

/**
 * @author antja03
 */
public class KeyPressEvent {
    private int keyCode;

    public KeyPressEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
