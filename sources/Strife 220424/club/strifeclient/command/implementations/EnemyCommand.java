package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.util.player.ChatUtil;

@CommandInfo(name = "Enemy", description = "Add or remove a enemy from the client.", aliases = {"target", "e"})
public class EnemyCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        if (args[0] != null) {
            if (args[0].equals(mc.session.getUsername())) {
                ChatUtil.sendMessageWithPrefix("No need to do that! Don't make an enemy out of yourself!");
                return;
            }
            final boolean isEnemy = Client.INSTANCE.getTargetManager().isEnemy(args[0]);
            ChatUtil.sendMessageWithPrefix(Client.INSTANCE.getTargetManager().getAllEnemies());
            if (isEnemy) {
                Client.INSTANCE.getTargetManager().remove(args[0]);
                ChatUtil.sendMessageWithPrefix("&c" + args[0] + " &7is no longer a &aenemy.");
            } else {
                Client.INSTANCE.getTargetManager().addEnemy(args[0]);
                ChatUtil.sendMessageWithPrefix("&c" + args[0] + " &7is now a &cenemy.");
            }
        }
    }
}
