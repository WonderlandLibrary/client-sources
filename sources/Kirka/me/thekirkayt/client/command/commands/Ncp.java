/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Com(names={"ncp", "testncp"})
public class Ncp
extends Command {
    @Override
    public void runCommand(String[] args) {
        if (args.length > 1) {
            ClientUtils.packet(new C01PacketChatMessage("/testncp input"));
            ClientUtils.packet(new C01PacketChatMessage("/testncp input " + args[1]));
        } else {
            ClientUtils.sendMessage(this.getHelp());
        }
    }

    @Override
    public String getHelp() {
        return "Ncp - ncp <testncp> (name) - Sets a player as a testncp target.";
    }
}

