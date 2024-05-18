package me.protocol_client.modules;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PotionSaver extends Module{

	public PotionSaver() {
		super("Potion Saver", "potionsaver", 0, Category.PLAYER,new String[]{""});
	}
	@EventTarget
	public void onPacketSent(EventPacketSent packet){
		if(!Wrapper.isMoving() && !Wrapper.getPlayer().isEating()){
		if(packet.getPacket() instanceof C03PacketPlayer){
		packet.setCancelled(true);	
		}
		}
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
}
