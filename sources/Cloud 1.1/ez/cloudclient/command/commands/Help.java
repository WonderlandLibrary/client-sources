package ez.cloudclient.command.commands;

import ez.cloudclient.command.Command;
import ez.cloudclient.command.CommandManager;
import ez.cloudclient.util.MessageUtil;


public class Help extends Command {

    /*
     * Added by RemainingToast 12/07/20
     */

    StringBuilder sb = new StringBuilder();
    Integer i = 0;

    public Help() {
        super("Help", "Show commands", "help");
    }

    @Override
    protected void call(String[] args) {
        if (mc.player == null) return;
        if (args.length < 1) {
            sb.replace(0, sb.capacity(), "");
            for (Command command : CommandManager.commands) {
                i++;
                if (command.getName().equalsIgnoreCase("Help")) continue;
                sb.append(command.getName()).append(", ");
                if (CommandManager.commands.size() == i) {
                    i = 0;
                    break;
                }
            }
            sb.replace(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 1, "");
            MessageUtil.sendMessage(sb.toString(), MessageUtil.Color.GRAY);
        }
    }
}
