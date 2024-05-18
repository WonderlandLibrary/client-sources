// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.management.option.types.BooleanOption;
import net.minecraft.client.triton.management.option.types.NumberOption;
import net.minecraft.client.triton.utils.ClientUtils;

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
                ClientUtils.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + option.getValue());
                OptionManager.save();
            }
            else if (option instanceof NumberOption) {
                try {
                    option.setValue(Double.parseDouble(args[2]));
                    ClientUtils.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + args[2]);
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
