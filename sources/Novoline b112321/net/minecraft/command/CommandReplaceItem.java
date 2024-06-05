package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
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
   private static final Map SHORTCUTS = Maps.newHashMap();

   public String getCommandName() {
      return "replaceitem";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.replaceitem.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 1) {
         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
      } else {
         if(var2[0].equals("entity")) {
            boolean var3 = false;
         } else {
            if(!var2[0].equals("block")) {
               throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }

            boolean var16 = true;
         }

         if(var2.length < 6) {
            throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
         } else {
            int var4 = 4;
            int var5 = this.getSlotForShortcut(var2[var4++]);

            Item var6;
            try {
               var6 = getItemByText(var1, var2[var4]);
            } catch (NumberInvalidException var15) {
               if(Block.getBlockFromName(var2[var4]) != Blocks.air) {
                  throw var15;
               }

               var6 = null;
            }

            ++var4;
            int var7 = var2.length > var4?parseInt(var2[var4++], 1, 64):1;
            int var8 = var2.length > var4?parseInt(var2[var4++]):0;
            ItemStack var9 = new ItemStack(var6, var7, var8);
            if(var2.length > var4) {
               String var10 = getChatComponentFromNthArg(var1, var2, var4).getUnformattedText();
               ItemStack var21 = var9;
               String var23 = var10;

               try {
                  var21.setTagCompound(JsonToNBT.getTagFromJson(var23));
               } catch (NBTException var14) {
                  throw new CommandException("commands.replaceitem.tagError", new Object[]{var14.getMessage()});
               }
            }

            if(var9.getItem() == null) {
               var9 = null;
            }

            var1.setCommandStat(CommandResultStats$Type.AFFECTED_ITEMS, 0);
            BlockPos var19 = parseBlockPos(var1, var2, 1, false);
            World var11 = var1.getEntityWorld();
            TileEntity var12 = var11.getTileEntity(var19);
            if(!(var12 instanceof IInventory)) {
               throw new CommandException("commands.replaceitem.noContainer", new Object[]{Integer.valueOf(var19.getX()), Integer.valueOf(var19.getY()), Integer.valueOf(var19.getZ())});
            } else {
               IInventory var13 = (IInventory)var12;
               if(var5 < var13.getSizeInventory()) {
                  var13.setInventorySlotContents(var5, var9);
               }

               var1.setCommandStat(CommandResultStats$Type.AFFECTED_ITEMS, var7);
               notifyOperators(var1, this, "commands.replaceitem.success", new Object[]{Integer.valueOf(var5), Integer.valueOf(var7), "Air"});
            }
         }
      }
   }

   private int getSlotForShortcut(String var1) throws CommandException {
      if(!SHORTCUTS.containsKey(var1)) {
         throw new CommandException("commands.generic.parameter.invalid", new Object[]{var1});
      } else {
         return ((Integer)SHORTCUTS.get(var1)).intValue();
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, new String[]{"entity", "block"}):(var2.length == 2 && var2[0].equals("entity")?getListOfStringsMatchingLastWord(var2, this.getUsernames()):(var2.length >= 2 && var2.length <= 4 && var2[0].equals("block")?b(var2, 1, var3):((var2.length != 3 || !var2[0].equals("entity")) && (var2.length != 5 || !var2[0].equals("block"))?(var2.length == 4 && var2[0].equals("entity") || var2.length == 6 && var2[0].equals("block")?getListOfStringsMatchingLastWord(var2, Item.itemRegistry.getKeys()):null):getListOfStringsMatchingLastWord(var2, SHORTCUTS.keySet()))));
   }

   protected String[] getUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var1.length > 0 && var1[0].equals("entity") && var2 == 1;
   }

   static {
      for(int var7 = 0; var7 < 54; ++var7) {
         SHORTCUTS.put("slot.container." + var7, Integer.valueOf(var7));
      }

      for(int var8 = 0; var8 < 9; ++var8) {
         SHORTCUTS.put("slot.hotbar." + var8, Integer.valueOf(var8));
      }

      for(int var9 = 0; var9 < 27; ++var9) {
         SHORTCUTS.put("slot.inventory." + var9, Integer.valueOf(9 + var9));
      }

      for(int var10 = 0; var10 < 27; ++var10) {
         SHORTCUTS.put("slot.enderchest." + var10, Integer.valueOf(200 + var10));
      }

      for(int var11 = 0; var11 < 8; ++var11) {
         SHORTCUTS.put("slot.villager." + var11, Integer.valueOf(300 + var11));
      }

      for(int var12 = 0; var12 < 15; ++var12) {
         SHORTCUTS.put("slot.horse." + var12, Integer.valueOf(500 + var12));
      }

      SHORTCUTS.put("slot.weapon", Integer.valueOf(99));
      SHORTCUTS.put("slot.armor.head", Integer.valueOf(103));
      SHORTCUTS.put("slot.armor.chest", Integer.valueOf(102));
      SHORTCUTS.put("slot.armor.legs", Integer.valueOf(101));
      SHORTCUTS.put("slot.armor.feet", Integer.valueOf(100));
      SHORTCUTS.put("slot.horse.saddle", Integer.valueOf(400));
      SHORTCUTS.put("slot.horse.armor", Integer.valueOf(401));
      SHORTCUTS.put("slot.horse.chest", Integer.valueOf(499));
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
