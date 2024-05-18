// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Mod.Mod;
import net.minecraft.client.network.badlion.Utils.ChatUtils;

@Info(name = "mods", syntax = { "" }, help = "Displays all modules")
public class Mods extends Cmd
{
    @Override
    public void execute(final String[] p0) throws Error {
        final StringBuilder list = new StringBuilder("Mods (" + Badlion.getWinter().theMods.getMods().size() + "): ");
        for (final Mod mod : Badlion.getWinter().theMods.getMods()) {
            list.append(mod.isEnabled() ? "§a" : "§c").append(mod.getModName()).append("§f, ");
        }
        ChatUtils.sendMessageToPlayer(list.toString().substring(0, list.toString().length() - 2));
    }
}
