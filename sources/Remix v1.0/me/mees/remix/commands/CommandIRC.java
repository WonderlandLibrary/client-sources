package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.*;
import me.mees.remix.irc.*;

public class CommandIRC extends Command
{
    public CommandIRC() {
        super("IRC", "irc");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 0) {
            MiscellaneousUtil.sendInfo("Commands: .irc <nick/connect/disconnect/reload>");
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("connect")) {
                MiscellaneousUtil.sendInfo("Trying to connect to the IRC!");
                Base.INSTANCE.ircManager.connect();
            }
            if (args[0].equalsIgnoreCase("disconnect")) {
                MiscellaneousUtil.sendInfo("Disconnecting from the IR!");
                Base.INSTANCE.ircManager.disconnect();
            }
            if (args[0].equalsIgnoreCase("reload")) {
                MiscellaneousUtil.sendInfo("Trying to reconnect to the IRC!");
                Base.INSTANCE.ircManager.disconnect();
                Base.INSTANCE.ircManager.connect();
            }
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("nick")) {
            try {
                Base.INSTANCE.ircManager.changeNick(args[1]);
                (Base.INSTANCE.ircManager = new IrcManager(args[1])).connect();
            }
            catch (Exception e) {
                MiscellaneousUtil.addChatMessage("Something went wrong, please enter a name that only contains numbers and letters!");
            }
        }
    }
}
