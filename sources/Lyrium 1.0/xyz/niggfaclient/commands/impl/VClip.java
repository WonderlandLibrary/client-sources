// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import xyz.niggfaclient.utils.other.StringConversions;
import xyz.niggfaclient.utils.other.Printer;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.commands.Command;

public class VClip extends Command
{
    protected Minecraft mc;
    
    public VClip() {
        super("VClip", "", "", new String[] { "v" });
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 2) {
            Printer.addMessage(".vclip <distance>");
            return;
        }
        if (args.length == 1 && StringConversions.isNumeric(args[0])) {
            this.mc.thePlayer.setPositionAndUpdate(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Double.parseDouble(args[0]), this.mc.thePlayer.posZ);
            Printer.addMessage("Teleported " + args[0] + (args[0].contains("1") ? " block" : " blocks"));
            return;
        }
        Printer.addMessage(".vclip <distance>");
    }
}
