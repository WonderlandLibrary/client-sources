package me.valk.command;

import java.util.List;

import me.valk.Vital;
import me.valk.utils.chat.ChatBuilder;
import me.valk.utils.chat.ChatColor;

public class CommandListener {

	public void onCommandRan(List<String> args){}
	
	public void addChat(String message){
		new ChatBuilder()
		.appendText("[", ChatColor.DARK_GRAY)
		.appendText(Vital.getVital().getClient().getName(), Vital.getVital().getClient().getData().getDisplayColor())
		.appendText("]", ChatColor.DARK_GRAY)
		.appendText(" " + message, ChatColor.GRAY).send();
	}
	
	public void addChat(ChatBuilder chatBuilder){
		new ChatBuilder()
		.appendText("[", ChatColor.DARK_GRAY)
		.appendText(Vital.getVital().getClient().getName(), Vital.getVital().getClient().getData().getDisplayColor())
		.appendText("]", ChatColor.DARK_GRAY)
		.appendText(" ", ChatColor.GRAY)
		.appendMessage(chatBuilder.getMessage()).send();
	}
	
	public void error(String message){
		new ChatBuilder()
		.appendText("[", ChatColor.DARK_GRAY)
		.appendText(Vital.getVital().getClient().getName(), Vital.getVital().getClient().getData().getDisplayColor())
		.appendText("]", ChatColor.DARK_GRAY)
		.appendText(" " + message, ChatColor.RED).send();
	}
	
	public void error(ChatBuilder chatBuilder){
		new ChatBuilder()
		.appendText("[", ChatColor.DARK_GRAY)
		.appendText(Vital.getVital().getClient().getName(), Vital.getVital().getClient().getData().getDisplayColor())
		.appendText("]", ChatColor.DARK_GRAY)
		.appendText(" ", ChatColor.RED)
		.appendMessage(chatBuilder.getMessage()).send();
	}
	
}
