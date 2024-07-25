package club.bluezenith.events.impl;

import club.bluezenith.events.EventType;
import club.bluezenith.events.MultiTypeEvent;

public class MoveEvent extends MultiTypeEvent {
    public double x, y, z;
    public int safeWalkState;

    public MoveEvent(double x, double y, double z, int safeWalk, EventType type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.safeWalkState = safeWalk;
        this.type = type;
    }
    public int getSafeWalkState(){
        return Math.min(safeWalkState, 2);
    }

    public void setType(EventType t) {
        this.type = t;
    }
}
