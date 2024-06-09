package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;

public class AntiHurtcam extends Module{
	public AntiHurtcam() {
		super("Anti Hurtcam", "antihurtcam", 0, Category.PLAYER, new String[]{"toggle", "t"});
		setShowing(false);
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void runCmd(String cmd){
		for(Module mod : Protocol.getModules()){
			if(mod.alias.equalsIgnoreCase(cmd) || mod.getName().equalsIgnoreCase(cmd)){
				mod.toggle();
				Wrapper.tellPlayer(Protocol.primColor + mod.getName() + "\2477 has been toggled.");
				return;
			}
		}
		Wrapper.invalidCommand("Toggle");
		Wrapper.tellPlayer("\2477-"  + Protocol.primColor +  "t \2477<mod>");
	}
}
