/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ItemNameTag
/*    */   extends Item
/*    */ {
/*    */   public ItemNameTag() {
/* 12 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 20 */     if (!stack.hasDisplayName())
/*    */     {
/* 22 */       return false;
/*    */     }
/* 24 */     if (target instanceof EntityLiving) {
/*    */       
/* 26 */       EntityLiving entityliving = (EntityLiving)target;
/* 27 */       entityliving.setCustomNameTag(stack.getDisplayName());
/* 28 */       entityliving.enablePersistence();
/* 29 */       stack.stackSize--;
/* 30 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return super.itemInteractionForEntity(stack, playerIn, target);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemNameTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */