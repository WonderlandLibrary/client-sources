package none.command;

import none.event.events.EventChat;

public abstract class Command {
	public String input;
	public abstract String getAlias();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;
	
	public void evc(String message) {
		EventChat.addchatmessage(message);
	}
}