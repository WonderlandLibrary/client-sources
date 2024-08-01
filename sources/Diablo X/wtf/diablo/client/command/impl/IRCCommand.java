package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.impl.misc.IRCModule;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.irc.IrcClient;

@Command(
        name = "irc",
        description = "allows you to connect to an irc server",
        usage = "irc <message>"
)
public class IRCCommand extends AbstractCommand {

    @Override
    public void execute(String[] args) {
        final IRCModule ircModule = Diablo.getInstance().getModuleRepository().getModuleInstance(IRCModule.class);
        if (ircModule.isEnabled()) {
            final IrcClient ircClient = ircModule.getIrcClient();
            if (ircClient != null) {
                ircClient.postMessage(String.join(" ", args));
            }
        }
        else
        {
            ChatUtil.addChatMessage("Please enable the IRC Module to use this feature.");
        }
    }
}
