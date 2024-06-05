package net.minecraft.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockJukebox$TileEntityJukebox extends TileEntity {
   private ItemStack record;

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      if(var1.hasKey("RecordItem", 10)) {
         this.setRecord(ItemStack.loadItemStackFromNBT(var1.getCompoundTag("RecordItem")));
      } else if(var1.getInteger("Record") > 0) {
         this.setRecord(new ItemStack(Item.getItemById(var1.getInteger("Record")), 1, 0));
      }

   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      if(this.getRecord() != null) {
         var1.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
      }

   }

   public ItemStack getRecord() {
      return this.record;
   }

   public void setRecord(ItemStack var1) {
      this.record = var1;
      this.markDirty();
   }
}
