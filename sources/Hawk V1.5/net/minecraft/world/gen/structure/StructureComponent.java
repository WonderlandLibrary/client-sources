package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public abstract class StructureComponent {
   protected StructureBoundingBox boundingBox;
   protected int componentType;
   protected EnumFacing coordBaseMode;
   private static final String __OBFID = "CL_00000511";

   protected void fillWithRandomizedBlocks(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, Random var10, StructureComponent.BlockSelector var11) {
      for(int var12 = var4; var12 <= var7; ++var12) {
         for(int var13 = var3; var13 <= var6; ++var13) {
            for(int var14 = var5; var14 <= var8; ++var14) {
               if (!var9 || this.func_175807_a(var1, var13, var12, var14, var2).getBlock().getMaterial() != Material.air) {
                  var11.selectBlocks(var10, var13, var12, var14, var12 == var4 || var12 == var7 || var13 == var3 || var13 == var6 || var14 == var5 || var14 == var8);
                  this.func_175811_a(var1, var11.func_180780_a(), var13, var12, var14, var2);
               }
            }
         }
      }

   }

   public StructureComponent() {
   }

   protected void func_175809_a(World var1, StructureBoundingBox var2, Random var3, float var4, int var5, int var6, int var7, IBlockState var8) {
      if (var3.nextFloat() < var4) {
         this.func_175811_a(var1, var8, var5, var6, var7, var2);
      }

   }

   protected IBlockState func_175807_a(World var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      int var6 = this.getXWithOffset(var2, var4);
      int var7 = this.getYWithOffset(var3);
      int var8 = this.getZWithOffset(var2, var4);
      return !var5.func_175898_b(new BlockPos(var6, var7, var8)) ? Blocks.air.getDefaultState() : var1.getBlockState(new BlockPos(var6, var7, var8));
   }

   protected boolean isLiquidInStructureBoundingBox(World var1, StructureBoundingBox var2) {
      int var3 = Math.max(this.boundingBox.minX - 1, var2.minX);
      int var4 = Math.max(this.boundingBox.minY - 1, var2.minY);
      int var5 = Math.max(this.boundingBox.minZ - 1, var2.minZ);
      int var6 = Math.min(this.boundingBox.maxX + 1, var2.maxX);
      int var7 = Math.min(this.boundingBox.maxY + 1, var2.maxY);
      int var8 = Math.min(this.boundingBox.maxZ + 1, var2.maxZ);

      int var9;
      int var10;
      for(var9 = var3; var9 <= var6; ++var9) {
         for(var10 = var5; var10 <= var8; ++var10) {
            if (var1.getBlockState(new BlockPos(var9, var4, var10)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (var1.getBlockState(new BlockPos(var9, var7, var10)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for(var9 = var3; var9 <= var6; ++var9) {
         for(var10 = var4; var10 <= var7; ++var10) {
            if (var1.getBlockState(new BlockPos(var9, var10, var5)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (var1.getBlockState(new BlockPos(var9, var10, var8)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for(var9 = var5; var9 <= var8; ++var9) {
         for(var10 = var4; var10 <= var7; ++var10) {
            if (var1.getBlockState(new BlockPos(var3, var10, var9)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (var1.getBlockState(new BlockPos(var6, var10, var9)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      return false;
   }

   protected void func_180777_a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, boolean var10) {
      float var11 = (float)(var6 - var3 + 1);
      float var12 = (float)(var7 - var4 + 1);
      float var13 = (float)(var8 - var5 + 1);
      float var14 = (float)var3 + var11 / 2.0F;
      float var15 = (float)var5 + var13 / 2.0F;

      for(int var16 = var4; var16 <= var7; ++var16) {
         float var17 = (float)(var16 - var4) / var12;

         for(int var18 = var3; var18 <= var6; ++var18) {
            float var19 = ((float)var18 - var14) / (var11 * 0.5F);

            for(int var20 = var5; var20 <= var8; ++var20) {
               float var21 = ((float)var20 - var15) / (var13 * 0.5F);
               if (!var10 || this.func_175807_a(var1, var18, var16, var20, var2).getBlock().getMaterial() != Material.air) {
                  float var22 = var19 * var19 + var17 * var17 + var21 * var21;
                  if (var22 <= 1.05F) {
                     this.func_175811_a(var1, var9, var18, var16, var20, var2);
                  }
               }
            }
         }
      }

   }

   protected int getXWithOffset(int var1, int var2) {
      if (this.coordBaseMode == null) {
         return var1;
      } else {
         switch(this.coordBaseMode) {
         case NORTH:
         case SOUTH:
            return this.boundingBox.minX + var1;
         case WEST:
            return this.boundingBox.maxX - var2;
         case EAST:
            return this.boundingBox.minX + var2;
         default:
            return var1;
         }
      }
   }

   protected void func_175808_b(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      int var7 = this.getXWithOffset(var3, var5);
      int var8 = this.getYWithOffset(var4);
      int var9 = this.getZWithOffset(var3, var5);
      if (var6.func_175898_b(new BlockPos(var7, var8, var9))) {
         while((var1.isAirBlock(new BlockPos(var7, var8, var9)) || var1.getBlockState(new BlockPos(var7, var8, var9)).getBlock().getMaterial().isLiquid()) && var8 > 1) {
            var1.setBlockState(new BlockPos(var7, var8, var9), var2, 2);
            --var8;
         }
      }

   }

   public static StructureComponent findIntersecting(List var0, StructureBoundingBox var1) {
      Iterator var2 = var0.iterator();

      StructureComponent var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (StructureComponent)var2.next();
      } while(var3.getBoundingBox() == null || !var3.getBoundingBox().intersectsWith(var1));

      return var3;
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
   }

   public BlockPos func_180776_a() {
      return new BlockPos(this.boundingBox.func_180717_f());
   }

   protected void func_175805_a(World var1, StructureBoundingBox var2, Random var3, float var4, int var5, int var6, int var7, int var8, int var9, int var10, IBlockState var11, IBlockState var12, boolean var13) {
      for(int var14 = var6; var14 <= var9; ++var14) {
         for(int var15 = var5; var15 <= var8; ++var15) {
            for(int var16 = var7; var16 <= var10; ++var16) {
               if (var3.nextFloat() <= var4 && (!var13 || this.func_175807_a(var1, var15, var14, var16, var2).getBlock().getMaterial() != Material.air)) {
                  if (var14 != var6 && var14 != var9 && var15 != var5 && var15 != var8 && var16 != var7 && var16 != var10) {
                     this.func_175811_a(var1, var12, var15, var14, var16, var2);
                  } else {
                     this.func_175811_a(var1, var11, var15, var14, var16, var2);
                  }
               }
            }
         }
      }

   }

   public StructureBoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public void func_143009_a(World var1, NBTTagCompound var2) {
      if (var2.hasKey("BB")) {
         this.boundingBox = new StructureBoundingBox(var2.getIntArray("BB"));
      }

      int var3 = var2.getInteger("O");
      this.coordBaseMode = var3 == -1 ? null : EnumFacing.getHorizontal(var3);
      this.componentType = var2.getInteger("GD");
      this.readStructureFromNBT(var2);
   }

   protected abstract void readStructureFromNBT(NBTTagCompound var1);

   protected boolean func_180778_a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, List var7, int var8) {
      BlockPos var9 = new BlockPos(this.getXWithOffset(var4, var6), this.getYWithOffset(var5), this.getZWithOffset(var4, var6));
      if (var2.func_175898_b(var9) && var1.getBlockState(var9).getBlock() != Blocks.chest) {
         IBlockState var10 = Blocks.chest.getDefaultState();
         var1.setBlockState(var9, Blocks.chest.func_176458_f(var1, var9, var10), 2);
         TileEntity var11 = var1.getTileEntity(var9);
         if (var11 instanceof TileEntityChest) {
            WeightedRandomChestContent.generateChestContents(var3, var7, (TileEntityChest)var11, var8);
         }

         return true;
      } else {
         return false;
      }
   }

   protected int getYWithOffset(int var1) {
      return this.coordBaseMode == null ? var1 : var1 + this.boundingBox.minY;
   }

   public int getComponentType() {
      return this.componentType;
   }

   protected void clearCurrentPositionBlocksUpwards(World var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      BlockPos var6 = new BlockPos(this.getXWithOffset(var2, var4), this.getYWithOffset(var3), this.getZWithOffset(var2, var4));
      if (var5.func_175898_b(var6)) {
         while(!var1.isAirBlock(var6) && var6.getY() < 255) {
            var1.setBlockState(var6, Blocks.air.getDefaultState(), 2);
            var6 = var6.offsetUp();
         }
      }

   }

   public NBTTagCompound func_143010_b() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setString("id", MapGenStructureIO.func_143036_a(this));
      var1.setTag("BB", this.boundingBox.func_151535_h());
      var1.setInteger("O", this.coordBaseMode == null ? -1 : this.coordBaseMode.getHorizontalIndex());
      var1.setInteger("GD", this.componentType);
      this.writeStructureToNBT(var1);
      return var1;
   }

   protected int getZWithOffset(int var1, int var2) {
      if (this.coordBaseMode == null) {
         return var2;
      } else {
         switch(this.coordBaseMode) {
         case NORTH:
            return this.boundingBox.maxZ - var2;
         case SOUTH:
            return this.boundingBox.minZ + var2;
         case WEST:
         case EAST:
            return this.boundingBox.minZ + var1;
         default:
            return var2;
         }
      }
   }

   protected boolean func_175806_a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, int var7, List var8, int var9) {
      BlockPos var10 = new BlockPos(this.getXWithOffset(var4, var6), this.getYWithOffset(var5), this.getZWithOffset(var4, var6));
      if (var2.func_175898_b(var10) && var1.getBlockState(var10).getBlock() != Blocks.dispenser) {
         var1.setBlockState(var10, Blocks.dispenser.getStateFromMeta(this.getMetadataWithOffset(Blocks.dispenser, var7)), 2);
         TileEntity var11 = var1.getTileEntity(var10);
         if (var11 instanceof TileEntityDispenser) {
            WeightedRandomChestContent.func_177631_a(var3, var8, (TileEntityDispenser)var11, var9);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void func_175804_a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, IBlockState var10, boolean var11) {
      for(int var12 = var4; var12 <= var7; ++var12) {
         for(int var13 = var3; var13 <= var6; ++var13) {
            for(int var14 = var5; var14 <= var8; ++var14) {
               if (!var11 || this.func_175807_a(var1, var13, var12, var14, var2).getBlock().getMaterial() != Material.air) {
                  if (var12 != var4 && var12 != var7 && var13 != var3 && var13 != var6 && var14 != var5 && var14 != var8) {
                     this.func_175811_a(var1, var10, var13, var12, var14, var2);
                  } else {
                     this.func_175811_a(var1, var9, var13, var12, var14, var2);
                  }
               }
            }
         }
      }

   }

   protected StructureComponent(int var1) {
      this.componentType = var1;
   }

   protected int getMetadataWithOffset(Block var1, int var2) {
      if (var1 == Blocks.rail) {
         if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST) {
            if (var2 == 1) {
               return 0;
            }

            return 1;
         }
      } else if (var1 instanceof BlockDoor) {
         if (this.coordBaseMode == EnumFacing.SOUTH) {
            if (var2 == 0) {
               return 2;
            }

            if (var2 == 2) {
               return 0;
            }
         } else {
            if (this.coordBaseMode == EnumFacing.WEST) {
               return var2 + 1 & 3;
            }

            if (this.coordBaseMode == EnumFacing.EAST) {
               return var2 + 3 & 3;
            }
         }
      } else if (var1 != Blocks.stone_stairs && var1 != Blocks.oak_stairs && var1 != Blocks.nether_brick_stairs && var1 != Blocks.stone_brick_stairs && var1 != Blocks.sandstone_stairs) {
         if (var1 == Blocks.ladder) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (var2 == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }

               if (var2 == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (var2 == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (var2 == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (var2 == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (var2 == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (var2 == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (var2 == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (var2 == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (var2 == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            }
         } else if (var1 == Blocks.stone_button) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (var2 == 3) {
                  return 4;
               }

               if (var2 == 4) {
                  return 3;
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (var2 == 3) {
                  return 1;
               }

               if (var2 == 4) {
                  return 2;
               }

               if (var2 == 2) {
                  return 3;
               }

               if (var2 == 1) {
                  return 4;
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (var2 == 3) {
                  return 2;
               }

               if (var2 == 4) {
                  return 1;
               }

               if (var2 == 2) {
                  return 3;
               }

               if (var2 == 1) {
                  return 4;
               }
            }
         } else if (var1 != Blocks.tripwire_hook && !(var1 instanceof BlockDirectional)) {
            if (var1 == Blocks.piston || var1 == Blocks.sticky_piston || var1 == Blocks.lever || var1 == Blocks.dispenser) {
               if (this.coordBaseMode == EnumFacing.SOUTH) {
                  if (var2 == EnumFacing.NORTH.getIndex() || var2 == EnumFacing.SOUTH.getIndex()) {
                     return EnumFacing.getFront(var2).getOpposite().getIndex();
                  }
               } else if (this.coordBaseMode == EnumFacing.WEST) {
                  if (var2 == EnumFacing.NORTH.getIndex()) {
                     return EnumFacing.WEST.getIndex();
                  }

                  if (var2 == EnumFacing.SOUTH.getIndex()) {
                     return EnumFacing.EAST.getIndex();
                  }

                  if (var2 == EnumFacing.WEST.getIndex()) {
                     return EnumFacing.NORTH.getIndex();
                  }

                  if (var2 == EnumFacing.EAST.getIndex()) {
                     return EnumFacing.SOUTH.getIndex();
                  }
               } else if (this.coordBaseMode == EnumFacing.EAST) {
                  if (var2 == EnumFacing.NORTH.getIndex()) {
                     return EnumFacing.EAST.getIndex();
                  }

                  if (var2 == EnumFacing.SOUTH.getIndex()) {
                     return EnumFacing.WEST.getIndex();
                  }

                  if (var2 == EnumFacing.WEST.getIndex()) {
                     return EnumFacing.NORTH.getIndex();
                  }

                  if (var2 == EnumFacing.EAST.getIndex()) {
                     return EnumFacing.SOUTH.getIndex();
                  }
               }
            }
         } else {
            EnumFacing var3 = EnumFacing.getHorizontal(var2);
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (var3 == EnumFacing.SOUTH || var3 == EnumFacing.NORTH) {
                  return var3.getOpposite().getHorizontalIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (var3 == EnumFacing.NORTH) {
                  return EnumFacing.WEST.getHorizontalIndex();
               }

               if (var3 == EnumFacing.SOUTH) {
                  return EnumFacing.EAST.getHorizontalIndex();
               }

               if (var3 == EnumFacing.WEST) {
                  return EnumFacing.NORTH.getHorizontalIndex();
               }

               if (var3 == EnumFacing.EAST) {
                  return EnumFacing.SOUTH.getHorizontalIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (var3 == EnumFacing.NORTH) {
                  return EnumFacing.EAST.getHorizontalIndex();
               }

               if (var3 == EnumFacing.SOUTH) {
                  return EnumFacing.WEST.getHorizontalIndex();
               }

               if (var3 == EnumFacing.WEST) {
                  return EnumFacing.NORTH.getHorizontalIndex();
               }

               if (var3 == EnumFacing.EAST) {
                  return EnumFacing.SOUTH.getHorizontalIndex();
               }
            }
         }
      } else if (this.coordBaseMode == EnumFacing.SOUTH) {
         if (var2 == 2) {
            return 3;
         }

         if (var2 == 3) {
            return 2;
         }
      } else if (this.coordBaseMode == EnumFacing.WEST) {
         if (var2 == 0) {
            return 2;
         }

         if (var2 == 1) {
            return 3;
         }

         if (var2 == 2) {
            return 0;
         }

         if (var2 == 3) {
            return 1;
         }
      } else if (this.coordBaseMode == EnumFacing.EAST) {
         if (var2 == 0) {
            return 2;
         }

         if (var2 == 1) {
            return 3;
         }

         if (var2 == 2) {
            return 1;
         }

         if (var2 == 3) {
            return 0;
         }
      }

      return var2;
   }

   protected abstract void writeStructureToNBT(NBTTagCompound var1);

   protected void fillWithAir(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for(int var9 = var4; var9 <= var7; ++var9) {
         for(int var10 = var3; var10 <= var6; ++var10) {
            for(int var11 = var5; var11 <= var8; ++var11) {
               this.func_175811_a(var1, Blocks.air.getDefaultState(), var10, var9, var11, var2);
            }
         }
      }

   }

   public abstract boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3);

   protected void func_175811_a(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      BlockPos var7 = new BlockPos(this.getXWithOffset(var3, var5), this.getYWithOffset(var4), this.getZWithOffset(var3, var5));
      if (var6.func_175898_b(var7)) {
         var1.setBlockState(var7, var2, 2);
      }

   }

   protected void func_175810_a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, EnumFacing var7) {
      BlockPos var8 = new BlockPos(this.getXWithOffset(var4, var6), this.getYWithOffset(var5), this.getZWithOffset(var4, var6));
      if (var2.func_175898_b(var8)) {
         ItemDoor.func_179235_a(var1, var8, var7.rotateYCCW(), Blocks.oak_door);
      }

   }

   public abstract static class BlockSelector {
      protected IBlockState field_151562_a;
      private static final String __OBFID = "CL_00000512";

      protected BlockSelector() {
         this.field_151562_a = Blocks.air.getDefaultState();
      }

      public IBlockState func_180780_a() {
         return this.field_151562_a;
      }

      public abstract void selectBlocks(Random var1, int var2, int var3, int var4, boolean var5);
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00001969";
      static final int[] field_176100_a = new int[EnumFacing.values().length];

      static {
         try {
            field_176100_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_176100_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_176100_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_176100_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
