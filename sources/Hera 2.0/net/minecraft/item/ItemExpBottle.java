/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityExpBottle;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemExpBottle extends Item {
/*    */   public ItemExpBottle() {
/* 13 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 26 */     if (!playerIn.capabilities.isCreativeMode)
/*    */     {
/* 28 */       itemStackIn.stackSize--;
/*    */     }
/*    */     
/* 31 */     worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 33 */     if (!worldIn.isRemote)
/*    */     {
/* 35 */       worldIn.spawnEntityInWorld((Entity)new EntityExpBottle(worldIn, (EntityLivingBase)playerIn));
/*    */     }
/*    */     
/* 38 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 39 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */