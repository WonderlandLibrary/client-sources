/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.util.Timer;

public class FastSneak
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u44d9\u71c4\u7f8c\ua7e1"), Qprot0.0("\u44da\u71c4\u7f9a\ua7e9\u760a\uc142"), Qprot0.0("\u44da\u71c4\u7f9a\ua7e9\u760a\uc142"), Qprot0.0("\u44d5\u71ea\u7fab"));
    public int delay = 600;
    public Timer timer = new Timer();
    public boolean sneak = false;

    @Override
    public void onEvent(Event Nigga) {
        boolean cfr_ignored_0 = Nigga instanceof EventUpdate;
    }

    public static {
        throw throwable;
    }

    public FastSneak() {
        super(Qprot0.0("\u44d2\u71ca\u7f9b\ua7f0\u7638\uc140\u8c2a\u28b0\u5709"), 0, Module.Category.MOVEMENT);
        FastSneak Nigga;
    }

    @Override
    public void onEnable() {
        FastSneak Nigga;
        Nigga.timer.reset();
    }
}

