package client.metaware.api.command.implementations;

import client.metaware.Metaware;
import client.metaware.api.command.Command;
import client.metaware.api.command.CommandInfo;
import client.metaware.api.command.argument.Argument;
import client.metaware.api.module.api.Module;
import client.metaware.client.Logger;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Bind", description = "Bind a key to a module.")
public class BindCommand extends Command {
    @Override
    public boolean execute(String[] args, String label) {
        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        Module module = Metaware.INSTANCE.getModuleManager().getModuleByName(args[0]);
        if(module != null) {
            if(args[1].equalsIgnoreCase("none") || args[1].equalsIgnoreCase("reset")) {
                module.setKey(0);
                Logger.print("&7The bind of &9" + module.getName() + " &7has been reset.");
            } else {
                module.setKey(key);
                Logger.print("&9" + module.getName() + " &7has been bound to &9" + Keyboard.getKeyName(key));
            }
        } else Logger.print("&7Module not found.");
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new Argument(String.class, "Module"), new Argument(String.class, "Key"));
    }
}
