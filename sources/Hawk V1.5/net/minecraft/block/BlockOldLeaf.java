package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOldLeaf extends BlockLeaves {
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
      private static final String __OBFID = "CL_00002085";

      public boolean apply(Object var1) {
         return this.func_180202_a((BlockPlanks.EnumType)var1);
      }

      public boolean func_180202_a(BlockPlanks.EnumType var1) {
         return var1.func_176839_a() < 4;
      }
   });
   private static final String __OBFID = "CL_00000280";

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      IBlockState var4 = var1.getBlockState(var2);
      if (var4.getBlock() == this) {
         BlockPlanks.EnumType var5 = (BlockPlanks.EnumType)var4.getValue(VARIANT_PROP);
         if (var5 == BlockPlanks.EnumType.SPRUCE) {
            return ColorizerFoliage.getFoliageColorPine();
         }

         if (var5 == BlockPlanks.EnumType.BIRCH) {
            return ColorizerFoliage.getFoliageColorBirch();
         }
      }

      return super.colorMultiplier(var1, var2, var3);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
   }

   protected int func_176232_d(IBlockState var1) {
      return var1.getValue(VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE ? 40 : super.func_176232_d(var1);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP, field_176236_b, field_176237_a});
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.OAK.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()));
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().getItem() == Items.shears) {
         var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(var1, var3, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)var4.getValue(VARIANT_PROP)).func_176839_a()));
      } else {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   public int getRenderColor(IBlockState var1) {
      if (var1.getBlock() != this) {
         return super.getRenderColor(var1);
      } else {
         BlockPlanks.EnumType var2 = (BlockPlanks.EnumType)var1.getValue(VARIANT_PROP);
         return var2 == BlockPlanks.EnumType.SPRUCE ? ColorizerFoliage.getFoliageColorPine() : (var2 == BlockPlanks.EnumType.BIRCH ? ColorizerFoliage.getFoliageColorBirch() : super.getRenderColor(var1));
      }
   }

   public BlockOldLeaf() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.OAK).withProperty(field_176236_b, true).withProperty(field_176237_a, true));
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a());
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, this.func_176233_b(var1)).withProperty(field_176237_a, (var1 & 4) == 0).withProperty(field_176236_b, (var1 & 8) > 0);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockPlanks.EnumType)var1.getValue(VARIANT_PROP)).func_176839_a();
      if (!(Boolean)var1.getValue(field_176237_a)) {
         var3 |= 4;
      }

      if ((Boolean)var1.getValue(field_176236_b)) {
         var3 |= 8;
      }

      return var3;
   }

   public BlockPlanks.EnumType func_176233_b(int var1) {
      return BlockPlanks.EnumType.func_176837_a((var1 & 3) % 4);
   }

   protected void func_176234_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if (var3.getValue(VARIANT_PROP) == BlockPlanks.EnumType.OAK && var1.rand.nextInt(var4) == 0) {
         spawnAsEntity(var1, var2, new ItemStack(Items.apple, 1, 0));
      }

   }
}
