package net.minecraft.command.common;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandReplaceItem extends CommandBase {
   private static final Map field_175785_a = Maps.newHashMap();
   private static final String __OBFID = "CL_00002340";

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
      } else {
         boolean var3;
         if (var2[0].equals("entity")) {
            var3 = false;
         } else {
            if (!var2[0].equals("block")) {
               throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }

            var3 = true;
         }

         byte var4;
         if (var3) {
            if (var2.length < 6) {
               throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }

            var4 = 4;
         } else {
            if (var2.length < 4) {
               throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
            }

            var4 = 2;
         }

         int var5 = var4 + 1;
         int var6 = this.func_175783_e(var2[var4]);

         Item var7;
         try {
            var7 = getItemByText(var1, var2[var5]);
         } catch (NumberInvalidException var16) {
            if (Block.getBlockFromName(var2[var5]) != Blocks.air) {
               throw var16;
            }

            var7 = null;
         }

         ++var5;
         int var8 = var2.length > var5 ? parseInt(var2[var5++], 1, 64) : 1;
         int var9 = var2.length > var5 ? parseInt(var2[var5++]) : 0;
         ItemStack var10 = new ItemStack(var7, var8, var9);
         if (var2.length > var5) {
            String var11 = getChatComponentFromNthArg(var1, var2, var5).getUnformattedText();

            try {
               var10.setTagCompound(JsonToNBT.func_180713_a(var11));
            } catch (NBTException var15) {
               throw new CommandException("commands.replaceitem.tagError", new Object[]{var15.getMessage()});
            }
         }

         if (var10.getItem() == null) {
            var10 = null;
         }

         if (var3) {
            var1.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            BlockPos var18 = func_175757_a(var1, var2, 1, false);
            World var12 = var1.getEntityWorld();
            TileEntity var13 = var12.getTileEntity(var18);
            if (var13 == null || !(var13 instanceof IInventory)) {
               throw new CommandException("commands.replaceitem.noContainer", new Object[]{var18.getX(), var18.getY(), var18.getZ()});
            }

            IInventory var14 = (IInventory)var13;
            if (var6 >= 0 && var6 < var14.getSizeInventory()) {
               var14.setInventorySlotContents(var6, var10);
            }
         } else {
            Entity var17 = func_175768_b(var1, var2[1]);
            var1.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            if (var17 instanceof EntityPlayer) {
               ((EntityPlayer)var17).inventoryContainer.detectAndSendChanges();
            }

            if (!var17.func_174820_d(var6, var10)) {
               throw new CommandException("commands.replaceitem.failed", new Object[]{var6, var8, var10 == null ? "Air" : var10.getChatComponent()});
            }

            if (var17 instanceof EntityPlayer) {
               ((EntityPlayer)var17).inventoryContainer.detectAndSendChanges();
            }
         }

         var1.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, var8);
         notifyOperators(var1, this, "commands.replaceitem.success", new Object[]{var6, var8, var10 == null ? "Air" : var10.getChatComponent()});
      }
   }

   protected String[] func_175784_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.replaceitem.usage";
   }

   public String getCommandName() {
      return "replaceitem";
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? getListOfStringsMatchingLastWord(var2, new String[]{"entity", "block"}) : (var2.length == 2 && var2[0].equals("entity") ? getListOfStringsMatchingLastWord(var2, this.func_175784_d()) : (var2.length == 3 && var2[0].equals("entity") || var2.length == 5 && var2[0].equals("block") ? func_175762_a(var2, field_175785_a.keySet()) : (var2.length == 4 && var2[0].equals("entity") || var2.length == 6 && var2[0].equals("block") ? func_175762_a(var2, Item.itemRegistry.getKeys()) : null)));
   }

   private int func_175783_e(String var1) throws CommandException {
      if (!field_175785_a.containsKey(var1)) {
         throw new CommandException("commands.generic.parameter.invalid", new Object[]{var1});
      } else {
         return (Integer)field_175785_a.get(var1);
      }
   }

   static {
      int var0;
      for(var0 = 0; var0 < 54; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.container.")).append(var0)), var0);
      }

      for(var0 = 0; var0 < 9; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.hotbar.")).append(var0)), var0);
      }

      for(var0 = 0; var0 < 27; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.inventory.")).append(var0)), 9 + var0);
      }

      for(var0 = 0; var0 < 27; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.enderchest.")).append(var0)), 200 + var0);
      }

      for(var0 = 0; var0 < 8; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.villager.")).append(var0)), 300 + var0);
      }

      for(var0 = 0; var0 < 15; ++var0) {
         field_175785_a.put(String.valueOf((new StringBuilder("slot.horse.")).append(var0)), 500 + var0);
      }

      field_175785_a.put("slot.weapon", 99);
      field_175785_a.put("slot.armor.head", 103);
      field_175785_a.put("slot.armor.chest", 102);
      field_175785_a.put("slot.armor.legs", 101);
      field_175785_a.put("slot.armor.feet", 100);
      field_175785_a.put("slot.horse.saddle", 400);
      field_175785_a.put("slot.horse.armor", 401);
      field_175785_a.put("slot.horse.chest", 499);
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var1.length > 0 && var1[0].equals("entity") && var2 == 1;
   }
}
