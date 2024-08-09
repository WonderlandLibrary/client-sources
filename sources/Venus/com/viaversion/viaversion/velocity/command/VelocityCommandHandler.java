/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.command.CommandInvocation
 *  com.velocitypowered.api.command.SimpleCommand
 *  com.velocitypowered.api.command.SimpleCommand$Invocation
 */
package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandInvocation;
import com.velocitypowered.api.command.SimpleCommand;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.velocity.command.VelocityCommandSender;
import com.viaversion.viaversion.velocity.command.subs.ProbeSubCmd;
import java.util.List;

public class VelocityCommandHandler
extends ViaCommandHandler
implements SimpleCommand {
    public VelocityCommandHandler() {
        try {
            this.registerSubCommand(new ProbeSubCmd());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void execute(SimpleCommand.Invocation invocation) {
        this.onCommand(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }

    public List<String> suggest(SimpleCommand.Invocation invocation) {
        return this.onTabComplete(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }

    public boolean hasPermission(SimpleCommand.Invocation invocation) {
        return invocation.source().hasPermission("viaversion.admin");
    }

    public boolean hasPermission(CommandInvocation commandInvocation) {
        return this.hasPermission((SimpleCommand.Invocation)commandInvocation);
    }

    public List suggest(CommandInvocation commandInvocation) {
        return this.suggest((SimpleCommand.Invocation)commandInvocation);
    }

    public void execute(CommandInvocation commandInvocation) {
        this.execute((SimpleCommand.Invocation)commandInvocation);
    }
}

