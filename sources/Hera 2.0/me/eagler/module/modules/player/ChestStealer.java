/*    */ package me.eagler.module.modules.player;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ import me.eagler.utils.TimeHelper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ 
/*    */ public class ChestStealer
/*    */   extends Module {
/* 12 */   public TimeHelper time = new TimeHelper();
/*    */   
/*    */   public ChestStealer() {
/* 15 */     super("ChestStealer", Category.Player);
/*    */     
/* 17 */     this.settingManager.addSetting(new Setting("CSDelay", this, 200.0D, 1.0D, 500.0D, true));
/* 18 */     this.settingManager.addSetting(new Setting("Instant", this, false));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 23 */     long delay = (long)this.settingManager.getSettingByName("CSDelay").getValue();
/*    */     
/* 25 */     boolean instant = this.settingManager.getSettingByName("Instant").getBoolean();
/*    */     
/* 27 */     setExtraTag(delay);
/*    */     
/* 29 */     if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
/*    */       
/* 31 */       ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
/*    */       
/* 33 */       if (!isChestEmpty(chest)) {
/*    */         
/* 35 */         for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++)
/*    */         {
/* 37 */           if (chest.getLowerChestInventory().getStackInSlot(i) != null)
/*    */           {
/* 39 */             if (!instant)
/*    */             {
/* 41 */               if (this.time.hasReached(delay))
/*    */               {
/* 43 */                 this.mc.playerController.windowClick(chest.windowId, i, 0, 1, (EntityPlayer)this.mc.thePlayer);
/*    */                 
/* 45 */                 this.time.reset();
/*    */               }
/*    */             
/*    */             }
/*    */             else
/*    */             {
/* 51 */               this.mc.playerController.windowClick(chest.windowId, i, 0, 1, (EntityPlayer)this.mc.thePlayer);
/*    */             
/*    */             }
/*    */           
/*    */           }
/*    */         }
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 61 */         this.mc.thePlayer.closeScreen();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isChestEmpty(ContainerChest chest) {
/* 71 */     for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
/*    */       
/* 73 */       if (chest.getLowerChestInventory().getStackInSlot(i) != null)
/*    */       {
/* 75 */         return false;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 81 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\ChestStealer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */