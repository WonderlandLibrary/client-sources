package net;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandClone$StaticCloneData;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class Fm extends CommandBase {
   public String getCommandName() {
      return "clone";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.clone.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 9) {
         throw new WrongUsageException("commands.clone.usage", new Object[0]);
      } else {
         var1.setCommandStat(CommandResultStats$Type.AFFECTED_BLOCKS, 0);
         BlockPos var3 = parseBlockPos(var1, var2, 0, false);
         BlockPos var4 = parseBlockPos(var1, var2, 3, false);
         BlockPos var5 = parseBlockPos(var1, var2, 6, false);
         StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
         StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.add(var6.func_175896_b()));
         int var8 = var6.getXSize() * var6.getYSize() * var6.getZSize();
         if(var8 > '耀') {
            throw new CommandException("commands.clone.tooManyBlocks", new Object[]{Integer.valueOf(var8), Integer.valueOf('耀')});
         } else {
            boolean var9 = false;
            Block var10 = null;
            int var11 = -1;
            if((var2.length < 11 || !var2[10].equals("force") && !var2[10].equals("move")) && var6.intersectsWith(var7)) {
               throw new CommandException("commands.clone.noOverlap", new Object[0]);
            } else {
               if(var2.length >= 11 && var2[10].equals("move")) {
                  var9 = true;
               }

               if(var6.minY >= 0 && var6.maxY < 256 && var7.minY >= 0 && var7.maxY < 256) {
                  World var12 = var1.getEntityWorld();
                  if(var12.isAreaLoaded(var6) && var12.isAreaLoaded(var7)) {
                     boolean var13 = false;
                     if(var2.length >= 10) {
                        if(var2[9].equals("masked")) {
                           var13 = true;
                        } else if(var2[9].equals("filtered")) {
                           if(var2.length < 12) {
                              throw new WrongUsageException("commands.clone.usage", new Object[0]);
                           }

                           var10 = getBlockByText(var1, var2[11]);
                           if(var2.length >= 13) {
                              var11 = parseInt(var2[12], 0, 15);
                           }
                        }
                     }

                     ArrayList var14 = Lists.newArrayList();
                     ArrayList var15 = Lists.newArrayList();
                     ArrayList var16 = Lists.newArrayList();
                     LinkedList var17 = Lists.newLinkedList();
                     BlockPos var18 = new BlockPos(var7.minX - var6.minX, var7.minY - var6.minY, var7.minZ - var6.minZ);

                     for(int var19 = var6.minZ; var19 <= var6.maxZ; ++var19) {
                        for(int var20 = var6.minY; var20 <= var6.maxY; ++var20) {
                           for(int var21 = var6.minX; var21 <= var6.maxX; ++var21) {
                              BlockPos var22 = new BlockPos(var21, var20, var19);
                              BlockPos var23 = var22.add(var18);
                              IBlockState var24 = var12.getBlockState(var22);
                              if(var24.getBlock() != Blocks.air && var24.getBlock() == var10 && var24.getBlock().getMetaFromState(var24) == var11) {
                                 TileEntity var25 = var12.getTileEntity(var22);
                                 NBTTagCompound var26 = new NBTTagCompound();
                                 var25.writeToNBT(var26);
                                 var15.add(new CommandClone$StaticCloneData(var23, var24, var26));
                                 var17.addLast(var22);
                              }
                           }
                        }
                     }

                     for(BlockPos var33 : var17) {
                        TileEntity var36 = var12.getTileEntity(var33);
                        if(var36 instanceof IInventory) {
                           ((IInventory)var36).clear();
                        }

                        var12.setBlockState(var33, Blocks.barrier.getDefaultState(), 2);
                     }

                     for(BlockPos var34 : var17) {
                        var12.setBlockState(var34, Blocks.air.getDefaultState(), 3);
                     }

                     ArrayList var32 = Lists.newArrayList();
                     var32.addAll(var14);
                     var32.addAll(var15);
                     var32.addAll(var16);
                     List var35 = Lists.reverse(var32);

                     for(CommandClone$StaticCloneData var42 : var35) {
                        TileEntity var47 = var12.getTileEntity(var42.field_179537_a);
                        if(var47 instanceof IInventory) {
                           ((IInventory)var47).clear();
                        }

                        var12.setBlockState(var42.field_179537_a, Blocks.barrier.getDefaultState(), 2);
                     }

                     var8 = 0;

                     for(CommandClone$StaticCloneData var43 : var32) {
                        if(var12.setBlockState(var43.field_179537_a, var43.blockState, 2)) {
                           ++var8;
                        }
                     }

                     for(CommandClone$StaticCloneData var44 : var15) {
                        TileEntity var48 = var12.getTileEntity(var44.field_179537_a);
                        if(var44.field_179536_c != null) {
                           var44.field_179536_c.setInteger("x", var44.field_179537_a.getX());
                           var44.field_179536_c.setInteger("y", var44.field_179537_a.getY());
                           var44.field_179536_c.setInteger("z", var44.field_179537_a.getZ());
                           var48.readFromNBT(var44.field_179536_c);
                           var48.markDirty();
                        }

                        var12.setBlockState(var44.field_179537_a, var44.blockState, 2);
                     }

                     for(CommandClone$StaticCloneData var45 : var35) {
                        var12.notifyNeighborsRespectDebug(var45.field_179537_a, var45.blockState.getBlock());
                     }

                     for(NextTickListEntry var49 : var12.func_175712_a(var6, false)) {
                        if(var6.isVecInside(var49.position)) {
                           BlockPos var50 = var49.position.add(var18);
                           var12.scheduleBlockUpdate(var50, var49.getBlock(), (int)(var49.scheduledTime - var12.getWorldInfo().getWorldTotalTime()), var49.priority);
                        }
                     }

                     throw new CommandException("commands.clone.failed", new Object[0]);
                  } else {
                     throw new CommandException("commands.clone.outOfWorld", new Object[0]);
                  }
               } else {
                  throw new CommandException("commands.clone.outOfWorld", new Object[0]);
               }
            }
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3?b(var2, 0, var3):(var2.length > 3 && var2.length <= 6?b(var2, 3, var3):(var2.length > 6 && var2.length <= 9?b(var2, 6, var3):(var2.length == 10?getListOfStringsMatchingLastWord(var2, new String[]{"replace", "masked", "filtered"}):(var2.length == 11?getListOfStringsMatchingLastWord(var2, new String[]{"normal", "force", "move"}):(var2.length == 12 && "filtered".equals(var2[9])?getListOfStringsMatchingLastWord(var2, Block.blockRegistry.getKeys()):null)))));
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
