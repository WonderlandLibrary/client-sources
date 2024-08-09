/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.util.text.ITextComponent;

public class CommandException
extends RuntimeException {
    private final ITextComponent component;

    public CommandException(ITextComponent iTextComponent) {
        super(iTextComponent.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
        this.component = iTextComponent;
    }

    public ITextComponent getComponent() {
        return this.component;
    }
}

