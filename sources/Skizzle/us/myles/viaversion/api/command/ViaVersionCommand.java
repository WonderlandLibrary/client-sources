/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.command;

import java.util.List;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.command.ViaSubCommand;

public interface ViaVersionCommand {
    public void registerSubCommand(ViaSubCommand var1) throws Exception;

    public boolean hasSubCommand(String var1);

    @Nullable
    public ViaSubCommand getSubCommand(String var1);

    public boolean onCommand(ViaCommandSender var1, String[] var2);

    public List<String> onTabComplete(ViaCommandSender var1, String[] var2);
}

