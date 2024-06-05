package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 11.09.2020 : 21:48
 */
public class KeyBindEvent extends Event {

    public int key;

    public KeyBindEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
