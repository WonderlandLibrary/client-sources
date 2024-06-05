package net.shoreline.client.api.manager.player.rotation;

import net.shoreline.client.api.module.RotationModule;

/**
 *
 *
 * @author linus, bon55
 * @since 1.0
 */
public class RotationRequest
{
    //
    private final RotationModule requester;
    private final int priority;
    private long time;
    //
    private float yaw, pitch;

    /**
     *
     * @param requester
     * @param priority
     * @param yaw
     * @param pitch
     */
    public RotationRequest(RotationModule requester,
                           int priority, float yaw, float pitch)
    {
        this.requester = requester;
        this.time = System.currentTimeMillis();
        this.priority = priority;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     *
     * @param requester
     * @param yaw
     * @param pitch
     */
    public RotationRequest(RotationModule requester,
                           float yaw, float pitch)
    {
        this(requester, 100, yaw, pitch);
    }

    public RotationModule getModule()
    {
        return requester;
    }

    /**
     *
     * @return
     */
    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public int getPriority()
    {
        return priority;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getPitch()
    {
        return pitch;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }
}
