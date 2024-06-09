package lunadevs.luna.events;

public class EventMoveRaw
extends Event
{
public static double x;
public static double y;
public static double z;

public EventMoveRaw(double x, double y, double z)
{
  this.x = x;
  this.y = y;
  this.z = z;
}
}
