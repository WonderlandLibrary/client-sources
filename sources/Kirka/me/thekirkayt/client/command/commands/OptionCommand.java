/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.client.option.types.NumberOption;
import me.thekirkayt.utils.ClientUtils;

public class OptionCommand
extends Command {
    @Override
    public void runCommand(String[] args) {
        if (args.length < 2) {
            ClientUtils.sendMessage(OptionCommand.getHelpString());
            return;
        }
        Module mod = ModuleManager.getModule(args[0]);
        if (!mod.getId().equalsIgnoreCase("option")) {
            Option option = OptionManager.getOption(args[1], mod.getId());
            if (option instanceof BooleanOption) {
                BooleanOption booleanOption;
                booleanOption.setValue((Boolean)(booleanOption = (BooleanOption)option).getValue() == false);
                ClientUtils.sendMessage(String.valueOf(String.valueOf(option.getDisplayName())) + " set to " + option.getValue());
                OptionManager.save();
            } else if (option instanceof NumberOption) {
                try {
                    option.setValue(Double.parseDouble(args[2]));
                    ClientUtils.sendMessage(String.valueOf(String.valueOf(option.getDisplayName())) + " set to " + args[2]);
                }
                catch (NumberFormatException e) {
                    ClientUtils.sendMessage("Number option format error.");
                }
                OptionManager.save();
            } else {
                ClientUtils.sendMessage("Option not recognized.");
            }
        } else {
            ClientUtils.sendMessage(OptionCommand.getHelpString());
        }
    }

    public static String getHelpString() {
        return "Set option - (modname) (option name) [value]";
    }
}

