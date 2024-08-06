package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.util.math.Vec3d;

public class JumpEvent extends Event {

    Vec3d jumpPos;

    public JumpEvent(Vec3d jumpPos) {
        this.jumpPos = jumpPos;
    }
}
