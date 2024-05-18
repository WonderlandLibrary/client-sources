package me.protocol_client.commands;

public interface CommandEZ {
	String getAlias();
	String getDescription();
	String getSyntax();

	void onCommandSent(String command, String[] args) throws Exception;
}
