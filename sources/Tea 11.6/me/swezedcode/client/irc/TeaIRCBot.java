package me.swezedcode.client.irc;

import me.swezedcode.client.pricbot.NickAlreadyInUseException;
import me.swezedcode.client.pricbot.PircBot;
import me.swezedcode.client.utils.console.ConsoleUtils;

public class TeaIRCBot extends PircBot implements Runnable {
	private String channel = "#ZuesClient";
	private String server = "irc.mibbit.net";
	private boolean _autoNickChange;
	public static String username;
	public static IRCBotClient bot;

	public TeaIRCBot(String username) {
		try {
			String firstname = username.substring(0, 1);
			int i = Integer.parseInt(firstname);

			username = "MC" + username;
		} catch (NumberFormatException localNumberFormatException) {
		}
	}

	public void run() {
		try {
			bot = new IRCBotClient(username);

			bot.setVerbose(true);
			bot.connect(this.server);
			bot.joinChannel(this.channel);
		} catch (NickAlreadyInUseException localNickAlreadyInUseException) {
		} catch (Exception e) {
			ConsoleUtils.logChat("Error in IRC chat");
			e.printStackTrace();
		}
	}

	public void send(String message) {
		bot.sendMessage(this.channel, message);
	}

	public String getUsername() {
		return username;
	}

	public void quit() {
		bot.disconnect();
	}

	public void setUsername(String s) {
		username = s;
		setName(s);
	}
}
