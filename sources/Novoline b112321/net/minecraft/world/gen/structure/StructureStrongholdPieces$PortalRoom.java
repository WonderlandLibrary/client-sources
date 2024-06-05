package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.a1C;
import net.mZ;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$3;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stronghold$Door;

public class StructureStrongholdPieces$PortalRoom extends a1C {
   private boolean hasSpawner;

   public StructureStrongholdPieces$PortalRoom() {
   }

   public StructureStrongholdPieces$PortalRoom(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Mob", this.hasSpawner);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.hasSpawner = var1.getBoolean("Mob");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      ((StructureStrongholdPieces$Stairs2)var1).strongholdPortalRoom = this;
   }

   public static StructureStrongholdPieces$PortalRoom func_175865_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -4, -1, 0, 11, 8, 16, var5);
      return a(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureStrongholdPieces$PortalRoom(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 10, 7, 15, false, var2, StructureStrongholdPieces.access$100());
      this.a(var1, var2, var3, StructureStrongholdPieces$Stronghold$Door.GRATES, 4, 1, 0);
      int var4 = 6;
      this.fillWithRandomizedBlocks(var1, var3, 1, var4, 1, 1, var4, 14, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 9, var4, 1, 9, var4, 14, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 2, var4, 1, 8, var4, 2, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 2, var4, 14, 8, var4, 14, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 1, 1, 1, 2, 1, 4, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 8, 1, 1, 9, 1, 4, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithBlocks(var1, var3, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
      this.fillWithRandomizedBlocks(var1, var3, 3, 1, 8, 7, 1, 12, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithBlocks(var1, var3, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);

      for(int var5 = 3; var5 < 14; var5 += 2) {
         this.fillWithBlocks(var1, var3, 0, 3, var5, 0, 4, var5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 10, 3, var5, 10, 4, var5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
      }

      for(int var13 = 2; var13 < 9; var13 += 2) {
         this.fillWithBlocks(var1, var3, var13, 3, 15, var13, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
      }

      int var14 = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
      this.fillWithRandomizedBlocks(var1, var3, 4, 1, 5, 6, 1, 7, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 4, 2, 6, 6, 2, 7, false, var2, StructureStrongholdPieces.access$100());
      this.fillWithRandomizedBlocks(var1, var3, 4, 3, 7, 6, 3, 7, false, var2, StructureStrongholdPieces.access$100());

      for(int var6 = 4; var6 <= 6; ++var6) {
         this.setBlockState(var1, Blocks.stone_brick_stairs.getStateFromMeta(var14), var6, 1, 4, var3);
         this.setBlockState(var1, Blocks.stone_brick_stairs.getStateFromMeta(var14), var6, 2, 5, var3);
         this.setBlockState(var1, Blocks.stone_brick_stairs.getStateFromMeta(var14), var6, 3, 6, var3);
      }

      int var15 = EnumFacing.NORTH.getHorizontalIndex();
      int var7 = EnumFacing.SOUTH.getHorizontalIndex();
      int var8 = EnumFacing.EAST.getHorizontalIndex();
      int var9 = EnumFacing.WEST.getHorizontalIndex();
      if(this.coordBaseMode != null) {
         switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
         case 1:
            var15 = EnumFacing.SOUTH.getHorizontalIndex();
            var7 = EnumFacing.NORTH.getHorizontalIndex();
            break;
         case 2:
            var15 = EnumFacing.WEST.getHorizontalIndex();
            var7 = EnumFacing.EAST.getHorizontalIndex();
            var8 = EnumFacing.SOUTH.getHorizontalIndex();
            var9 = EnumFacing.NORTH.getHorizontalIndex();
            break;
         case 3:
            var15 = EnumFacing.EAST.getHorizontalIndex();
            var7 = EnumFacing.WEST.getHorizontalIndex();
            var8 = EnumFacing.SOUTH.getHorizontalIndex();
            var9 = EnumFacing.NORTH.getHorizontalIndex();
         }
      }

      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var15).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 4, 3, 8, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var15).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 5, 3, 8, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var15).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 6, 3, 8, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 4, 3, 12, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 5, 3, 12, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var7).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 6, 3, 12, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 3, 3, 9, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 3, 3, 10, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var8).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 3, 3, 11, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 7, 3, 9, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 7, 3, 10, var3);
      this.setBlockState(var1, Blocks.end_portal_frame.getStateFromMeta(var9).withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(var2.nextFloat() > 0.9F)), 7, 3, 11, var3);
      if(!this.hasSpawner) {
         var4 = this.getYWithOffset(3);
         BlockPos var10 = new BlockPos(this.getXWithOffset(5, 6), var4, this.getZWithOffset(5, 6));
         if(var3.isVecInside(var10)) {
            this.hasSpawner = true;
            var1.setBlockState(var10, Blocks.mob_spawner.getDefaultState(), 2);
            TileEntity var11 = var1.getTileEntity(var10);
            if(var11 instanceof TileEntityMobSpawner) {
               ((TileEntityMobSpawner)var11).getSpawnerBaseLogic().setEntityName("Silverfish");
            }
         }
      }

      return true;
   }
}
