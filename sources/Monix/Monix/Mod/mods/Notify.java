/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;

public class Notify
extends Mod {
    public static int Ticks = 0;
    public static boolean Notify = false;
    public static String NotifySays = "";

    public Notify() {
        super("Notify", "", 0, Category.NONE);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (++Ticks == 60) {
            Ticks = 0;
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        Notify = true;
    }

    @Override
    public void onDisable() {
        Notify = false;
    }
}

