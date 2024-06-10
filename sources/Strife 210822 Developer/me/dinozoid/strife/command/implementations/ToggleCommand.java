package me.dinozoid.strife.command.implementations;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.util.player.PlayerUtil;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Toggle", description = "Toggle a module.", aliases = "t")
public class ToggleCommand extends Command {

    @Override
    public boolean execute(String[] args, String label) {
        Module module = StrifeClient.INSTANCE.moduleRepository().moduleBy(args[0]);
        if(module != null) {
            module.toggle();
            PlayerUtil.sendMessageWithPrefix("&c" + module.name() + " &7has been " + (module.toggled() ? "&cEnabled" : "&cDisabled"));
        } else PlayerUtil.sendMessageWithPrefix("&7Module not found.");
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new Argument(String.class, "Module"));
    }

}
