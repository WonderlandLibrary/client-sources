package me.swezedcode.client.irc;

import me.swezedcode.client.pricbot.PircBot;
import me.swezedcode.client.utils.console.ConsoleUtils;

public class IRCBotClient extends PircBot
{
    private String username;
    
    public IRCBotClient(final String name) {
        this.setName(this.username = name);
    }
    
    public void onMessage(final String channel, final String sender, final String login, final String hostname, final String message) {
        if (sender.replace("_", "").equals("OnGround")) {
            ConsoleUtils.logIRC("§7[§c" + sender.replace("_", "") + "§7]: " + message);
        }
        else {
        	ConsoleUtils.logIRC("§f" + sender.replace("_", "") + ": " + message);
        }
    }
    
    @Override
    protected void onKick(final String channel, final String kickerNick, final String kickerLogin, final String kickerHostname, final String recipientNick, final String reason) {
        if (recipientNick.equalsIgnoreCase(this.getNick())) {
        	ConsoleUtils.logIRC("&cYou have been kicked by §f" + kickerNick + " &cfor &f" + reason);
        	ConsoleUtils.logIRC("&cRestart minecraft to get back into the IRC");
        }
        else {
        	ConsoleUtils.logIRC(String.valueOf(recipientNick) + "&c has been kicked by &f" + kickerNick + " &cfor &f" + reason);
        }
    }
    
    public void setUsername(final String name) {
        this.setName(this.username = name);
    }
}
