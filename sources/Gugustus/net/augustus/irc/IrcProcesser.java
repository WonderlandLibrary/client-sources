package net.augustus.irc;

import java.util.ArrayList;

import net.augustus.Augustus;

public class IrcProcesser {
	
	public DiscordBot sender;
	
	public ArrayList<String> logs = new ArrayList<String>();
	public String latestMessage = "", lastMessageSentByMe = "";
	
	public IrcProcesser(DiscordBot bot) {
		this.sender = bot;
	}
	
	public void messageReceived(String message) {
		if((!(message.equals(lastMessageSentByMe)))) {
			latestMessage = message;
			logs.add(message);
		}
	}
	
	public void sendMessage(String message) {
		sender.sendMessage(message, Augustus.getInstance().getName());
	}
	
	public void initBot() {
		sender.init();
	}

}
