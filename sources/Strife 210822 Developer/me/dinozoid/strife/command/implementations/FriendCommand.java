package me.dinozoid.strife.command.implementations;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.argument.implementations.MultiChoiceArgument;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.target.implementations.Friend;
import me.dinozoid.strife.util.player.PlayerUtil;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Friend", aliases = "F", description = "Add a friend.")
public class FriendCommand extends Command {
    @Override
    public boolean execute(String[] args, String label) {
        switch (args[0].toLowerCase()) {
            case "add": {
                StrifeClient.INSTANCE.targetRepository().add(new Friend(args[1]));
                PlayerUtil.sendMessageWithPrefix("&c" + args[1] + " &7has been added as a friend.");
            }
            break;
            case "remove": {
                StrifeClient.INSTANCE.targetRepository().remove(args[1]);
                PlayerUtil.sendMessageWithPrefix("&c" + args[1] + " &7is no longer a friend.");
            }
            break;
        }
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new MultiChoiceArgument(String.class, "Operation", "Add", "Remove"), new Argument(String.class, "Player"));
    }
}
