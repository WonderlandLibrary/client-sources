package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$1;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;

public class BlockRailBase$Rail {
   private final World world;
   private final BlockPos pos;
   private final BlockRailBase block;
   private IBlockState state;
   private final boolean isPowered;
   private final List field_150657_g;
   final BlockRailBase this$0;

   public BlockRailBase$Rail(BlockRailBase var1, World var2, BlockPos var3, IBlockState var4) {
      this.this$0 = var1;
      this.field_150657_g = Lists.newArrayList();
      this.world = var2;
      this.pos = var3;
      this.state = var4;
      this.block = (BlockRailBase)var4.getBlock();
      BlockRailBase$EnumRailDirection var5 = (BlockRailBase$EnumRailDirection)var4.getValue(var1.getShapeProperty());
      this.isPowered = this.block.isPowered;
      this.func_180360_a(var5);
   }

   private void func_180360_a(BlockRailBase$EnumRailDirection var1) {
      this.field_150657_g.clear();
      switch(BlockRailBase$1.$SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[var1.ordinal()]) {
      case 1:
         this.field_150657_g.add(this.pos.north());
         this.field_150657_g.add(this.pos.south());
         break;
      case 2:
         this.field_150657_g.add(this.pos.west());
         this.field_150657_g.add(this.pos.east());
         break;
      case 3:
         this.field_150657_g.add(this.pos.west());
         this.field_150657_g.add(this.pos.east().up());
         break;
      case 4:
         this.field_150657_g.add(this.pos.west().up());
         this.field_150657_g.add(this.pos.east());
         break;
      case 5:
         this.field_150657_g.add(this.pos.north().up());
         this.field_150657_g.add(this.pos.south());
         break;
      case 6:
         this.field_150657_g.add(this.pos.north());
         this.field_150657_g.add(this.pos.south().up());
         break;
      case 7:
         this.field_150657_g.add(this.pos.east());
         this.field_150657_g.add(this.pos.south());
         break;
      case 8:
         this.field_150657_g.add(this.pos.west());
         this.field_150657_g.add(this.pos.south());
         break;
      case 9:
         this.field_150657_g.add(this.pos.west());
         this.field_150657_g.add(this.pos.north());
         break;
      case 10:
         this.field_150657_g.add(this.pos.east());
         this.field_150657_g.add(this.pos.north());
      }

   }

   private void func_150651_b() {
      for(int var1 = 0; var1 < this.field_150657_g.size(); ++var1) {
         BlockRailBase$Rail var2 = this.findRailAt((BlockPos)this.field_150657_g.get(var1));
         if(var2.func_150653_a(this)) {
            this.field_150657_g.set(var1, var2.pos);
         } else {
            this.field_150657_g.remove(var1--);
         }
      }

   }

   private boolean hasRailAt(BlockPos var1) {
      return BlockRailBase.isRailBlock(this.world, var1) || BlockRailBase.isRailBlock(this.world, var1.up()) || BlockRailBase.isRailBlock(this.world, var1.down());
   }

   private BlockRailBase$Rail findRailAt(BlockPos var1) {
      IBlockState var2 = this.world.getBlockState(var1);
      if(BlockRailBase.isRailBlock(var2)) {
         BlockRailBase var8 = this.this$0;
         this.this$0.getClass();
         return new BlockRailBase$Rail(var8, this.world, var1, var2);
      } else {
         BlockPos var3 = var1.up();
         var2 = this.world.getBlockState(var3);
         if(BlockRailBase.isRailBlock(var2)) {
            BlockRailBase var7 = this.this$0;
            this.this$0.getClass();
            return new BlockRailBase$Rail(var7, this.world, var3, var2);
         } else {
            var3 = var1.down();
            var2 = this.world.getBlockState(var3);
            BlockRailBase$Rail var10000;
            if(BlockRailBase.isRailBlock(var2)) {
               BlockRailBase var10002 = this.this$0;
               this.this$0.getClass();
               var10000 = new BlockRailBase$Rail(var10002, this.world, var3, var2);
            } else {
               var10000 = null;
            }

            return var10000;
         }
      }
   }

   private boolean func_150653_a(BlockRailBase$Rail var1) {
      return this.func_180363_c(var1.pos);
   }

   private boolean func_180363_c(BlockPos var1) {
      for(BlockPos var3 : this.field_150657_g) {
         if(var3.getX() == var1.getX() && var3.getZ() == var1.getZ()) {
            return true;
         }
      }

      return false;
   }

   protected int countAdjacentRails() {
      int var1 = 0;

      for(Object var3 : EnumFacing$Plane.HORIZONTAL) {
         if(this.hasRailAt(this.pos.offset((EnumFacing)var3))) {
            ++var1;
         }
      }

      return var1;
   }

   private boolean func_150649_b(BlockRailBase$Rail var1) {
      return this.func_150653_a(var1) || this.field_150657_g.size() != 2;
   }

   private void func_150645_c(BlockRailBase$Rail var1) {
      this.field_150657_g.add(var1.pos);
      BlockPos var2 = this.pos.north();
      BlockPos var3 = this.pos.south();
      BlockPos var4 = this.pos.west();
      BlockPos var5 = this.pos.east();
      boolean var6 = this.func_180363_c(var2);
      boolean var7 = this.func_180363_c(var3);
      boolean var8 = this.func_180363_c(var4);
      boolean var9 = this.func_180363_c(var5);
      BlockRailBase$EnumRailDirection var10 = null;
      var10 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      var10 = BlockRailBase$EnumRailDirection.EAST_WEST;
      if(!this.isPowered) {
         var10 = BlockRailBase$EnumRailDirection.SOUTH_EAST;
         var10 = BlockRailBase$EnumRailDirection.SOUTH_WEST;
         var10 = BlockRailBase$EnumRailDirection.NORTH_WEST;
         var10 = BlockRailBase$EnumRailDirection.NORTH_EAST;
      }

      if(var10 == BlockRailBase$EnumRailDirection.NORTH_SOUTH) {
         if(BlockRailBase.isRailBlock(this.world, var2.up())) {
            var10 = BlockRailBase$EnumRailDirection.ASCENDING_NORTH;
         }

         if(BlockRailBase.isRailBlock(this.world, var3.up())) {
            var10 = BlockRailBase$EnumRailDirection.ASCENDING_SOUTH;
         }
      }

      if(var10 == BlockRailBase$EnumRailDirection.EAST_WEST) {
         if(BlockRailBase.isRailBlock(this.world, var5.up())) {
            var10 = BlockRailBase$EnumRailDirection.ASCENDING_EAST;
         }

         if(BlockRailBase.isRailBlock(this.world, var4.up())) {
            var10 = BlockRailBase$EnumRailDirection.ASCENDING_WEST;
         }
      }

      var10 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      this.state = this.state.withProperty(this.block.getShapeProperty(), var10);
      this.world.setBlockState(this.pos, this.state, 3);
   }

   private boolean func_180361_d(BlockPos var1) {
      BlockRailBase$Rail var2 = this.findRailAt(var1);
      return false;
   }

   public BlockRailBase$Rail func_180364_a(boolean var1, boolean var2) {
      BlockPos var3 = this.pos.north();
      BlockPos var4 = this.pos.south();
      BlockPos var5 = this.pos.west();
      BlockPos var6 = this.pos.east();
      boolean var7 = this.func_180361_d(var3);
      boolean var8 = this.func_180361_d(var4);
      boolean var9 = this.func_180361_d(var5);
      boolean var10 = this.func_180361_d(var6);
      BlockRailBase$EnumRailDirection var11 = null;
      var11 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      var11 = BlockRailBase$EnumRailDirection.EAST_WEST;
      if(!this.isPowered) {
         var11 = BlockRailBase$EnumRailDirection.SOUTH_EAST;
         var11 = BlockRailBase$EnumRailDirection.SOUTH_WEST;
         var11 = BlockRailBase$EnumRailDirection.NORTH_WEST;
         var11 = BlockRailBase$EnumRailDirection.NORTH_EAST;
      }

      var11 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      var11 = BlockRailBase$EnumRailDirection.EAST_WEST;
      if(!this.isPowered) {
         var11 = BlockRailBase$EnumRailDirection.SOUTH_EAST;
         var11 = BlockRailBase$EnumRailDirection.SOUTH_WEST;
         var11 = BlockRailBase$EnumRailDirection.NORTH_EAST;
         var11 = BlockRailBase$EnumRailDirection.NORTH_WEST;
      }

      if(var11 == BlockRailBase$EnumRailDirection.NORTH_SOUTH) {
         if(BlockRailBase.isRailBlock(this.world, var3.up())) {
            var11 = BlockRailBase$EnumRailDirection.ASCENDING_NORTH;
         }

         if(BlockRailBase.isRailBlock(this.world, var4.up())) {
            var11 = BlockRailBase$EnumRailDirection.ASCENDING_SOUTH;
         }
      }

      if(var11 == BlockRailBase$EnumRailDirection.EAST_WEST) {
         if(BlockRailBase.isRailBlock(this.world, var6.up())) {
            var11 = BlockRailBase$EnumRailDirection.ASCENDING_EAST;
         }

         if(BlockRailBase.isRailBlock(this.world, var5.up())) {
            var11 = BlockRailBase$EnumRailDirection.ASCENDING_WEST;
         }
      }

      var11 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
      this.func_180360_a(var11);
      this.state = this.state.withProperty(this.block.getShapeProperty(), var11);
      if(this.world.getBlockState(this.pos) != this.state) {
         this.world.setBlockState(this.pos, this.state, 3);

         for(BlockPos var13 : this.field_150657_g) {
            BlockRailBase$Rail var14 = this.findRailAt(var13);
            var14.func_150651_b();
            if(var14.func_150649_b(this)) {
               var14.func_150645_c(this);
            }
         }
      }

      return this;
   }

   public IBlockState getBlockState() {
      return this.state;
   }
}
