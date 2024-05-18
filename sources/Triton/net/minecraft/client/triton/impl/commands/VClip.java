package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.utils.ClientUtils;

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