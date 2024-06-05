package net.shoreline.client.impl.event.entity;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.StageEvent;

@Cancelable
public class UpdateVelocityEvent extends StageEvent
{
    private final float speed;
    private float yaw;

    public UpdateVelocityEvent(float speed)
    {
        this.speed = speed;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getSpeed()
    {
        return speed;
    }
}
