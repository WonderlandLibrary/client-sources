package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPumpkin$1;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPattern$PatternHelper;
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
   private BlockPattern snowmanBasePattern;
   private BlockPattern snowmanPattern;
   private BlockPattern golemBasePattern;
   private BlockPattern golemPattern;
   private static final Predicate field_181085_Q = new BlockPumpkin$1();

   protected BlockPumpkin() {
      super(Material.gourd, MapColor.adobeColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      this.trySpawnGolem(var1, var2);
   }

   public boolean canDispenserPlace(World var1, BlockPos var2) {
      return this.getSnowmanBasePattern().match(var1, var2) != null || this.getGolemBasePattern().match(var1, var2) != null;
   }

   private void trySpawnGolem(World var1, BlockPos var2) {
      BlockPattern$PatternHelper var3;
      if((var3 = this.getSnowmanPattern().match(var1, var2)) != null) {
         for(int var4 = 0; var4 < this.getSnowmanPattern().getThumbLength(); ++var4) {
            BlockWorldState var5 = var3.translateOffset(0, var4, 0);
            var1.setBlockState(var5.getPos(), Blocks.air.getDefaultState(), 2);
         }

         EntitySnowman var10 = new EntitySnowman(var1);
         BlockPos var13 = var3.translateOffset(0, 2, 0).getPos();
         var10.setLocationAndAngles((double)var13.getX() + 0.5D, (double)var13.getY() + 0.05D, (double)var13.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var10);

         for(int var6 = 0; var6 < 120; ++var6) {
            var1.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, (double)var13.getX() + var1.rand.nextDouble(), (double)var13.getY() + var1.rand.nextDouble() * 2.5D, (double)var13.getZ() + var1.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
         }

         for(int var16 = 0; var16 < this.getSnowmanPattern().getThumbLength(); ++var16) {
            BlockWorldState var7 = var3.translateOffset(0, var16, 0);
            var1.notifyNeighborsRespectDebug(var7.getPos(), Blocks.air);
         }
      } else if((var3 = this.getGolemPattern().match(var1, var2)) != null) {
         for(int var11 = 0; var11 < this.getGolemPattern().getPalmLength(); ++var11) {
            for(int var14 = 0; var14 < this.getGolemPattern().getThumbLength(); ++var14) {
               var1.setBlockState(var3.translateOffset(var11, var14, 0).getPos(), Blocks.air.getDefaultState(), 2);
            }
         }

         BlockPos var12 = var3.translateOffset(1, 2, 0).getPos();
         EntityIronGolem var15 = new EntityIronGolem(var1);
         var15.setPlayerCreated(true);
         var15.setLocationAndAngles((double)var12.getX() + 0.5D, (double)var12.getY() + 0.05D, (double)var12.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var15);

         for(int var17 = 0; var17 < 120; ++var17) {
            var1.spawnParticle(EnumParticleTypes.SNOWBALL, (double)var12.getX() + var1.rand.nextDouble(), (double)var12.getY() + var1.rand.nextDouble() * 3.9D, (double)var12.getZ() + var1.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
         }

         for(int var18 = 0; var18 < this.getGolemPattern().getPalmLength(); ++var18) {
            for(int var19 = 0; var19 < this.getGolemPattern().getThumbLength(); ++var19) {
               BlockWorldState var8 = var3.translateOffset(var18, var19, 0);
               var1.notifyNeighborsRespectDebug(var8.getPos(), Blocks.air);
            }
         }
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(var1, var2.down());
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(FACING, var8.getHorizontalFacing().getOpposite());
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(FACING)).getHorizontalIndex();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }

   protected BlockPattern getSnowmanBasePattern() {
      if(this.snowmanBasePattern == null) {
         this.snowmanBasePattern = FactoryBlockPattern.start().aisle(new String[]{" ", "#", "#"}).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
      }

      return this.snowmanBasePattern;
   }

   protected BlockPattern getSnowmanPattern() {
      if(this.snowmanPattern == null) {
         this.snowmanPattern = FactoryBlockPattern.start().aisle(new String[]{"^", "#", "#"}).where('^', BlockWorldState.hasState(field_181085_Q)).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
      }

      return this.snowmanPattern;
   }

   protected BlockPattern getGolemBasePattern() {
      if(this.golemBasePattern == null) {
         this.golemBasePattern = FactoryBlockPattern.start().aisle(new String[]{"~ ~", "###", "~#~"}).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.golemBasePattern;
   }

   protected BlockPattern getGolemPattern() {
      if(this.golemPattern == null) {
         this.golemPattern = FactoryBlockPattern.start().aisle(new String[]{"~^~", "###", "~#~"}).where('^', BlockWorldState.hasState(field_181085_Q)).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
      }

      return this.golemPattern;
   }
}
