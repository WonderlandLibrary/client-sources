package events;

import darkmagician6.Event;
import darkmagician6.EventCancellable;

public class EventMove
extends EventCancellable
{
public double x;
public double y;
public double z;

public EventMove(double x, double y, double z)
{
  this.x = x;
  this.y = y;
  this.z = z;
}
}