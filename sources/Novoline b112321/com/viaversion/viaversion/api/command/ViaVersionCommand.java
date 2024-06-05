package com.viaversion.viaversion.api.command;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public interface ViaVersionCommand {
   void registerSubCommand(ViaSubCommand var1) throws Exception;

   boolean hasSubCommand(String var1);

   @Nullable
   ViaSubCommand getSubCommand(String var1);

   boolean onCommand(ViaCommandSender var1, String[] var2);

   List onTabComplete(ViaCommandSender var1, String[] var2);
}
