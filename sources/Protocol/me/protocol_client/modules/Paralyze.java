package me.protocol_client.modules;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;
import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.DamageSource;

public class Paralyze extends Module{

	public Paralyze() {
		super("Paralyze", "paralyze", 0, Category.PLAYER,new String[]{""});
	}
	
	 public void onEnable()
	  {
	    EventManager.register(this);
	  }
	  
	  public void onDisable()
	  {
	    EventManager.unregister(this);
	  }
	  
	  @EventTarget
	  public void onEvent(EventPreMotionUpdates event)
	  {
	    for (int i = 0; i < 15; i++) {
	    	Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 1.0E-5D, Wrapper.getPlayer().posZ, false));
	        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
	    }
	  }
}
