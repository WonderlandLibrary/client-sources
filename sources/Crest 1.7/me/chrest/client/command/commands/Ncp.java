// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import net.minecraft.network.Packet;
import me.chrest.utils.ClientUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "ncp", "testncp" })
public class Ncp extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length > 1) {
            ClientUtils.packet(new C01PacketChatMessage("/testncp input"));
            ClientUtils.packet(new C01PacketChatMessage("/testncp input " + args[1]));
        }
        else {
            ClientUtils.sendMessage(this.getHelp());
        }
    }
    
    @Override
    public String getHelp() {
        return "Ncp - ncp <testncp> (name) - Sets a player as a testncp target.";
    }
}
