package exhibition.module.impl.other;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventChat;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.combat.AntiBot;
import exhibition.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;

public class AutoL extends Module {

	public static ArrayList<String> fuckTextArrayList = new ArrayList<String>();
	
	public AutoL(ModuleData data) {
		super(data);
	}

	@Override
	@RegisterEvent(events = { EventChat.class })
	public void onEvent(Event var1) {
		if(var1 instanceof EventChat) {
			EventChat eventChat = (EventChat)var1.cast();
	        	  String message = eventChat.getText();
	        		if(message.toLowerCase().contains("was killed by " + mc.thePlayer.getName().toLowerCase() + ".") ||
	        				message.toLowerCase().contains("was thrown into the void by " + mc.thePlayer.getName().toLowerCase() + ".")||
	        				message.toLowerCase().contains("was thrown off a cliff by " + mc.thePlayer.getName().toLowerCase() + ".")){
	    					Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C01PacketChatMessage(String.format(fuckTextArrayList.get(new Random(System.nanoTime()).nextInt(fuckTextArrayList.size())),"")));
	        		}
	      }
	}

}
