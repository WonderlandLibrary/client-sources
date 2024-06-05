package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockPumpkin extends BlockDirectional {
   private static final String __OBFID = "CL_00000291";
   private BlockPattern field_176394_a;
   private BlockPattern field_176393_b;
   private BlockPattern field_176395_M;
   private BlockPattern field_176396_O;

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
   }

   protected BlockPattern func_176391_l() {
      if (this.field_176393_b == null) {
         this.field_176393_b = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
      }

      return this.field_176393_b;
   }

   private void createGolem(World var1, BlockPos var2) {
      BlockPattern.PatternHelper var3;
      int var4;
      int var5;
      if ((var3 = this.func_176391_l().func_177681_a(var1, var2)) != null) {
         for(var4 = 0; var4 < this.func_176391_l().func_177685_b(); ++var4) {
            BlockWorldState var6 = var3.func_177670_a(0, var4, 0);
            var1.setBlockState(var6.getPos(), Blocks.air.getDefaultState(), 2);
         }

         EntitySnowman var10 = new EntitySnowman(var1);
         BlockPos var7 = var3.func_177670_a(0, 2, 0).getPos();
         var10.setLocationAndAngles((double)var7.getX() + 0.5D, (double)var7.getY() + 0.05D, (double)var7.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var10);

         for(var5 = 0; var5 < 120; ++var5) {
            var1.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, (double)var7.getX() + var1.rand.nextDouble(), (double)var7.getY() + var1.rand.nextDouble() * 2.5D, (double)var7.getZ() + var1.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
         }

         for(var5 = 0; var5 < this.func_176391_l().func_177685_b(); ++var5) {
            BlockWorldState var8 = var3.func_177670_a(0, var5, 0);
            var1.func_175722_b(var8.getPos(), Blocks.air);
         }
      } else if ((var3 = this.func_176388_T().func_177681_a(var1, var2)) != null) {
         for(var4 = 0; var4 < this.func_176388_T().func_177684_c(); ++var4) {
            for(int var11 = 0; var11 < this.func_176388_T().func_177685_b(); ++var11) {
               var1.setBlockState(var3.func_177670_a(var4, var11, 0).getPos(), Blocks.air.getDefaultState(), 2);
            }
         }

         BlockPos var12 = var3.func_177670_a(1, 2, 0).getPos();
         EntityIronGolem var13 = new EntityIronGolem(var1);
         var13.setPlayerCreated(true);
         var13.setLocationAndAngles((double)var12.getX() + 0.5D, (double)var12.getY() + 0.05D, (double)var12.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var13);

         for(var5 = 0; var5 < 120; ++var5) {
            var1.spawnParticle(EnumParticleTypes.SNOWBALL, (double)var12.getX() + var1.rand.nextDouble(), (double)var12.getY() + var1.rand.nextDouble() * 3.9D, (double)var12.getZ() + var1.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
         }

         for(var5 = 0; var5 < this.func_176388_T().func_177684_c(); ++var5) {
            for(int var14 = 0; var14 < this.func_176388_T().func_177685_b(); ++var14) {
               BlockWorldState var9 = var3.func_177670_a(var5, var14, 0);
               var1.func_175722_b(var9.getPos(), Blocks.air);
            }
         }
      }

   }

   protected BlockPumpkin() {
      super(Material.gourd);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, EnumFacing.NORTH));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   protected BlockPattern func_176392_j() {
      if (this.field_176394_a == null) {
         this.field_176394_a = FactoryBlockPattern.start().aisle(" ", "#", "#").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
      }

      return this.field_176394_a;
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(AGE)).getHorizontalIndex();
   }

   public boolean func_176390_d(World var1, BlockPos var2) {
      return this.func_176392_j().func_177681_a(var1, var2) != null || this.func_176389_S().func_177681_a(var1, var2) != null;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      this.createGolem(var1, var2);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(AGE, var8.func_174811_aO().getOpposite());
   }

   protected BlockPattern func_176388_T() {
      if (this.field_176396_O == null) {
         this.field_176396_O = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.field_176396_O;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE});
   }

   protected BlockPattern func_176389_S() {
      if (this.field_176395_M == null) {
         this.field_176395_M = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.field_176395_M;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(var1));
   }
}
