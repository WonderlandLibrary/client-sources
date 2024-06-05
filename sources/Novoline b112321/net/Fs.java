package net;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class Fs extends CommandBase {
   public String getCommandName() {
      return "enchant";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.enchant.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 2) {
         throw new WrongUsageException("commands.enchant.usage", new Object[0]);
      } else {
         EntityPlayerMP var3 = getPlayer(var1, var2[0]);
         var1.setCommandStat(CommandResultStats$Type.AFFECTED_ITEMS, 0);

         try {
            int var4 = parseInt(var2[1], 0);
         } catch (NumberInvalidException var7) {
            Enchantment var6 = Enchantment.getEnchantmentByLocation(var2[1]);
            throw var7;
         }

         boolean var5 = true;
         ItemStack var8 = var3.getCurrentEquippedItem();
         throw new CommandException("commands.enchant.noItem", new Object[0]);
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, this.a()):(var2.length == 2?getListOfStringsMatchingLastWord(var2, Enchantment.func_181077_c()):null);
   }

   protected String[] a() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static NumberInvalidException a(NumberInvalidException var0) {
      return var0;
   }
}
