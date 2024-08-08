package me.xatzdevelopments.modules.player;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.modules.Module;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, Category.PLAYER, "Place items with 0 delay");
    }

    public void onEvent(Event e) {
        mc.rightClickDelayTimer = 0;
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
        super.onDisable();
    }
}