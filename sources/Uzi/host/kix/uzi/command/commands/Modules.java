package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.utilities.minecraft.Logger;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class Modules extends Command {

    public Modules() {
        super("Modules", "Allows the user to see the modules.", "mods", "listmods", "m");
    }

    @Override
    public void dispatch(String message) {
        StringBuilder mods = new StringBuilder("Modules (" + Uzi.getInstance().getModuleManager().getContents().size() + "): ");
        Uzi.getInstance().getModuleManager().getContents()
                .forEach(mod -> mods.append(mod.isEnabled() ? "\247a" : "\247c").append(mod.getName()).append("\247r, "));
        Logger.logToChat(mods.toString().substring(0, mods.length() - 2));

    }
}
