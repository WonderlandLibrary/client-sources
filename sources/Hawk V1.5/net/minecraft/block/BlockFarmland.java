package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockFarmland extends Block {
   public static final PropertyInteger field_176531_a = PropertyInteger.create("moisture", 0, 7);
   private static final String __OBFID = "CL_00000241";

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176531_a});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176531_a, var1 & 7);
   }

   protected BlockFarmland() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176531_a, 0));
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.setLightOpacity(255);
   }

   private boolean func_176530_e(World var1, BlockPos var2) {
      Iterator var3 = BlockPos.getAllInBoxMutable(var2.add(-4, 0, -4), var2.add(4, 1, 4)).iterator();

      while(var3.hasNext()) {
         BlockPos.MutableBlockPos var4 = (BlockPos.MutableBlockPos)var3.next();
         if (var1.getBlockState(var4).getBlock().getMaterial() == Material.water) {
            return true;
         }
      }

      return false;
   }

   private boolean func_176529_d(World var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2.offsetUp()).getBlock();
      return var3 instanceof BlockCrops || var3 instanceof BlockStem;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.dirt);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), var2, var3);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.onNeighborBlockChange(var1, var2, var3, var4);
      if (var1.getBlockState(var2.offsetUp()).getBlock().getMaterial().isSolid()) {
         var1.setBlockState(var2, Blocks.dirt.getDefaultState());
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB((double)var2.getX(), (double)var2.getY(), (double)var2.getZ(), (double)(var2.getX() + 1), (double)(var2.getY() + 1), (double)(var2.getZ() + 1));
   }

   public boolean isFullCube() {
      return false;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = (Integer)var3.getValue(field_176531_a);
      if (!this.func_176530_e(var1, var2) && !var1.func_175727_C(var2.offsetUp())) {
         if (var5 > 0) {
            var1.setBlockState(var2, var3.withProperty(field_176531_a, var5 - 1), 2);
         } else if (!this.func_176529_d(var1, var2)) {
            var1.setBlockState(var2, Blocks.dirt.getDefaultState());
         }
      } else if (var5 < 7) {
         var1.setBlockState(var2, var3.withProperty(field_176531_a, 7), 2);
      }

   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(field_176531_a);
   }

   public void onFallenUpon(World var1, BlockPos var2, Entity var3, float var4) {
      if (var3 instanceof EntityLivingBase) {
         if (!var1.isRemote && var1.rand.nextFloat() < var4 - 0.5F) {
            if (!(var3 instanceof EntityPlayer) && !var1.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
               return;
            }

            var1.setBlockState(var2, Blocks.dirt.getDefaultState());
         }

         super.onFallenUpon(var1, var2, var3, var4);
      }

   }
}
