/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntityFishHook;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFishingRod extends Item {
/*    */   public ItemFishingRod() {
/* 13 */     setMaxDamage(64);
/* 14 */     setMaxStackSize(1);
/* 15 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRotateAroundWhenRendering() {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 40 */     if (playerIn.fishEntity != null) {
/*    */       
/* 42 */       int i = playerIn.fishEntity.handleHookRetraction();
/* 43 */       itemStackIn.damageItem(i, (EntityLivingBase)playerIn);
/* 44 */       playerIn.swingItem();
/*    */     }
/*    */     else {
/*    */       
/* 48 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */       
/* 50 */       if (!worldIn.isRemote)
/*    */       {
/* 52 */         worldIn.spawnEntityInWorld((Entity)new EntityFishHook(worldIn, playerIn));
/*    */       }
/*    */       
/* 55 */       playerIn.swingItem();
/* 56 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */     } 
/*    */     
/* 59 */     return itemStackIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemTool(ItemStack stack) {
/* 67 */     return super.isItemTool(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemEnchantability() {
/* 75 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemFishingRod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */