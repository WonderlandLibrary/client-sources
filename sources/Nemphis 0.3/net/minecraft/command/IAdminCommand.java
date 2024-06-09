/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public interface IAdminCommand {
    public /* varargs */ void notifyOperators(ICommandSender var1, ICommand var2, int var3, String var4, Object ... var5);
}

