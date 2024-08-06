package club.strifeclient.command.implementations;

import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.util.player.ChatUtil;
import club.strifeclient.util.system.StringUtil;

@CommandInfo(name = "Username", description = "Copy's your in-game username.", aliases = "user")
public class UsernameCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        StringUtil.copyToClipboard(mc.session.getUsername());
        ChatUtil.sendMessageWithPrefix("Your username has been copied to the clipboard.");
    }
}
