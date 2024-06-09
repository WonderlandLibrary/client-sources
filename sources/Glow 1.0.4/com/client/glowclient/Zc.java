package com.client.glowclient;

import mcp.*;
import javax.annotation.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class zC extends CommandBase
{
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public <T extends ITextComponent> T withStyle(final T t, final TextFormatting color, @Nullable final String s) {
        final Style style = new Style();
        style.setColor(color);
        if (s != null) {
            style.setClickEvent(new ClickEvent(ClickEvent$Action.RUN_COMMAND, s));
        }
        t.setStyle(style);
        return t;
    }
    
    public zC() {
        super();
    }
    
    public boolean checkPermission(final MinecraftServer minecraftServer, final ICommandSender commandSender) {
        return super.checkPermission(minecraftServer, commandSender) || (commandSender instanceof EntityPlayerMP && this.getRequiredPermissionLevel() <= 0);
    }
}
