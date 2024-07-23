package io.github.liticane.monoxide.listener.event.minecraft.player.combat;

import io.github.liticane.monoxide.listener.event.Event;

public class KnockbackModifierEvent extends Event {
    private boolean flag;

    public KnockbackModifierEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
