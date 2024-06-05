package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 00:06
 */
public class BlockReachEvent extends Event {

    public float reach;

    public BlockReachEvent(float reach) {
        this.reach = reach;
    }

    public float getReach() {
        return reach;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }
}
