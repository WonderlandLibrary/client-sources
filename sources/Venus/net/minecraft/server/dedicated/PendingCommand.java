/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import net.minecraft.command.CommandSource;

public class PendingCommand {
    public final String command;
    public final CommandSource sender;

    public PendingCommand(String string, CommandSource commandSource) {
        this.command = string;
        this.sender = commandSource;
    }
}

