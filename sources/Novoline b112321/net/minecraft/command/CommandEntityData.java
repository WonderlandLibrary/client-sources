package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class CommandEntityData extends CommandBase {
   public String getCommandName() {
      return "entitydata";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.entitydata.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 2) {
         throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
      } else {
         Entity var3 = func_175768_b(var1, var2[0]);
         if(var3 instanceof EntityPlayer) {
            throw new CommandException("commands.entitydata.noPlayers", new Object[]{var3.getDisplayName()});
         } else {
            NBTTagCompound var4 = new NBTTagCompound();
            var3.writeToNBT(var4);
            NBTTagCompound var5 = (NBTTagCompound)var4.copy();
            ICommandSender var10000 = var1;
            String[] var10001 = var2;
            byte var10002 = 1;

            NBTTagCompound var6;
            try {
               var6 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(var10000, var10001, var10002).getUnformattedText());
            } catch (NBTException var8) {
               throw new CommandException("commands.entitydata.tagError", new Object[]{var8.getMessage()});
            }

            var6.removeTag("UUIDMost");
            var6.removeTag("UUIDLeast");
            var4.merge(var6);
            if(var4.equals(var5)) {
               throw new CommandException("commands.entitydata.failed", new Object[]{var4.toString()});
            } else {
               var3.readFromNBT(var4);
               notifyOperators(var1, this, "commands.entitydata.success", new Object[]{var4.toString()});
            }
         }
      }
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static NBTException a(NBTException var0) {
      return var0;
   }
}
