package net;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class a1r extends StructureVillagePieces$Village {
   public a1r() {
   }

   public a1r(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static StructureBoundingBox a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6) {
      StructureBoundingBox var7 = mZ.a(var3, var4, var5, 0, 0, 0, 3, 4, 2, var6);
      return StructureComponent.findIntersecting(var1, var7) != null?null:var7;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 0, 0, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 1, 0, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 2, 0, var3);
      this.setBlockState(var1, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, var3);
      boolean var4 = this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH;
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), 2, 3, 0, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 1, 3, 1, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), 0, 3, 0, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 1, 3, -1, var3);
      return true;
   }
}
