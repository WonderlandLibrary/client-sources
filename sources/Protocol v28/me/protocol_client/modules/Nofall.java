package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.border.WorldBorder;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Nofall extends Module{
	public Nofall(){
	 super("NoFall", "nofall", 0, Category.PLAYER, new String[]{"dsdfsdfsdfsdghgh"});
}
	WorldBorder worldborder;
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		if(Wrapper.getPlayer().fallDistance > 2f){
			Wrapper.sendPacket(new C03PacketPlayer(true));
		}
	}
	 public void onEnable(){
	    	EventManager.register(this);
	    }
	    public void onDisable(){
	    	EventManager.unregister(this);
	    }
}
