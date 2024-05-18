/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFirework
/*    */   extends Item
/*    */ {
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 21 */     if (!worldIn.isRemote) {
/*    */       
/* 23 */       EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (pos.getX() + hitX), (pos.getY() + hitY), (pos.getZ() + hitZ), stack);
/* 24 */       worldIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */       
/* 26 */       if (!playerIn.capabilities.isCreativeMode)
/*    */       {
/* 28 */         stack.stackSize--;
/*    */       }
/*    */       
/* 31 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 44 */     if (stack.hasTagCompound()) {
/*    */       
/* 46 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Fireworks");
/*    */       
/* 48 */       if (nbttagcompound != null) {
/*    */         
/* 50 */         if (nbttagcompound.hasKey("Flight", 99))
/*    */         {
/* 52 */           tooltip.add(String.valueOf(StatCollector.translateToLocal("item.fireworks.flight")) + " " + nbttagcompound.getByte("Flight"));
/*    */         }
/*    */         
/* 55 */         NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);
/*    */         
/* 57 */         if (nbttaglist != null && nbttaglist.tagCount() > 0)
/*    */         {
/* 59 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */             
/* 61 */             NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 62 */             List<String> list = Lists.newArrayList();
/* 63 */             ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);
/*    */             
/* 65 */             if (list.size() > 0) {
/*    */               
/* 67 */               for (int j = 1; j < list.size(); j++)
/*    */               {
/* 69 */                 list.set(j, "  " + (String)list.get(j));
/*    */               }
/*    */               
/* 72 */               tooltip.addAll(list);
/*    */             } 
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemFirework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */