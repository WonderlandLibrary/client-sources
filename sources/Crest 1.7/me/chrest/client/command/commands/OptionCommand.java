// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import me.chrest.client.option.Option;
import me.chrest.client.module.Module;
import me.chrest.client.option.types.NumberOption;
import me.chrest.client.option.types.BooleanOption;
import me.chrest.client.option.OptionManager;
import me.chrest.client.module.ModuleManager;
import me.chrest.utils.ClientUtils;
import me.chrest.client.command.Command;

public class OptionCommand extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length < 2) {
            ClientUtils.sendMessage(getHelpString());
            return;
        }
        final Module mod = ModuleManager.getModule(args[0]);
        if (!mod.getId().equalsIgnoreCase("Null")) {
            final Option option = OptionManager.getOption(args[1], mod.getId());
            if (option instanceof BooleanOption) {
                final BooleanOption booleanOption = (BooleanOption)option;
                booleanOption.setValue(Boolean.valueOf(!booleanOption.getValue()));
                ClientUtils.sendMessage(String.valueOf(String.valueOf(option.getDisplayName())) + " set to " + option.getValue());
                OptionManager.save();
            }
            else if (option instanceof NumberOption) {
                try {
                    option.setValue(Double.parseDouble(args[2]));
                    ClientUtils.sendMessage(String.valueOf(String.valueOf(option.getDisplayName())) + " set to " + args[2]);
                }
                catch (NumberFormatException e) {
                    ClientUtils.sendMessage("Number option format error.");
                }
                OptionManager.save();
            }
            else {
                ClientUtils.sendMessage("Option not recognized.");
            }
        }
        else {
            ClientUtils.sendMessage(getHelpString());
        }
    }
    
    public static String getHelpString() {
        return "Set option - (modname) (option name) <value>";
    }
}
