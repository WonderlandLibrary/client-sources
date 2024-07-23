package io.github.liticane.monoxide.listener.event.minecraft.player.rotation;

import io.github.liticane.monoxide.listener.event.Event;
import io.github.liticane.monoxide.util.interfaces.Methods;

public final class RayTraceRangeEvent extends Event implements Methods
{
    public float blockReachDistance;
    public float range;
    public float rayTraceRange;
    
    public RayTraceRangeEvent(final float range) {
        this.blockReachDistance = RayTraceRangeEvent.mc.playerController.getBlockReachDistance();
        this.range = range;
    }

    public float getBlockReachDistance() {
        return blockReachDistance;
    }

    public float getRange() {
        return range;
    }

    public float getRayTraceRange() {
        return rayTraceRange;
    }

    public void setBlockReachDistance(float blockReachDistance) {
        this.blockReachDistance = blockReachDistance;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setRayTraceRange(float rayTraceRange) {
        this.rayTraceRange = rayTraceRange;
    }
}
