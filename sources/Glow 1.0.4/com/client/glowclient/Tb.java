package com.client.glowclient;

import mcp.*;
import javax.annotation.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import java.util.*;
import com.google.common.hash.*;
import com.google.common.base.*;
import java.io.*;
import net.minecraft.util.text.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class tb extends zC
{
    public tb() {
        super();
    }
    
    public void execute(final MinecraftServer minecraftServer, final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException(this.getUsage(commandSender), new Object[0]);
        }
        if (!(commandSender instanceof EntityPlayer)) {
            throw new CommandException("schematica.command.save.playersOnly", new Object[0]);
        }
        final EntityPlayer entityPlayer = (EntityPlayer)commandSender;
        boolean b = false;
        String s = String.join(" ", (CharSequence[])array);
        final String s2;
        if (array.length > 1 && (s2 = array[array.length - 1]).length() == 32) {
            s = String.join(" ", (CharSequence[])Arrays.copyOfRange(array, 0, array.length - 1));
            if (s2.equals(Hashing.md5().hashString((CharSequence)s, Charsets.UTF_8).toString())) {
                b = true;
            }
        }
        final File m = kB.b.M(entityPlayer, true);
        final File file;
        if (!Rd.M(m, file = new File(m, s))) {
            ld.H.error("{} has tried to downloadFile the file {}", (Object)entityPlayer.getName(), (Object)s);
            throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
        }
        if (!file.exists()) {
            throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
        }
        if (!b) {
            final String format = String.format("/%s %s %s", "schematicaRemove", s, Hashing.md5().hashString((CharSequence)s, Charsets.UTF_8).toString());
            final TextComponentTranslation textComponentTranslation2;
            final TextComponentTranslation textComponentTranslation = textComponentTranslation2 = new TextComponentTranslation("schematica.command.remove.areYouSure", new Object[] { s });
            textComponentTranslation2.appendSibling((ITextComponent)new TextComponentString(" ["));
            textComponentTranslation2.appendSibling(this.withStyle((ITextComponent)new TextComponentTranslation("gui.yes", new Object[0]), TextFormatting.RED, format));
            textComponentTranslation.appendSibling((ITextComponent)new TextComponentString("]"));
            commandSender.sendMessage((ITextComponent)textComponentTranslation);
            return;
        }
        if (!file.delete()) {
            throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
        }
        commandSender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.remove.schematicRemoved", new Object[] { s }));
    }
    
    public String getUsage(final ICommandSender commandSender) {
        return "schematica.command.remove.usage";
    }
    
    public String getName() {
        return "schematicaRemove";
    }
}
