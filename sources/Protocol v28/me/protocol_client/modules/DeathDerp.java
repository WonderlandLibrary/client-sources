package me.protocol_client.modules;

import net.minecraft.entity.Entity;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class DeathDerp extends Module{

	public DeathDerp() {
		super("Death Derp", "deathderp", 0, Category.MISC, new String[]{"dsdfsdfsdfsdghgh"});
		setShowing(false);
	}
	
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		if(Wrapper.getPlayer().getHealth() <= 0){
			Wrapper.getPlayer().addVelocity(Wrapper.getPlayer().motionX / 2, 0.6, Wrapper.getPlayer().motionZ / 2);
		}
	}
}
