// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Utils.MathUtils;

@Info(name = "vclip", syntax = { "<value>" }, help = "Vclip duh")
public class VClipCmd extends Cmd
{
    @Override
    public void execute(final String[] p0) throws Error {
        if (p0.length > 1) {
            this.syntaxError();
        }
        else if (p0.length == 1) {
            if (MathUtils.isDouble(p0[0])) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Double.parseDouble(p0[0]), this.mc.thePlayer.posZ);
            }
            else {
                this.syntaxError("Please choose a valid numher!");
            }
        }
        else {
            this.syntaxError();
        }
    }
}
