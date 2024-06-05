package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine$1;
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
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final PropertyBool UP = PropertyBool.create("up");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool[] ALL_FACES = new PropertyBool[]{UP, NORTH, SOUTH, WEST, EAST};

   public BlockVine() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(UP, Boolean.valueOf(var2.getBlockState(var3.up()).getBlock().isBlockNormalCube()));
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      return true;
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
      if(((Boolean)var1.getBlockState(var2).getValue(WEST)).booleanValue()) {
         var7 = Math.max(var7, 0.0625F);
         var4 = 0.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if(((Boolean)var1.getBlockState(var2).getValue(EAST)).booleanValue()) {
         var4 = Math.min(var4, 0.9375F);
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if(((Boolean)var1.getBlockState(var2).getValue(NORTH)).booleanValue()) {
         var9 = Math.max(var9, 0.0625F);
         var6 = 0.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if(((Boolean)var1.getBlockState(var2).getValue(SOUTH)).booleanValue()) {
         var6 = Math.min(var6, 0.9375F);
         var9 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if(this.canPlaceOn(var1.getBlockState(var2.up()).getBlock())) {
         var5 = Math.min(var5, 0.9375F);
         var8 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
      }

      this.setBlockBounds(var4, var5, var6, var7, var8, var9);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      switch(BlockVine$1.$SwitchMap$net$minecraft$util$EnumFacing[var3.ordinal()]) {
      case 1:
         return this.canPlaceOn(var1.getBlockState(var2.up()).getBlock());
      case 2:
      case 3:
      case 4:
      case 5:
         return this.canPlaceOn(var1.getBlockState(var2.offset(var3.getOpposite())).getBlock());
      default:
         return false;
      }
   }

   private boolean canPlaceOn(Block var1) {
      return var1.isFullCube() && var1.blockMaterial.blocksMovement();
   }

   private boolean recheckGrownSides(World var1, BlockPos var2, IBlockState var3) {
      IBlockState var4 = var3;

      for(Object var6 : EnumFacing$Plane.HORIZONTAL) {
         PropertyBool var7 = getPropertyFor((EnumFacing)var6);
         if(((Boolean)var3.getValue(var7)).booleanValue() && !this.canPlaceOn(var1.getBlockState(var2.offset((EnumFacing)var6)).getBlock())) {
            IBlockState var8 = var1.getBlockState(var2.up());
            if(var8.getBlock() != this || !((Boolean)var8.getValue(var7)).booleanValue()) {
               var3 = var3.withProperty(var7, Boolean.FALSE);
            }
         }
      }

      if(getNumGrownFaces(var3) == 0) {
         return false;
      } else {
         if(var4 != var3) {
            var1.setBlockState(var2, var3, 2);
         }

         return true;
      }
   }

   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   public int getRenderColor(IBlockState var1) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.getBiomeGenForCoords(var2).getFoliageColorAtPos(var2);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote && !this.recheckGrownSides(var1, var2, var3)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote && var1.rand.nextInt(4) == 0) {
         boolean var5 = true;
         int var6 = 5;
         boolean var7 = false;

         label59:
         for(int var8 = -4; var8 <= 4; ++var8) {
            for(int var9 = -4; var9 <= 4; ++var9) {
               for(int var10 = -1; var10 <= 1; ++var10) {
                  if(var1.getBlockState(var2.a(var8, var10, var9)).getBlock() == this) {
                     --var6;
                     var7 = true;
                     break label59;
                  }
               }
            }
         }

         EnumFacing var20 = EnumFacing.random(var4);
         BlockPos var21 = var2.up();
         if(var20 == EnumFacing.UP && var2.getY() < 255 && var1.isAirBlock(var21)) {
            IBlockState var24 = var3;

            for(Object var28 : EnumFacing$Plane.HORIZONTAL) {
               if(var4.nextBoolean() || !this.canPlaceOn(var1.getBlockState(var21.offset((EnumFacing)var28)).getBlock())) {
                  var24 = var24.withProperty(getPropertyFor((EnumFacing)var28), Boolean.FALSE);
               }
            }

            if(((Boolean)var24.getValue(NORTH)).booleanValue() || ((Boolean)var24.getValue(EAST)).booleanValue() || ((Boolean)var24.getValue(SOUTH)).booleanValue() || ((Boolean)var24.getValue(WEST)).booleanValue()) {
               var1.setBlockState(var21, var24, 2);
            }
         } else if(var20.getAxis().isHorizontal() && !((Boolean)var3.getValue(getPropertyFor(var20))).booleanValue()) {
            BlockPos var23 = var2.offset(var20);
            Block var25 = var1.getBlockState(var23).getBlock();
            if(var25.blockMaterial == Material.air) {
               EnumFacing var27 = var20.rotateY();
               EnumFacing var30 = var20.rotateYCCW();
               boolean var32 = ((Boolean)var3.getValue(getPropertyFor(var27))).booleanValue();
               boolean var34 = ((Boolean)var3.getValue(getPropertyFor(var30))).booleanValue();
               BlockPos var35 = var23.offset(var27);
               BlockPos var17 = var23.offset(var30);
               if(this.canPlaceOn(var1.getBlockState(var35).getBlock())) {
                  var1.setBlockState(var23, this.getDefaultState().withProperty(getPropertyFor(var27), Boolean.TRUE), 2);
               } else if(this.canPlaceOn(var1.getBlockState(var17).getBlock())) {
                  var1.setBlockState(var23, this.getDefaultState().withProperty(getPropertyFor(var30), Boolean.TRUE), 2);
               } else if(var1.isAirBlock(var35) && this.canPlaceOn(var1.getBlockState(var2.offset(var27)).getBlock())) {
                  var1.setBlockState(var35, this.getDefaultState().withProperty(getPropertyFor(var20.getOpposite()), Boolean.TRUE), 2);
               } else if(var1.isAirBlock(var17) && this.canPlaceOn(var1.getBlockState(var2.offset(var30)).getBlock())) {
                  var1.setBlockState(var17, this.getDefaultState().withProperty(getPropertyFor(var20.getOpposite()), Boolean.TRUE), 2);
               } else if(this.canPlaceOn(var1.getBlockState(var23.up()).getBlock())) {
                  var1.setBlockState(var23, this.getDefaultState(), 2);
               }
            } else if(var25.blockMaterial.isOpaque() && var25.isFullCube()) {
               var1.setBlockState(var2, var3.withProperty(getPropertyFor(var20), Boolean.TRUE), 2);
            }
         } else if(var2.getY() > 1) {
            BlockPos var22 = var2.down();
            IBlockState var11 = var1.getBlockState(var22);
            Block var12 = var11.getBlock();
            if(var12.blockMaterial == Material.air) {
               IBlockState var13 = var3;

               for(Object var15 : EnumFacing$Plane.HORIZONTAL) {
                  if(var4.nextBoolean()) {
                     var13 = var13.withProperty(getPropertyFor((EnumFacing)var15), Boolean.FALSE);
                  }
               }

               if(((Boolean)var13.getValue(NORTH)).booleanValue() || ((Boolean)var13.getValue(EAST)).booleanValue() || ((Boolean)var13.getValue(SOUTH)).booleanValue() || ((Boolean)var13.getValue(WEST)).booleanValue()) {
                  var1.setBlockState(var22, var13, 2);
               }
            } else if(var12 == this) {
               IBlockState var29 = var11;

               for(Object var33 : EnumFacing$Plane.HORIZONTAL) {
                  PropertyBool var16 = getPropertyFor((EnumFacing)var33);
                  if(var4.nextBoolean() && ((Boolean)var3.getValue(var16)).booleanValue()) {
                     var29 = var29.withProperty(var16, Boolean.TRUE);
                  }
               }

               if(((Boolean)var29.getValue(NORTH)).booleanValue() || ((Boolean)var29.getValue(EAST)).booleanValue() || ((Boolean)var29.getValue(SOUTH)).booleanValue() || ((Boolean)var29.getValue(WEST)).booleanValue()) {
                  var1.setBlockState(var22, var29, 2);
               }
            }
         }
      }

   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.getDefaultState().withProperty(UP, Boolean.FALSE).withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE);
      return var3.getAxis().isHorizontal()?var9.withProperty(getPropertyFor(var3.getOpposite()), Boolean.TRUE):var9;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if(!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().getItem() == Items.shears) {
         var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(var1, var3, new ItemStack(Blocks.vine, 1, 0));
      } else {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(SOUTH, Boolean.valueOf((var1 & 1) > 0)).withProperty(WEST, Boolean.valueOf((var1 & 2) > 0)).withProperty(NORTH, Boolean.valueOf((var1 & 4) > 0)).withProperty(EAST, Boolean.valueOf((var1 & 8) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if(((Boolean)var1.getValue(SOUTH)).booleanValue()) {
         var2 |= 1;
      }

      if(((Boolean)var1.getValue(WEST)).booleanValue()) {
         var2 |= 2;
      }

      if(((Boolean)var1.getValue(NORTH)).booleanValue()) {
         var2 |= 4;
      }

      if(((Boolean)var1.getValue(EAST)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{UP, NORTH, EAST, SOUTH, WEST});
   }

   public static PropertyBool getPropertyFor(EnumFacing var0) {
      switch(BlockVine$1.$SwitchMap$net$minecraft$util$EnumFacing[var0.ordinal()]) {
      case 1:
         return UP;
      case 2:
         return NORTH;
      case 3:
         return SOUTH;
      case 4:
         return EAST;
      case 5:
         return WEST;
      default:
         throw new IllegalArgumentException(var0 + " is an invalid choice");
      }
   }

   public static int getNumGrownFaces(IBlockState var0) {
      int var1 = 0;

      for(PropertyBool var5 : ALL_FACES) {
         if(((Boolean)var0.getValue(var5)).booleanValue()) {
            ++var1;
         }
      }

      return var1;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
