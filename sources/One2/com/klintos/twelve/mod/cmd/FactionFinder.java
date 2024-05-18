// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import net.minecraft.client.Minecraft;

public class FactionFinder extends Cmd
{
    public FactionFinder() {
        super("ff", "Finds factions.", "ff <Radius>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/f map on");
            for (int radius = Integer.parseInt(msg), x = -radius; x < radius; ++x) {
                for (int z = -radius; z < radius; ++z) {}
            }
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
