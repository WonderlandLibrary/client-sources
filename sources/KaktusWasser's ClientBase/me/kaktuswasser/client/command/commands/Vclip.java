// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.utilities.Logger;
import net.minecraft.client.Minecraft;

public class Vclip extends Command
{
    public Vclip() {
        super("vclip", "<blocks>");
    }
    
    @Override
    public void run(final String message) {
        final String[] arguments = message.split(" ");
        final String blockChangeString = arguments[1];
        try {
            final double newHeight = Double.parseDouble(blockChangeString);
            Minecraft.getMinecraft().thePlayer.func_174826_a(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0, newHeight, 0.0));
            Logger.writeChat(String.format("You've teleported %s block%s", newHeight, (newHeight > 1.0 || newHeight < -1.0) ? "s" : ""));
        }
        catch (NumberFormatException e) {
            Logger.writeChat(String.format("\"%s\" is not a number.", blockChangeString));
        }
    }
}
