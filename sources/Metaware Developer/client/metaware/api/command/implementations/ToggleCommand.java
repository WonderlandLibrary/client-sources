package client.metaware.api.command.implementations;

import client.metaware.Metaware;
import client.metaware.api.command.Command;
import client.metaware.api.command.CommandInfo;
import client.metaware.api.command.argument.Argument;
import client.metaware.api.module.api.Module;
import client.metaware.client.Logger;
import client.metaware.impl.module.movmeent.Sprint;
import client.metaware.impl.module.render.TestOverlay;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Toggle", description = "Toggle a module.", aliases = "t")
public class ToggleCommand extends Command {

    @Override
    public boolean execute(String[] args, String label) {
        Module module = Metaware.INSTANCE.getModuleManager().getModuleByName(args[0]);
        if(args[0].equalsIgnoreCase("all")){
            for(Module m : Metaware.INSTANCE.getModuleManager().getModules()){
                if(module.isToggled() && (module.getClass() == TestOverlay.class || module.getClass() == Sprint.class)) {
                    m.toggle();
                }
            }
        }else {
            if (module != null) {
                module.toggle();
                Logger.print("&9" + module.getName() + " &7has been " + (module.isToggled() ? "&9Enabled" : "&9Disabled"));
            } else Logger.print("&7Module not found.");
        }
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new Argument(String.class, "Module"));
    }

}
