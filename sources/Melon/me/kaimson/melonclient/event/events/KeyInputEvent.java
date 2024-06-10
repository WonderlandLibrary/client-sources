package me.kaimson.melonclient.event.events;

import me.kaimson.melonclient.event.*;

public class KeyInputEvent extends Event
{
    public final int keycode;
    
    public KeyInputEvent(final int keycode) {
        this.keycode = keycode;
    }
}
