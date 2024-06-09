package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.util.AxisAlignedBB;

public class EventLiquidCollide
  implements Event
{
  public AxisAlignedBB bound;
  public int x;
  public int y;
  public int z;
  
  public EventLiquidCollide(AxisAlignedBB bound, int x, int y, int z)
  {
    this.bound = bound;
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
