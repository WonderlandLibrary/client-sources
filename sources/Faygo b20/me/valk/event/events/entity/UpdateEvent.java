package me.valk.event.events.entity;

import me.valk.event.Event;

public class UpdateEvent extends Event
{
    public double y;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public me.valk.event.EventType state;
    public boolean alwaysSend;
    private double stepHeight;
    private boolean active;
	public boolean sendAnyway;
    
    public UpdateEvent (final double y, final float yaw, final float pitch, final boolean onGround) {
        this.y = y;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.state = me.valk.event.EventType.PRE;
    }
    
    public UpdateEvent() {
        this.state = me.valk.event.EventType.POST;
    }
    
    public double getStepHeight() {
        return this.stepHeight;
    }
    
    
    
    public boolean isActive() {
        return this.active;
    }
    
    public void setStepHeight(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
    
    public void setActive(final boolean bypass) {
        this.active = bypass;
    }
    
    public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

    public double getY()
    {
      return this.y;
    }

    public void setY(double y)
    {
      this.y = y;
    }

    
    
  
  }
