/*  1:   */ package net.minecraft.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ import net.minecraft.nbt.NBTTagList;
/*  7:   */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  8:   */ 
/*  9:   */ public class InventoryEnderChest
/* 10:   */   extends InventoryBasic
/* 11:   */ {
/* 12:   */   private TileEntityEnderChest associatedChest;
/* 13:   */   private static final String __OBFID = "CL_00001759";
/* 14:   */   
/* 15:   */   public InventoryEnderChest()
/* 16:   */   {
/* 17:16 */     super("container.enderchest", false, 27);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void func_146031_a(TileEntityEnderChest p_146031_1_)
/* 21:   */   {
/* 22:21 */     this.associatedChest = p_146031_1_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void loadInventoryFromNBT(NBTTagList par1NBTTagList)
/* 26:   */   {
/* 27:28 */     for (int var2 = 0; var2 < getSizeInventory(); var2++) {
/* 28:30 */       setInventorySlotContents(var2, null);
/* 29:   */     }
/* 30:33 */     for (var2 = 0; var2 < par1NBTTagList.tagCount(); var2++)
/* 31:   */     {
/* 32:35 */       NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
/* 33:36 */       int var4 = var3.getByte("Slot") & 0xFF;
/* 34:38 */       if ((var4 >= 0) && (var4 < getSizeInventory())) {
/* 35:40 */         setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
/* 36:   */       }
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public NBTTagList saveInventoryToNBT()
/* 41:   */   {
/* 42:47 */     NBTTagList var1 = new NBTTagList();
/* 43:49 */     for (int var2 = 0; var2 < getSizeInventory(); var2++)
/* 44:   */     {
/* 45:51 */       ItemStack var3 = getStackInSlot(var2);
/* 46:53 */       if (var3 != null)
/* 47:   */       {
/* 48:55 */         NBTTagCompound var4 = new NBTTagCompound();
/* 49:56 */         var4.setByte("Slot", (byte)var2);
/* 50:57 */         var3.writeToNBT(var4);
/* 51:58 */         var1.appendTag(var4);
/* 52:   */       }
/* 53:   */     }
/* 54:62 */     return var1;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 58:   */   {
/* 59:70 */     return (this.associatedChest != null) && (!this.associatedChest.func_145971_a(par1EntityPlayer)) ? false : super.isUseableByPlayer(par1EntityPlayer);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void openInventory()
/* 63:   */   {
/* 64:75 */     if (this.associatedChest != null) {
/* 65:77 */       this.associatedChest.func_145969_a();
/* 66:   */     }
/* 67:80 */     super.openInventory();
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void closeInventory()
/* 71:   */   {
/* 72:85 */     if (this.associatedChest != null) {
/* 73:87 */       this.associatedChest.func_145970_b();
/* 74:   */     }
/* 75:90 */     super.closeInventory();
/* 76:91 */     this.associatedChest = null;
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryEnderChest
 * JD-Core Version:    0.7.0.1
 */