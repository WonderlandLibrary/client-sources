package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase {
   public String getCommandName() {
      return "time";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.time.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length > 1) {
         if(var2[0].equals("set")) {
            int var6;
            if(var2[1].equals("day")) {
               var6 = 1000;
            } else if(var2[1].equals("night")) {
               var6 = 13000;
            } else {
               var6 = parseInt(var2[1], 0);
            }

            this.setTime(var1, var6);
            notifyOperators(var1, this, "commands.time.set", new Object[]{Integer.valueOf(var6)});
            return;
         }

         if(var2[0].equals("add")) {
            int var5 = parseInt(var2[1], 0);
            this.addTime(var1, var5);
            notifyOperators(var1, this, "commands.time.added", new Object[]{Integer.valueOf(var5)});
            return;
         }

         if(var2[0].equals("query")) {
            if(var2[1].equals("daytime")) {
               int var4 = (int)(var1.getEntityWorld().getWorldTime() % 2147483647L);
               var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var4);
               notifyOperators(var1, this, "commands.time.query", new Object[]{Integer.valueOf(var4)});
               return;
            }

            if(var2[1].equals("gametime")) {
               int var3 = (int)(var1.getEntityWorld().getTotalWorldTime() % 2147483647L);
               var1.setCommandStat(CommandResultStats$Type.QUERY_RESULT, var3);
               notifyOperators(var1, this, "commands.time.query", new Object[]{Integer.valueOf(var3)});
               return;
            }
         }
      }

      throw new WrongUsageException("commands.time.usage", new Object[0]);
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, new String[]{"set", "add", "query"}):(var2.length == 2 && var2[0].equals("set")?getListOfStringsMatchingLastWord(var2, new String[]{"day", "night"}):(var2.length == 2 && var2[0].equals("query")?getListOfStringsMatchingLastWord(var2, new String[]{"daytime", "gametime"}):null));
   }

   protected void setTime(ICommandSender var1, int var2) {
      for(int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
         MinecraftServer.getServer().worldServers[var3].setWorldTime((long)var2);
      }

   }

   protected void addTime(ICommandSender var1, int var2) {
      for(int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
         WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
         var4.setWorldTime(var4.getWorldTime() + (long)var2);
      }

   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
