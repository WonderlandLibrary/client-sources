package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkull extends BlockContainer {
   private BlockPattern field_176421_O;
   public static final PropertyDirection field_176418_a = PropertyDirection.create("facing");
   private BlockPattern field_176420_N;
   private static final String __OBFID = "CL_00000307";
   public static final PropertyBool field_176417_b = PropertyBool.create("nodrop");
   private static final Predicate field_176419_M = new Predicate() {
      private static final String __OBFID = "CL_00002065";

      public boolean func_177062_a(BlockWorldState var1) {
         return var1.func_177509_a().getBlock() == Blocks.skull && var1.func_177507_b() instanceof TileEntitySkull && ((TileEntitySkull)var1.func_177507_b()).getSkullType() == 1;
      }

      public boolean apply(Object var1) {
         return this.func_177062_a((BlockWorldState)var1);
      }
   };

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
   }

   public boolean isFullCube() {
      return false;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176418_a)).getIndex();
      if ((Boolean)var1.getValue(field_176417_b)) {
         var3 |= 8;
      }

      return var3;
   }

   public void func_180679_a(World var1, BlockPos var2, TileEntitySkull var3) {
      if (var3.getSkullType() == 1 && var2.getY() >= 2 && var1.getDifficulty() != EnumDifficulty.PEACEFUL && !var1.isRemote) {
         BlockPattern var4 = this.func_176416_l();
         BlockPattern.PatternHelper var5 = var4.func_177681_a(var1, var2);
         if (var5 != null) {
            int var6;
            for(var6 = 0; var6 < 3; ++var6) {
               BlockWorldState var7 = var5.func_177670_a(var6, 0, 0);
               var1.setBlockState(var7.getPos(), var7.func_177509_a().withProperty(field_176417_b, true), 2);
            }

            for(var6 = 0; var6 < var4.func_177684_c(); ++var6) {
               for(int var14 = 0; var14 < var4.func_177685_b(); ++var14) {
                  BlockWorldState var8 = var5.func_177670_a(var6, var14, 0);
                  var1.setBlockState(var8.getPos(), Blocks.air.getDefaultState(), 2);
               }
            }

            BlockPos var15 = var5.func_177670_a(1, 0, 0).getPos();
            EntityWither var16 = new EntityWither(var1);
            BlockPos var9 = var5.func_177670_a(1, 2, 0).getPos();
            var16.setLocationAndAngles((double)var9.getX() + 0.5D, (double)var9.getY() + 0.55D, (double)var9.getZ() + 0.5D, var5.func_177669_b().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F, 0.0F);
            var16.renderYawOffset = var5.func_177669_b().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F;
            var16.func_82206_m();
            Iterator var10 = var1.getEntitiesWithinAABB(EntityPlayer.class, var16.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D)).iterator();

            while(var10.hasNext()) {
               EntityPlayer var11 = (EntityPlayer)var10.next();
               var11.triggerAchievement(AchievementList.spawnWither);
            }

            var1.spawnEntityInWorld(var16);

            int var17;
            for(var17 = 0; var17 < 120; ++var17) {
               var1.spawnParticle(EnumParticleTypes.SNOWBALL, (double)var15.getX() + var1.rand.nextDouble(), (double)(var15.getY() - 2) + var1.rand.nextDouble() * 3.9D, (double)var15.getZ() + var1.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for(var17 = 0; var17 < var4.func_177684_c(); ++var17) {
               for(int var12 = 0; var12 < var4.func_177685_b(); ++var12) {
                  BlockWorldState var13 = var5.func_177670_a(var17, var12, 0);
                  var1.func_175722_b(var13.getPos(), Blocks.air);
               }
            }
         }
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntitySkull();
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.skull;
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.capabilities.isCreativeMode) {
         var3 = var3.withProperty(field_176417_b, true);
         var1.setBlockState(var2, var3, 4);
      }

      super.onBlockHarvested(var1, var2, var3, var4);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176418_a, field_176417_b});
   }

   protected BlockPattern func_176414_j() {
      if (this.field_176420_N == null) {
         this.field_176420_N = FactoryBlockPattern.start().aisle("   ", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.field_176420_N;
   }

   public boolean func_176415_b(World var1, BlockPos var2, ItemStack var3) {
      return var3.getMetadata() == 1 && var2.getY() >= 2 && var1.getDifficulty() != EnumDifficulty.PEACEFUL && !var1.isRemote ? this.func_176414_j().func_177681_a(var1, var2) != null : false;
   }

   public int getDamageValue(World var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      return var3 instanceof TileEntitySkull ? ((TileEntitySkull)var3).getSkullType() : super.getDamageValue(var1, var2);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(field_176418_a, var8.func_174811_aO()).withProperty(field_176417_b, false);
   }

   protected BlockSkull() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176418_a, EnumFacing.NORTH).withProperty(field_176417_b, false));
      this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.skull;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176418_a, EnumFacing.getFront(var1 & 7)).withProperty(field_176417_b, (var1 & 8) > 0);
   }

   protected BlockPattern func_176416_l() {
      if (this.field_176421_O == null) {
         this.field_176421_O = FactoryBlockPattern.start().aisle("^^^", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', field_176419_M).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.field_176421_O;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      switch((EnumFacing)var1.getBlockState(var2).getValue(field_176418_a)) {
      case UP:
      default:
         this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
         break;
      case NORTH:
         this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
         break;
      case SOUTH:
         this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
         break;
      case WEST:
         this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
         break;
      case EAST:
         this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         if (!(Boolean)var3.getValue(field_176417_b)) {
            TileEntity var4 = var1.getTileEntity(var2);
            if (var4 instanceof TileEntitySkull) {
               TileEntitySkull var5 = (TileEntitySkull)var4;
               ItemStack var6 = new ItemStack(Items.skull, 1, this.getDamageValue(var1, var2));
               if (var5.getSkullType() == 3 && var5.getPlayerProfile() != null) {
                  var6.setTagCompound(new NBTTagCompound());
                  NBTTagCompound var7 = new NBTTagCompound();
                  NBTUtil.writeGameProfile(var7, var5.getPlayerProfile());
                  var6.getTagCompound().setTag("SkullOwner", var7);
               }

               spawnAsEntity(var1, var2, var6);
            }
         }

         super.breakBlock(var1, var2, var3);
      }

   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00002064";
      static final int[] field_177063_a = new int[EnumFacing.values().length];

      static {
         try {
            field_177063_a[EnumFacing.UP.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177063_a[EnumFacing.NORTH.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177063_a[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177063_a[EnumFacing.WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177063_a[EnumFacing.EAST.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
