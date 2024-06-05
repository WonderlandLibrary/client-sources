package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.Block$EnumOffsetType;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoublePlant$EnumBlockHalf;
import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockDoublePlant extends BlockBush implements IGrowable {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockDoublePlant$EnumPlantType.class);
   public static final PropertyEnum HALF = PropertyEnum.create("half", BlockDoublePlant$EnumBlockHalf.class);
   public static final PropertyEnum field_181084_N = BlockDirectional.FACING;

   public BlockDoublePlant() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockDoublePlant$EnumPlantType.SUNFLOWER).withProperty(HALF, BlockDoublePlant$EnumBlockHalf.LOWER).withProperty(field_181084_N, EnumFacing.NORTH));
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.setUnlocalizedName("doublePlant");
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public BlockDoublePlant$EnumPlantType getVariant(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if(var3.getBlock() == this) {
         var3 = this.getActualState(var3, var1, var2);
         return (BlockDoublePlant$EnumPlantType)var3.getValue(VARIANT);
      } else {
         return BlockDoublePlant$EnumPlantType.FERN;
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && var1.isAirBlock(var2.up());
   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if(var3.getBlock() != this) {
         return true;
      } else {
         BlockDoublePlant$EnumPlantType var4 = (BlockDoublePlant$EnumPlantType)this.getActualState(var3, var1, var2).getValue(VARIANT);
         return var4 == BlockDoublePlant$EnumPlantType.FERN || var4 == BlockDoublePlant$EnumPlantType.GRASS;
      }
   }

   protected void checkAndDropBlock(World var1, BlockPos var2, IBlockState var3) {
      if(!this.canBlockStay(var1, var2, var3)) {
         boolean var4 = var3.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER;
         BlockPos var6 = var2.down();
         Block var8 = var1.getBlockState(var6).getBlock();
         if(this == this) {
            var1.setBlockState(var2, Blocks.air.getDefaultState(), 2);
         }

         if(var8 == this) {
            var1.setBlockState(var6, Blocks.air.getDefaultState(), 3);
            this.dropBlockAsItem(var1, var6, var3, 0);
         }
      }

   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      if(var3.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER) {
         return var1.getBlockState(var2.down()).getBlock() == this;
      } else {
         IBlockState var4 = var1.getBlockState(var2.up());
         return var4.getBlock() == this && super.canBlockStay(var1, var2, var4);
      }
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      if(var1.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER) {
         return null;
      } else {
         BlockDoublePlant$EnumPlantType var4 = (BlockDoublePlant$EnumPlantType)var1.getValue(VARIANT);
         return var4 == BlockDoublePlant$EnumPlantType.FERN?null:(var4 == BlockDoublePlant$EnumPlantType.GRASS?(var2.nextInt(8) == 0?Items.wheat_seeds:null):Item.getItemFromBlock(this));
      }
   }

   public int damageDropped(IBlockState var1) {
      return var1.getValue(HALF) != BlockDoublePlant$EnumBlockHalf.UPPER && var1.getValue(VARIANT) != BlockDoublePlant$EnumPlantType.GRASS?((BlockDoublePlant$EnumPlantType)var1.getValue(VARIANT)).getMeta():0;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      BlockDoublePlant$EnumPlantType var4 = this.getVariant(var1, var2);
      return var4 != BlockDoublePlant$EnumPlantType.GRASS && var4 != BlockDoublePlant$EnumPlantType.FERN?16777215:BiomeColorHelper.getGrassColorAtPos(var1, var2);
   }

   public void placeAt(World var1, BlockPos var2, BlockDoublePlant$EnumPlantType var3, int var4) {
      var1.setBlockState(var2, this.getDefaultState().withProperty(HALF, BlockDoublePlant$EnumBlockHalf.LOWER).withProperty(VARIANT, var3), var4);
      var1.setBlockState(var2.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant$EnumBlockHalf.UPPER), var4);
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant$EnumBlockHalf.UPPER), 2);
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if(var1.isRemote || var2.getCurrentEquippedItem() == null || var2.getCurrentEquippedItem().getItem() != Items.shears || var4.getValue(HALF) != BlockDoublePlant$EnumBlockHalf.LOWER || !this.onHarvest(var1, var3, var4, var2)) {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if(var3.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER) {
         if(var1.getBlockState(var2.down()).getBlock() == this) {
            if(!var4.abilities.isCreative()) {
               IBlockState var5 = var1.getBlockState(var2.down());
               BlockDoublePlant$EnumPlantType var6 = (BlockDoublePlant$EnumPlantType)var5.getValue(VARIANT);
               if(var6 != BlockDoublePlant$EnumPlantType.FERN && var6 != BlockDoublePlant$EnumPlantType.GRASS) {
                  var1.destroyBlock(var2.down(), true);
               } else if(!var1.isRemote) {
                  if(var4.getCurrentEquippedItem() != null && var4.getCurrentEquippedItem().getItem() == Items.shears) {
                     this.onHarvest(var1, var2, var5, var4);
                     var1.setBlockToAir(var2.down());
                  } else {
                     var1.destroyBlock(var2.down(), true);
                  }
               } else {
                  var1.setBlockToAir(var2.down());
               }
            } else {
               var1.setBlockToAir(var2.down());
            }
         }
      } else if(var4.abilities.isCreative() && var1.getBlockState(var2.up()).getBlock() == this) {
         var1.setBlockState(var2.up(), Blocks.air.getDefaultState(), 2);
      }

      super.onBlockHarvested(var1, var2, var3, var4);
   }

   private boolean onHarvest(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockDoublePlant$EnumPlantType var5 = (BlockDoublePlant$EnumPlantType)var3.getValue(VARIANT);
      if(var5 != BlockDoublePlant$EnumPlantType.FERN && var5 != BlockDoublePlant$EnumPlantType.GRASS) {
         return false;
      } else {
         var4.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         int var6 = (var5 == BlockDoublePlant$EnumPlantType.GRASS?BlockTallGrass$EnumType.GRASS:BlockTallGrass$EnumType.FERN).getMeta();
         spawnAsEntity(var1, var2, new ItemStack(Blocks.tallgrass, 2, var6));
         return true;
      }
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockDoublePlant$EnumPlantType var7 : BlockDoublePlant$EnumPlantType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMeta()));
      }

   }

   public int getDamageValue(World var1, BlockPos var2) {
      return this.getVariant(var1, var2).getMeta();
   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      BlockDoublePlant$EnumPlantType var5 = this.getVariant(var1, var2);
      return var5 != BlockDoublePlant$EnumPlantType.GRASS && var5 != BlockDoublePlant$EnumPlantType.FERN;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      spawnAsEntity(var1, var3, new ItemStack(this, 1, this.getVariant(var1, var3).getMeta()));
   }

   public IBlockState getStateFromMeta(int var1) {
      return (var1 & 8) > 0?this.getDefaultState().withProperty(HALF, BlockDoublePlant$EnumBlockHalf.UPPER):this.getDefaultState().withProperty(HALF, BlockDoublePlant$EnumBlockHalf.LOWER).withProperty(VARIANT, BlockDoublePlant$EnumPlantType.byMetadata(var1 & 7));
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if(var1.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER) {
         IBlockState var4 = var2.getBlockState(var3.down());
         if(var4.getBlock() == this) {
            var1 = var1.withProperty(VARIANT, var4.getValue(VARIANT));
         }
      }

      return var1;
   }

   public int getMetaFromState(IBlockState var1) {
      return var1.getValue(HALF) == BlockDoublePlant$EnumBlockHalf.UPPER?8 | ((EnumFacing)var1.getValue(field_181084_N)).getHorizontalIndex():((BlockDoublePlant$EnumPlantType)var1.getValue(VARIANT)).getMeta();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{HALF, VARIANT, field_181084_N});
   }

   public Block$EnumOffsetType getOffsetType() {
      return Block$EnumOffsetType.XZ;
   }
}
