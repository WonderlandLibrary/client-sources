package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class VClip extends Module{

	public VClip() {
		super("", "up", Keyboard.KEY_GRAVE, Category.OTHER, new String[]{"vclip", "up"});
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event)
	{
		if(this.isToggled())
		{
			this.setToggled(false);
		}
	}
	public void onEnable()
	{
		mc.thePlayer.boundingBox.offsetAndUpdate(0, 8, 0);
		this.setToggled(false);
	}
	public void runCmd(String cmd){
          	 try{
          		 String line = cmd.split(" ")[0];
          		 Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Float.parseFloat(line), Wrapper.getPlayer().posZ);
          		 Wrapper.tellPlayer("\2477Teleporting " + Protocol.primColor + line + "\2477 blocks " + (Float.parseFloat(line) < 0 ? "downward" : "up"));
          	 }catch(Exception e){
          		 Wrapper.invalidCommand("Up");
          		 Wrapper.tellPlayer("\2477-" + Protocol.primColor + "up \2477<amount>");
          	 }
               return;
           }
}
