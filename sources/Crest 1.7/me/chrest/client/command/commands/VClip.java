// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import me.chrest.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "vc", "vclip" })
public class VClip extends Command
{
    @Override
    public void runCommand(final String[] args) {
        final double posMod = Double.parseDouble(args[1]);
        final Minecraft mc = Minecraft.getMinecraft();
        ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY + posMod, ClientUtils.player().posZ);
        ClientUtils.sendMessage("Teleported " + posMod + " blocks vertically.");
    }
    
    @Override
    public String getHelp() {
        return "VClip - Vertical teleport.";
    }
}
