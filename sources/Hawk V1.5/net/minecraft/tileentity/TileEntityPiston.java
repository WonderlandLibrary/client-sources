package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox {
   private List field_174933_k = Lists.newArrayList();
   private static final String __OBFID = "CL_00000369";
   private boolean extending;
   private float progress;
   private EnumFacing field_174931_f;
   private float lastProgress;
   private IBlockState field_174932_a;
   private boolean shouldHeadBeRendered;

   public boolean shouldPistonHeadBeRendered() {
      return this.shouldHeadBeRendered;
   }

   public float func_145860_a(float var1) {
      if (var1 > 1.0F) {
         var1 = 1.0F;
      }

      return this.lastProgress + (this.progress - this.lastProgress) * var1;
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.field_174932_a = Block.getBlockById(var1.getInteger("blockId")).getStateFromMeta(var1.getInteger("blockData"));
      this.field_174931_f = EnumFacing.getFront(var1.getInteger("facing"));
      this.lastProgress = this.progress = var1.getFloat("progress");
      this.extending = var1.getBoolean("extending");
   }

   public boolean isExtending() {
      return this.extending;
   }

   public IBlockState func_174927_b() {
      return this.field_174932_a;
   }

   public float func_174926_d(float var1) {
      return this.extending ? (this.func_145860_a(var1) - 1.0F) * (float)this.field_174931_f.getFrontOffsetZ() : (1.0F - this.func_145860_a(var1)) * (float)this.field_174931_f.getFrontOffsetZ();
   }

   public TileEntityPiston(IBlockState var1, EnumFacing var2, boolean var3, boolean var4) {
      this.field_174932_a = var1;
      this.field_174931_f = var2;
      this.extending = var3;
      this.shouldHeadBeRendered = var4;
   }

   public EnumFacing func_174930_e() {
      return this.field_174931_f;
   }

   public void update() {
      this.lastProgress = this.progress;
      if (this.lastProgress >= 1.0F) {
         this.func_145863_a(1.0F, 0.25F);
         this.worldObj.removeTileEntity(this.pos);
         this.invalidate();
         if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
            this.worldObj.setBlockState(this.pos, this.field_174932_a, 3);
            this.worldObj.notifyBlockOfStateChange(this.pos, this.field_174932_a.getBlock());
         }
      } else {
         this.progress += 0.5F;
         if (this.progress >= 1.0F) {
            this.progress = 1.0F;
         }

         if (this.extending) {
            this.func_145863_a(this.progress, this.progress - this.lastProgress + 0.0625F);
         }
      }

   }

   public TileEntityPiston() {
   }

   public float func_174929_b(float var1) {
      return this.extending ? (this.func_145860_a(var1) - 1.0F) * (float)this.field_174931_f.getFrontOffsetX() : (1.0F - this.func_145860_a(var1)) * (float)this.field_174931_f.getFrontOffsetX();
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setInteger("blockId", Block.getIdFromBlock(this.field_174932_a.getBlock()));
      var1.setInteger("blockData", this.field_174932_a.getBlock().getMetaFromState(this.field_174932_a));
      var1.setInteger("facing", this.field_174931_f.getIndex());
      var1.setFloat("progress", this.lastProgress);
      var1.setBoolean("extending", this.extending);
   }

   public int getBlockMetadata() {
      return 0;
   }

   public void clearPistonTileEntity() {
      if (this.lastProgress < 1.0F && this.worldObj != null) {
         this.lastProgress = this.progress = 1.0F;
         this.worldObj.removeTileEntity(this.pos);
         this.invalidate();
         if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
            this.worldObj.setBlockState(this.pos, this.field_174932_a, 3);
            this.worldObj.notifyBlockOfStateChange(this.pos, this.field_174932_a.getBlock());
         }
      }

   }

   public float func_174928_c(float var1) {
      return this.extending ? (this.func_145860_a(var1) - 1.0F) * (float)this.field_174931_f.getFrontOffsetY() : (1.0F - this.func_145860_a(var1)) * (float)this.field_174931_f.getFrontOffsetY();
   }

   private void func_145863_a(float var1, float var2) {
      if (this.extending) {
         var1 = 1.0F - var1;
      } else {
         --var1;
      }

      AxisAlignedBB var3 = Blocks.piston_extension.func_176424_a(this.worldObj, this.pos, this.field_174932_a, var1, this.field_174931_f);
      if (var3 != null) {
         List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, var3);
         if (!var4.isEmpty()) {
            this.field_174933_k.addAll(var4);
            Iterator var5 = this.field_174933_k.iterator();

            while(true) {
               while(var5.hasNext()) {
                  Entity var6 = (Entity)var5.next();
                  if (this.field_174932_a.getBlock() == Blocks.slime_block && this.extending) {
                     switch(this.field_174931_f.getAxis()) {
                     case X:
                        var6.motionX = (double)this.field_174931_f.getFrontOffsetX();
                        break;
                     case Y:
                        var6.motionY = (double)this.field_174931_f.getFrontOffsetY();
                        break;
                     case Z:
                        var6.motionZ = (double)this.field_174931_f.getFrontOffsetZ();
                     }
                  } else {
                     var6.moveEntity((double)(var2 * (float)this.field_174931_f.getFrontOffsetX()), (double)(var2 * (float)this.field_174931_f.getFrontOffsetY()), (double)(var2 * (float)this.field_174931_f.getFrontOffsetZ()));
                  }
               }

               this.field_174933_k.clear();
               break;
            }
         }
      }

   }

   static final class SwitchAxis {
      static final int[] field_177248_a = new int[EnumFacing.Axis.values().length];
      private static final String __OBFID = "CL_00002034";

      static {
         try {
            field_177248_a[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177248_a[EnumFacing.Axis.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177248_a[EnumFacing.Axis.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
