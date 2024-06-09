package me.protocol_client.modules;

import java.util.ArrayList;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C16PacketClientStatus;



public class PingSpoof extends Module{

	private ArrayList<Packet> packets = new ArrayList();
	  private TimerUtil timer = new TimerUtil();
	
	public PingSpoof() {
		super("Ping Spoof", "pingspoof", 0, Category.MISC, new String[]{""});
	}
	@EventTarget
	  public void onSendPacket(EventPacketSent event)
	  {
	    if ((((event.getPacket() instanceof C00PacketKeepAlive)) || ((event.getPacket() instanceof C16PacketClientStatus))) && 
	      (Wrapper.getPlayer().isEntityAlive()))
	    {
	      event.setCancelled(true);
	      this.packets.add(event.getPacket());
	    }
	    if (this.timer.hasReach(25000.0F))
	    {
	      Wrapper.sendPacket(new C00PacketKeepAlive());
	      this.timer.reset();
	    }
	  }
	  
	  public void onEnable()
	  {
		  EventManager.register(this);
	  }
	  
	  public void onDisable()
	  {
		  EventManager.unregister(this);
	    for (Packet packet : this.packets) {
	      Wrapper.getPlayer().sendQueue.addToSendQueue(packet);
	    }
	    this.packets.clear();
	  }
}
