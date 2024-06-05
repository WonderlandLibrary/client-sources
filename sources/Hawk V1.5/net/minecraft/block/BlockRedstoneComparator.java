package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
   public static final PropertyBool field_176464_a = PropertyBool.create("powered");
   public static final PropertyEnum field_176463_b = PropertyEnum.create("mode", BlockRedstoneComparator.Mode.class);
   private static final String __OBFID = "CL_00000220";

   protected IBlockState func_180674_e(IBlockState var1) {
      Boolean var2 = (Boolean)var1.getValue(field_176464_a);
      BlockRedstoneComparator.Mode var3 = (BlockRedstoneComparator.Mode)var1.getValue(field_176463_b);
      EnumFacing var4 = (EnumFacing)var1.getValue(AGE);
      return Blocks.powered_comparator.getDefaultState().withProperty(AGE, var4).withProperty(field_176464_a, var2).withProperty(field_176463_b, var3);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.comparator;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      super.onBlockAdded(var1, var2, var3);
      var1.setTileEntity(var2, this.createNewTileEntity(var1, 0));
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      var1.removeTileEntity(var2);
      this.func_176400_h(var1, var2, var3);
   }

   public boolean onBlockEventReceived(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      super.onBlockEventReceived(var1, var2, var3, var4, var5);
      TileEntity var6 = var1.getTileEntity(var2);
      return var6 == null ? false : var6.receiveClientEvent(var4, var5);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityComparator();
   }

   protected boolean func_176406_l(IBlockState var1) {
      return this.isRepeaterPowered || (Boolean)var1.getValue(field_176464_a);
   }

   protected int func_176397_f(World var1, BlockPos var2, IBlockState var3) {
      int var4 = super.func_176397_f(var1, var2, var3);
      EnumFacing var5 = (EnumFacing)var3.getValue(AGE);
      BlockPos var6 = var2.offset(var5);
      Block var7 = var1.getBlockState(var6).getBlock();
      if (var7.hasComparatorInputOverride()) {
         var4 = var7.getComparatorInputOverride(var1, var6);
      } else if (var4 < 15 && var7.isNormalCube()) {
         var6 = var6.offset(var5);
         var7 = var1.getBlockState(var6).getBlock();
         if (var7.hasComparatorInputOverride()) {
            var4 = var7.getComparatorInputOverride(var1, var6);
         } else if (var7.getMaterial() == Material.air) {
            EntityItemFrame var8 = this.func_176461_a(var1, var5, var6);
            if (var8 != null) {
               var4 = var8.func_174866_q();
            }
         }
      }

      return var4;
   }

   private EntityItemFrame func_176461_a(World var1, EnumFacing var2, BlockPos var3) {
      List var4 = var1.func_175647_a(EntityItemFrame.class, new AxisAlignedBB((double)var3.getX(), (double)var3.getY(), (double)var3.getZ(), (double)(var3.getX() + 1), (double)(var3.getY() + 1), (double)(var3.getZ() + 1)), new Predicate(this, var2) {
         final BlockRedstoneComparator this$0;
         private final EnumFacing val$p_176461_2_;
         private static final String __OBFID = "CL_00002129";

         {
            this.this$0 = var1;
            this.val$p_176461_2_ = var2;
         }

         public boolean func_180416_a(Entity var1) {
            return var1 != null && var1.func_174811_aO() == this.val$p_176461_2_;
         }

         public boolean apply(Object var1) {
            return this.func_180416_a((Entity)var1);
         }
      });
      return var4.size() == 1 ? (EntityItemFrame)var4.get(0) : null;
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState().withProperty(AGE, var8.func_174811_aO().getOpposite()).withProperty(field_176464_a, false).withProperty(field_176463_b, BlockRedstoneComparator.Mode.COMPARE);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var4.capabilities.allowEdit) {
         return false;
      } else {
         var3 = var3.cycleProperty(field_176463_b);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, var3.getValue(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT ? 0.55F : 0.5F);
         var1.setBlockState(var2, var3, 2);
         this.func_176462_k(var1, var2, var3);
         return true;
      }
   }

   protected boolean func_176404_e(World var1, BlockPos var2, IBlockState var3) {
      int var4 = this.func_176397_f(var1, var2, var3);
      if (var4 >= 15) {
         return true;
      } else if (var4 == 0) {
         return false;
      } else {
         int var5 = this.func_176407_c(var1, var2, var3);
         return var5 == 0 ? true : var4 >= var5;
      }
   }

   protected void func_176398_g(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isBlockTickPending(var2, this)) {
         int var4 = this.func_176460_j(var1, var2, var3);
         TileEntity var5 = var1.getTileEntity(var2);
         int var6 = var5 instanceof TileEntityComparator ? ((TileEntityComparator)var5).getOutputSignal() : 0;
         if (var4 != var6 || this.func_176406_l(var3) != this.func_176404_e(var1, var2, var3)) {
            if (this.func_176402_i(var1, var2, var3)) {
               var1.func_175654_a(var2, this, 2, -1);
            } else {
               var1.func_175654_a(var2, this, 2, 0);
            }
         }
      }

   }

   protected int func_176408_a(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.getTileEntity(var2);
      return var4 instanceof TileEntityComparator ? ((TileEntityComparator)var4).getOutputSignal() : 0;
   }

   private int func_176460_j(World var1, BlockPos var2, IBlockState var3) {
      return var3.getValue(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT ? Math.max(this.func_176397_f(var1, var2, var3) - this.func_176407_c(var1, var2, var3), 0) : this.func_176397_f(var1, var2, var3);
   }

   protected int func_176403_d(IBlockState var1) {
      return 2;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.comparator;
   }

   public BlockRedstoneComparator(boolean var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, EnumFacing.NORTH).withProperty(field_176464_a, false).withProperty(field_176463_b, BlockRedstoneComparator.Mode.COMPARE));
      this.isBlockContainer = true;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(var1)).withProperty(field_176464_a, (var1 & 8) > 0).withProperty(field_176463_b, (var1 & 4) > 0 ? BlockRedstoneComparator.Mode.SUBTRACT : BlockRedstoneComparator.Mode.COMPARE);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE, field_176463_b, field_176464_a});
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.isRepeaterPowered) {
         var1.setBlockState(var2, this.func_180675_k(var3).withProperty(field_176464_a, true), 4);
      }

      this.func_176462_k(var1, var2, var3);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(AGE)).getHorizontalIndex();
      if ((Boolean)var1.getValue(field_176464_a)) {
         var3 |= 8;
      }

      if (var1.getValue(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT) {
         var3 |= 4;
      }

      return var3;
   }

   private void func_176462_k(World var1, BlockPos var2, IBlockState var3) {
      int var4 = this.func_176460_j(var1, var2, var3);
      TileEntity var5 = var1.getTileEntity(var2);
      int var6 = 0;
      if (var5 instanceof TileEntityComparator) {
         TileEntityComparator var7 = (TileEntityComparator)var5;
         var6 = var7.getOutputSignal();
         var7.setOutputSignal(var4);
      }

      if (var6 != var4 || var3.getValue(field_176463_b) == BlockRedstoneComparator.Mode.COMPARE) {
         boolean var9 = this.func_176404_e(var1, var2, var3);
         boolean var8 = this.func_176406_l(var3);
         if (var8 && !var9) {
            var1.setBlockState(var2, var3.withProperty(field_176464_a, false), 2);
         } else if (!var8 && var9) {
            var1.setBlockState(var2, var3.withProperty(field_176464_a, true), 2);
         }

         this.func_176400_h(var1, var2, var3);
      }

   }

   protected IBlockState func_180675_k(IBlockState var1) {
      Boolean var2 = (Boolean)var1.getValue(field_176464_a);
      BlockRedstoneComparator.Mode var3 = (BlockRedstoneComparator.Mode)var1.getValue(field_176463_b);
      EnumFacing var4 = (EnumFacing)var1.getValue(AGE);
      return Blocks.unpowered_comparator.getDefaultState().withProperty(AGE, var4).withProperty(field_176464_a, var2).withProperty(field_176463_b, var3);
   }

   public static enum Mode implements IStringSerializable {
      private static final String __OBFID = "CL_00002128";
      SUBTRACT("SUBTRACT", 1, "subtract");

      private final String field_177041_c;
      private static final BlockRedstoneComparator.Mode[] ENUM$VALUES = new BlockRedstoneComparator.Mode[]{COMPARE, SUBTRACT};
      private static final BlockRedstoneComparator.Mode[] $VALUES = new BlockRedstoneComparator.Mode[]{COMPARE, SUBTRACT};
      COMPARE("COMPARE", 0, "compare");

      public String getName() {
         return this.field_177041_c;
      }

      private Mode(String var3, int var4, String var5) {
         this.field_177041_c = var5;
      }

      public String toString() {
         return this.field_177041_c;
      }
   }
}
