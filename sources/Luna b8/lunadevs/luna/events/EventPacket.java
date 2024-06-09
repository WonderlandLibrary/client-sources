package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Cancellable;
import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;

public class EventPacket implements Event{
	
	  private EventPacketType type;
	  public static boolean sendcanel;
	  public EntityLivingBase target;
	  private boolean cancelled;
	  public static boolean recievecancel;
	  private Packet packet;
	  
	  public EventPacket(EventPacketType type, Packet packet)
	  {
	    this.type = type;
	    this.packet = packet;
	  }
	  
	  public void setCancelled(final boolean state) {
	      this.cancelled = state;
	  }
	  
	  public Packet getReceivedPacket()
	  {
	    return this.packet;
	  }
	  public Packet getPacket()
	  {
	    return this.packet;
	  }
	  
	  public void setPacket(Packet packet)
	  {
	    this.packet = packet;
	  }
	  
	  public EventPacketType getType()
	  {
	    return this.type;
	  }
	  
	  public static enum EventPacketType
	  {
	    SEND,  RECEIVE;
	  }

	public EntityLivingBase getTarget() {
        return target;
}

}

