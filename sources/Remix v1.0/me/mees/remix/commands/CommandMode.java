package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.module.*;

public class CommandMode extends Command
{
    public CommandMode() {
        super("Mode", "mode");
    }
    
    public static String replaceLast(final String string, final String toReplace, final String replacement) {
        final int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos) + replacement + string.substring(pos + toReplace.length(), string.length());
        }
        return string;
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            try {
                final Module TheMod = Base.INSTANCE.getModuleManager().getModByName(args[0]);
                if (!TheMod.getModes().isEmpty()) {
                    String Modes = "";
                    for (int i = 0; i < TheMod.getModes().size(); ++i) {
                        Modes = Modes + "\"" + TheMod.getModes().get(i).getName() + ", ";
                    }
                    Modes = Modes.replace(" ", "\" ");
                    Modes = replaceLast(Modes, ",", "");
                    Modes = replaceLast(Modes, " ", "");
                    Modes += ".";
                    MiscellaneousUtil.sendInfo("The " + TheMod.getName() + " has the modes: " + Modes);
                }
                else {
                    MiscellaneousUtil.sendInfo("The module " + args[0] + " doesn't have any modes!");
                }
            }
            catch (Exception e) {
                MiscellaneousUtil.sendInfo("Could not the module " + args[0]);
            }
        }
        else if (args.length != 2) {
            MiscellaneousUtil.sendInfo("An error has occured! Please try .Mode <Module> <Mode>");
        }
        else {
            try {
                final Module TheMod = Base.INSTANCE.getModuleManager().getModByName(args[0]);
                if (!TheMod.getModes().isEmpty()) {
                    final String Mode = args[1];
                    for (int i = 0; i < TheMod.getModes().size(); ++i) {
                        if (TheMod.getModes().get(i).getName().equalsIgnoreCase(Mode)) {
                            TheMod.setMode(TheMod.getModes().get(i));
                            MiscellaneousUtil.sendInfo("The " + TheMod.getName() + " mode has been set to " + Mode);
                        }
                        else if (i + 1 == TheMod.getModes().size()) {
                            MiscellaneousUtil.sendInfo("Could not find the mode " + Mode);
                        }
                    }
                }
                else {
                    MiscellaneousUtil.sendInfo("The module " + args[0] + " doesn't have any modes!");
                }
            }
            catch (Exception e) {
                MiscellaneousUtil.sendInfo("Could not the module " + args[0]);
            }
        }
    }
}
