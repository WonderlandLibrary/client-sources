package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.network.Packet;

public class PacketSendEvent extends EventCancellable{

	 private boolean cancel;
	  private Packet packet;
	  
	  public PacketSendEvent(Packet packet)
	  {
	    this.packet = packet;
	  }
	  
	  public Packet getPacket()
	  {
	    return this.packet;
	  }
	  
	  public boolean isCancelled()
	  {
	    return this.cancel;
	  }
	  
	  public void setCancelled(boolean cancel)
	  {
	    this.cancel = cancel;
	  }
	  
	  public void setPacket(Packet packet)
	  {
	    this.packet = packet;
	  }
	
}
