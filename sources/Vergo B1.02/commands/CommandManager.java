package xyz.vergoclient.commands;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import xyz.vergoclient.commands.impl.*;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventChatMessage;
import xyz.vergoclient.event.impl.EventKey;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.network.play.server.S02PacketChat;

public class CommandManager implements OnEventInterface {
	
	// Command prefix
	public static final String prefix = ".";
	
	// All the commands
	public ArrayList<OnCommandInterface> commands = new ArrayList<>();
	
	// Create all the commands and add them to the arraylist
	public void init() {
		commands.add(new CommandGetYawAndPitch());
		commands.add(new CommandHelp());
		commands.add(new CommandConfig());
		commands.add(new CommandBind());
	}
	
	// Event hook so we can use the commands
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventChatMessage && e.isPre()) {
			EventChatMessage event = (EventChatMessage)e;
			
			// If chat message starts with prefix
			if (event.message.startsWith(prefix)) {
				
				// Cancel event
				e.setCanceled(true);
				for (OnCommandInterface command : commands) {
					
					// Split chat message
					String[] commandSplit = event.message.split(" ");
					
					// Command found
					if (commandSplit[0].equalsIgnoreCase(prefix + command.getName())) {
						command.onCommand(commandSplit);
						
						// Return
						return;
					}
				}

				ChatUtils.addChatMessage("Command unrecognized. Please use .help for all valid commands.");
				
			}
		}
		else if (e instanceof EventKey) {
			EventKey event = (EventKey)e;
			if (event.key == Keyboard.KEY_PERIOD) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiChat("."));
			}
		}
		else if (e instanceof EventReceivePacket && e.isPre()) {
			EventReceivePacket event = (EventReceivePacket)e;
			if (event.packet instanceof S02PacketChat) {
				S02PacketChat packet = (S02PacketChat)event.packet;
				try {
					if (packet.chatComponent.getChatStyle().getChatClickEvent().getAction() == Action.RUN_COMMAND || packet.chatComponent.getChatStyle().getChatClickEvent().getAction() == Action.SUGGEST_COMMAND) {
						if (packet.chatComponent.getChatStyle().getChatClickEvent().getValue().startsWith(CommandManager.prefix)) {
							packet.chatComponent.getChatStyle().getChatClickEvent().setValue(CommandManager.prefix + "say " + packet.chatComponent.getChatStyle().getChatClickEvent().getValue());
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
}
