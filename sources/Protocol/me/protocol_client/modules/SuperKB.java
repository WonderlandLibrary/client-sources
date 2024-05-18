package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreAttack;

public class SuperKB extends Module{

	public SuperKB() {
		super("SuperKB", "superkb", 0, Category.COMBAT, new String[]{""});
	}
	@EventTarget
	public void onEvent(EventPreAttack event){
		if(Wrapper.getPlayer().onGround){
		C03PacketPlayer packet = new C03PacketPlayer(this.mc.thePlayer.onGround);
	    for (int i = 0; i < 250; i++) {
	    	Wrapper.sendPacket(packet);
	}
		}
}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void onEnable(){
		EventManager.register(this);
	}
}