package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.util.player.ChatUtil;

@CommandInfo(name = "Reload", description = "Reload's the client.", aliases = "r")
public class ReloadCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        Client.INSTANCE.reload();
        Client.INSTANCE.startup();
        ChatUtil.sendMessageWithPrefix("Client has been reloaded.");
    }
}
