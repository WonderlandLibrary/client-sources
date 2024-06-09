/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.utils.ClientUtils;

@Com(names={""})
public class UnknownCommand
extends Command {
    @Override
    public void runCommand(String[] args) {
        ClientUtils.sendMessage("Unknown Command. Type \"help\" for a list of commands.");
    }
}

