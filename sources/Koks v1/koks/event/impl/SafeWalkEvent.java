package koks.event.impl;

import koks.event.Event;
import net.minecraft.network.play.server.S42PacketCombatEvent;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:47
 */
public class SafeWalkEvent extends Event {

    private boolean isSafe;

    public SafeWalkEvent(boolean isSafe) {
        this.isSafe = isSafe;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }
}
