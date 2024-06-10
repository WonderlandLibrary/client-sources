package me.dinozoid.strife.command.implementations;

import com.sun.org.apache.xpath.internal.Arg;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.util.player.PlayerUtil;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Bind", description = "Bind a key to a module.")
public class BindCommand extends Command {
    @Override
    public boolean execute(String[] args, String label) {
        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        Module module = StrifeClient.INSTANCE.moduleRepository().moduleBy(args[0]);
        if(module != null) {
            if(args[1].equalsIgnoreCase("none") || args[1].equalsIgnoreCase("reset")) {
                module.key(0);
                PlayerUtil.sendMessageWithPrefix("&7The bind of &c" + module.name() + " &7has been reset.");
            } else {
                module.key(key);
                PlayerUtil.sendMessageWithPrefix("&c" + module.name() + " &7has been bound to &c" + Keyboard.getKeyName(key));
            }
        } else PlayerUtil.sendMessageWithPrefix("&7Module not found.");
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new Argument(String.class, "Module"), new Argument(String.class, "Key"));
    }
}
