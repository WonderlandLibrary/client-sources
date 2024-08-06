package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.util.player.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

@CommandInfo(name = "Friend", description = "Add or remove a friend from the client.", aliases = {"f"})
public class FriendCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        if (args[0] != null) {
            if (args[0].equals(mc.session.getUsername())) {
                ChatUtil.sendMessageWithPrefix("No need to do that! You already have friends to hang out with!");
                return;
            }
            final boolean isFriend = Client.INSTANCE.getTargetManager().isFriend(args[0]);
            if (isFriend) {
                Client.INSTANCE.getTargetManager().remove(args[0]);
                ChatUtil.sendMessageWithPrefix("&a" + args[0] + " &7is no longer a &cfriend.");
            } else {
                Client.INSTANCE.getTargetManager().addFriend(args[0]);
                ChatUtil.sendMessageWithPrefix("&a" + args[0] + " &7is now a &afriend.");
            }
        }
    }
}
