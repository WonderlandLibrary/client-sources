package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEndPortalFrame extends Block {
   public static final PropertyBool field_176507_b;
   public static final PropertyDirection field_176508_a;
   private static final String __OBFID = "CL_00000237";

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176508_a, field_176507_b});
   }

   public BlockEndPortalFrame() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176508_a, EnumFacing.NORTH).withProperty(field_176507_b, false));
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176507_b, (var1 & 4) != 0).withProperty(field_176508_a, EnumFacing.getHorizontal(var1 & 3));
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      if ((Boolean)var1.getBlockState(var2).getValue(field_176507_b)) {
         this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
         super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      }

      this.setBlockBoundsForItemRender();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(field_176508_a, var8.func_174811_aO().getOpposite()).withProperty(field_176507_b, false);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return null;
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
   }

   static {
      field_176508_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      field_176507_b = PropertyBool.create("eye");
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(field_176508_a)).getHorizontalIndex();
      if ((Boolean)var1.getValue(field_176507_b)) {
         var3 |= 4;
      }

      return var3;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return (Boolean)var1.getBlockState(var2).getValue(field_176507_b) ? 15 : 0;
   }
}
