package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTestFor extends CommandBase {
   public String getCommandName() {
      return "testfor";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.testfor.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 1) {
         throw new WrongUsageException("commands.testfor.usage", new Object[0]);
      } else {
         Entity var3 = func_175768_b(var1, var2[0]);
         NBTTagCompound var4 = null;
         if(var2.length >= 2) {
            String[] var10000 = var2;
            byte var10001 = 1;

            try {
               var4 = JsonToNBT.getTagFromJson(buildString(var10000, var10001));
            } catch (NBTException var6) {
               throw new CommandException("commands.testfor.tagError", new Object[]{var6.getMessage()});
            }
         }

         NBTTagCompound var5 = new NBTTagCompound();
         var3.writeToNBT(var5);
         if(!NBTUtil.a(var4, var5, true)) {
            throw new CommandException("commands.testfor.failure", new Object[]{var3.getName()});
         } else {
            notifyOperators(var1, this, "commands.testfor.success", new Object[]{var3.getName()});
         }
      }
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()):null;
   }

   private static NBTException a(NBTException var0) {
      return var0;
   }
}
