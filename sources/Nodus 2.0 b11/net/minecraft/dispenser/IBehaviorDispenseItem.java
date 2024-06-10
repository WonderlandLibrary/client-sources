/* 1:  */ package net.minecraft.dispenser;
/* 2:  */ 
/* 3:  */ import net.minecraft.item.ItemStack;
/* 4:  */ 
/* 5:  */ public abstract interface IBehaviorDispenseItem
/* 6:  */ {
/* 7:7 */   public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem()
/* 8:  */   {
/* 9:  */     private static final String __OBFID = "CL_00001200";
/* ::  */     
/* ;:  */     public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* <:  */     {
/* =:< */       return par2ItemStack;
/* >:  */     }
/* ?:  */   };
/* @:  */   
/* A:  */   public abstract ItemStack dispense(IBlockSource paramIBlockSource, ItemStack paramItemStack);
/* B:  */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.dispenser.IBehaviorDispenseItem
 * JD-Core Version:    0.7.0.1
 */