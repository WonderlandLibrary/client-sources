package host.kix.uzi.command.commands;

import host.kix.uzi.command.Command;
import host.kix.uzi.utilities.minecraft.Logger;
import host.kix.uzi.Uzi;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/4/2017.
 */
public class Toggle extends Command {

    public Toggle() {
        super("toggle", "Allows the user to toggle modules.", "t","on", "turnon", "enable");
    }

    @Override
    public void dispatch(String message) {
        String[] args = message.split(" ");
        Module module = Uzi.getInstance().getModuleManager().find(args[1]);
        if (args[1] != null) {
            module.toggle();
        } else {
            Logger.logToChat("Not a Module!");
        }
    }
}
