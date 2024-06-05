package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {
   public String getCommandName() {
      return "save-all";
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.save.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      MinecraftServer var3 = MinecraftServer.getServer();
      var1.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
      if(var3.getConfigurationManager() != null) {
         var3.getConfigurationManager().saveAllPlayerData();
      }

      try {
         for(int var4 = 0; var4 < var3.worldServers.length; ++var4) {
            if(var3.worldServers[var4] != null) {
               WorldServer var5 = var3.worldServers[var4];
               boolean var6 = var5.disableLevelSaving;
               var5.disableLevelSaving = false;
               var5.saveAllChunks(true, (IProgressUpdate)null);
               var5.disableLevelSaving = var6;
            }
         }

         if(var2.length > 0 && "flush".equals(var2[0])) {
            var1.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));

            for(int var8 = 0; var8 < var3.worldServers.length; ++var8) {
               if(var3.worldServers[var8] != null) {
                  WorldServer var9 = var3.worldServers[var8];
                  boolean var10 = var9.disableLevelSaving;
                  var9.disableLevelSaving = false;
                  var9.saveChunkData();
                  var9.disableLevelSaving = var10;
               }
            }

            var1.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
         }
      } catch (MinecraftException var7) {
         notifyOperators(var1, this, "commands.save.failed", new Object[]{var7.getMessage()});
         return;
      }

      notifyOperators(var1, this, "commands.save.success", new Object[0]);
   }

   private static MinecraftException a(MinecraftException var0) {
      return var0;
   }
}
