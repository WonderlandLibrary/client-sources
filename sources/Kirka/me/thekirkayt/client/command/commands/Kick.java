/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

@Com(names={"kick", "k"})
public class Kick
extends Command {
    @Override
    public void runCommand(String[] args) {
        ClientUtils.packet(new C03PacketPlayer.C05PacketPlayerLook(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
    }

    @Override
    public String getHelp() {
        return "\".Kick\" - Kicks you from most servers.";
    }
}

