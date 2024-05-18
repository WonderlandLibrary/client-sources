/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityEnderPearl;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemEnderPearl extends Item {
/*    */   public ItemEnderPearl() {
/* 13 */     this.maxStackSize = 16;
/* 14 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 22 */     if (playerIn.capabilities.isCreativeMode)
/*    */     {
/* 24 */       return itemStackIn;
/*    */     }
/*    */ 
/*    */     
/* 28 */     itemStackIn.stackSize--;
/* 29 */     worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 31 */     if (!worldIn.isRemote)
/*    */     {
/* 33 */       worldIn.spawnEntityInWorld((Entity)new EntityEnderPearl(worldIn, (EntityLivingBase)playerIn));
/*    */     }
/*    */     
/* 36 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 37 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */