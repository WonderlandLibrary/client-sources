package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.network.Packet;

public class PacketRecieveEvent extends EventCancellable implements Event{

	 private Packet packet = null;
	  
	  public PacketRecieveEvent(Packet packet)
	  {
	    setPacket(packet);
	  }
	  
	  public Packet getPacket()
	  {
	    return this.packet;
	  }
	  
	  public void setPacket(Packet packet)
	  {
	    this.packet = packet;
	  }
	
}
