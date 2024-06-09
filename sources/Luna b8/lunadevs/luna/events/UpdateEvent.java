package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class UpdateEvent 
extends EventCancellable
{
public double y;
public float yaw;
public float pitch;
public boolean onGround;
public EventState state;
public boolean alwaysSend;
private double stepHeight;
private boolean active;

public UpdateEvent(double y, float yaw, float pitch, boolean onGround)
{
  this.y = y;
  this.yaw = yaw;
  this.pitch = pitch;
  this.onGround = onGround;
  this.state = EventState.PRE;
}


public EventState getState()
{
  return this.state;
}

public UpdateEvent()
{
  this.state = EventState.POST;
}

public double getStepHeight()
{
  return this.stepHeight;
}

public boolean shouldAlwaysSend()
{
  return this.alwaysSend;
}

public boolean isActive()
{
  return this.active;
}

public void setStepHeight(double stepHeight)
{
  this.stepHeight = stepHeight;
}

public void setActive(boolean bypass)
{
  this.active = bypass;
}

public void setAlwaysSend(boolean alwaysSend)
{
  this.alwaysSend = alwaysSend;
}
}
