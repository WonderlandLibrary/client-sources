package net.minecraft.command.server;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandTestForBlock extends CommandBase {
   private static final String __OBFID = "CL_00001181";

   public String getCommandUsage(ICommandSender var1) {
      return "commands.testforblock.usage";
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3 ? func_175771_a(var2, 0, var3) : (var2.length == 4 ? func_175762_a(var2, Block.blockRegistry.getKeys()) : null);
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandName() {
      return "testforblock";
   }

   public static boolean func_175775_a(NBTBase var0, NBTBase var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!var0.getClass().equals(var1.getClass())) {
         return false;
      } else if (var0 instanceof NBTTagCompound) {
         NBTTagCompound var9 = (NBTTagCompound)var0;
         NBTTagCompound var10 = (NBTTagCompound)var1;
         Iterator var11 = var9.getKeySet().iterator();

         while(var11.hasNext()) {
            String var12 = (String)var11.next();
            NBTBase var13 = var9.getTag(var12);
            if (!func_175775_a(var13, var10.getTag(var12), var2)) {
               return false;
            }
         }

         return true;
      } else if (var0 instanceof NBTTagList && var2) {
         NBTTagList var3 = (NBTTagList)var0;
         NBTTagList var4 = (NBTTagList)var1;
         if (var3.tagCount() == 0) {
            return var4.tagCount() == 0;
         } else {
            for(int var5 = 0; var5 < var3.tagCount(); ++var5) {
               NBTBase var6 = var3.get(var5);
               boolean var7 = false;

               for(int var8 = 0; var8 < var4.tagCount(); ++var8) {
                  if (func_175775_a(var6, var4.get(var8), var2)) {
                     var7 = true;
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return var0.equals(var1);
      }
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 4) {
         throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
      } else {
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos var3 = func_175757_a(var1, var2, 0, false);
         Block var4 = Block.getBlockFromName(var2[3]);
         if (var4 == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[]{var2[3]});
         } else {
            int var5 = -1;
            if (var2.length >= 5) {
               var5 = parseInt(var2[4], -1, 15);
            }

            World var6 = var1.getEntityWorld();
            if (!var6.isBlockLoaded(var3)) {
               throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
            } else {
               NBTTagCompound var7 = new NBTTagCompound();
               boolean var8 = false;
               if (var2.length >= 6 && var4.hasTileEntity()) {
                  String var9 = getChatComponentFromNthArg(var1, var2, 5).getUnformattedText();

                  try {
                     var7 = JsonToNBT.func_180713_a(var9);
                     var8 = true;
                  } catch (NBTException var13) {
                     throw new CommandException("commands.setblock.tagError", new Object[]{var13.getMessage()});
                  }
               }

               IBlockState var14 = var6.getBlockState(var3);
               Block var10 = var14.getBlock();
               if (var10 != var4) {
                  throw new CommandException("commands.testforblock.failed.tile", new Object[]{var3.getX(), var3.getY(), var3.getZ(), var10.getLocalizedName(), var4.getLocalizedName()});
               } else {
                  if (var5 > -1) {
                     int var11 = var14.getBlock().getMetaFromState(var14);
                     if (var11 != var5) {
                        throw new CommandException("commands.testforblock.failed.data", new Object[]{var3.getX(), var3.getY(), var3.getZ(), var11, var5});
                     }
                  }

                  if (var8) {
                     TileEntity var15 = var6.getTileEntity(var3);
                     if (var15 == null) {
                        throw new CommandException("commands.testforblock.failed.tileEntity", new Object[]{var3.getX(), var3.getY(), var3.getZ()});
                     }

                     NBTTagCompound var12 = new NBTTagCompound();
                     var15.writeToNBT(var12);
                     if (!func_175775_a(var7, var12, true)) {
                        throw new CommandException("commands.testforblock.failed.nbt", new Object[]{var3.getX(), var3.getY(), var3.getZ()});
                     }
                  }

                  var1.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
                  notifyOperators(var1, this, "commands.testforblock.success", new Object[]{var3.getX(), var3.getY(), var3.getZ()});
               }
            }
         }
      }
   }
}
