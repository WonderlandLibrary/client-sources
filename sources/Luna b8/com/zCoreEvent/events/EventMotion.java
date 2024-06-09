package com.zCoreEvent.events;

import javax.xml.stream.Location;

import com.zCoreEvent.Event;

public class EventMotion
extends Event
{
public double x;
public double y;
public double z;
private Location location;
private float yaw;
private float pitch;
private boolean onGround;
private EventType type;

public EventMotion(Location location, boolean onGround, float yaw, float pitch, EventType type)
{
  this.location = location;
  this.onGround = onGround;
  this.type = type;
  this.yaw = yaw;
  this.pitch = pitch;
}

public Location getLocation()
{
  return this.location;
}

public void setLocation(Location location)
{
  this.location = location;
}

public boolean isOnGround()
{
  return this.onGround;
}

public void setOnGround(boolean onGround)
{
  this.onGround = onGround;
}

public EventType getType()
{
  return this.type;
}

public float getYaw()
{
  return this.yaw;
}

public void setYaw(float yaw)
{
  this.yaw = yaw;
}

public float getPitch()
{
  return this.pitch;
}

public void setPitch(float pitch)
{
  this.pitch = pitch;
}
}