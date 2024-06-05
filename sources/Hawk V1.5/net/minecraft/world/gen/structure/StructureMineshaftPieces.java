package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureMineshaftPieces {
   private static final String __OBFID = "CL_00000444";
   private static final List field_175893_a;

   static StructureComponent access$0(StructureComponent var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      return func_175890_b(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   static List access$1() {
      return field_175893_a;
   }

   private static StructureComponent func_175892_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      int var7 = var1.nextInt(100);
      StructureBoundingBox var8;
      if (var7 >= 80) {
         var8 = StructureMineshaftPieces.Cross.func_175813_a(var0, var1, var2, var3, var4, var5);
         if (var8 != null) {
            return new StructureMineshaftPieces.Cross(var6, var1, var8, var5);
         }
      } else if (var7 >= 70) {
         var8 = StructureMineshaftPieces.Stairs.func_175812_a(var0, var1, var2, var3, var4, var5);
         if (var8 != null) {
            return new StructureMineshaftPieces.Stairs(var6, var1, var8, var5);
         }
      } else {
         var8 = StructureMineshaftPieces.Corridor.func_175814_a(var0, var1, var2, var3, var4, var5);
         if (var8 != null) {
            return new StructureMineshaftPieces.Corridor(var6, var1, var8, var5);
         }
      }

      return null;
   }

   public static void registerStructurePieces() {
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Corridor.class, "MSCorridor");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Cross.class, "MSCrossing");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Room.class, "MSRoom");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Stairs.class, "MSStairs");
   }

   static {
      field_175893_a = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeColorDamage(), 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1)});
   }

   private static StructureComponent func_175890_b(StructureComponent var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      if (var7 > 8) {
         return null;
      } else if (Math.abs(var3 - var0.getBoundingBox().minX) <= 80 && Math.abs(var5 - var0.getBoundingBox().minZ) <= 80) {
         StructureComponent var8 = func_175892_a(var1, var2, var3, var4, var5, var6, var7 + 1);
         if (var8 != null) {
            var1.add(var8);
            var8.buildComponent(var0, var1, var2);
         }

         return var8;
      } else {
         return null;
      }
   }

   public static class Corridor extends StructureComponent {
      private boolean hasSpiders;
      private boolean spawnerPlaced;
      private int sectionCount;
      private boolean hasRails;
      private static final String __OBFID = "CL_00000445";

      protected void readStructureFromNBT(NBTTagCompound var1) {
         this.hasRails = var1.getBoolean("hr");
         this.hasSpiders = var1.getBoolean("sc");
         this.spawnerPlaced = var1.getBoolean("hps");
         this.sectionCount = var1.getInteger("Num");
      }

      public void buildComponent(StructureComponent var1, List var2, Random var3) {
         int var4 = this.getComponentType();
         int var5 = var3.nextInt(4);
         if (this.coordBaseMode != null) {
            switch(this.coordBaseMode) {
            case NORTH:
               if (var5 <= 1) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
               } else if (var5 == 2) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, var4);
               } else {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, var4);
               }
               break;
            case SOUTH:
               if (var5 <= 1) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
               } else if (var5 == 2) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, var4);
               } else {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, var4);
               }
               break;
            case WEST:
               if (var5 <= 1) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
               } else if (var5 == 2) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
               } else {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
               }
               break;
            case EAST:
               if (var5 <= 1) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
               } else if (var5 == 2) {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
               } else {
                  StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + var3.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
               }
            }
         }

         if (var4 < 8) {
            int var6;
            int var7;
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
               for(var6 = this.boundingBox.minX + 3; var6 + 3 <= this.boundingBox.maxX; var6 += 5) {
                  var7 = var3.nextInt(5);
                  if (var7 == 0) {
                     StructureMineshaftPieces.access$0(var1, var2, var3, var6, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4 + 1);
                  } else if (var7 == 1) {
                     StructureMineshaftPieces.access$0(var1, var2, var3, var6, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4 + 1);
                  }
               }
            } else {
               for(var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5) {
                  var7 = var3.nextInt(5);
                  if (var7 == 0) {
                     StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, var6, EnumFacing.WEST, var4 + 1);
                  } else if (var7 == 1) {
                     StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, var6, EnumFacing.EAST, var4 + 1);
                  }
               }
            }
         }

      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.isLiquidInStructureBoundingBox(var1, var3)) {
            return false;
         } else {
            boolean var4 = false;
            boolean var5 = true;
            boolean var6 = false;
            boolean var7 = true;
            int var8 = this.sectionCount * 5 - 1;
            this.func_175804_a(var1, var3, 0, 0, 0, 2, 1, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175805_a(var1, var3, var2, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (this.hasSpiders) {
               this.func_175805_a(var1, var3, var2, 0.6F, 0, 0, 0, 2, 1, var8, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            int var9;
            int var10;
            for(var9 = 0; var9 < this.sectionCount; ++var9) {
               var10 = 2 + var9 * 5;
               this.func_175804_a(var1, var3, 0, 0, var10, 0, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, 2, 0, var10, 2, 1, var10, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
               if (var2.nextInt(4) == 0) {
                  this.func_175804_a(var1, var3, 0, 2, var10, 0, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                  this.func_175804_a(var1, var3, 2, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               } else {
                  this.func_175804_a(var1, var3, 0, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               }

               this.func_175809_a(var1, var3, var2, 0.1F, 0, 2, var10 - 1, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.1F, 2, 2, var10 - 1, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.1F, 0, 2, var10 + 1, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.1F, 2, 2, var10 + 1, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.05F, 0, 2, var10 - 2, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.05F, 2, 2, var10 - 2, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.05F, 0, 2, var10 + 2, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.05F, 2, 2, var10 + 2, Blocks.web.getDefaultState());
               this.func_175809_a(var1, var3, var2, 0.05F, 1, 2, var10 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
               this.func_175809_a(var1, var3, var2, 0.05F, 1, 2, var10 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
               if (var2.nextInt(100) == 0) {
                  this.func_180778_a(var1, var3, var2, 2, 0, var10 - 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$1(), Items.enchanted_book.getRandomEnchantedBook(var2)), 3 + var2.nextInt(4));
               }

               if (var2.nextInt(100) == 0) {
                  this.func_180778_a(var1, var3, var2, 0, 0, var10 + 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$1(), Items.enchanted_book.getRandomEnchantedBook(var2)), 3 + var2.nextInt(4));
               }

               if (this.hasSpiders && !this.spawnerPlaced) {
                  int var11 = this.getYWithOffset(0);
                  int var12 = var10 - 1 + var2.nextInt(3);
                  int var13 = this.getXWithOffset(1, var12);
                  var12 = this.getZWithOffset(1, var12);
                  BlockPos var14 = new BlockPos(var13, var11, var12);
                  if (var3.func_175898_b(var14)) {
                     this.spawnerPlaced = true;
                     var1.setBlockState(var14, Blocks.mob_spawner.getDefaultState(), 2);
                     TileEntity var15 = var1.getTileEntity(var14);
                     if (var15 instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)var15).getSpawnerBaseLogic().setEntityName("CaveSpider");
                     }
                  }
               }
            }

            for(var9 = 0; var9 <= 2; ++var9) {
               for(var10 = 0; var10 <= var8; ++var10) {
                  byte var16 = -1;
                  IBlockState var18 = this.func_175807_a(var1, var9, var16, var10, var3);
                  if (var18.getBlock().getMaterial() == Material.air) {
                     byte var19 = -1;
                     this.func_175811_a(var1, Blocks.planks.getDefaultState(), var9, var19, var10, var3);
                  }
               }
            }

            if (this.hasRails) {
               for(var9 = 0; var9 <= var8; ++var9) {
                  IBlockState var17 = this.func_175807_a(var1, 1, -1, var9, var3);
                  if (var17.getBlock().getMaterial() != Material.air && var17.getBlock().isFullBlock()) {
                     this.func_175809_a(var1, var3, var2, 0.7F, 1, 0, var9, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, 0)));
                  }
               }
            }

            return true;
         }
      }

      public Corridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.coordBaseMode = var4;
         this.boundingBox = var3;
         this.hasRails = var2.nextInt(3) == 0;
         this.hasSpiders = !this.hasRails && var2.nextInt(23) == 0;
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
            this.sectionCount = var3.getXSize() / 5;
         } else {
            this.sectionCount = var3.getZSize() / 5;
         }

      }

      public static StructureBoundingBox func_175814_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);

         int var7;
         for(var7 = var1.nextInt(3) + 2; var7 > 0; --var7) {
            int var8 = var7 * 5;
            switch(var5) {
            case NORTH:
               var6.maxX = var2 + 2;
               var6.minZ = var4 - (var8 - 1);
               break;
            case SOUTH:
               var6.maxX = var2 + 2;
               var6.maxZ = var4 + (var8 - 1);
               break;
            case WEST:
               var6.minX = var2 - (var8 - 1);
               var6.maxZ = var4 + 2;
               break;
            case EAST:
               var6.maxX = var2 + (var8 - 1);
               var6.maxZ = var4 + 2;
            }

            if (StructureComponent.findIntersecting(var0, var6) == null) {
               break;
            }
         }

         return var7 > 0 ? var6 : null;
      }

      protected boolean func_180778_a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, List var7, int var8) {
         BlockPos var9 = new BlockPos(this.getXWithOffset(var4, var6), this.getYWithOffset(var5), this.getZWithOffset(var4, var6));
         if (var2.func_175898_b(var9) && var1.getBlockState(var9).getBlock().getMaterial() == Material.air) {
            int var10 = var3.nextBoolean() ? 1 : 0;
            var1.setBlockState(var9, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, var10)), 2);
            EntityMinecartChest var11 = new EntityMinecartChest(var1, (double)((float)var9.getX() + 0.5F), (double)((float)var9.getY() + 0.5F), (double)((float)var9.getZ() + 0.5F));
            WeightedRandomChestContent.generateChestContents(var3, var7, var11, var8);
            var1.spawnEntityInWorld(var11);
            return true;
         } else {
            return false;
         }
      }

      protected void writeStructureToNBT(NBTTagCompound var1) {
         var1.setBoolean("hr", this.hasRails);
         var1.setBoolean("sc", this.hasSpiders);
         var1.setBoolean("hps", this.spawnerPlaced);
         var1.setInteger("Num", this.sectionCount);
      }

      public Corridor() {
      }
   }

   public static class Cross extends StructureComponent {
      private static final String __OBFID = "CL_00000446";
      private EnumFacing corridorDirection;
      private boolean isMultipleFloors;

      protected void readStructureFromNBT(NBTTagCompound var1) {
         this.isMultipleFloors = var1.getBoolean("tf");
         this.corridorDirection = EnumFacing.getHorizontal(var1.getInteger("D"));
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.isLiquidInStructureBoundingBox(var1, var3)) {
            return false;
         } else {
            if (this.isMultipleFloors) {
               this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            } else {
               this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.func_175804_a(var1, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(var1, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(var1, var3, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(var1, var3, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);

            for(int var4 = this.boundingBox.minX; var4 <= this.boundingBox.maxX; ++var4) {
               for(int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
                  if (this.func_175807_a(var1, var4, this.boundingBox.minY - 1, var5, var3).getBlock().getMaterial() == Material.air) {
                     this.func_175811_a(var1, Blocks.planks.getDefaultState(), var4, this.boundingBox.minY - 1, var5, var3);
                  }
               }
            }

            return true;
         }
      }

      protected void writeStructureToNBT(NBTTagCompound var1) {
         var1.setBoolean("tf", this.isMultipleFloors);
         var1.setInteger("D", this.corridorDirection.getHorizontalIndex());
      }

      public Cross() {
      }

      public static StructureBoundingBox func_175813_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);
         if (var1.nextInt(4) == 0) {
            var6.maxY += 4;
         }

         switch(var5) {
         case NORTH:
            var6.minX = var2 - 1;
            var6.maxX = var2 + 3;
            var6.minZ = var4 - 4;
            break;
         case SOUTH:
            var6.minX = var2 - 1;
            var6.maxX = var2 + 3;
            var6.maxZ = var4 + 4;
            break;
         case WEST:
            var6.minX = var2 - 4;
            var6.minZ = var4 - 1;
            var6.maxZ = var4 + 3;
            break;
         case EAST:
            var6.maxX = var2 + 4;
            var6.minZ = var4 - 1;
            var6.maxZ = var4 + 3;
         }

         return StructureComponent.findIntersecting(var0, var6) != null ? null : var6;
      }

      public Cross(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.corridorDirection = var4;
         this.boundingBox = var3;
         this.isMultipleFloors = var3.getYSize() > 3;
      }

      public void buildComponent(StructureComponent var1, List var2, Random var3) {
         int var4 = this.getComponentType();
         switch(this.corridorDirection) {
         case NORTH:
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
            break;
         case SOUTH:
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
            break;
         case WEST:
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
            break;
         case EAST:
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
         }

         if (this.isMultipleFloors) {
            if (var3.nextBoolean()) {
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            }
         }

      }
   }

   public static class Room extends StructureComponent {
      private static final String __OBFID = "CL_00000447";
      private List roomsLinkedToTheRoom = Lists.newLinkedList();

      public Room() {
      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.isLiquidInStructureBoundingBox(var1, var3)) {
            return false;
         } else {
            this.func_175804_a(var1, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), true);
            this.func_175804_a(var1, var3, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            Iterator var4 = this.roomsLinkedToTheRoom.iterator();

            while(var4.hasNext()) {
               StructureBoundingBox var5 = (StructureBoundingBox)var4.next();
               this.func_175804_a(var1, var3, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            this.func_180777_a(var1, var3, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), false);
            return true;
         }
      }

      protected void writeStructureToNBT(NBTTagCompound var1) {
         NBTTagList var2 = new NBTTagList();
         Iterator var3 = this.roomsLinkedToTheRoom.iterator();

         while(var3.hasNext()) {
            StructureBoundingBox var4 = (StructureBoundingBox)var3.next();
            var2.appendTag(var4.func_151535_h());
         }

         var1.setTag("Entrances", var2);
      }

      public void buildComponent(StructureComponent var1, List var2, Random var3) {
         int var4 = this.getComponentType();
         int var5 = this.boundingBox.getYSize() - 3 - 1;
         if (var5 <= 0) {
            var5 = 1;
         }

         int var6;
         StructureComponent var7;
         StructureBoundingBox var8;
         for(var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += var3.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
               break;
            }

            var7 = StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + var6, this.boundingBox.minY + var3.nextInt(var5) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            if (var7 != null) {
               var8 = var7.getBoundingBox();
               this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
            }
         }

         for(var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
            var6 += var3.nextInt(this.boundingBox.getXSize());
            if (var6 + 3 > this.boundingBox.getXSize()) {
               break;
            }

            var7 = StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX + var6, this.boundingBox.minY + var3.nextInt(var5) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            if (var7 != null) {
               var8 = var7.getBoundingBox();
               this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
            }
         }

         for(var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += var3.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
               break;
            }

            var7 = StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var3.nextInt(var5) + 1, this.boundingBox.minZ + var6, EnumFacing.WEST, var4);
            if (var7 != null) {
               var8 = var7.getBoundingBox();
               this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
            }
         }

         for(var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
            var6 += var3.nextInt(this.boundingBox.getZSize());
            if (var6 + 3 > this.boundingBox.getZSize()) {
               break;
            }

            var7 = StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var3.nextInt(var5) + 1, this.boundingBox.minZ + var6, EnumFacing.EAST, var4);
            if (var7 != null) {
               var8 = var7.getBoundingBox();
               this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
            }
         }

      }

      protected void readStructureFromNBT(NBTTagCompound var1) {
         NBTTagList var2 = var1.getTagList("Entrances", 11);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.getIntArray(var3)));
         }

      }

      public Room(int var1, Random var2, int var3, int var4) {
         super(var1);
         this.boundingBox = new StructureBoundingBox(var3, 50, var4, var3 + 7 + var2.nextInt(6), 54 + var2.nextInt(6), var4 + 7 + var2.nextInt(6));
      }
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00001998";
      static final int[] field_175894_a = new int[EnumFacing.values().length];

      static {
         try {
            field_175894_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_175894_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_175894_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_175894_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static class Stairs extends StructureComponent {
      private static final String __OBFID = "CL_00000449";

      public static StructureBoundingBox func_175812_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3 - 5, var4, var2, var3 + 2, var4);
         switch(StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[var5.ordinal()]) {
         case 1:
            var6.maxX = var2 + 2;
            var6.minZ = var4 - 8;
            break;
         case 2:
            var6.maxX = var2 + 2;
            var6.maxZ = var4 + 8;
            break;
         case 3:
            var6.minX = var2 - 8;
            var6.maxZ = var4 + 2;
            break;
         case 4:
            var6.maxX = var2 + 8;
            var6.maxZ = var4 + 2;
         }

         return StructureComponent.findIntersecting(var0, var6) != null ? null : var6;
      }

      public void buildComponent(StructureComponent var1, List var2, Random var3) {
         int var4 = this.getComponentType();
         if (this.coordBaseMode != null) {
            switch(StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[this.coordBaseMode.ordinal()]) {
            case 1:
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
               break;
            case 2:
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
               break;
            case 3:
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, var4);
               break;
            case 4:
               StructureMineshaftPieces.access$0(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, var4);
            }
         }

      }

      public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
         if (this.isLiquidInStructureBoundingBox(var1, var3)) {
            return false;
         } else {
            this.func_175804_a(var1, var3, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(var1, var3, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

            for(int var4 = 0; var4 < 5; ++var4) {
               this.func_175804_a(var1, var3, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            return true;
         }
      }

      public Stairs() {
      }

      protected void writeStructureToNBT(NBTTagCompound var1) {
      }

      protected void readStructureFromNBT(NBTTagCompound var1) {
      }

      public Stairs(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.coordBaseMode = var4;
         this.boundingBox = var3;
      }
   }
}
