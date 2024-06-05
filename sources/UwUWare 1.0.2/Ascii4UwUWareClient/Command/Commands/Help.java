package Ascii4UwUWareClient.Command.Commands;

import Ascii4UwUWareClient.Command.Command;
import Ascii4UwUWareClient.Util.Helper;

public class Help extends Command {
	public Help() {
		super("Help", new String[] { "list" }, "", "sketit");
	}

	@Override
	public String execute(String[] args) {
		if (args.length == 0) {
			Helper.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
			Helper.sendMessageWithoutPrefix("\u00a7b\u00a7lAscii Client");
            Helper.sendMessageWithoutPrefix("\u00a7b.help >\u00a77 list commands");
            Helper.sendMessageWithoutPrefix("\u00a7b.bind >\u00a77 bind a module to a key");
            Helper.sendMessageWithoutPrefix("\u00a7b.t >\u00a77 toggle a module on/off");
            Helper.sendMessageWithoutPrefix("\u00a7b.friend >\u00a77 friend a player");
            Helper.sendMessageWithoutPrefix("\u00a7b.modlist >\u00a77 list all modules");
			Helper.sendMessageWithoutPrefix("\u00a7b.clientname >\u00a77 set the client name to whatever you want");
			Helper.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
		} else {
			Helper.sendMessage("Correct usage .help");
		}
		return null;
	}
}
