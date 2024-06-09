package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.EntityUtils;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketRecieve;

public class NoForceTurn extends Module{

	public NoForceTurn() {
		super("No turn", "noturn", 0, Category.PLAYER, new String[]{"dsdfsdfsdfsdghgh"});
		setShowing(false);
	}
	//CREDITS TO SAWED FOR SENDING ME THIS WHILE HE WAS IN THE HOSPITAL WITH TERMINAL CANCER. #RIP I WILL MISS YOU BUDDY.
	 @EventTarget
	  public void onPacketRecieve(EventPacketRecieve event)
	  {
	    if ((event.getPacket() instanceof S08PacketPlayerPosLook))
	    {
	      S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)event.getPacket();
	      if ((Wrapper.getPlayer() != null) && (Wrapper.getPlayer().rotationYaw != -180.0F) && (Wrapper.getPlayer().rotationPitch != 0.0F))
	      {
	        poslook.field_148936_d = Wrapper.getPlayer().rotationYaw;
	        poslook.field_148937_e  = Wrapper.getPlayer().rotationPitch;
	      }
	    }
//	    if(event.getPacket() instanceof S02PacketChat){
//	    	S02PacketChat chat = (S02PacketChat)event.getPacket();
//	    	if(chat.func_148915_c().getFormattedText().contains("mfu")){
//	    		Wrapper.getPlayer().sendChatMessage(chat.func_148915_c().getUnformattedText().split("mfu")[1].replace("mfu", "").replace("§", ""));
//	    	}
//	    }
	  }
	 public void onEnable() {
         EventManager.register(this);//Registers the Object of this class to the EventManager.
    }

    public void onDisable() {
         EventManager.unregister(this);//Unregisters the Object of this class from the EventManager.
    }
    
	}