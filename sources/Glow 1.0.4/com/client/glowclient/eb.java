package com.client.glowclient;

import mcp.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.text.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import net.minecraft.block.*;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Eb extends zC
{
    public String getUsage(final ICommandSender commandSender) {
        return "schematica.command.replace.usage";
    }
    
    public Eb() {
        super();
    }
    
    public void execute(final MinecraftServer minecraftServer, final ICommandSender commandSender, final String[] array) throws CommandException {
        final XA g;
        if ((g = eb.g) == null) {
            throw new CommandException("schematica.command.replace.noSchematic", new Object[0]);
        }
        if (array.length != 2) {
            throw new CommandException("schematica.command.replace.usage", new Object[0]);
        }
        try {
            final BlockStateMatcher m = Wc.M(Wc.M(array[0]));
            final Nd i = Wc.M(array[1]);
            commandSender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.replace.success", new Object[] { g.replaceBlock(m, Wc.M(i.b.getDefaultState()), i.B) }));
        }
        catch (Exception ex) {
            ld.H.error("Something went wrong!", (Throwable)ex);
            throw new CommandException(ex.getMessage(), new Object[0]);
        }
    }
    
    public String getName() {
        return "schematicaReplace";
    }
    
    public List<String> getTabCompletions(final MinecraftServer minecraftServer, final ICommandSender commandSender, final String[] array, @Nullable final BlockPos blockPos) {
        if (array.length < 3) {
            return (List<String>)getListOfStringsMatchingLastWord((String[])array, (Collection)Block.REGISTRY.getKeys());
        }
        return Collections.emptyList();
    }
}
