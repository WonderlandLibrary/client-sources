package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class EventCollision implements Event{
	
	  private Entity entity;
	  private Block block;
	  private Location location;
	  private AxisAlignedBB boundingBox;
	  
	  public EventCollision(Entity entity, Location location, AxisAlignedBB boundingBox, Block block)
	  {
	    this.entity = entity;
	    this.location = location;
	    this.boundingBox = boundingBox;
	    this.block = block;
	  }
	  
	  public AxisAlignedBB getBoundingBox()
	  {
	    return this.boundingBox;
	  }
	  
	  public void setBoundingBox(AxisAlignedBB boundingBox)
	  {
	    this.boundingBox = boundingBox;
	  }
	  
	  public Entity getEntity()
	  {
	    return this.entity;
	  }
	  
	  public Location getLocation()
	  {
	    return this.location;
	  }
	  
	  public Block getBlock()
	  {
	    return this.block;
	  }

}
