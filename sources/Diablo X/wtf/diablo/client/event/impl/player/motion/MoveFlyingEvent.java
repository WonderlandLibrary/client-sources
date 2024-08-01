package wtf.diablo.client.event.impl.player.motion;

import wtf.diablo.client.event.api.AbstractEvent;

public final class MoveFlyingEvent extends AbstractEvent {

    private float yaw, friction, strafe, forward;

    public MoveFlyingEvent(final float yaw, final float friction, final float strafe, final float forward)
    {
        this.yaw = yaw;
        this.friction = friction;
        this.strafe = strafe;
        this.forward = forward;
    }

    public float getYaw()
    {
        return yaw;
    }

    public void setYaw(final float yaw)
    {
        this.yaw = yaw;
    }

    public float getFriction()
    {
        return friction;
    }

    public void setFriction(final float friction)
    {
        this.friction = friction;
    }

    public float getStrafe()
    {
        return strafe;
    }

    public void setStrafe(final float strafe)
    {
        this.strafe = strafe;
    }

    public float getForward()
    {
        return forward;
    }

    public void setForward(final float forward)
    {
        this.forward = forward;
    }


}
