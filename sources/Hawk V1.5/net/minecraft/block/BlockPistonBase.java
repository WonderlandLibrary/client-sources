package net.minecraft.block;

import java.util.List;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends Block {
   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
   private static final String __OBFID = "CL_00000366";
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   private final boolean isSticky;

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if ((Boolean)var1.getValue(EXTENDED)) {
         var3 |= 8;
      }

      return var3;
   }

   public boolean onBlockEventReceived(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      EnumFacing var6 = (EnumFacing)var3.getValue(FACING);
      if (!var1.isRemote) {
         boolean var7 = this.func_176318_b(var1, var2, var6);
         if (var7 && var4 == 1) {
            var1.setBlockState(var2, var3.withProperty(EXTENDED, true), 2);
            return false;
         }

         if (!var7 && var4 == 0) {
            return false;
         }
      }

      if (var4 == 0) {
         if (!this.func_176319_a(var1, var2, var6, true)) {
            return false;
         }

         var1.setBlockState(var2, var3.withProperty(EXTENDED, true), 2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "tile.piston.out", 0.5F, var1.rand.nextFloat() * 0.25F + 0.6F);
      } else if (var4 == 1) {
         TileEntity var13 = var1.getTileEntity(var2.offset(var6));
         if (var13 instanceof TileEntityPiston) {
            ((TileEntityPiston)var13).clearPistonTileEntity();
         }

         var1.setBlockState(var2, Blocks.piston_extension.getDefaultState().withProperty(BlockPistonMoving.field_176426_a, var6).withProperty(BlockPistonMoving.field_176425_b, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
         var1.setTileEntity(var2, BlockPistonMoving.func_176423_a(this.getStateFromMeta(var5), var6, false, true));
         if (this.isSticky) {
            BlockPos var8 = var2.add(var6.getFrontOffsetX() * 2, var6.getFrontOffsetY() * 2, var6.getFrontOffsetZ() * 2);
            Block var9 = var1.getBlockState(var8).getBlock();
            boolean var10 = false;
            if (var9 == Blocks.piston_extension) {
               TileEntity var11 = var1.getTileEntity(var8);
               if (var11 instanceof TileEntityPiston) {
                  TileEntityPiston var12 = (TileEntityPiston)var11;
                  if (var12.func_174930_e() == var6 && var12.isExtending()) {
                     var12.clearPistonTileEntity();
                     var10 = true;
                  }
               }
            }

            if (!var10 && var9.getMaterial() != Material.air && func_180696_a(var9, var1, var8, var6.getOpposite(), false) && (var9.getMobilityFlag() == 0 || var9 == Blocks.piston || var9 == Blocks.sticky_piston)) {
               this.func_176319_a(var1, var2, var6, false);
            }
         } else {
            var1.setBlockToAir(var2.offset(var6));
         }

         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "tile.piston.in", 0.5F, var1.rand.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         this.func_176316_e(var1, var2, var3);
      }

   }

   public BlockPistonBase(boolean var1) {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXTENDED, false));
      this.isSticky = var1;
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, func_176317_b(var1)).withProperty(EXTENDED, (var1 & 8) > 0);
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   private boolean func_176318_b(World var1, BlockPos var2, EnumFacing var3) {
      EnumFacing[] var4 = EnumFacing.values();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         EnumFacing var7 = var4[var6];
         if (var7 != var3 && var1.func_175709_b(var2.offset(var7), var7)) {
            return true;
         }
      }

      if (var1.func_175709_b(var2, EnumFacing.NORTH)) {
         return true;
      } else {
         BlockPos var11 = var2.offsetUp();
         EnumFacing[] var8 = EnumFacing.values();
         var6 = var8.length;

         for(int var9 = 0; var9 < var6; ++var9) {
            EnumFacing var10 = var8[var9];
            if (var10 != EnumFacing.DOWN && var1.func_175709_b(var11.offset(var10), var10)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean func_180696_a(Block var0, World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      if (var0 == Blocks.obsidian) {
         return false;
      } else if (!var1.getWorldBorder().contains(var2)) {
         return false;
      } else if (var2.getY() < 0 || var3 == EnumFacing.DOWN && var2.getY() == 0) {
         return false;
      } else if (var2.getY() > var1.getHeight() - 1 || var3 == EnumFacing.UP && var2.getY() == var1.getHeight() - 1) {
         return false;
      } else {
         if (var0 != Blocks.piston && var0 != Blocks.sticky_piston) {
            if (var0.getBlockHardness(var1, var2) == -1.0F) {
               return false;
            }

            if (var0.getMobilityFlag() == 2) {
               return false;
            }

            if (var0.getMobilityFlag() == 1) {
               if (!var4) {
                  return false;
               }

               return true;
            }
         } else if ((Boolean)var1.getBlockState(var2).getValue(EXTENDED)) {
            return false;
         }

         return !(var0 instanceof ITileEntityProvider);
      }
   }

   public static EnumFacing func_180695_a(World var0, BlockPos var1, EntityLivingBase var2) {
      if (MathHelper.abs((float)var2.posX - (float)var1.getX()) < 2.0F && MathHelper.abs((float)var2.posZ - (float)var1.getZ()) < 2.0F) {
         double var3 = var2.posY + (double)var2.getEyeHeight();
         if (var3 - (double)var1.getY() > 2.0D) {
            return EnumFacing.UP;
         }

         if ((double)var1.getY() - var3 > 0.0D) {
            return EnumFacing.DOWN;
         }
      }

      return var2.func_174811_aO().getOpposite();
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if (var3.getBlock() == this && (Boolean)var3.getValue(EXTENDED)) {
         float var4 = 0.25F;
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         if (var5 != null) {
            switch(var5) {
            case DOWN:
               this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case UP:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
               break;
            case NORTH:
               this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
               break;
            case SOUTH:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
               break;
            case WEST:
               this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case EAST:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
         }
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote && var1.getTileEntity(var2) == null) {
         this.func_176316_e(var1, var2, var3);
      }

   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
   }

   private boolean func_176319_a(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      if (!var4) {
         var1.setBlockToAir(var2.offset(var3));
      }

      BlockPistonStructureHelper var5 = new BlockPistonStructureHelper(var1, var2, var3, var4);
      List var6 = var5.func_177254_c();
      List var7 = var5.func_177252_d();
      if (!var5.func_177253_a()) {
         return false;
      } else {
         int var8 = var6.size() + var7.size();
         Block[] var9 = new Block[var8];
         EnumFacing var10 = var4 ? var3 : var3.getOpposite();

         int var11;
         BlockPos var12;
         for(var11 = var7.size() - 1; var11 >= 0; --var11) {
            var12 = (BlockPos)var7.get(var11);
            Block var13 = var1.getBlockState(var12).getBlock();
            var13.dropBlockAsItem(var1, var12, var1.getBlockState(var12), 0);
            var1.setBlockToAir(var12);
            --var8;
            var9[var8] = var13;
         }

         IBlockState var17;
         for(var11 = var6.size() - 1; var11 >= 0; --var11) {
            var12 = (BlockPos)var6.get(var11);
            var17 = var1.getBlockState(var12);
            Block var14 = var17.getBlock();
            var14.getMetaFromState(var17);
            var1.setBlockToAir(var12);
            var12 = var12.offset(var10);
            var1.setBlockState(var12, Blocks.piston_extension.getDefaultState().withProperty(FACING, var3), 4);
            var1.setTileEntity(var12, BlockPistonMoving.func_176423_a(var17, var3, var4, false));
            --var8;
            var9[var8] = var14;
         }

         BlockPos var18 = var2.offset(var3);
         if (var4) {
            BlockPistonExtension.EnumPistonType var15 = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
            var17 = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.field_176326_a, var3).withProperty(BlockPistonExtension.field_176325_b, var15);
            IBlockState var16 = Blocks.piston_extension.getDefaultState().withProperty(BlockPistonMoving.field_176426_a, var3).withProperty(BlockPistonMoving.field_176425_b, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
            var1.setBlockState(var18, var16, 4);
            var1.setTileEntity(var18, BlockPistonMoving.func_176423_a(var17, var3, true, false));
         }

         int var19;
         for(var19 = var7.size() - 1; var19 >= 0; --var19) {
            var1.notifyNeighborsOfStateChange((BlockPos)var7.get(var19), var9[var8++]);
         }

         for(var19 = var6.size() - 1; var19 >= 0; --var19) {
            var1.notifyNeighborsOfStateChange((BlockPos)var6.get(var19), var9[var8++]);
         }

         if (var4) {
            var1.notifyNeighborsOfStateChange(var18, Blocks.piston_head);
            var1.notifyNeighborsOfStateChange(var2, this);
         }

         return true;
      }
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, func_180695_a(var1, var2, var8)).withProperty(EXTENDED, false);
   }

   public boolean isFullCube() {
      return false;
   }

   private void func_176316_e(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      boolean var5 = this.func_176318_b(var1, var2, var4);
      if (var5 && !(Boolean)var3.getValue(EXTENDED)) {
         if ((new BlockPistonStructureHelper(var1, var2, var4, true)).func_177253_a()) {
            var1.addBlockEvent(var2, this, 0, var4.getIndex());
         }
      } else if (!var5 && (Boolean)var3.getValue(EXTENDED)) {
         var1.setBlockState(var2, var3.withProperty(EXTENDED, false), 2);
         var1.addBlockEvent(var2, this, 1, var4.getIndex());
      }

   }

   public static EnumFacing func_176317_b(int var0) {
      int var1 = var0 & 7;
      return var1 > 5 ? null : EnumFacing.getFront(var1);
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2, var3.withProperty(FACING, func_180695_a(var1, var2, var4)), 2);
      if (!var1.isRemote) {
         this.func_176316_e(var1, var2, var3);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, EXTENDED});
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00002037";
      static final int[] field_177243_a = new int[EnumFacing.values().length];

      static {
         try {
            field_177243_a[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_177243_a[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177243_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177243_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177243_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177243_a[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
