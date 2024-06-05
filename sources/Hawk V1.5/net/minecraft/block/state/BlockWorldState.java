package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
   private boolean field_177512_e;
   private TileEntity field_177511_d;
   private final World world;
   private final BlockPos pos;
   private IBlockState field_177514_c;
   private static final String __OBFID = "CL_00002026";

   public BlockWorldState(World var1, BlockPos var2) {
      this.world = var1;
      this.pos = var2;
   }

   public static Predicate hasState(Predicate var0) {
      return new Predicate(var0) {
         private static final String __OBFID = "CL_00002025";
         private final Predicate val$p_177510_0_;

         public boolean apply(Object var1) {
            return this.func_177503_a((BlockWorldState)var1);
         }

         {
            this.val$p_177510_0_ = var1;
         }

         public boolean func_177503_a(BlockWorldState var1) {
            return var1 != null && this.val$p_177510_0_.apply(var1.func_177509_a());
         }
      };
   }

   public TileEntity func_177507_b() {
      if (this.field_177511_d == null && !this.field_177512_e) {
         this.field_177511_d = this.world.getTileEntity(this.pos);
         this.field_177512_e = true;
      }

      return this.field_177511_d;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public IBlockState func_177509_a() {
      if (this.field_177514_c == null && this.world.isBlockLoaded(this.pos)) {
         this.field_177514_c = this.world.getBlockState(this.pos);
      }

      return this.field_177514_c;
   }
}
