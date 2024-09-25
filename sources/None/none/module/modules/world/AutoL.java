package none.module.modules.world;

import java.util.ConcurrentModificationException;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Antibot;
import none.utils.Utils;

public class AutoL extends Module{

	public AutoL() {
		super("AutoL", "AutoL", Category.WORLD, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = {EventTick.class, EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet packet = ep.getPacket();
			if (ep.isIncoming() && packet instanceof S02PacketChat) {
				final S02PacketChat chat = (S02PacketChat) packet;
				String message = chat.getChatComponent().getUnformattedText();
				if(message.toLowerCase().contains("has been killed by " + mc.thePlayer.getName().toLowerCase() + "!") ||
						message.toLowerCase().contains("weapon could not stand against " + mc.thePlayer.getName().toLowerCase() + "!") ||
						message.toLowerCase().contains("was brutally murdered by " + mc.thePlayer.getName().toLowerCase() + "!") ||
						message.toLowerCase().contains(mc.thePlayer.getName().toLowerCase() + " could not resist killing") ||
						message.toLowerCase().contains(mc.thePlayer.getName().toLowerCase() + " gave a helping hand in ")) {
					String message1 = "";
					String[] split = message.split(" ");
					if (message.toLowerCase().contains(mc.thePlayer.getName().toLowerCase() + " gave a helping hand in ")) {
						message1 = split[split.length - 2];
						sendL(message1);
					}else {
						if (!split[1].equalsIgnoreCase(mc.thePlayer.getName())) {
							message1 = split[1];
							sendL(message1);
						}else if (!split[split.length - 1].equalsIgnoreCase(mc.thePlayer.getName() + ".") || !split[split.length].equalsIgnoreCase(mc.thePlayer.getName())) {
							message1 = split[split.length - 1];
							sendL(message1);
						}
					}
				}
			}
		}
		
		if (event instanceof EventTick) {
			
		}
	}
	
	public void sendL(String name) {
		String named = Utils.random(0, 1) == 0 ? name.substring(Utils.random(0, name.length())).toUpperCase() : name.substring(Utils.random(0, name.length())).toLowerCase();
		mc.thePlayer.sendChatMessage("L " + name);
	}
	
}
