package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class FastBreak extends Module{

	public FastBreak() {
		super("FastBreak", "fastbreak", 0, Category.PLAYER, new String[]{"fastbreak", "break"});
	}
	public final ClampedValue<Float> speed = new ClampedValue<>("fastbreak_speed", 1.5f, 1.2f, 5f);
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		this.setDisplayName("FastBreak[" + this.speed.getValue().longValue() + "]");
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void runCmd(String s){
		try{
		String[] args = s.split(" ");
		if(args[0].startsWith("speed")){
			float speed = Float.parseFloat(args[1]);
			this.speed.setValue(speed);
			Wrapper.tellPlayer("\2477Fastbreak speed set to " + Protocol.primColor + speed);
			return;
		}
		Wrapper.tellPlayer("\2477Invalid commmand. -" + Protocol.primColor + "fastbreak \2477 speed <speed>");
		}catch(Exception e){
			Wrapper.tellPlayer("\2477Invalid command. -" + Protocol.primColor + "fastbreak \2477 speed <speed>");
		}
	}
}
