// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Mod.Mod;
import net.minecraft.client.network.badlion.Utils.ChatUtils;

@Info(name = "t", syntax = { "<mod>" }, help = "Toggles mods")
public class ToggleCmd extends Cmd
{
    @Override
    public void execute(final String[] p0) throws Error {
        if (p0.length < 1) {
            this.syntaxError();
        }
        else {
            final Mod mod = Badlion.getWinter().theMods.getMod(p0[0]);
            if (mod != null) {
                mod.toggle();
            }
            else {
                ChatUtils.sendMessageToPlayer("Uknown module!");
            }
        }
    }
}
