// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Utils.ChatUtils;
import net.minecraft.client.network.badlion.Utils.ModeUtils;

@Info(name = "Aura", syntax = { "Players / Animals / Mobs" }, help = "Aura values")
public class AuraCmd extends Cmd
{
    @Override
    public void execute(final String[] p0) throws Error {
        if (p0.length > 1) {
            this.syntaxError();
        }
        else if (p0.length == 1) {
            if (p0[0].equalsIgnoreCase("players")) {
                ModeUtils.auraP = !ModeUtils.auraP;
                ChatUtils.sendMessageToPlayer("Aura players set to " + ModeUtils.auraP);
            }
            else if (p0[0].equalsIgnoreCase("mobs")) {
                ModeUtils.auraM = !ModeUtils.auraM;
                ChatUtils.sendMessageToPlayer("Mobs players set to " + ModeUtils.auraM);
            }
            else if (p0[0].equalsIgnoreCase("animals")) {
                ModeUtils.auraA = !ModeUtils.auraA;
                ChatUtils.sendMessageToPlayer("Aura Animals set to " + ModeUtils.auraA);
            }
            else {
                this.syntaxError();
            }
        }
        else {
            this.syntaxError();
        }
    }
}
