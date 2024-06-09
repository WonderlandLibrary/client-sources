/*    */ package me.eagler.module.modules.player;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.utils.PlayerUtils;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class TraitorCheck
/*    */   extends Module {
/*    */   public TraitorCheck() {
/* 14 */     super("TraitorCheck", Category.Player);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 19 */     for (Entity e : this.mc.theWorld.loadedEntityList) {
/*    */       
/* 21 */       if (e instanceof EntityPlayer) {
/*    */         
/* 23 */         EntityPlayer ep = (EntityPlayer)e;
/*    */         
/* 25 */         for (int i = 0; i < ep.inventoryContainer.inventorySlots.size(); i++) {
/*    */           
/* 27 */           if (ep.inventoryContainer.getSlot(i) != null) {
/*    */             
/* 29 */             ep.setTraitor(false);
/*    */             
/* 31 */             if (ep.inventoryContainer.getSlot(i).getHasStack()) {
/*    */               
/* 33 */               ItemStack stack = ep.inventoryContainer.getSlot(i).getStack();
/*    */               
/* 35 */               if (stack.getItem() == Items.spawn_egg) {
/*    */                 
/* 37 */                 ep.setTraitor(true);
/* 38 */                 PlayerUtils.sendMessage(ep.getName(), true);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\TraitorCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */