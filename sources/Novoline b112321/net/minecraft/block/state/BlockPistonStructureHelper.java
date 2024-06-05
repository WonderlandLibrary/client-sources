package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPistonStructureHelper {
   private final World world;
   private final BlockPos pistonPos;
   private final BlockPos blockToMove;
   private final EnumFacing moveDirection;
   private final List toMove = Lists.newArrayList();
   private final List toDestroy = Lists.newArrayList();

   public BlockPistonStructureHelper(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      this.world = var1;
      this.pistonPos = var2;
      this.moveDirection = var3;
      this.blockToMove = var2.offset(var3);
   }

   public boolean canMove() {
      this.toMove.clear();
      this.toDestroy.clear();
      Block var1 = this.world.getBlockState(this.blockToMove).getBlock();
      if(!BlockPistonBase.canPush(var1, this.world, this.blockToMove, this.moveDirection, false)) {
         if(var1.getMobilityFlag() != 1) {
            return false;
         } else {
            this.toDestroy.add(this.blockToMove);
            return true;
         }
      } else if(!this.func_177251_a(this.blockToMove)) {
         return false;
      } else {
         for(BlockPos var3 : this.toMove) {
            if(this.world.getBlockState(var3).getBlock() == Blocks.slime_block && !this.func_177250_b(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_177251_a(BlockPos var1) {
      Block var2 = this.world.getBlockState(var1).getBlock();
      if(var2.getMaterial() == Material.air) {
         return true;
      } else if(!BlockPistonBase.canPush(var2, this.world, var1, this.moveDirection, false)) {
         return true;
      } else if(var1.equals(this.pistonPos)) {
         return true;
      } else if(this.toMove.contains(var1)) {
         return true;
      } else {
         int var3 = 1;
         if(var3 + this.toMove.size() > 12) {
            return false;
         } else {
            while(var2 == Blocks.slime_block) {
               BlockPos var4 = var1.offset(this.moveDirection.getOpposite(), var3);
               var2 = this.world.getBlockState(var4).getBlock();
               if(var2.getMaterial() == Material.air || !BlockPistonBase.canPush(var2, this.world, var4, this.moveDirection, false) || var4.equals(this.pistonPos)) {
                  break;
               }

               ++var3;
               if(var3 + this.toMove.size() > 12) {
                  return false;
               }
            }

            int var6 = 0;
            int var5 = var3 - 1;

            while(true) {
               this.toMove.add(var1.offset(this.moveDirection.getOpposite(), var5));
               ++var6;
               --var5;
            }
         }
      }
   }

   private void func_177255_a(int var1, int var2) {
      ArrayList var3 = Lists.newArrayList();
      ArrayList var4 = Lists.newArrayList();
      ArrayList var5 = Lists.newArrayList();
      var3.addAll(this.toMove.subList(0, var2));
      var4.addAll(this.toMove.subList(this.toMove.size() - var1, this.toMove.size()));
      var5.addAll(this.toMove.subList(var2, this.toMove.size() - var1));
      this.toMove.clear();
      this.toMove.addAll(var3);
      this.toMove.addAll(var4);
      this.toMove.addAll(var5);
   }

   private boolean func_177250_b(BlockPos var1) {
      for(EnumFacing var5 : EnumFacing.values()) {
         if(var5.getAxis() != this.moveDirection.getAxis() && !this.func_177251_a(var1.offset(var5))) {
            return false;
         }
      }

      return true;
   }

   public List getBlocksToMove() {
      return this.toMove;
   }

   public List getBlocksToDestroy() {
      return this.toDestroy;
   }
}
