package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.*;
import me.satisfactory.base.module.*;

public class CommandToggle extends Command
{
    public CommandToggle() {
        super("Toggle", "toggle");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length != 1) {
            MiscellaneousUtil.sendInfo("An error has occured! Please try .Toggle <Module>");
        }
        else {
            final Module module = Base.INSTANCE.getModuleManager().getModByName(args[0]);
            if (module != null) {
                module.toggle();
            }
            else {
                MiscellaneousUtil.sendInfo("Could not find the module " + args[0] + "!");
            }
        }
    }
}
