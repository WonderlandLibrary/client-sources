package net.minecraft.block;

import net.iV;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
   public static final iV P = iV.a("power", 0, 15);
   private final int field_150068_a;

   protected BlockPressurePlateWeighted(Material var1, int var2) {
      this(var1, var2, var1.getMaterialMapColor());
   }

   protected BlockPressurePlateWeighted(Material var1, int var2, MapColor var3) {
      super(var1, var3);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.field_150068_a = var2;
   }

   protected int computeRedstoneStrength(World var1, BlockPos var2) {
      int var3 = Math.min(var1.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(var2)).size(), this.field_150068_a);
      float var4 = (float)Math.min(this.field_150068_a, var3) / (float)this.field_150068_a;
      return MathHelper.ceiling_float_int(var4 * 15.0F);
   }

   protected int getRedstoneStrength(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected IBlockState setRedstoneStrength(IBlockState var1, int var2) {
      return var1.withProperty(P, Integer.valueOf(var2));
   }

   public int tickRate(World var1) {
      return 10;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P});
   }
}
