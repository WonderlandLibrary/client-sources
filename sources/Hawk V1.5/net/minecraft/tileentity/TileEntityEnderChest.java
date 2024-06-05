package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityEnderChest extends TileEntity implements IUpdatePlayerListBox {
   public int field_145973_j;
   private static final String __OBFID = "CL_00000355";
   public float field_145972_a;
   private int field_145974_k;
   public float prevLidAngle;

   public boolean func_145971_a(EntityPlayer var1) {
      return this.worldObj.getTileEntity(this.pos) != this ? false : var1.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
   }

   public boolean receiveClientEvent(int var1, int var2) {
      if (var1 == 1) {
         this.field_145973_j = var2;
         return true;
      } else {
         return super.receiveClientEvent(var1, var2);
      }
   }

   public void update() {
      if (++this.field_145974_k % 20 * 4 == 0) {
         this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
      }

      this.prevLidAngle = this.field_145972_a;
      int var1 = this.pos.getX();
      int var2 = this.pos.getY();
      int var3 = this.pos.getZ();
      float var4 = 0.1F;
      double var5;
      if (this.field_145973_j > 0 && this.field_145972_a == 0.0F) {
         double var7 = (double)var1 + 0.5D;
         var5 = (double)var3 + 0.5D;
         this.worldObj.playSoundEffect(var7, (double)var2 + 0.5D, var5, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (this.field_145973_j == 0 && this.field_145972_a > 0.0F || this.field_145973_j > 0 && this.field_145972_a < 1.0F) {
         float var11 = this.field_145972_a;
         if (this.field_145973_j > 0) {
            this.field_145972_a += var4;
         } else {
            this.field_145972_a -= var4;
         }

         if (this.field_145972_a > 1.0F) {
            this.field_145972_a = 1.0F;
         }

         float var8 = 0.5F;
         if (this.field_145972_a < var8 && var11 >= var8) {
            var5 = (double)var1 + 0.5D;
            double var9 = (double)var3 + 0.5D;
            this.worldObj.playSoundEffect(var5, (double)var2 + 0.5D, var9, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
         }

         if (this.field_145972_a < 0.0F) {
            this.field_145972_a = 0.0F;
         }
      }

   }

   public void invalidate() {
      this.updateContainingBlockInfo();
      super.invalidate();
   }

   public void func_145970_b() {
      --this.field_145973_j;
      this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
   }

   public void func_145969_a() {
      ++this.field_145973_j;
      this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
   }
}
