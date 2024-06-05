package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.event.Cancellable;
import net.minecraft.util.AxisAlignedBB;

public class EventLiquidCollision extends Cancellable {
    private AxisAlignedBB axis;
    public EventLiquidCollision(AxisAlignedBB ax) {
        this.axis = ax;
    }

    private void setAxis(AxisAlignedBB ax) {
        this.axis = ax;
    }

    public AxisAlignedBB getBounds() {
        return axis;
    }
}
