// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Utils.ChatUtils;

@Info(name = "help", syntax = { "" }, help = "Displays commands and info about them")
public class HelpCmd extends Cmd
{
    @Override
    public void execute(final String[] p0) throws Error {
        for (final Cmd cmd : Badlion.getWinter().theCmds.getCmds()) {
            String output = "§o." + cmd.getCmdName() + "§r";
            if (cmd.getSyntax().length != 0) {
                output = String.valueOf(output) + " " + cmd.getSyntax()[0];
                for (int i = 1; i < this.getSyntax().length; ++i) {
                    output = String.valueOf(output) + "\n    " + cmd.getSyntax()[i];
                }
            }
            String[] split;
            for (int length = (split = output.split("\n")).length, j = 0; j < length; ++j) {
                final String line = split[j];
                ChatUtils.sendMessageToPlayer(String.valueOf(line) + " - " + cmd.getHelp());
            }
        }
    }
}
