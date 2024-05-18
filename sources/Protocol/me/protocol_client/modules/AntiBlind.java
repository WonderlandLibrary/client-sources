package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.files.allfiles.Keybinds;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.potion.Potion;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class AntiBlind extends Module{

	public AntiBlind() {
		super("Anti Blind", "antiblind", 0, Category.RENDER, new String[]{"bind", "binds"});
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
		Wrapper.getPlayer().removePotionEffect(Potion.confusion.getId());
		Wrapper.getPlayer().removePotionEffect(Potion.blindness.getId());
	}
	public void runCmd(String s){
		try{
		String[] args = s.split(" ");
			String modName = "";
			String keyName = "";
	     modName = args[0];
	     keyName = args[1];
	     Module module = Protocol.module.getModule(modName);
		    
		    
		    if (module.name.equalsIgnoreCase("null"))
		    {
		      Wrapper.tellPlayer("\2477Invalid Module.");
		      return;
		    }
		    if (keyName == "none" && !modName.equals("zoot"))
		    {
		    	Keybinds.bindKey(module, Keyboard.KEY_NONE);
		      Wrapper.tellPlayer(Protocol.primColor + module.name + "\2477's bind has been cleared.");
		      module.keyCode = 0;
		      return;
		    }
		    module.keyCode = Keyboard.getKeyIndex(keyName.toUpperCase());
		    if (Keyboard.getKeyIndex(keyName.toUpperCase()) == 0 && !keyName.equalsIgnoreCase("none")) {
		      Wrapper.tellPlayer("§7Couldn't find that key.");
		    } else {
		    	if(keyName.equalsIgnoreCase("none")){
		    		module.keyCode = 0;
		    	}
		    	Keybinds.bindKey(module, Keyboard.getKeyIndex(keyName.toUpperCase()));
		      Wrapper.tellPlayer(Protocol.primColor + module.name + " §7bound to " + Protocol.primColor + keyName);
		    }
			return;
	}catch(Exception e){
		Wrapper.invalidCommand("Bind");
		Wrapper.tellPlayer("\2477-"  + Protocol.primColor + "Bind\2477 <mod> <key>");
	}
	}
}
