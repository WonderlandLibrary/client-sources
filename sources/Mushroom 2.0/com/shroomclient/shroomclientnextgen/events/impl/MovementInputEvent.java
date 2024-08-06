package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;

public class MovementInputEvent extends Event {

    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public boolean sneaking;
    public boolean jumping;

    // last variable is "update" because "new" doesnt work
    public MovementInputEvent(
        boolean forward,
        boolean backward,
        boolean left,
        boolean right,
        boolean sneaking,
        boolean jumping
    ) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;

        this.sneaking = sneaking;
        this.jumping = jumping;
    }
}
