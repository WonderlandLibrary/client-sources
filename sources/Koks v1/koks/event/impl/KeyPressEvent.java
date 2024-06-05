package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 02:36
 */
public class KeyPressEvent extends Event {

    private final int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
