package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.world.World;

public class BlockPortal$Size {
   private final World world;
   private final EnumFacing$Axis axis;
   private final EnumFacing field_150866_c;
   private final EnumFacing field_150863_d;
   private int field_150864_e = 0;
   private BlockPos field_150861_f;
   private int field_150862_g;
   private int field_150868_h;

   public BlockPortal$Size(World var1, BlockPos var2, EnumFacing$Axis var3) {
      this.world = var1;
      this.axis = var3;
      if(var3 == EnumFacing$Axis.X) {
         this.field_150863_d = EnumFacing.EAST;
         this.field_150866_c = EnumFacing.WEST;
      } else {
         this.field_150863_d = EnumFacing.NORTH;
         this.field_150866_c = EnumFacing.SOUTH;
      }

      for(BlockPos var4 = var2; var2.getY() > var4.getY() - 21 && var2.getY() > 0 && this.func_150857_a(var1.getBlockState(var2.down()).getBlock()); var2 = var2.down()) {
         ;
      }

      int var5 = this.func_180120_a(var2, this.field_150863_d) - 1;
      this.field_150861_f = var2.offset(this.field_150863_d, var5);
      this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
      if(this.field_150868_h < 2 || this.field_150868_h > 21) {
         this.field_150861_f = null;
         this.field_150868_h = 0;
      }

      if(this.field_150861_f != null) {
         this.field_150862_g = this.func_150858_a();
      }

   }

   protected int func_180120_a(BlockPos var1, EnumFacing var2) {
      int var3;
      for(var3 = 0; var3 < 22; ++var3) {
         BlockPos var4 = var1.offset(var2, var3);
         if(!this.func_150857_a(this.world.getBlockState(var4).getBlock()) || this.world.getBlockState(var4.down()).getBlock() != Blocks.obsidian) {
            break;
         }
      }

      Block var5 = this.world.getBlockState(var1.offset(var2, var3)).getBlock();
      return var5 == Blocks.obsidian?var3:0;
   }

   public int func_181100_a() {
      return this.field_150862_g;
   }

   public int func_181101_b() {
      return this.field_150868_h;
   }

   protected int func_150858_a() {
      label24:
      for(this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g) {
         for(int var1 = 0; var1 < this.field_150868_h; ++var1) {
            BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1).up(this.field_150862_g);
            Block var3 = this.world.getBlockState(var2).getBlock();
            if(!this.func_150857_a(var3)) {
               break label24;
            }

            if(var3 == Blocks.portal) {
               ++this.field_150864_e;
            }

            var3 = this.world.getBlockState(var2.offset(this.field_150863_d)).getBlock();
            if(var3 != Blocks.obsidian) {
               break label24;
            }
         }
      }

      for(int var4 = 0; var4 < this.field_150868_h; ++var4) {
         if(this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, var4).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
            this.field_150862_g = 0;
            break;
         }
      }

      if(this.field_150862_g <= 21 && this.field_150862_g >= 3) {
         return this.field_150862_g;
      } else {
         this.field_150861_f = null;
         this.field_150868_h = 0;
         this.field_150862_g = 0;
         return 0;
      }
   }

   protected boolean func_150857_a(Block var1) {
      return var1.blockMaterial == Material.air || var1 == Blocks.fire || var1 == Blocks.portal;
   }

   public boolean func_150860_b() {
      return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
   }

   public void func_150859_c() {
      for(int var1 = 0; var1 < this.field_150868_h; ++var1) {
         BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1);

         for(int var3 = 0; var3 < this.field_150862_g; ++var3) {
            this.world.setBlockState(var2.up(var3), Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
         }
      }

   }

   static int access$000(BlockPortal$Size var0) {
      return var0.field_150864_e;
   }

   static int access$100(BlockPortal$Size var0) {
      return var0.field_150868_h;
   }

   static int access$200(BlockPortal$Size var0) {
      return var0.field_150862_g;
   }

   static EnumFacing access$300(BlockPortal$Size var0) {
      return var0.field_150866_c;
   }

   static BlockPos access$400(BlockPortal$Size var0) {
      return var0.field_150861_f;
   }
}
