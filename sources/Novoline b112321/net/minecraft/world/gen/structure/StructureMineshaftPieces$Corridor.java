package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.bgN;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;

public class StructureMineshaftPieces$Corridor extends StructureComponent {
   private boolean hasRails;
   private boolean hasSpiders;
   private boolean spawnerPlaced;
   private int sectionCount;

   public StructureMineshaftPieces$Corridor() {
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      var1.setBoolean("hr", this.hasRails);
      var1.setBoolean("sc", this.hasSpiders);
      var1.setBoolean("hps", this.spawnerPlaced);
      var1.setInteger("Num", this.sectionCount);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      this.hasRails = var1.getBoolean("hr");
      this.hasSpiders = var1.getBoolean("sc");
      this.spawnerPlaced = var1.getBoolean("hps");
      this.sectionCount = var1.getInteger("Num");
   }

   public StructureMineshaftPieces$Corridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
      this.hasRails = var2.nextInt(3) == 0;
      this.hasSpiders = !this.hasRails && var2.nextInt(23) == 0;
      if(this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
         this.sectionCount = var3.getXSize() / 5;
      } else {
         this.sectionCount = var3.getZSize() / 5;
      }

   }

   public static StructureBoundingBox func_175814_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
      StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);
      int var7 = var1.nextInt(3) + 2;

      while(true) {
         int var8 = var7 * 5;
         switch(bgN.a[var5.ordinal()]) {
         case 1:
            var6.maxX = var2 + 2;
            var6.minZ = var4 - (var8 - 1);
            break;
         case 2:
            var6.maxX = var2 + 2;
            var6.maxZ = var4 + var8 - 1;
            break;
         case 3:
            var6.minX = var2 - (var8 - 1);
            var6.maxZ = var4 + 2;
            break;
         case 4:
            var6.maxX = var2 + var8 - 1;
            var6.maxZ = var4 + 2;
         }

         if(StructureComponent.findIntersecting(var0, var6) == null) {
            return var6;
         }

         --var7;
      }
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      int var4 = this.getComponentType();
      int var5 = var3.nextInt(4);
      if(this.coordBaseMode != null) {
         switch(bgN.a[this.coordBaseMode.ordinal()]) {
         case 1:
            if(var5 <= 1) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
            } else if(var5 == 2) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, var4);
            } else {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, var4);
            }
            break;
         case 2:
            if(var5 <= 1) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
            } else if(var5 == 2) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, var4);
            } else {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, var4);
            }
            break;
         case 3:
            if(var5 <= 1) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
            } else if(var5 == 2) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            } else {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            }
            break;
         case 4:
            if(var5 <= 1) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
            } else if(var5 == 2) {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            } else {
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            }
         }
      }

      if(var4 < 8) {
         if(this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
            for(int var8 = this.boundingBox.minX + 3; var8 + 3 <= this.boundingBox.maxX; var8 += 5) {
               int var9 = var3.nextInt(5);
               StructureMineshaftPieces.access$000(var1, var2, var3, var8, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4 + 1);
            }
         } else {
            for(int var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5) {
               int var7 = var3.nextInt(5);
               StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, var6, EnumFacing.WEST, var4 + 1);
            }
         }
      }

   }

   protected boolean generateChestContents(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, List var7, int var8) {
      BlockPos var9 = new BlockPos(this.getXWithOffset(var4, var6), this.getYWithOffset(var5), this.getZWithOffset(var4, var6));
      if(var2.isVecInside(var9) && var1.getBlockState(var9).getBlock().getMaterial() == Material.air) {
         int var10 = var3.nextBoolean()?1:0;
         var1.setBlockState(var9, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, var10)), 2);
         EntityMinecartChest var11 = new EntityMinecartChest(var1, (double)((float)var9.getX() + 0.5F), (double)((float)var9.getY() + 0.5F), (double)((float)var9.getZ() + 0.5F));
         WeightedRandomChestContent.generateChestContents(var3, var7, var11, var8);
         var1.spawnEntityInWorld(var11);
         return true;
      } else {
         return false;
      }
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         boolean var4 = false;
         boolean var5 = true;
         boolean var6 = false;
         boolean var7 = true;
         int var8 = this.sectionCount * 5 - 1;
         this.fillWithBlocks(var1, var3, 0, 0, 0, 2, 1, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.func_175805_a(var1, var3, var2, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         if(this.hasSpiders) {
            this.func_175805_a(var1, var3, var2, 0.6F, 0, 0, 0, 2, 1, var8, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         for(int var9 = 0; var9 < this.sectionCount; ++var9) {
            int var10 = 2 + var9 * 5;
            this.fillWithBlocks(var1, var3, 0, 0, var10, 0, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 2, 0, var10, 2, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            if(var2.nextInt(4) == 0) {
               this.fillWithBlocks(var1, var3, 0, 2, var10, 0, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.fillWithBlocks(var1, var3, 2, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            } else {
               this.fillWithBlocks(var1, var3, 0, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 0, 2, var10 - 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 2, 2, var10 - 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 0, 2, var10 + 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 2, 2, var10 + 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 0, 2, var10 - 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 2, 2, var10 - 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 0, 2, var10 + 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 2, 2, var10 + 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 1, 2, var10 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            this.randomlyPlaceBlock(var1, var3, var2, 0.05F, 1, 2, var10 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            if(var2.nextInt(100) == 0) {
               this.generateChestContents(var1, var3, var2, 2, 0, var10 - 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2)}), 3 + var2.nextInt(4));
            }

            if(var2.nextInt(100) == 0) {
               this.generateChestContents(var1, var3, var2, 0, 0, var10 + 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2)}), 3 + var2.nextInt(4));
            }

            if(this.hasSpiders && !this.spawnerPlaced) {
               int var11 = this.getYWithOffset(0);
               int var12 = var10 - 1 + var2.nextInt(3);
               int var13 = this.getXWithOffset(1, var12);
               var12 = this.getZWithOffset(1, var12);
               BlockPos var14 = new BlockPos(var13, var11, var12);
               if(var3.isVecInside(var14)) {
                  this.spawnerPlaced = true;
                  var1.setBlockState(var14, Blocks.mob_spawner.getDefaultState(), 2);
                  TileEntity var15 = var1.getTileEntity(var14);
                  if(var15 instanceof TileEntityMobSpawner) {
                     ((TileEntityMobSpawner)var15).getSpawnerBaseLogic().setEntityName("CaveSpider");
                  }
               }
            }
         }

         for(int var16 = 0; var16 <= 2; ++var16) {
            for(int var18 = 0; var18 <= var8; ++var18) {
               byte var20 = -1;
               IBlockState var22 = this.getBlockStateFromPos(var1, var16, var20, var18, var3);
               if(var22.getBlock().getMaterial() == Material.air) {
                  byte var23 = -1;
                  this.setBlockState(var1, Blocks.planks.getDefaultState(), var16, var23, var18, var3);
               }
            }
         }

         if(this.hasRails) {
            for(int var17 = 0; var17 <= var8; ++var17) {
               IBlockState var19 = this.getBlockStateFromPos(var1, 1, -1, var17, var3);
               if(var19.getBlock().getMaterial() != Material.air && var19.getBlock().isFullBlock()) {
                  this.randomlyPlaceBlock(var1, var3, var2, 0.7F, 1, 0, var17, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, 0)));
               }
            }
         }

         return true;
      }
   }
}
