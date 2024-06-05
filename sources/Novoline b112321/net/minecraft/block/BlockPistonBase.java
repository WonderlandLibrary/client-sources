package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase$1;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
   private final boolean R;

   public BlockPistonBase(boolean var1) {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXTENDED, Boolean.FALSE));
      this.R = var1;
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(FACING, getFacingFromEntity(var1, var2, var4)), 2);
      if(!var1.isRemote) {
         this.checkForMove(var1, var2, var3);
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote) {
         this.checkForMove(var1, var2, var3);
      }

   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if(!var1.isRemote && var1.getTileEntity(var2) == null) {
         this.checkForMove(var1, var2, var3);
      }

   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, getFacingFromEntity(var1, var2, var8)).withProperty(EXTENDED, Boolean.FALSE);
   }

   private void checkForMove(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      boolean var5 = this.shouldBeExtended(var1, var2, var4);
      if(!((Boolean)var3.getValue(EXTENDED)).booleanValue()) {
         if((new BlockPistonStructureHelper(var1, var2, var4, true)).canMove()) {
            var1.addBlockEvent(var2, this, 0, var4.getIndex());
         }
      } else if(((Boolean)var3.getValue(EXTENDED)).booleanValue()) {
         var1.setBlockState(var2, var3.withProperty(EXTENDED, Boolean.FALSE), 2);
         var1.addBlockEvent(var2, this, 1, var4.getIndex());
      }

   }

   private boolean shouldBeExtended(World var1, BlockPos var2, EnumFacing var3) {
      for(EnumFacing var7 : EnumFacing.values()) {
         if(var7 != var3 && var1.isSidePowered(var2.offset(var7), var7)) {
            return true;
         }
      }

      if(var1.isSidePowered(var2, EnumFacing.DOWN)) {
         return true;
      } else {
         BlockPos var9 = var2.up();

         for(EnumFacing var8 : EnumFacing.values()) {
            if(var8 != EnumFacing.DOWN && var1.isSidePowered(var9.offset(var8), var8)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean onBlockEventReceived(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      EnumFacing var6 = (EnumFacing)var3.getValue(FACING);
      if(!var1.isRemote) {
         boolean var7 = this.shouldBeExtended(var1, var2, var6);
         if(var4 == 1) {
            var1.setBlockState(var2, var3.withProperty(EXTENDED, Boolean.TRUE), 2);
            return false;
         } else {
            return false;
         }
      } else if(!this.doMove(var1, var2, var6, true)) {
         return false;
      } else {
         var1.setBlockState(var2, var3.withProperty(EXTENDED, Boolean.TRUE), 2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "tile.piston.out", 0.5F, var1.rand.nextFloat() * 0.25F + 0.6F);
         return true;
      }
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if(var3.getBlock() == this && ((Boolean)var3.getValue(EXTENDED)).booleanValue()) {
         float var4 = 0.25F;
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         switch(BlockPistonBase$1.$SwitchMap$net$minecraft$util$EnumFacing[var5.ordinal()]) {
         case 1:
            this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case 2:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
            break;
         case 3:
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
            break;
         case 4:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
            break;
         case 5:
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case 6:
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
         }
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public boolean isFullCube() {
      return false;
   }

   public static EnumFacing getFacing(int var0) {
      int var1 = var0 & 7;
      return var1 > 5?null:EnumFacing.getFront(var1);
   }

   public static EnumFacing getFacingFromEntity(World var0, BlockPos var1, EntityLivingBase var2) {
      if(MathHelper.abs((float)var2.posX - (float)var1.getX()) < 2.0F && MathHelper.abs((float)var2.posZ - (float)var1.getZ()) < 2.0F) {
         double var3 = var2.posY + (double)var2.getEyeHeight();
         if(var3 - (double)var1.getY() > 2.0D) {
            return EnumFacing.UP;
         }

         if((double)var1.getY() - var3 > 0.0D) {
            return EnumFacing.DOWN;
         }
      }

      return var2.getHorizontalFacing().getOpposite();
   }

   public static boolean canPush(Block var0, World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      if(var0 == Blocks.obsidian) {
         return false;
      } else if(!var1.getWorldBorder().contains(var2)) {
         return false;
      } else if(var2.getY() >= 0 && (var3 != EnumFacing.DOWN || var2.getY() != 0)) {
         if(var2.getY() <= var1.getHeight() - 1 && (var3 != EnumFacing.UP || var2.getY() != var1.getHeight() - 1)) {
            if(var0 != Blocks.piston && var0 != Blocks.sticky_piston) {
               if(var0.getBlockHardness(var1, var2) == -1.0F) {
                  return false;
               }

               if(var0.getMobilityFlag() == 2) {
                  return false;
               }

               if(var0.getMobilityFlag() == 1) {
                  return var4;
               }
            } else if(((Boolean)var1.getBlockState(var2).getValue(EXTENDED)).booleanValue()) {
               return false;
            }

            return !(var0 instanceof ITileEntityProvider);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean doMove(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      var1.setBlockToAir(var2.offset(var3));
      BlockPistonStructureHelper var5 = new BlockPistonStructureHelper(var1, var2, var3, var4);
      List var6 = var5.getBlocksToMove();
      List var7 = var5.getBlocksToDestroy();
      if(var5.canMove()) {
         int var8 = var6.size() + var7.size();
         Block[] var9 = new Block[var8];
         int var11 = var7.size() - 1;

         while(true) {
            BlockPos var12 = (BlockPos)var7.get(var11);
            Block var13 = var1.getBlockState(var12).getBlock();
            var13.dropBlockAsItem(var1, var12, var1.getBlockState(var12), 0);
            var1.setBlockToAir(var12);
            --var8;
            var9[var8] = var13;
            --var11;
         }
      }

      return false;
   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, getFacing(var1)).withProperty(EXTENDED, Boolean.valueOf((var1 & 8) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if(((Boolean)var1.getValue(EXTENDED)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, EXTENDED});
   }
}
