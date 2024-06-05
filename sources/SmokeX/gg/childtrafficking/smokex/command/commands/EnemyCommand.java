// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Enemies", description = "add enemies", usage = ".e add [ign] | .e remove [ign] | .e clear | .e list", aliases = { "e" })
public final class EnemyCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "add": {
                    ChatUtils.addChatMessage("Added enemies " + args[1]);
                    SmokeXClient.getInstance().getPlayerManager().addEnemy(args[1]);
                    break;
                }
                case "remove": {
                    ChatUtils.addChatMessage("Removed enemies " + args[1]);
                    SmokeXClient.getInstance().getPlayerManager().removeEnemy(args[1]);
                    break;
                }
                case "clear": {
                    ChatUtils.addChatMessage("Cleared enemies");
                    SmokeXClient.getInstance().getPlayerManager().clearEnemies();
                    break;
                }
                case "list": {
                    if (!SmokeXClient.getInstance().getPlayerManager().getEnemies().isEmpty()) {
                        SmokeXClient.getInstance().getPlayerManager().getEnemies().forEach(enemy -> ChatUtils.addChatMessage("- " + enemy));
                        break;
                    }
                    ChatUtils.addChatMessage("The enemies list is empty");
                    break;
                }
            }
        }
    }
}
