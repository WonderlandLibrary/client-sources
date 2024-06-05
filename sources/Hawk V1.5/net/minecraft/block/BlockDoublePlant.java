package net.minecraft.block;

import java.util.List;
import java.util.Random;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockDoublePlant extends BlockBush implements IGrowable {
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockDoublePlant.EnumPlantType.class);
   private static final String __OBFID = "CL_00000231";
   public static final PropertyEnum HALF_PROP = PropertyEnum.create("half", BlockDoublePlant.EnumBlockHalf.class);

   public IBlockState getStateFromMeta(int var1) {
      return (var1 & 8) > 0 ? this.getDefaultState().withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.LOWER).withProperty(VARIANT_PROP, BlockDoublePlant.EnumPlantType.func_176938_a(var1 & 7));
   }

   public BlockDoublePlant() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockDoublePlant.EnumPlantType.SUNFLOWER).withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.LOWER));
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.setUnlocalizedName("doublePlant");
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public BlockDoublePlant.EnumPlantType func_176490_e(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if (var3.getBlock() == this) {
         var3 = this.getActualState(var3, var1, var2);
         return (BlockDoublePlant.EnumPlantType)var3.getValue(VARIANT_PROP);
      } else {
         return BlockDoublePlant.EnumPlantType.FERN;
      }
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      BlockDoublePlant.EnumPlantType var4 = this.func_176490_e(var1, var2);
      return var4 != BlockDoublePlant.EnumPlantType.GRASS && var4 != BlockDoublePlant.EnumPlantType.FERN ? 16777215 : BiomeColorHelper.func_180286_a(var1, var2);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockDoublePlant.EnumPlantType[] var4 = BlockDoublePlant.EnumPlantType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockDoublePlant.EnumPlantType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176936_a()));
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && var1.isAirBlock(var2.offsetUp());
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      if (var3.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return var1.getBlockState(var2.offsetDown()).getBlock() == this;
      } else {
         IBlockState var4 = var1.getBlockState(var2.offsetUp());
         return var4.getBlock() == this && super.canBlockStay(var1, var2, var4);
      }
   }

   public int getDamageValue(World var1, BlockPos var2) {
      return this.func_176490_e(var1, var2).func_176936_a();
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (var1.isRemote || var2.getCurrentEquippedItem() == null || var2.getCurrentEquippedItem().getItem() != Items.shears || var4.getValue(HALF_PROP) != BlockDoublePlant.EnumBlockHalf.LOWER || !this.func_176489_b(var1, var3, var4, var2)) {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   public void func_176491_a(World var1, BlockPos var2, BlockDoublePlant.EnumPlantType var3, int var4) {
      var1.setBlockState(var2, this.getDefaultState().withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.LOWER).withProperty(VARIANT_PROP, var3), var4);
      var1.setBlockState(var2.offsetUp(), this.getDefaultState().withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.UPPER), var4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.setBlockState(var2.offsetUp(), this.getDefaultState().withProperty(HALF_PROP, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      if (var3.getBlock() != this) {
         return true;
      } else {
         BlockDoublePlant.EnumPlantType var4 = (BlockDoublePlant.EnumPlantType)this.getActualState(var3, var1, var2).getValue(VARIANT_PROP);
         return var4 == BlockDoublePlant.EnumPlantType.FERN || var4 == BlockDoublePlant.EnumPlantType.GRASS;
      }
   }

   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XZ;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      if (var1.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return null;
      } else {
         BlockDoublePlant.EnumPlantType var4 = (BlockDoublePlant.EnumPlantType)var1.getValue(VARIANT_PROP);
         return var4 == BlockDoublePlant.EnumPlantType.FERN ? null : (var4 == BlockDoublePlant.EnumPlantType.GRASS ? (var2.nextInt(8) == 0 ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
      }
   }

   public int getMetaFromState(IBlockState var1) {
      return var1.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER ? 8 : ((BlockDoublePlant.EnumPlantType)var1.getValue(VARIANT_PROP)).func_176936_a();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{HALF_PROP, VARIANT_PROP});
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      spawnAsEntity(var1, var3, new ItemStack(this, 1, this.func_176490_e(var1, var3).func_176936_a()));
   }

   public int damageDropped(IBlockState var1) {
      return var1.getValue(HALF_PROP) != BlockDoublePlant.EnumBlockHalf.UPPER && var1.getValue(VARIANT_PROP) != BlockDoublePlant.EnumPlantType.GRASS ? ((BlockDoublePlant.EnumPlantType)var1.getValue(VARIANT_PROP)).func_176936_a() : 0;
   }

   protected void func_176475_e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.canBlockStay(var1, var2, var3)) {
         boolean var4 = var3.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER;
         BlockPos var5 = var4 ? var2 : var2.offsetUp();
         BlockPos var6 = var4 ? var2.offsetDown() : var2;
         Object var7 = var4 ? this : var1.getBlockState(var5).getBlock();
         Object var8 = var4 ? var1.getBlockState(var6).getBlock() : this;
         if (var7 == this) {
            var1.setBlockState(var5, Blocks.air.getDefaultState(), 3);
         }

         if (var8 == this) {
            var1.setBlockState(var6, Blocks.air.getDefaultState(), 3);
            if (!var4) {
               this.dropBlockAsItem(var1, var6, var3, 0);
            }
         }
      }

   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         IBlockState var4 = var2.getBlockState(var3.offsetDown());
         if (var4.getBlock() == this) {
            var1 = var1.withProperty(VARIANT_PROP, var4.getValue(VARIANT_PROP));
         }
      }

      return var1;
   }

   private boolean func_176489_b(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockDoublePlant.EnumPlantType var5 = (BlockDoublePlant.EnumPlantType)var3.getValue(VARIANT_PROP);
      if (var5 != BlockDoublePlant.EnumPlantType.FERN && var5 != BlockDoublePlant.EnumPlantType.GRASS) {
         return false;
      } else {
         var4.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         int var6 = (var5 == BlockDoublePlant.EnumPlantType.GRASS ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).func_177044_a();
         spawnAsEntity(var1, var2, new ItemStack(Blocks.tallgrass, 2, var6));
         return true;
      }
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var3.getValue(HALF_PROP) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         if (var1.getBlockState(var2.offsetDown()).getBlock() == this) {
            if (!var4.capabilities.isCreativeMode) {
               IBlockState var5 = var1.getBlockState(var2.offsetDown());
               BlockDoublePlant.EnumPlantType var6 = (BlockDoublePlant.EnumPlantType)var5.getValue(VARIANT_PROP);
               if (var6 != BlockDoublePlant.EnumPlantType.FERN && var6 != BlockDoublePlant.EnumPlantType.GRASS) {
                  var1.destroyBlock(var2.offsetDown(), true);
               } else if (!var1.isRemote) {
                  if (var4.getCurrentEquippedItem() != null && var4.getCurrentEquippedItem().getItem() == Items.shears) {
                     this.func_176489_b(var1, var2, var5, var4);
                     var1.setBlockToAir(var2.offsetDown());
                  } else {
                     var1.destroyBlock(var2.offsetDown(), true);
                  }
               } else {
                  var1.setBlockToAir(var2.offsetDown());
               }
            } else {
               var1.setBlockToAir(var2.offsetDown());
            }
         }
      } else if (var4.capabilities.isCreativeMode && var1.getBlockState(var2.offsetUp()).getBlock() == this) {
         var1.setBlockState(var2.offsetUp(), Blocks.air.getDefaultState(), 2);
      }

      super.onBlockHarvested(var1, var2, var3, var4);
   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      BlockDoublePlant.EnumPlantType var5 = this.func_176490_e(var1, var2);
      return var5 != BlockDoublePlant.EnumPlantType.GRASS && var5 != BlockDoublePlant.EnumPlantType.FERN;
   }

   static enum EnumBlockHalf implements IStringSerializable {
      private static final String __OBFID = "CL_00002122";
      private static final BlockDoublePlant.EnumBlockHalf[] ENUM$VALUES = new BlockDoublePlant.EnumBlockHalf[]{UPPER, LOWER};
      UPPER("UPPER", 0),
      LOWER("LOWER", 1);

      private static final BlockDoublePlant.EnumBlockHalf[] $VALUES = new BlockDoublePlant.EnumBlockHalf[]{UPPER, LOWER};

      public String toString() {
         return this.getName();
      }

      public String getName() {
         return this == UPPER ? "upper" : "lower";
      }

      private EnumBlockHalf(String var3, int var4) {
      }
   }

   public static enum EnumPlantType implements IStringSerializable {
      private final String field_176950_i;
      private static final BlockDoublePlant.EnumPlantType[] $VALUES = new BlockDoublePlant.EnumPlantType[]{SUNFLOWER, SYRINGA, GRASS, FERN, ROSE, PAEONIA};
      GRASS("GRASS", 2, 2, "double_grass", "grass");

      private final int field_176949_h;
      SUNFLOWER("SUNFLOWER", 0, 0, "sunflower"),
      SYRINGA("SYRINGA", 1, 1, "syringa");

      private static final BlockDoublePlant.EnumPlantType[] ENUM$VALUES = new BlockDoublePlant.EnumPlantType[]{SUNFLOWER, SYRINGA, GRASS, FERN, ROSE, PAEONIA};
      private static final String __OBFID = "CL_00002121";
      PAEONIA("PAEONIA", 5, 5, "paeonia"),
      FERN("FERN", 3, 3, "double_fern", "fern");

      private static final BlockDoublePlant.EnumPlantType[] field_176941_g = new BlockDoublePlant.EnumPlantType[values().length];
      ROSE("ROSE", 4, 4, "double_rose", "rose");

      private final String field_176947_j;

      static {
         BlockDoublePlant.EnumPlantType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockDoublePlant.EnumPlantType var3 = var0[var2];
            field_176941_g[var3.func_176936_a()] = var3;
         }

      }

      public String getName() {
         return this.field_176950_i;
      }

      public int func_176936_a() {
         return this.field_176949_h;
      }

      public static BlockDoublePlant.EnumPlantType func_176938_a(int var0) {
         if (var0 < 0 || var0 >= field_176941_g.length) {
            var0 = 0;
         }

         return field_176941_g[var0];
      }

      private EnumPlantType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      public String func_176939_c() {
         return this.field_176947_j;
      }

      public String toString() {
         return this.field_176950_i;
      }

      private EnumPlantType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176949_h = var5;
         this.field_176950_i = var6;
         this.field_176947_j = var7;
      }
   }
}
