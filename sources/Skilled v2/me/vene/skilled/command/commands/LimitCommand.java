package me.vene.skilled.command.commands;

import me.vene.skilled.command.*;
import me.vene.skilled.*;
import me.vene.skilled.modules.mods.combat.*;
import me.vene.skilled.modules.*;
import me.vene.skilled.modules.Module;

public class LimitCommand implements Command
{
    @Override
    public String getCommandName() {
        return "item";
    }
    
    @Override
    public void execute(final SkilledClient skilledClient, final String[] args) {
        try {
            if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") && args.length == 2) {
                return;
            }
            final AutoClicker autoClicker = (AutoClicker)Module.getModule(AutoClicker.class);
            if (args[0].equalsIgnoreCase("add")) {
                autoClicker.addToList(args[1]);
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                autoClicker.removeFromList(args[1]);
            }
        }
        catch (Exception ex) {}
    }
}
