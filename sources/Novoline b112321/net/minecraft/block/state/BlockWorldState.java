package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.block.state.BlockWorldState$1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
   private final World world;
   private final BlockPos pos;
   private final boolean field_181628_c;
   private IBlockState state;
   private TileEntity tileEntity;
   private boolean tileEntityInitialized;

   public BlockWorldState(World var1, BlockPos var2, boolean var3) {
      this.world = var1;
      this.pos = var2;
      this.field_181628_c = var3;
   }

   public IBlockState getBlockState() {
      if(this.state == null && (this.field_181628_c || this.world.isBlockLoaded(this.pos))) {
         this.state = this.world.getBlockState(this.pos);
      }

      return this.state;
   }

   public TileEntity getTileEntity() {
      if(this.tileEntity == null && !this.tileEntityInitialized) {
         this.tileEntity = this.world.getTileEntity(this.pos);
         this.tileEntityInitialized = true;
      }

      return this.tileEntity;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public static Predicate hasState(Predicate var0) {
      return new BlockWorldState$1(var0);
   }
}
