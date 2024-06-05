package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInteractionObject;

public class TileEntityEnchantmentTable extends TileEntity implements IInteractionObject, IUpdatePlayerListBox {
   public int tickCount;
   public float bookRotation;
   public float field_145929_l;
   private String field_145922_s;
   public float field_145932_k;
   public float bookRotationPrev;
   public float bookSpreadPrev;
   public float pageFlip;
   public float field_145924_q;
   public float bookSpread;
   public float pageFlipPrev;
   private static Random field_145923_r = new Random();
   private static final String __OBFID = "CL_00000354";

   public String getName() {
      return this.hasCustomName() ? this.field_145922_s : "container.enchant";
   }

   public void func_145920_a(String var1) {
      this.field_145922_s = var1;
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      if (var1.hasKey("CustomName", 8)) {
         this.field_145922_s = var1.getString("CustomName");
      }

   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerEnchantment(var1, this.worldObj, this.pos);
   }

   public String getGuiID() {
      return "minecraft:enchanting_table";
   }

   public boolean hasCustomName() {
      return this.field_145922_s != null && this.field_145922_s.length() > 0;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      if (this.hasCustomName()) {
         var1.setString("CustomName", this.field_145922_s);
      }

   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public void update() {
      this.bookSpreadPrev = this.bookSpread;
      this.bookRotationPrev = this.bookRotation;
      EntityPlayer var1 = this.worldObj.getClosestPlayer((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 0.5F), (double)((float)this.pos.getZ() + 0.5F), 3.0D);
      if (var1 != null) {
         double var2 = var1.posX - (double)((float)this.pos.getX() + 0.5F);
         double var4 = var1.posZ - (double)((float)this.pos.getZ() + 0.5F);
         this.field_145924_q = (float)Math.atan2(var4, var2);
         this.bookSpread += 0.1F;
         if (this.bookSpread < 0.5F || field_145923_r.nextInt(40) == 0) {
            float var6 = this.field_145932_k;

            do {
               this.field_145932_k += (float)(field_145923_r.nextInt(4) - field_145923_r.nextInt(4));
            } while(var6 == this.field_145932_k);
         }
      } else {
         this.field_145924_q += 0.02F;
         this.bookSpread -= 0.1F;
      }

      while(this.bookRotation >= 3.1415927F) {
         this.bookRotation -= 6.2831855F;
      }

      while(this.bookRotation < -3.1415927F) {
         this.bookRotation += 6.2831855F;
      }

      while(this.field_145924_q >= 3.1415927F) {
         this.field_145924_q -= 6.2831855F;
      }

      while(this.field_145924_q < -3.1415927F) {
         this.field_145924_q += 6.2831855F;
      }

      float var7;
      for(var7 = this.field_145924_q - this.bookRotation; var7 >= 3.1415927F; var7 -= 6.2831855F) {
      }

      while(var7 < -3.1415927F) {
         var7 += 6.2831855F;
      }

      this.bookRotation += var7 * 0.4F;
      this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0F, 1.0F);
      ++this.tickCount;
      this.pageFlipPrev = this.pageFlip;
      float var3 = (this.field_145932_k - this.pageFlip) * 0.4F;
      float var8 = 0.2F;
      var3 = MathHelper.clamp_float(var3, -var8, var8);
      this.field_145929_l += (var3 - this.field_145929_l) * 0.9F;
      this.pageFlip += this.field_145929_l;
   }
}
