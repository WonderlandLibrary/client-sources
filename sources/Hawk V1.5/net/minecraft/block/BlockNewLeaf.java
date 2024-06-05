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
import net.minecraft.world.World;

public class BlockNewLeaf extends BlockLeaves {
   public static final PropertyEnum field_176240_P = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
      private static final String __OBFID = "CL_00002090";

      public boolean apply(Object var1) {
         return this.func_180195_a((BlockPlanks.EnumType)var1);
      }

      public boolean func_180195_a(BlockPlanks.EnumType var1) {
         return var1.func_176839_a() >= 4;
      }
   });
   private static final String __OBFID = "CL_00000276";

   public BlockNewLeaf() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176240_P, BlockPlanks.EnumType.ACACIA).withProperty(field_176236_b, true).withProperty(field_176237_a, true));
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockPlanks.EnumType)var1.getValue(field_176240_P)).func_176839_a() - 4;
      if (!(Boolean)var1.getValue(field_176237_a)) {
         var3 |= 4;
      }

      if ((Boolean)var1.getValue(field_176236_b)) {
         var3 |= 8;
      }

      return var3;
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().getItem() == Items.shears) {
         var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(var1, var3, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)var4.getValue(field_176240_P)).func_176839_a() - 4));
      } else {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)var1.getValue(field_176240_P)).func_176839_a() - 4);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176240_P, field_176236_b, field_176237_a});
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks.EnumType)var1.getValue(field_176240_P)).func_176839_a();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176240_P, this.func_176233_b(var1)).withProperty(field_176237_a, (var1 & 4) == 0).withProperty(field_176236_b, (var1 & 8) > 0);
   }

   public BlockPlanks.EnumType func_176233_b(int var1) {
      return BlockPlanks.EnumType.func_176837_a((var1 & 3) + 4);
   }

   protected void func_176234_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if (var3.getValue(field_176240_P) == BlockPlanks.EnumType.DARK_OAK && var1.rand.nextInt(var4) == 0) {
         spawnAsEntity(var1, var2, new ItemStack(Items.apple, 1, 0));
      }

   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock().getMetaFromState(var3) & 3;
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
   }
}
