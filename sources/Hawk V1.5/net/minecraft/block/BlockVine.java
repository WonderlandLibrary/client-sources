package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final int field_176276_R;
   public static final PropertyBool[] field_176274_P;
   public static final int field_176275_S;
   public static final PropertyBool field_176278_M = PropertyBool.create("east");
   public static final int field_176272_Q;
   public static final PropertyBool field_176277_a = PropertyBool.create("up");
   public static final PropertyBool field_176280_O = PropertyBool.create("west");
   public static final int field_176271_T;
   public static final PropertyBool field_176273_b = PropertyBool.create("north");
   private static final String __OBFID = "CL_00000330";
   public static final PropertyBool field_176279_N = PropertyBool.create("south");

   private boolean func_150093_a(Block var1) {
      return var1.isFullCube() && var1.blockMaterial.blocksMovement();
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(field_176277_a, var2.getBlockState(var3.offsetUp()).getBlock().isSolidFullCube());
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.0625F;
      float var4 = 1.0F;
      float var5 = 1.0F;
      float var6 = 1.0F;
      float var7 = 0.0F;
      float var8 = 0.0F;
      float var9 = 0.0F;
      boolean var10 = false;
      if ((Boolean)var1.getBlockState(var2).getValue(field_176280_O)) {
         var7 = Math.max(var7, 0.0625F);
         var4 = 0.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if ((Boolean)var1.getBlockState(var2).getValue(field_176278_M)) {
         var4 = Math.min(var4, 0.9375F);
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if ((Boolean)var1.getBlockState(var2).getValue(field_176273_b)) {
         var9 = Math.max(var9, 0.0625F);
         var6 = 0.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if ((Boolean)var1.getBlockState(var2).getValue(field_176279_N)) {
         var6 = Math.min(var6, 0.9375F);
         var9 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if (!var10 && this.func_150093_a(var1.getBlockState(var2.offsetUp()).getBlock())) {
         var5 = Math.min(var5, 0.9375F);
         var8 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
      }

      this.setBlockBounds(var4, var5, var6, var7, var8, var9);
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      switch(var3) {
      case UP:
         return this.func_150093_a(var1.getBlockState(var2.offsetUp()).getBlock());
      case NORTH:
      case SOUTH:
      case EAST:
      case WEST:
         return this.func_150093_a(var1.getBlockState(var2.offset(var3.getOpposite())).getBlock());
      default:
         return false;
      }
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState().withProperty(field_176277_a, false).withProperty(field_176273_b, false).withProperty(field_176278_M, false).withProperty(field_176279_N, false).withProperty(field_176280_O, false);
      return var3.getAxis().isHorizontal() ? var9.withProperty(func_176267_a(var3.getOpposite()), true) : var9;
   }

   public BlockVine() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176277_a, false).withProperty(field_176273_b, false).withProperty(field_176278_M, false).withProperty(field_176279_N, false).withProperty(field_176280_O, false));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   private static int func_176270_b(EnumFacing var0) {
      return 1 << var0.getHorizontalIndex();
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.getBiomeGenForCoords(var2).func_180625_c(var2);
   }

   public static int func_176268_d(IBlockState var0) {
      int var1 = 0;
      PropertyBool[] var2 = field_176274_P;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyBool var5 = var2[var4];
         if ((Boolean)var0.getValue(var5)) {
            ++var1;
         }
      }

      return var1;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      return true;
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().getItem() == Items.shears) {
         var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(var1, var3, new ItemStack(Blocks.vine, 1, 0));
      } else {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   public static PropertyBool func_176267_a(EnumFacing var0) {
      switch(var0) {
      case UP:
         return field_176277_a;
      case NORTH:
         return field_176273_b;
      case SOUTH:
         return field_176279_N;
      case EAST:
         return field_176278_M;
      case WEST:
         return field_176280_O;
      default:
         throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append(var0).append(" is an invalid choice")));
      }
   }

   public int getRenderColor(IBlockState var1) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176277_a, field_176273_b, field_176278_M, field_176279_N, field_176280_O});
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176273_b, (var1 & field_176276_R) > 0).withProperty(field_176278_M, (var1 & field_176275_S) > 0).withProperty(field_176279_N, (var1 & field_176272_Q) > 0).withProperty(field_176280_O, (var1 & field_176271_T) > 0);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote && var1.rand.nextInt(4) == 0) {
         byte var5 = 4;
         int var6 = 5;
         boolean var7 = false;

         label221:
         for(int var8 = -var5; var8 <= var5; ++var8) {
            for(int var9 = -var5; var9 <= var5; ++var9) {
               for(int var10 = -1; var10 <= 1; ++var10) {
                  if (var1.getBlockState(var2.add(var8, var10, var9)).getBlock() == this) {
                     --var6;
                     if (var6 <= 0) {
                        var7 = true;
                        break label221;
                     }
                  }
               }
            }
         }

         EnumFacing var17 = EnumFacing.random(var4);
         EnumFacing var18;
         if (var17 == EnumFacing.UP && var2.getY() < 255 && var1.isAirBlock(var2.offsetUp())) {
            if (!var7) {
               IBlockState var20 = var3;
               Iterator var22 = EnumFacing.Plane.HORIZONTAL.iterator();

               while(true) {
                  do {
                     if (!var22.hasNext()) {
                        if ((Boolean)var20.getValue(field_176273_b) || (Boolean)var20.getValue(field_176278_M) || (Boolean)var20.getValue(field_176279_N) || (Boolean)var20.getValue(field_176280_O)) {
                           var1.setBlockState(var2.offsetUp(), var20, 2);
                        }

                        return;
                     }

                     var18 = (EnumFacing)var22.next();
                  } while(!var4.nextBoolean() && this.func_150093_a(var1.getBlockState(var2.offset(var18).offsetUp()).getBlock()));

                  var20 = var20.withProperty(func_176267_a(var18), false);
               }
            }
         } else {
            BlockPos var19;
            if (var17.getAxis().isHorizontal() && !(Boolean)var3.getValue(func_176267_a(var17))) {
               if (!var7) {
                  var19 = var2.offset(var17);
                  Block var21 = var1.getBlockState(var19).getBlock();
                  if (var21.blockMaterial == Material.air) {
                     var18 = var17.rotateY();
                     EnumFacing var23 = var17.rotateYCCW();
                     boolean var24 = (Boolean)var3.getValue(func_176267_a(var18));
                     boolean var25 = (Boolean)var3.getValue(func_176267_a(var23));
                     BlockPos var26 = var19.offset(var18);
                     BlockPos var27 = var19.offset(var23);
                     if (var24 && this.func_150093_a(var1.getBlockState(var26).getBlock())) {
                        var1.setBlockState(var19, this.getDefaultState().withProperty(func_176267_a(var18), true), 2);
                     } else if (var25 && this.func_150093_a(var1.getBlockState(var27).getBlock())) {
                        var1.setBlockState(var19, this.getDefaultState().withProperty(func_176267_a(var23), true), 2);
                     } else if (var24 && var1.isAirBlock(var26) && this.func_150093_a(var1.getBlockState(var2.offset(var18)).getBlock())) {
                        var1.setBlockState(var26, this.getDefaultState().withProperty(func_176267_a(var17.getOpposite()), true), 2);
                     } else if (var25 && var1.isAirBlock(var27) && this.func_150093_a(var1.getBlockState(var2.offset(var23)).getBlock())) {
                        var1.setBlockState(var27, this.getDefaultState().withProperty(func_176267_a(var17.getOpposite()), true), 2);
                     } else if (this.func_150093_a(var1.getBlockState(var19.offsetUp()).getBlock())) {
                        var1.setBlockState(var19, this.getDefaultState(), 2);
                     }
                  } else if (var21.blockMaterial.isOpaque() && var21.isFullCube()) {
                     var1.setBlockState(var2, var3.withProperty(func_176267_a(var17), true), 2);
                  }
               }
            } else if (var2.getY() > 1) {
               var19 = var2.offsetDown();
               IBlockState var11 = var1.getBlockState(var19);
               Block var12 = var11.getBlock();
               IBlockState var13;
               Iterator var14;
               EnumFacing var15;
               if (var12.blockMaterial == Material.air) {
                  var13 = var3;
                  var14 = EnumFacing.Plane.HORIZONTAL.iterator();

                  while(var14.hasNext()) {
                     var15 = (EnumFacing)var14.next();
                     if (var4.nextBoolean()) {
                        var13 = var13.withProperty(func_176267_a(var15), false);
                     }
                  }

                  if ((Boolean)var13.getValue(field_176273_b) || (Boolean)var13.getValue(field_176278_M) || (Boolean)var13.getValue(field_176279_N) || (Boolean)var13.getValue(field_176280_O)) {
                     var1.setBlockState(var19, var13, 2);
                  }
               } else if (var12 == this) {
                  var13 = var11;
                  var14 = EnumFacing.Plane.HORIZONTAL.iterator();

                  while(true) {
                     PropertyBool var16;
                     do {
                        if (!var14.hasNext()) {
                           if ((Boolean)var13.getValue(field_176273_b) || (Boolean)var13.getValue(field_176278_M) || (Boolean)var13.getValue(field_176279_N) || (Boolean)var13.getValue(field_176280_O)) {
                              var1.setBlockState(var19, var13, 2);
                           }

                           return;
                        }

                        var15 = (EnumFacing)var14.next();
                        var16 = func_176267_a(var15);
                     } while(!var4.nextBoolean() && (Boolean)var3.getValue(var16));

                     var13 = var13.withProperty(var16, false);
                  }
               }
            }
         }
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote && !this.func_176269_e(var1, var2, var3)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   private boolean func_176269_e(World var1, BlockPos var2, IBlockState var3) {
      IBlockState var4 = var3;
      Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(true) {
         PropertyBool var7;
         IBlockState var8;
         do {
            EnumFacing var6;
            do {
               do {
                  if (!var5.hasNext()) {
                     if (func_176268_d(var3) == 0) {
                        return false;
                     }

                     if (var4 != var3) {
                        var1.setBlockState(var2, var3, 2);
                     }

                     return true;
                  }

                  var6 = (EnumFacing)var5.next();
                  var7 = func_176267_a(var6);
               } while(!(Boolean)var3.getValue(var7));
            } while(this.func_150093_a(var1.getBlockState(var2.offset(var6)).getBlock()));

            var8 = var1.getBlockState(var2.offsetUp());
         } while(var8.getBlock() == this && (Boolean)var8.getValue(var7));

         var3 = var3.withProperty(var7, false);
      }
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if ((Boolean)var1.getValue(field_176273_b)) {
         var2 |= field_176276_R;
      }

      if ((Boolean)var1.getValue(field_176278_M)) {
         var2 |= field_176275_S;
      }

      if ((Boolean)var1.getValue(field_176279_N)) {
         var2 |= field_176272_Q;
      }

      if ((Boolean)var1.getValue(field_176280_O)) {
         var2 |= field_176271_T;
      }

      return var2;
   }

   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   static {
      field_176274_P = new PropertyBool[]{field_176277_a, field_176273_b, field_176279_N, field_176280_O, field_176278_M};
      field_176272_Q = func_176270_b(EnumFacing.SOUTH);
      field_176276_R = func_176270_b(EnumFacing.NORTH);
      field_176275_S = func_176270_b(EnumFacing.EAST);
      field_176271_T = func_176270_b(EnumFacing.WEST);
   }

   public boolean isFullCube() {
      return false;
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   static final class SwitchEnumFacing {
      static final int[] field_177057_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002049";

      static {
         try {
            field_177057_a[EnumFacing.UP.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177057_a[EnumFacing.NORTH.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177057_a[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177057_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177057_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
