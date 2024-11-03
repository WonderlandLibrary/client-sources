package com.viaversion.viaversion.api.command;

import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ViaVersionCommand {
   void registerSubCommand(ViaSubCommand var1);

   boolean hasSubCommand(String var1);

   @Nullable
   ViaSubCommand getSubCommand(String var1);

   boolean onCommand(ViaCommandSender var1, String[] var2);

   List<String> onTabComplete(ViaCommandSender var1, String[] var2);

   void showHelp(ViaCommandSender var1);
}
