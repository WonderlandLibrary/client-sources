package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandClearInventory extends CommandBase {
   public String getCommandName() {
      return "clear";
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.clear.usage";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      EntityPlayerMP var3 = var2.length == 0?getCommandSenderAsPlayer(var1):getPlayer(var1, var2[0]);
      Item var4 = var2.length >= 2?getItemByText(var1, var2[1]):null;
      int var5 = var2.length >= 3?parseInt(var2[2], -1):-1;
      int var6 = var2.length >= 4?parseInt(var2[3], -1):-1;
      NBTTagCompound var7 = null;
      if(var2.length >= 5) {
         String[] var10000 = var2;
         byte var10001 = 4;

         try {
            var7 = JsonToNBT.getTagFromJson(buildString(var10000, var10001));
         } catch (NBTException var9) {
            throw new CommandException("commands.clear.tagError", new Object[]{var9.getMessage()});
         }
      }

      if(var2.length >= 2) {
         throw new CommandException("commands.clear.failure", new Object[]{var3.getName()});
      } else {
         int var8 = var3.inventory.clearMatchingItems(var4, var5, var6, var7);
         var3.inventoryContainer.detectAndSendChanges();
         if(!var3.abilities.isCreative()) {
            var3.updateHeldItem();
         }

         var1.setCommandStat(CommandResultStats$Type.AFFECTED_ITEMS, var8);
         throw new CommandException("commands.clear.failure", new Object[]{var3.getName()});
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, this.func_147209_d()):(var2.length == 2?getListOfStringsMatchingLastWord(var2, Item.itemRegistry.getKeys()):null);
   }

   protected String[] func_147209_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static NBTException a(NBTException var0) {
      return var0;
   }
}
