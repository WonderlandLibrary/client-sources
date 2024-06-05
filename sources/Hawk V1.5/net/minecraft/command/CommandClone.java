package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandClone extends CommandBase {
   private static final String __OBFID = "CL_00002348";

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3 ? func_175771_a(var2, 0, var3) : (var2.length > 3 && var2.length <= 6 ? func_175771_a(var2, 3, var3) : (var2.length > 6 && var2.length <= 9 ? func_175771_a(var2, 6, var3) : (var2.length == 10 ? getListOfStringsMatchingLastWord(var2, new String[]{"replace", "masked", "filtered"}) : (var2.length == 11 ? getListOfStringsMatchingLastWord(var2, new String[]{"normal", "force", "move"}) : (var2.length == 12 && "filtered".equals(var2[9]) ? func_175762_a(var2, Block.blockRegistry.getKeys()) : null)))));
   }

   public String getCommandName() {
      return "clone";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 9) {
         throw new WrongUsageException("commands.clone.usage", new Object[0]);
      } else {
         var1.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos var3 = func_175757_a(var1, var2, 0, false);
         BlockPos var4 = func_175757_a(var1, var2, 3, false);
         BlockPos var5 = func_175757_a(var1, var2, 6, false);
         StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
         StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.add(var6.func_175896_b()));
         int var8 = var6.getXSize() * var6.getYSize() * var6.getZSize();
         if (var8 > 32768) {
            throw new CommandException("commands.clone.tooManyBlocks", new Object[]{var8, 32768});
         } else {
            boolean var9 = false;
            Block var10 = null;
            int var11 = -1;
            if ((var2.length < 11 || !var2[10].equals("force") && !var2[10].equals("move")) && var6.intersectsWith(var7)) {
               throw new CommandException("commands.clone.noOverlap", new Object[0]);
            } else {
               if (var2.length >= 11 && var2[10].equals("move")) {
                  var9 = true;
               }

               if (var6.minY >= 0 && var6.maxY < 256 && var7.minY >= 0 && var7.maxY < 256) {
                  World var12 = var1.getEntityWorld();
                  if (var12.isAreaLoaded(var6) && var12.isAreaLoaded(var7)) {
                     boolean var13 = false;
                     if (var2.length >= 10) {
                        if (var2[9].equals("masked")) {
                           var13 = true;
                        } else if (var2[9].equals("filtered")) {
                           if (var2.length < 12) {
                              throw new WrongUsageException("commands.clone.usage", new Object[0]);
                           }

                           var10 = getBlockByText(var1, var2[11]);
                           if (var2.length >= 13) {
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
                              if ((!var13 || var24.getBlock() != Blocks.air) && (var10 == null || var24.getBlock() == var10 && (var11 < 0 || var24.getBlock().getMetaFromState(var24) == var11))) {
                                 TileEntity var25 = var12.getTileEntity(var22);
                                 if (var25 != null) {
                                    NBTTagCompound var26 = new NBTTagCompound();
                                    var25.writeToNBT(var26);
                                    var15.add(new CommandClone.StaticCloneData(var23, var24, var26));
                                    var17.addLast(var22);
                                 } else if (!var24.getBlock().isFullBlock() && !var24.getBlock().isFullCube()) {
                                    var16.add(new CommandClone.StaticCloneData(var23, var24, (NBTTagCompound)null));
                                    var17.addFirst(var22);
                                 } else {
                                    var14.add(new CommandClone.StaticCloneData(var23, var24, (NBTTagCompound)null));
                                    var17.addLast(var22);
                                 }
                              }
                           }
                        }
                     }

                     if (var9) {
                        Iterator var28;
                        BlockPos var30;
                        for(var28 = var17.iterator(); var28.hasNext(); var12.setBlockState(var30, Blocks.barrier.getDefaultState(), 2)) {
                           var30 = (BlockPos)var28.next();
                           TileEntity var32 = var12.getTileEntity(var30);
                           if (var32 instanceof IInventory) {
                              ((IInventory)var32).clearInventory();
                           }
                        }

                        var28 = var17.iterator();

                        while(var28.hasNext()) {
                           var30 = (BlockPos)var28.next();
                           var12.setBlockState(var30, Blocks.air.getDefaultState(), 3);
                        }
                     }

                     ArrayList var29 = Lists.newArrayList();
                     var29.addAll(var14);
                     var29.addAll(var15);
                     var29.addAll(var16);
                     List var31 = Lists.reverse(var29);

                     Iterator var33;
                     CommandClone.StaticCloneData var34;
                     TileEntity var35;
                     for(var33 = var31.iterator(); var33.hasNext(); var12.setBlockState(var34.field_179537_a, Blocks.barrier.getDefaultState(), 2)) {
                        var34 = (CommandClone.StaticCloneData)var33.next();
                        var35 = var12.getTileEntity(var34.field_179537_a);
                        if (var35 instanceof IInventory) {
                           ((IInventory)var35).clearInventory();
                        }
                     }

                     var8 = 0;
                     var33 = var29.iterator();

                     while(var33.hasNext()) {
                        var34 = (CommandClone.StaticCloneData)var33.next();
                        if (var12.setBlockState(var34.field_179537_a, var34.field_179535_b, 2)) {
                           ++var8;
                        }
                     }

                     for(var33 = var15.iterator(); var33.hasNext(); var12.setBlockState(var34.field_179537_a, var34.field_179535_b, 2)) {
                        var34 = (CommandClone.StaticCloneData)var33.next();
                        var35 = var12.getTileEntity(var34.field_179537_a);
                        if (var34.field_179536_c != null && var35 != null) {
                           var34.field_179536_c.setInteger("x", var34.field_179537_a.getX());
                           var34.field_179536_c.setInteger("y", var34.field_179537_a.getY());
                           var34.field_179536_c.setInteger("z", var34.field_179537_a.getZ());
                           var35.readFromNBT(var34.field_179536_c);
                           var35.markDirty();
                        }
                     }

                     var33 = var31.iterator();

                     while(var33.hasNext()) {
                        var34 = (CommandClone.StaticCloneData)var33.next();
                        var12.func_175722_b(var34.field_179537_a, var34.field_179535_b.getBlock());
                     }

                     List var36 = var12.func_175712_a(var6, false);
                     if (var36 != null) {
                        Iterator var37 = var36.iterator();

                        while(var37.hasNext()) {
                           NextTickListEntry var38 = (NextTickListEntry)var37.next();
                           if (var6.func_175898_b(var38.field_180282_a)) {
                              BlockPos var27 = var38.field_180282_a.add(var18);
                              var12.func_180497_b(var27, var38.func_151351_a(), (int)(var38.scheduledTime - var12.getWorldInfo().getWorldTotalTime()), var38.priority);
                           }
                        }
                     }

                     if (var8 <= 0) {
                        throw new CommandException("commands.clone.failed", new Object[0]);
                     } else {
                        var1.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, var8);
                        notifyOperators(var1, this, "commands.clone.success", new Object[]{var8});
                     }
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

   public String getCommandUsage(ICommandSender var1) {
      return "commands.clone.usage";
   }

   static class StaticCloneData {
      private static final String __OBFID = "CL_00002347";
      public final NBTTagCompound field_179536_c;
      public final IBlockState field_179535_b;
      public final BlockPos field_179537_a;

      public StaticCloneData(BlockPos var1, IBlockState var2, NBTTagCompound var3) {
         this.field_179537_a = var1;
         this.field_179535_b = var2;
         this.field_179536_c = var3;
      }
   }
}
