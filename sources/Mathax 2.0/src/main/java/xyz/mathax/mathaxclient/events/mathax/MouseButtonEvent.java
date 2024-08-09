package xyz.mathax.mathaxclient.events.mathax;

import xyz.mathax.mathaxclient.events.Cancellable;
import xyz.mathax.mathaxclient.utils.input.KeyAction;

public class MouseButtonEvent extends Cancellable {
    private static final MouseButtonEvent INSTANCE = new MouseButtonEvent();

    public int button;
    public KeyAction action;

    public static MouseButtonEvent get(int button, KeyAction action) {
        INSTANCE.setCancelled(false);
        INSTANCE.button = button;
        INSTANCE.action = action;
        return INSTANCE;
    }
}
