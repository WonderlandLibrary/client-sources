package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 *
 */
@Cancelable
public class MovementPacketsEvent extends Event
{
    //
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    /**
     *
     *
     * @param x
     * @param y
     * @param z
     * @param yaw
     * @param pitch
     * @param onGround
     */
    public MovementPacketsEvent(double x, double y, double z, float yaw,
                                float pitch, boolean onGround)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getPitch()
    {
        return pitch;
    }

    public boolean getOnGround()
    {
        return onGround;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    public void setOnGround(boolean onGround)
    {
        this.onGround = onGround;
    }
}
