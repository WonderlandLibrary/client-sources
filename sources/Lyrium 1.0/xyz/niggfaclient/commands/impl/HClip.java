// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import net.minecraft.client.Minecraft;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.commands.Command;

public class HClip extends Command
{
    public HClip() {
        super("HClip", "", "", new String[] { "hc" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 2) {
            Printer.addMessage(".hclip <distance>");
        }
        if (args.length == 1) {
            Minecraft.getMinecraft().thePlayer.noClip = true;
            Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.getLookVec().xCoord * Double.parseDouble(args[0]), Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ + Minecraft.getMinecraft().thePlayer.getLookVec().zCoord * Double.parseDouble(args[0]));
            Printer.addMessage("Teleported " + args[0] + " blocks");
        }
    }
}
